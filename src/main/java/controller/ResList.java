package controller;

import codes.restaurant;
import codes.restaurantManager;
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
import util.*;
import util.RestaurantDTO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResList implements Initializable {


    @FXML private TableColumn<restaurant, List<String>> Categories = new TableColumn<>();
    @FXML private TableColumn<restaurant, String> PriceRange = new TableColumn<>();
    @FXML private TableColumn<restaurant, Integer> Rating = new TableColumn<>();
    @FXML private TableColumn<restaurant, String> ResName = new TableColumn<>();
    @FXML private TableColumn<restaurant, Integer> foodCount= new TableColumn<>();
    @FXML private TableColumn<restaurant, Integer> zip= new TableColumn<>();
    @FXML private TableView<restaurant> resFoodList;
    @FXML private ChoiceBox<String> resChoice;
    @FXML private Button searchConfirm;
    @FXML private TextField searchRes;
    @FXML private TextField searchText;

    public Button seeAll;
    private Main main;
    List<restaurant> Allrestaurants;
    ObservableList<restaurant> list= FXCollections.observableArrayList();
    restaurantManager RM= new restaurantManager();
    private String[] choices= {"Name", "Categories", "Price($)", "Rating", "ZIP"};


    public void init(Main main, List<restaurant> resList) throws Exception {
        this.main = main;
        Allrestaurants= resList;
        list.setAll(resList);
        RM.setList(resList);
        LoadRes(new ActionEvent());
    }

    @FXML
    public void LoadRes2(ActionEvent actionEvent) throws Exception {
        ResName.setCellValueFactory(new PropertyValueFactory<>("name"));
        Rating.setCellValueFactory(new PropertyValueFactory<>("score"));
        PriceRange.setCellValueFactory(new PropertyValueFactory<>("price_range"));
        Categories.setCellValueFactory(new PropertyValueFactory<>("categories"));
        foodCount.setCellValueFactory(new PropertyValueFactory<>("foodCount"));
        zip.setCellValueFactory(new PropertyValueFactory<>("zip"));

        resFoodList.setItems(list);

        resFoodList.setRowFactory(tv -> {
            TableRow<restaurant> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event1) -> {
                if (event1.getClickCount() == 2 && !row.isEmpty()) {

                    restaurant selectedItem = row.getItem();
                    System.out.println(selectedItem.getName());//input taken

                    RestaurantDTO resDTO= new RestaurantDTO();
                    resDTO.setRes(selectedItem);

                    try {
                        main.getNetworkUtil().write(resDTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            return row;
        });

    }


    @FXML
    void backHome(ActionEvent event) throws Exception {
        main.showHomePage(main.getUsername(), main.getUserType());
    }
    @FXML
    public void LoadRes(ActionEvent actionEvent) throws Exception {
        list.setAll(Allrestaurants);
        LoadRes2(new ActionEvent());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resChoice.setValue("Search by");
        resChoice.getItems().addAll(choices);

        // Set the font color of the ChoiceBox


    }

    @FXML
    void Search(ActionEvent event) throws Exception {
        String searchBy = resChoice.getValue();
        String name = searchText.getText();
        if (searchBy.equals("Name")) {
            list.setAll(RM.searchName(name));
            if (list.isEmpty()) {
                main.showAlert("No Restaurants Found", "No restaurants of this name is found", "");
            }

            LoadRes2(event);
        } else if (searchBy.equals("Categories")) {
            list.setAll(RM.searchCategory(name));
            if (list.isEmpty()) {
                main.showAlert("No Restaurants Found", "No restaurants of this category is found", "");
            }

            LoadRes2(event);
        } else if (searchBy.equals("Price($)")) {
            if (!(name.equals("$") || name.equals("$$") || name.equals("$$$"))) {
                main.showAlert("Invalid Price ", "Invalid Price Indentified", "");
                list.clear();
            } else {
                list.setAll(RM.searchPrice(name));
                if (list.isEmpty()) {
                    main.showAlert("No Restaurants Found", "No restaurants of this price is found", "");
                }

                LoadRes2(event);
            }

        } else if (searchBy.equals("ZIP")) {
            list.setAll(RM.searchZip(Integer.parseInt(name)));
            if (list.isEmpty()) {
                main.showAlert("No Restaurants Found", "No restaurants having this ZIP code is found", "");
            }
            LoadRes2(event);
        } else if (searchBy.equals("Rating")) {
            String[] line = name.split(",");
            if (line[0].equals(name)) {
                main.showAlert("Invalid Input", "Input Pattern must be lower_bound,upper_bound", "Comma is mandatory! Example:\n 4.1,4.5");
                list.clear();

            } else {
                list.setAll(RM.searchScore(Double.parseDouble(line[0]), Double.parseDouble(line[1])));
                if (list.isEmpty()) {
                    main.showAlert("No Restaurants Found", "No restaurants in this Rating bound is found", "");
                }
                LoadRes2(event);
            }
        }
    }


}
