package controller;
/////////////////////check for user type
import codes.*;
import codes.restaurant;
import com.example.fooddelivery.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class orderController {

    @FXML private Label orderLabel;
    @FXML private Label ResName;
    @FXML private Button HomePageButton;

   // ObservableList<orderStats> orderList= FXCollections.observableArrayList();
    List<order> list= new ArrayList<>();


    private Main main;

    public void init(Main main, orderList orders)  throws Exception {
        this.main = main;
        double sum = 0;
        String n="";

        for (var temp : orders.getListOfFood()) {
            n= n+ temp.getQty() + "x    " +temp.getFood().getName()+"      $"+ temp.getFood().getPrice()+"(each)"+"\n";
            sum = sum + temp.getQty() * temp.getFood().getPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedSum = decimalFormat.format(sum);
        sum= Double.parseDouble(formattedSum);

 //       if(main.getUserType()==1){
            ResName.setText(orders.getListOfFood().get(0).getResName());
            orderLabel.setText(n+"\n"+"TOTAL BILL:              $"+sum);
            //username= main.getUsername();
//        }
//        else
//        {
//            main.showAlert("Order", "Order received from user " , n+"\n"+"TOTAL BILL:              $"+sum);
//        }


        }

        @FXML void goHome(ActionEvent event) throws Exception {
            System.out.println("going home lmao");
            //if(main.getUserType()==1)
                main.showHomePage(main.getUsername(),main.getUserType());

        }

}

