package codes;

import java.io.Serializable;

public class OLIST implements Serializable {
    private food f;
    private int qty;
    private int id;
    private String name;
    private double price;
    private String category;

    public String getName() {
        name= f.getName();
        return name;
    }

    public double getPrice() {
        price= f.getPrice();
        return price;
    }

    public String getCategory()
    {
        category= f.getCategory();
        return category;
    }

    public OLIST(String category, String name, double price)
    {
        f= new food(id,  category,  name,  price);
        qty=0;
    }
    public OLIST(food f)
    {
        this.f= f;
        id= f.getId();
        qty=0;
    }
    public OLIST(food f, int q)
    {
        this.f= f;
        id= f.getId();
        qty=q;
    }



    public int getQty() {
        return qty;
    }

    public food getF() {
        return f;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setF(food f) {
        this.f = f;
    }
    public void addqty(int n)
    {
        qty= qty+n;
    }
}