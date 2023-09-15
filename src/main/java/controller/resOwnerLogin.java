package controller;

import com.example.fooddelivery.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.LoginDTO;

import java.io.IOException;

public class resOwnerLogin {

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField resID;
    private Main main;

    @FXML
    void loginAction(ActionEvent event) {
        String userName = resID.getText();
        String password = passwordText.getText();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);
        loginDTO.setUserType(2);
        main.setUsername(userName);


        try {
            main.getNetworkUtil().write(loginDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void resetAction(ActionEvent event) {
        resID.setText(null);
        passwordText.setText(null);
    }

    public void init(Main main) {
        this.main = main;

    }
}
