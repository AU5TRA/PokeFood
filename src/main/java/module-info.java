module com.example.fooddelivery {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fooddelivery to javafx.fxml;
    exports com.example.fooddelivery;
    exports codes;
    opens codes to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
}