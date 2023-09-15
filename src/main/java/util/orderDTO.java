package util;

import codes.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class orderDTO implements Serializable {
    private orderList orders;
    //private String username;
    private List<food>menu= new ArrayList<>();
    private List<OLIST> FOOD_QTY_List= new ArrayList<>();////////////////////////

    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setOrders(orderList orders) {
        this.orders = orders;
    }
    public orderList getOrders()
    {
        return orders;
    }


    public void setMenu(List<food> menu) {
        this.menu = menu;
    }
    public List<food> getMenu()
    {
        return menu;
    }
    //private ConcurrentHashMap<food,Integer> FOOD_QTY_List= new ConcurrentHashMap<>();

   /* public orderDTO(){
        FOOD_QTY_List= new ArrayList<>();
    }*/
    public List<OLIST>  getFOOD_QTY_List() {
        return this.FOOD_QTY_List;
    }

    public void setFOOD_QTY_List(List<OLIST> FOOD_QTY_List) {
        this.FOOD_QTY_List = FOOD_QTY_List;
    }
    /*public void setUsername(String name)
    {
        username= name;
    }

    public String getUsername() {
        return username;
    }*/
}
