package codes;

import javafx.fxml.Initializable;

import java.io.Serializable;
import java.util.List;

public class orderList implements Serializable {
    private String username;
    private List<order> listOfFood;
    private String res;
    public orderList(String resName,List <order> foods, String username)
    {
        this.res= resName;
        this.listOfFood= foods;
        this.username= username;
    }



    public void setResString(String res) {
        this.res = res;
    }

    public String getUsername() {
        return username;
    }

    public List<order> getListOfFood() {
        return listOfFood;
    }

    public String getRes() {
        return res;
    }

    public void setListOfFood(List<order> listOfFood) {
        this.listOfFood = listOfFood;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
