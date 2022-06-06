package com.example.gestion_formation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.EventListener;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class loginController implements Initializable {
    @FXML
    private Button loginButton;
    @FXML
    private Label loginMsgLabel;
    @FXML
    private ImageView loginImg;
    @FXML
    private ImageView lockImg;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField passwordText;
private Stage stage;
private Scene scene;
    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {
        File brandingFile = new File("assets/Tablet login.gif");
        Image brandingImg = new Image(brandingFile.toURI().toString());
        loginImg.setImage(brandingImg);
        File lockFile = new File("assets/padlock.png");
        Image lockerImg = new Image(lockFile.toURI().toString());
        lockImg.setImage(lockerImg);
    }

    public void loginButtonAction(ActionEvent event) {
        try{
        if (usernameTextfield.getText().isBlank() == false && passwordText.getText().isBlank() == false) {
            validation(event);

        } else {
            loginMsgLabel.setText("Please enter your mail and password ");

        }}
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void validation(ActionEvent event) {

        DatabaseConnection connect = new DatabaseConnection();
        Connection connectDb = connect.getConnection();
        String verifyLogin = "SELECT count(1) FROM user WHERE Login= '" + usernameTextfield.getText() + "' AND password='" + passwordText.getText() + "'";
        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginMsgLabel.setTextFill(Color.web("green"));
                    loginMsgLabel.setText("Connnection established");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dash.fxml"));
                    Parent root = loader.load();
                    //Parent root=FXMLLoader.load(getClass().getResource("dashb.fxml"));
                    stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                    scene=new Scene(root);
                    //String css =this.getClass().getResource("../resources/style.css").toExternalForm();
                    scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css")
                            .toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                } else {
                    loginMsgLabel.setText("invalid Login");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void registerForm() {
        Stage registerStage = new Stage();
        try {

            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root, 700, 450);
            Image icon = new Image(new File("src/online-course.png").toURI().toString());
            registerStage.getIcons().add(icon);
            registerStage.setTitle("Register page");
            registerStage.setScene(scene);
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}