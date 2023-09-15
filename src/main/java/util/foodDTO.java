package util;
import codes.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class foodDTO implements Serializable{

    private List<restaurant> RES;
    private List<food> foodList= new ArrayList<>();
    private String Category;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public List<restaurant> getRes()
    {
        return RES;
    }
    public void setRes(List<restaurant> RES)
    {
        this.RES= RES;
        for(var temp: RES)
        {
            foodList.addAll(temp.getFoodList());
        }
    }

    public List<food> getFoodList() {
        return foodList;
    }
}
