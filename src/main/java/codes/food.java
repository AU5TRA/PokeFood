package codes;

import java.io.Serializable;

public class food implements Serializable {
    private int id; // res id
    private String category;
    private String name;
    private double price;

    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public food(int id, String category, String name, double price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
    }


    public food(String[] array) {
        int size = array.length;
        this.id = Integer.parseInt(array[0]);
        this.category = array[1];
        this.name = array[2];
        if (size != 4) {
            for (int i = 3; i < size - 1; i++) {
                this.name = this.name + ", " + array[i];
            }
        }

        this.price = Double.parseDouble(array[size - 1]);

    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String show() {
        return ("Restaurant Id: " + id + "\nFood Name: " + name + "\nFood Category: " + category + "\nFood Price: "
                + price + "\n");
    }

    public food retFood()
    {
        return new food(id, category, name, price);
    }
}