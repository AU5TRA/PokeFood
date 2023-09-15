package com.example.fooddelivery;

import javafx.application.Platform;
import server.server;
import util.*;
import codes.*;


import java.io.IOException;
import java.util.List;

public class ReadThreadClient implements Runnable {
    private final Thread thr;
    private final Main main;

    public ReadThreadClient(Main main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }


    @Override
    public void run() {
        //System.out.println("inside run");
        try {
            while (true) {
                Object o = main.getNetworkUtil().read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (loginDTO.isStatus()) {
                                    System.out.println("Login Successful");
                                    try {
                                        if(loginDTO.getUserType()==1)
                                            main.showHomePage(loginDTO.getUserName(), loginDTO.getUserType());
                                        else if(loginDTO.getUserType()==2)
                                            main.showResOwnerMain(loginDTO.getfList(), loginDTO.getUserType(), loginDTO.getorderList());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    main.showAlert("Incorrect Credentials", "Incorrect Credentials", "Username and Password not Correct");
                                }
                            }
                        });
                    }

                    else if(o instanceof managerDTO ){
                        managerDTO mDTO = (managerDTO) o;
                       // System.out.println("all res received");
                        var resList= mDTO.getRes();
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                try {
                                    main.showResListPage(resList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }

                    else if(o instanceof RestaurantDTO){
                        RestaurantDTO rDTO = (RestaurantDTO) o;
                        //System.out.println("Certain res received");
                        var res= rDTO.getRes();
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                try {
                                    main.showResPage(res);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }



                    else if(o instanceof foodDTO){
                        foodDTO fDTO = (foodDTO) o;
                       // System.out.println("all foods received");
                        var foods= fDTO.getFoodList();
                        var Res= fDTO.getRes();
                        System.out.println("chicken in RTC "+ fDTO.getCategory());

                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                try {
                                    main.showFoodListPage(foods, Res, fDTO.getCategory());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }

                    else if(o instanceof orderDTO){
                        orderDTO oDTO = (orderDTO) o;
                        System.out.println("Receiving order...");

                        var order= oDTO.getOrders();
                        var FOODS= oDTO.getFOOD_QTY_List();
                        var menu= oDTO.getMenu();
                        //server.display();


                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                try {
                                    int i= main.getUserType();
                                    main.showOrdersPage(order, i, FOODS, menu);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    else if(o instanceof  List<?>){
                        List<?> oList=(List<?>)o;
                        if(!oList.isEmpty() && oList.get(0) instanceof orderList){
                            List<orderList>  myOrderList=(List<orderList>)o;


                            Platform.runLater(new Runnable(){
                                @Override
                                public void run(){
                                    System.out.println(main.getUsername());
                                    main.update(myOrderList);
                                }
                            });
                        }
                    }




                    if(o instanceof stopDTO){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                    main.showAlert("Sorry", "Sorry", "Server Stopped Abruptly...");
                                    main.destroyClient();

                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                main.getNetworkUtil().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

