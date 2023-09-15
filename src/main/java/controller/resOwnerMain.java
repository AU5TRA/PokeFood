package controller;
import codes.*;

import com.example.fooddelivery.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import util.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class resOwnerMain {

    @FXML
    private TableColumn<OLIST, String> Category;

    @FXML
    private TableColumn<OLIST, Double> Price;

    @FXML
    private TableColumn<OLIST, String> foodName;

    @FXML
    private TableColumn<OLIST, Integer> oCount;

    @FXML
    private TableView<OLIST> resFoodList;

    @FXML
    private Button seeAll1;

    @FXML
    private Button addButton;
    @FXML
    private Label ResName;

    @FXML
    private Label customer;
    @FXML
    private Label profit;

    private Main main;

    List <food> list= new ArrayList<>(); //list of all possible foods
    List <OLIST> order= new ArrayList<>(); //list of all possible order
    List<OLIST> FOOD_QTY= new ArrayList<>();

    ObservableList<OLIST> showList= FXCollections.observableArrayList();





    public void init(Main main, List<food> fList, List<orderList> ordersReceived){
        ResName.setText(main.getUsername());
        this.main = main;
        double sum=0;
        System.out.println("inside init");
        list = fList ;//list has all the items of a certain restaurant, we match it with the FOOD_QTY
        for(var temp: fList)
        {
            order.add(new OLIST(temp, 0));
        }
        if(ordersReceived!=null){
            for(var temp: ordersReceived)
            {
                //temp is an orderList
                for(var foodL: temp.getListOfFood())
                {
                    //foodL is an order
                    for(var t: order)
                    {
                        //finding the count
                        if(t.getName().equals(foodL.getFood().getName()))
                        {
                            int previous_count= t.getQty();
                            t.setQty(foodL.getQty()+previous_count);
                            sum= sum+ t.getQty()*t.getPrice();

                            break;
                        }
                    }
                }
            }
            customer.setText("Customers: " + ordersReceived.size());
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String formattedSum = decimalFormat.format(sum);
            profit.setText("Profit: $ " + formattedSum);
        }



        showList.setAll(order);
        loadTable();
    }


    public void loadTable()
    {
        foodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        Category.setCellValueFactory(new PropertyValueFactory<>("category"));
        oCount.setCellValueFactory(new PropertyValueFactory<>("qty"));

        resFoodList.setItems(showList);
    }

    @FXML
    void logout(ActionEvent event) {
            System.out.println("Restaurant owner logging out");
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



        public void updateTable(List<orderList> oList){
        order.clear();
        double sum=0;
            for(var temp: list)
            {
                order.add(new OLIST(temp, 0));
            }

            for(var temp: oList)
            {
                //temp is an orderList
                for(var foodL: temp.getListOfFood())
                {
                    //foodL is an order
                    for(var t: order)
                    {
                        //finding the count
                        if(t.getName().equals(foodL.getFood().getName()))
                        {
                            int previous_count= t.getQty();
                            System.out.println(t.getName()+", prev count : "+ previous_count+" new added "+ foodL.getQty());
                            t.setQty(foodL.getQty()+previous_count);
                            sum= sum+ t.getQty()*t.getPrice();

                            break;
                        }
                    }
                }
            }
            showList.setAll(order);
            customer.setText("Customers: " + oList.size());
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String formattedSum = decimalFormat.format(sum);
            profit.setText("Profit: $ " + formattedSum);
            loadTable();
        }








}
