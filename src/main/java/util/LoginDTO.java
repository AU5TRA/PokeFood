package util;

import codes.*;

import java.util.ArrayList;
import java.util.List;


import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class LoginDTO implements Serializable {

    private String userName;
    private String password;
    private boolean status;
    private int userType; // 1 for customer, 2 for restaurant owner



    private List<food> fList= new ArrayList<>();

    private List<orderList> o= new ArrayList<>();
    public List<orderList> getorderList() {
        return o;
    }

    public void setOrderList(List<orderList> order) {
        this.o= order; 
    }

    public void setfList(List<food> fList) {
        this.fList = fList;
    }
    public void addFood(food f)
    {
        fList.add(f);
    }
    public List<food> getfList() { //only for userType 2 it will be accessed
        return fList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserType(int i) { userType=i; }
    public int getUserType(){ return userType; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
