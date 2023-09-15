package util;
import codes.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class managerDTO implements Serializable{
    private List<restaurant> RES;
    //private List<food> foodList;

    public List<restaurant> getRes()
    {
        return RES;
    }
    public void setRes(List<restaurant> RES)
    {
        this.RES= RES;
    }



}