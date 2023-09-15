package server;
// login  done?
import codes.*;
import controller.resOwnerMain;
import javafx.application.Platform;
import util.*;
import controller.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReadThreadServer implements Runnable {
    SocketWrapper SW;
    server Server;
    Socket clientSocket;

    Thread t;
    private boolean threadOn = true;
    restaurantManager RDatabase;
    ConcurrentHashMap<String,String> passwordList;
    ConcurrentHashMap<String,String> resPasswordList;
    ConcurrentHashMap<String,SocketWrapper> NetworkMap;

    public String getNameFromSkt(SocketWrapper sw)
    {
        String key= null;
        for(Map.Entry<String, SocketWrapper> entry : NetworkMap.entrySet())
        {
            key = entry.getKey();
            if(SW== entry.getValue())
            {
                return entry.getKey();
            }
        }
        return key;
    }

    public SocketWrapper getSocketWrapperByName(String name) {
        for (Map.Entry<String, SocketWrapper> entry : NetworkMap.entrySet()) {
            if (entry.getKey().equals(name)) {
                return entry.getValue();
            }
        }
        // If the name is not found, return null or throw an exception, depending on your use case
        return null;
    }

    ReadThreadServer(Socket clientSocket, server Server, SocketWrapper SW) {

        this.clientSocket = clientSocket;
        this.Server = Server;
        this.SW = SW;
        this.RDatabase = Server.getResDataBase();
        this.passwordList = Server.getPasswordList();
        this.resPasswordList = Server.getResPasswordList();
        this.NetworkMap = Server.getCompanyNetworkMap();

        t = new Thread(this);
        t.start();
        //loadFQTY();

    }

    public void refreshDB()
    {
        this.RDatabase = Server.getResDataBase();
    }

    public void run() {

        while (threadOn) {
            Object next = null;
            while (threadOn) {
                try {
                    next = SW.read();
                    break;
                } catch (IOException | ClassNotFoundException ignored) {

                }
            }
            refreshDB();

            if (next instanceof LoginDTO) {
                refreshDB();
                LoginDTO loginDTO = (LoginDTO) next;
                String name = loginDTO.getUserName();
                String passGiven= loginDTO.getPassword();
                int userType= loginDTO.getUserType();
                boolean isAuth;
                String pass= "";
                if(userType==1)
                    pass= passwordList.get(name);
                else if(userType==2)
                    pass= resPasswordList.get(name);

                if(!pass.equals(passGiven))
                {
                    System.out.println("wrong credentials");
                    isAuth= false;
                }
                else{
                    //isAuth = loginDTO.getPassword().equals(pass);
                    isAuth= true;
                    refreshDB();

                    for(var temp: RDatabase.getResList())
                    {
                        if(temp.getName().equals(name))
                        {
                            loginDTO.setfList(temp.getFoodList());
                            loginDTO.setOrderList(server.getFoodAgainstRes().get(temp.getName()));
                        }
                    }
                }
                loginDTO.setStatus(isAuth);

                try {
                    SW.write(loginDTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(isAuth)
                {
                    Server.incrementClientCount();
                    NetworkMap.put(name,SW);

                }
            }

            else if(next instanceof managerDTO ){

                System.out.println(getNameFromSkt(SW)+" is requesting the list of all restaurants...");
                refreshDB();//refreshing DB
                var R= RDatabase.getResList();
                var NotSureYet= new managerDTO();
                NotSureYet.setRes(R);
                try {
                    SW.write(NotSureYet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            else if(next instanceof RestaurantDTO ){
                String R;
                refreshDB();
                var NotSureYet= new RestaurantDTO();
                if(((RestaurantDTO) next).getResName() == null)
                {
                     R= ((RestaurantDTO) next).getRes().getName();//R is the restaurant
                }
                else R=((RestaurantDTO) next).getResName();

                restaurant R1= new restaurant();
                for(var temp: RDatabase.getResList())
                {
                    if(temp.getName().equals(R))
                    {
                        R1= temp;
                    }
                }
                NotSureYet.setRes(R1);
                try {
                    SW.write(NotSureYet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            else if(next instanceof foodDTO)
            {
               // System.out.println(getNameFromSkt(SW)+" requesting all food list...");
                var R= RDatabase.getResList();
                var NotSureYet= new foodDTO();
                if(((foodDTO) next).getCategory()!= null)
                {
                    NotSureYet.setCategory(((foodDTO) next).getCategory());
                }
                NotSureYet.setRes(R);
                try{
                    SW.write(NotSureYet);
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            else if(next instanceof orderDTO ){
                System.out.println(getNameFromSkt(SW)+" is requesting an order...(RTS)");
                orderDTO NotSureYet= new orderDTO();
                var R= ((orderDTO) next).getOrders();
                server.setFoodAgainstRes(R.getRes(), R);
                var swRes= getSocketWrapperByName(R.getRes());//socket of res
                NotSureYet.setOrders(R);

                for(var t: Server.getResDataBase().getResList())
                {
                    if(t.getName().equals(R.getRes()))
                    {
                        NotSureYet.setMenu(t.getFoodList());
                        break;
                    }
                }

                try {
                    System.out.println("Order is dispatched...");
                    SW.write(NotSureYet);
                    if(swRes != null){
                        swRes.write(NotSureYet);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    if(swRes != null) {
                        swRes.write(server.getFoodAgainstRes().get(R.getRes()));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            else if(next instanceof LogoutDTO){
                Server.decrementClientCount();
                var lDTO = (LogoutDTO) next;
                System.out.println(lDTO.getName() + " logs out...");
                NetworkMap.remove(lDTO.getName());
            }
        }
    }
}