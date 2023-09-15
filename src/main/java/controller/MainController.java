package controller;
//controls the homepage

import com.example.fooddelivery.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import util.*;

import java.io.IOException;

public class MainController {
    private final String[] foodTypes = {"Name", "Category", "Price Range"};
    public Button SearchRes;
    public Button AllRes;
    public ChoiceBox<String> foodChoice;
    public Label SearchFood;
    ;
    public Button buttonCart;
    private Main main;


    @FXML
    private Label message;
    @FXML
    private Label userName;
    @FXML
    private ImageView image;
    @FXML
    private Button chicken;
    @FXML
    private Button button;

    public void init(String name, Main main) {
        this.main = main;
        main.setUsername(name);
        userName.setText("Hello " + name + " !");

    }

    @FXML
    public void viewCartPressed(ActionEvent actionEvent) {
    }

    @FXML
    void logout(ActionEvent event) {
        System.out.println("logging out");
        var lDTO = new LogoutDTO(main.getUsername());
        lDTO.setStatus(true);
        try {
            main.getNetworkUtil().write(lDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showAllRes(ActionEvent actionEvent) {

        managerDTO ARDTO= new managerDTO();
        try{
            main.getNetworkUtil().write(ARDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showAllFood(ActionEvent event) {

        foodDTO fDTO= new foodDTO();
        try{
            main.getNetworkUtil().write(fDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void findChicken(ActionEvent event) {
        foodDTO fDTO= new foodDTO();
        fDTO.setCategory("chicken");
        try{
            main.getNetworkUtil().write(fDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void findBev(ActionEvent event) {
        foodDTO fDTO= new foodDTO();
        fDTO.setCategory("beverage");
        try{
            main.getNetworkUtil().write(fDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void findFast(ActionEvent event) {
        foodDTO fDTO= new foodDTO();
        fDTO.setCategory("fastfood");
        try{
            main.getNetworkUtil().write(fDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void goKFC(ActionEvent event) {
        RestaurantDTO resDTO= new RestaurantDTO();
        resDTO.setResName("KFC");

        try {
            main.getNetworkUtil().write(resDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goMC(ActionEvent event) {
        RestaurantDTO resDTO= new RestaurantDTO();
        resDTO.setResName("McDonalds");

        try {
            main.getNetworkUtil().write(resDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goStarbucks(ActionEvent event) {
        RestaurantDTO resDTO= new RestaurantDTO();
        resDTO.setResName("Starbucks");

        try {
            main.getNetworkUtil().write(resDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}