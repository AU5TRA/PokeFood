package com.example.fooddelivery;
import controller.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.*;
import codes.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.SocketWrapper;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends Application {
    String username;
    int userType;
    private Stage stage;
    private SocketWrapper networkUtil;
    private resOwnerMain rOwnerMain;

    public Stage getStage() {
        return stage;
    }
    public SocketWrapper getNetworkUtil() {
        return networkUtil;
    }

    public void setUsername(String name){ this.username= name; }

    public String getUsername() {
        return username;
    }
    public int getUserType() {
        return userType;
    }






    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        System.out.println("at login stage");
        connectToServer();
        System.out.println("Connected to server....");
        showLoginPage();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new SocketWrapper(serverAddress, serverPort);
        new ReadThreadClient(this);
    }
    public void showLoginPage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Login controller = loader.getController();

        Image img = new Image("C:\\Users\\ASUS\\Desktop\\vscodes\\FoodDelivery - Copy - Copy\\src\\main\\resources\\images\\poke.png");
        stage.getIcons().add(img);

        controller.init(this);
        stage.setTitle("PokeFoods");
        stage.setScene(new Scene(root, 683, 582));
        stage.show();
    }

    public void showResLoginPage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("resOwnerLogin.fxml"));
        Parent root = loader.load();
        resOwnerLogin controller = loader.getController();

        controller.init(this);
        stage.setTitle("PokeFoods");
        stage.setScene(new Scene(root, 683, 582));
        stage.show();
    }
    public void showHomePage(String name, int i) throws Exception{
        userType=i;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainPage.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();

        controller.init(name, this);

        stage.setTitle("PokeFoods");
        stage.setScene(new Scene(root, 683, 582));
        stage.show();
    }

    public void showResPage(restaurant r) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Res.fxml"));
        Parent root = loader.load();

        RestController controller = loader.getController();
        controller.init(r, this);
        stage.setTitle("PokeFoods");
        stage.setScene(new Scene(root, 683, 582));
        stage.show();
    }
    public void showResListPage(List<restaurant> resList) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("resList.fxml"));
        Parent root = loader.load();
        ResList controller = loader.getController();
        controller.init(this, resList);

        stage.setTitle("PokeFoods");
        stage.setScene(new Scene(root, 683, 582));
        stage.show();
    }

    public void showFoodListPage(List<food> foodList, List<restaurant> ResList, String category) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("food.fxml"));
        Parent root = loader.load();
        FoodList controller = loader.getController();
        controller.init(this, foodList, ResList, category);

        stage.setTitle("PokeFoods");
        stage.setScene(new Scene(root, 683, 582));
        stage.show();
    }

    public void showResOwnerMain(List<food> fList, int i, List<orderList> FOOD_QTY) throws IOException {
        userType=i;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("resOwnerMain.fxml"));
        Parent root = loader.load();
        resOwnerMain controller = loader.getController();
        setResOwnerMain(controller);
        controller.init(this, fList, FOOD_QTY);

        stage.setTitle("PokeFoods");
        stage.setScene(new Scene(root, 683, 582));
        stage.show();
    }


    private void setResOwnerMain(resOwnerMain rOwnerMain){
        this.rOwnerMain=rOwnerMain;
    }


    public void update(List<orderList> oList){
        rOwnerMain.updateTable(oList);
    }
    public void showOrdersPage(orderList orders, int i, List<OLIST> FOOD_QTY, List<food> menu) throws Exception {
        //USER.addOrder(order);
        if(i==2)
        {
            double sum=0;
            String n="";
            for (var temp : orders.getListOfFood()) {
                n= n+ temp.getQty() + "x    " +temp.getFood().getName()+"      $"+ temp.getFood().getPrice()+"(each)"+"\n";
                sum = sum + temp.getQty() * temp.getFood().getPrice();
            }
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String formattedSum = decimalFormat.format(sum);
            sum= Double.parseDouble(formattedSum);
            showAlert("Success", "Order received! " , n+"\n"+"TOTAL BILL:              $"+sum);
            //showResOwnerMain(menu, i, FOOD_QTY);
        }

        else{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("order.fxml"));
        Parent root = loader.load();

        orderController controller = loader.getController();
        controller.init(this, orders);

        stage.setTitle("PokeFoods");
        stage.setScene(new Scene(root, 683, 582));
        stage.show();
        }


    }



    public void showAlert(String title, String header, String content) {
        Alert alert;
        if(title.equalsIgnoreCase("Success")){
            alert = new Alert(Alert.AlertType.INFORMATION);
            ImageView icon = new ImageView("C:\\Users\\ASUS\\Desktop\\vscodes\\FoodDelivery - Copy - Copy\\src\\main\\resources\\images\\icon.png");
            icon.setFitHeight(48);
            icon.setFitWidth(48);
            alert.getDialogPane().setGraphic(icon);

        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-font-family: 'Calibri'; " +
                        "-fx-text-fill: #333; " +
                        "-fx-background-color: #ffb203; " +
                        "-fx-border-color: #ffa303; " +
                        "-fx-border-width: 3px; " +
                        "-fx-border-radius: 8px;"
        );

        alert.showAndWait();
    }


    public void destroyClient(){
        System.exit(0);
    }

    public static void main(String[] args) throws Exception{
        launch();
    }


}