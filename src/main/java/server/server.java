package server;

import util.*;
import codes.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.concurrent.ConcurrentHashMap;

public class server {

    private restaurantManager RManager;
     ServerSocket serverSocket;

    private ConcurrentHashMap<String,SocketWrapper> NetworkMap;
    private volatile int clientCount;

    private volatile int updateCount;
    public HashMap<String, SocketWrapper> clientMap;
    private ConcurrentHashMap<String,String> passwordList;
    private ConcurrentHashMap<String,String> resPasswordList;

    public ConcurrentHashMap<String, SocketWrapper> getCompanyNetworkMap() {
        return NetworkMap;
    }

    public restaurantManager getResDataBase() { return RManager; }
    public ConcurrentHashMap<String, String> getPasswordList() { return passwordList; }

    public ConcurrentHashMap<String, String> getResPasswordList() { return resPasswordList; }
    public static ConcurrentHashMap<String, List<orderList>> foodAgainstRes= new ConcurrentHashMap<>();

    public List<OLIST> FOOD_QTY= new ArrayList<>();
    public void edit_rDB(restaurantManager r)
    {
//      RManager=r;
        var list= r.getResList();
        RManager= new restaurantManager(list);


    }

    public static ConcurrentHashMap<String, List<orderList>> getFoodAgainstRes() {
        return foodAgainstRes;
    }

    public static void setFoodAgainstRes(String resName,orderList orders) {
        //display();
        if(getFoodAgainstRes().get(resName)==null)
        {
            foodAgainstRes.put(resName, new ArrayList<>());
        }
        /*for(order o: orders.getListOfFood()){
            System.out.println(o.getFood().getName());
        }*/
        getFoodAgainstRes().get(resName).add(orders);
        //display();
    }

    ////
    public static void display(){
        System.out.println("Server.display(): ");
        for(String resName: foodAgainstRes.keySet()){
            //List<orderList> orders= new ArrayList<>();
            System.out.println("here "+resName);
            for(orderList o: foodAgainstRes.get(resName)){
                System.out.println("Res Name: "+o.getRes());
                System.out.println("User Name: "+o.getUsername());
                for(order f: o.getListOfFood()){
                    System.out.println(f.getFood().getName());
                }
            }
        }
    }

    public List<OLIST> getFOOD_QTY() {
        return FOOD_QTY;
    }

    /* public int getClientCount() {
        return clientCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }*/

    public synchronized void incrementClientCount(){
        clientCount++;
    }
    public synchronized void decrementClientCount(){
        clientCount--;
    }
    public void loadFQTY() //all orders are initialized to zero
    {
       // System.out.printf("loading the order list of all restaurants...");
        for(var temp: RManager.getResList())
        {
            for(var f: temp.getFoodList())
            {
                FOOD_QTY.add(new OLIST(f, 0));
            }
        }
    }

    public void loadFQTY(List<order> orders) {
        for (var temp : orders) {
            int orderQty = temp.getQty();
            food orderFood = temp.getFood();

            for (var entry : FOOD_QTY) {
                if (entry.getF().getName().equals(orderFood.getName())) {
                    int currentQty = entry.getQty();
                    int updatedQty = currentQty + orderQty;
                    entry.setQty(updatedQty);
                    break;
                }
            }
        }
    }

    server() throws IOException {
        clientCount = 0;
        updateCount = 0;
        List<restaurant> extractedRes = FileOperations.readResFromFile();
        RManager= new restaurantManager(extractedRes);
        System.out.println("Restaurant and food List are updated...");
        loadFQTY();

        passwordList = FileOperations.readPasswordsFromFile();
        resPasswordList= FileOperations.getResPasswordList();


        System.out.println("Credentials loaded...");
        NetworkMap = new ConcurrentHashMap<>();
        new InputThreadServer(this);
        try {
            serverSocket = new ServerSocket(33333);
            System.out.println("Server is waiting...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server accepts a client...");
                var socketW= new SocketWrapper(clientSocket);
                // serve(clientSocket);
                new ReadThreadServer(clientSocket, this, socketW);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }


    public static void main(String args[]) throws IOException{
        new server();
    }
}
