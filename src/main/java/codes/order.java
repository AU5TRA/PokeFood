package codes;

import javafx.beans.binding.DoubleExpression;

import java.io.Serializable;

public class order implements Serializable {
    private food Food;
    private restaurant res;
    private String resName;
    private int qty;
    public order(food F, int q, String name)
    {
        Food= F;
        qty= q;
        resName= name;
    }

    public restaurant getRes() {
        return res;
    }

    public void setRes(restaurant res) {
        this.res = res;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }
    public String getResName(){
        return resName;
    }

    public food getFood()
    {
        return Food;
    }
    public int getQty()
    {
        return qty;
    }
    public void setFood(food F)
    {
        this.Food= F;
    }
    public void setQty(int q)
    {
        qty=q;
    }
    public String getName()
    {
        return Food.getName();
    }
    public double getPrice()
    {
        return Food.getPrice();
    }

}
