package controller;

import codes.*;;
import com.example.fooddelivery.Main;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.RestaurantDTO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FoodList implements Initializable{

    @FXML
    private TableView<food> AllFoodList;

    @FXML
    private TableColumn<food, String> Category;

    @FXML
    private TableColumn<food, String> FoodName;



    @FXML
    private TableColumn<food, Double> foodPrice;

    @FXML
    private ChoiceBox<String> foodChoice;



    @FXML
    private Button searchConfirm;

    @FXML
    private TextField searchText;

    @FXML
    private Button seeAllFood;

    private String[] choices= {"Name", "Categories", "Price($)"};
    private Main main;
    restaurantManager RM= new restaurantManager();

    List<food> AllFoods; //all foods here
    ObservableList<food> FList= FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foodChoice.setValue("Search by");
        foodChoice.getItems().addAll(choices);


    }

    @FXML
    void backHome(ActionEvent event) throws Exception {
    main.showHomePage(main.getUsername(), main.getUserType());
    }

    public void init(Main main, List<food> foodList, List<restaurant> Rlist, String s) throws Exception {
        this.main = main;
        System.out.println("chicken in foodlist init");
        AllFoods= foodList;
        FList.setAll(foodList);
        RM.setList(Rlist);
        if(s==null)
        {
            LoadFood(new ActionEvent());
        }
        else if(s.equals("chicken")){
            FList.setAll(RM.searchFoodName(s));
            if (FList.isEmpty()) {
                main.showAlert("No food Found", "No foods of this category is found", "");
            }

            LoadFood2(new ActionEvent());
        }
        else if(s.equals("fastfood")){
            FList.setAll(RM.searchFoodCategory("combo"));
            if (FList.isEmpty()) {
                main.showAlert("No foods Found", "No foods of this category is found", "");
            }

            LoadFood2(new ActionEvent());
        }
        else if(s.equals("beverage")){
            FList.setAll(RM.searchFoodCategory(s));
            if (FList.isEmpty()) {
                main.showAlert("No foods Found", "No foods of this category is found", "");
            }

            LoadFood2(new ActionEvent());
        }


    }


    @FXML
    void LoadFood(ActionEvent event) throws Exception {
        FList.setAll(AllFoods);
        LoadFood2(new ActionEvent());
    }

    @FXML
    public void LoadFood2(ActionEvent actionEvent) throws Exception {
        FoodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        Category.setCellValueFactory(new PropertyValueFactory<>("category"));
        AllFoodList.setItems(FList);



    }


    @FXML
    void Search(ActionEvent event) throws Exception {
        String searchBy = foodChoice.getValue();
        String name = searchText.getText();
        if (searchBy.equals("Name")) {
            FList.setAll(RM.searchFoodName(name));
            if (FList.isEmpty()) {
                main.showAlert("No food Found", "No foods of this name is found", "");
            }
            LoadFood2(event);
        }

        else if (searchBy.equals("Categories")) {
            FList.setAll(RM.searchFoodCategory(name));
            if (FList.isEmpty()) {
                main.showAlert("No food Found", "No foods of this category is found", "");
            }

            LoadFood2(event);
        }

        else if (searchBy.equals("Price($)")) {
            String[] line = name.split(",");
            if (line[0].equals(name)) {
                main.showAlert("Invalid Input", "Input Pattern must be lower_bound,upper_bound", "Comma is mandatory! Example:\n 4.1,4.5");
                FList.clear();

            } else {
                FList.setAll(RM.searchFoodInPrice(Double.parseDouble(line[0]), Double.parseDouble(line[1])));
                if (FList.isEmpty()) {
                    main.showAlert("No food Found", "No foods in this Rating bound is found", "");
                }
                LoadFood2(event);
            }
        }




        }
    }




