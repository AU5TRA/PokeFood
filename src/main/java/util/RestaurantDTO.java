package util;

import codes.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class RestaurantDTO implements Serializable{
    private restaurant RES;
    private String resName;

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public restaurant getRes()
    {
        return RES;
    }
    public void setRes(restaurant res)
     {
         this.RES= res;
     }



}
