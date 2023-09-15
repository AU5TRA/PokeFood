package controller;


import codes.*;
import com.example.fooddelivery.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import util.*;


import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RestController implements Initializable {


    @FXML private Label total;
    @FXML private TableView<food> ResFoodList2;
    @FXML private TableColumn<food, String> FoodName;
    @FXML private TableColumn<food, String> Foodcat;
    @FXML private TableColumn<food, Double> foodPrice;
    @FXML
    private TableColumn<food, Integer> quantityColumn;
    @FXML private TextField FoodBox;
    @FXML private Label Header;
    @FXML private Button searchFood;
    @FXML private TableView<order> cart;
    @FXML private TableColumn<order, String> Name;
    @FXML private TableColumn<order, Double> price;
    @FXML private TableColumn<order, Integer> qty;
    @FXML private Button placeOrder;

    @FXML public ChoiceBox<String> foodChoice;
    private food selectedItem;
    private order s1;
    restaurant r;
    private Main main;
    List<food> foods; // all the foods
    List<order> orders= new ArrayList<>();
    //orderList l;
    /*public orderList(String resName,List <order> foods, String username)
    {
        this.res= resName;
        this.listOfFood= foods;
        this.username= username;
    }*/

    ObservableList<food> foodlist= FXCollections.observableArrayList();//temporary
    //ObservableList<food> list = FXCollections.observableArrayList(); //permanent
    //ObservableMap<food, Integer> orderList= FXCollections.observableHashMap();
    ObservableList<order> orderList= FXCollections.observableArrayList();//temporary


    private String[] choices2= {"Name", "Category", "Price Range", "Priciest","Show All"};

    @Override
    public void initialize(URL arg0, ResourceBundle agr1) {
        foodChoice.setValue("Search by");
        foodChoice.getItems().addAll(choices2);

        // Create a context menu for cart items
        ContextMenu cartContextMenu = new ContextMenu();
        MenuItem removeCartItem = new MenuItem("Remove from Cart");
        cartContextMenu.getItems().add(removeCartItem);
        //order selectedOrder;
        // Add a row factory to your cart TableView
        cart.setRowFactory(tv -> {
            TableRow<order> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event1) -> {
                if (event1.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                     s1 = row.getItem();
                    cartContextMenu.show(cart, event1.getScreenX(), event1.getScreenY());
                }
            });
            return row;
        });

        // Handle the "Remove from Cart" action
        removeCartItem.setOnAction(event -> {
            // Assuming orderList is your cart
            orders.remove(s1);
            orderList.remove(s1);
            updateCart();
        });
    }
    public void init(restaurant r, Main main) throws Exception {
        this.main= main;
        this.r= r;
        Header.setText(r.getName());
        total.setText("0.00");

        foods= r.getFoodList();
        foodlist.setAll(foods);

        if(foodlist.isEmpty())
        {
            main.showAlert("No Food Found", "No Food is available in this Restaurant", "");
        }
        LoadFood2(new ActionEvent());
    }


    @FXML
    void goBack(ActionEvent event) throws Exception {
        main.showHomePage(main.getUsername(), main.getUserType());
    }


    @FXML
    public void LoadFood2(ActionEvent actionEvent) throws Exception {
        FoodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        Foodcat.setCellValueFactory(new PropertyValueFactory<>("category"));
        foodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ResFoodList2.setItems(foodlist);


        //taking quantity input and adding to cart
        ContextMenu contextMenu = new ContextMenu();
        MenuItem enterIntegerItem = new MenuItem("Enter Quantity to Order");
        contextMenu.getItems().add(enterIntegerItem);
        ResFoodList2.setContextMenu(contextMenu);

        ResFoodList2.setRowFactory(tv -> {
            TableRow<food> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event1) -> {
                if (event1.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {

                     selectedItem = row.getItem();
                    System.out.println(selectedItem.getName());//input taken
                    contextMenu.show(ResFoodList2, event1.getScreenX(), event1.getScreenY());

                }
            });
            return row;
        });
        enterIntegerItem.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Quantity");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter order quantity(max 20):");

            // Set a custom integer converter for the dialog
            dialog.getEditor().setTextFormatter(new TextFormatter<>(new StringConverter<Integer>() {
                @Override
                public String toString(Integer object) {
                    return object == null ? "" : object.toString();
                }

                @Override
                public Integer fromString(String string) {
                    try {
                        return Integer.parseInt(string);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }));


            dialog.showAndWait().ifPresent(input -> {
                Integer intValue = Integer.parseInt(input);
                System.out.println("Entered qty: " + intValue);
                if(intValue>20) intValue=20;
                orderList.add(new order(selectedItem, intValue, r.getName()));
                orders.add(new order(selectedItem, intValue, r.getName()));
                updateCart();
            });
        });



    }

    public void updateCart()
    {
        double sum=0;
        orderList.clear();
        for(var temp: orders){
            orderList.add(temp);
        }
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        cart.setItems(orderList);
        for(var temp: orderList)
        {
            sum= sum+ temp.getQty()*temp.getFood().getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedSum = decimalFormat.format(sum);
        total.setText(String.valueOf(formattedSum));

    }
    @FXML
    void searchFood(ActionEvent event) throws Exception{
        String searchBy= foodChoice.getValue();
        String name= FoodBox.getText();
        if(searchBy.equals("Name"))
        {
            foodlist.setAll(r.hasFood(name));
            if(foodlist.isEmpty())
            {
                main.showAlert("No Food Found", "No Food of this name is found", "");
            }
            LoadFood2(event);
        }
        else if(searchBy.equals("Category"))
        {
            foodlist.setAll(r.hasFoodCat(name));
            if(foodlist.isEmpty())
            {
                main.showAlert("No Food Found", "No Food of this Category is found", "");
            }
            LoadFood2(event);
        }
        else if(searchBy.equals("Price Range"))
        {
            String[] line= name.split(",");
            if(line[0].equals(name))
            {
                main.showAlert("Invalid Input", "Input Pattern must be lower_bound,upper_bound", "Comma is mandatory! Example:\n 4.1,4.5");
                foodlist.clear();
            }
            else {
                foodlist.setAll(r.inPricerange(Double.parseDouble(line[0]), Double.parseDouble(line[1])));
                if (foodlist.isEmpty()) {
                    main.showAlert("No Restaurants Found", "No restaurants in this Rating bound is found", "");
                }

                LoadFood2(event);
            }
        }
        else if(searchBy.equals("Priciest")) {
            foodlist.setAll(r.priciest());
            if (foodlist.isEmpty()) {
                main.showAlert("No Food Found", "No Food of this Category is found", "");
            }
            LoadFood2(event);
        }

        else if(searchBy.equals("Show All"))
        {
        foodlist.setAll(foods);
        if(foodlist.isEmpty())
        {
            main.showAlert("No Food Found", "No Food is found in this Restaurant", "");
        }
        LoadFood2(event);
        }

    }

    @FXML
    void placeOrder(ActionEvent event) {
        for(var temp: orders)
        {
            temp.setRes(r);
        }
        orderList l= new orderList(orders.get(0).getResName(), orders, main.getUsername());

        orderDTO oDTO= new orderDTO();
        oDTO.setOrders(l);

        main.showAlert("Success", "Your Order has been placed!", "Thank you for ordering!");

        try {
            System.out.println("yes");
            main.getNetworkUtil().write(oDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
