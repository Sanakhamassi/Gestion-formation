package com.example.gestion_formation;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class registerController implements Initializable {
    @FXML
    private ImageView registerId;
    @FXML
    private ImageView iconId;
    @FXML
    private ComboBox combo;
    @FXML
    private Button cancelButton;
    @FXML
    private PasswordField passId;
    @FXML
    private PasswordField confirmpassId;
    @FXML
    private Label labelMsg;
    private String role;
    @FXML
    private TextField userId;
    @FXML
    private Label passmatchid;

    public void Select(ActionEvent event) {
        String s = combo.getSelectionModel().getSelectedItem().toString();
        role = s;
    }

    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {
        File brandingFile = new File("assets/registered .png");
        Image brandingImg = new Image(brandingFile.toURI().toString());
        iconId.setImage(brandingImg);
        File lockFile = new File("assets/regist.gif");
        Image lockerImg = new Image(lockFile.toURI().toString());
        registerId.setImage(lockerImg);
        ObservableList<String> list = FXCollections.observableArrayList("Administrateur", "Simple utilisateur");
        combo.setItems(list);
    }

    public void registerButtonevent(ActionEvent event) {
        if (passId.getText().equals(confirmpassId.getText())) {
            passmatchid.setText("");
            registerUser();

        } else {
            passmatchid.setText("Password does not match");
        }

    }

    public void registerUser() {
        DatabaseConnection connect = new DatabaseConnection();
        Connection connectDB = connect.getConnection();
        String userName = userId.getText().toString();
        String password = passId.getText().toString();
        String role2 = role;
        String insertFields = "INSERT INTO user(Login,password,Role) VALUES('";
        String insertValues = userName + "','" + password + "','" + role2 + "')";
        String verifyUser = "SELECT * FROM user WHERE Login= '" + userId.getText() + "'";
        boolean usernameExists = false;
        String requete = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyUser);
            if (queryResult.next()) {
                usernameExists = true;
            }
            if (userName.equals("") || password.equals("")) {
                labelMsg.setTextFill(Color.web("red"));
                labelMsg.setText("Field Required!");
            }
            if (usernameExists==true) {
                labelMsg.setTextFill(Color.web("red"));
                labelMsg.setText("User already exist with this username");
            } else {
                Statement statement1 = connectDB.createStatement();
                statement1.executeUpdate(requete);
                labelMsg.setTextFill(Color.web("green"));
                labelMsg.setText("User has been registered successufelly!");

            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void cancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
}
