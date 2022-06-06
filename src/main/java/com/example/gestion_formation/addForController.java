package com.example.gestion_formation;

import com.example.gestion_formation.models.Domain;
import com.example.gestion_formation.models.Former;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addForController implements Initializable {
    @FXML
    private Button cancelBtn;
    @FXML
    private Button confirmBtn;
    @FXML
    private ComboBox domainCombo;

    @FXML
    private TextField firstnTextfield;

    @FXML
    private TextField lastnTextfielf;

    @FXML
    private TextField mailTextfield;

    @FXML
    private TextField phoneTextfield;
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    private boolean update;
    int code;
    String domain;
    ObservableList options = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {
        try {
            DatabaseConnection connect = new DatabaseConnection();
            connection = connect.getConnection();
            query = "SELECT `libelle` FROM `domaine`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                options.add(new String(resultSet.getString(1)));


            }
            domainCombo.setItems(options);
        } catch (Exception e) {
            Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, e);

        }
        /*phoneTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneTextfield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });*/
    }

    public void confirmbutoon(ActionEvent event) {
        DatabaseConnection connect = new DatabaseConnection();
        connection = connect.getConnection();
        String name = firstnTextfield.getText();
        String prenom = lastnTextfielf.getText();
        String s = domainCombo.getSelectionModel().getSelectedItem().toString();
        String mail = mailTextfield.getText();
        String phone = phoneTextfield.getText();

        if (name.isEmpty() || prenom.isEmpty() || mail.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        }
        else {

            setQuery();
            insert();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            if (update = false) {
                alert.setContentText("Former Added successfully");

            } else {
                alert.setContentText("Former Updated successfully");
            }
            alert.showAndWait();
            clean();


        }

    }

    private void setQuery() {
        System.out.println(update);

        if (update == false) {

            query = "INSERT INTO `formateur`( `Nom`, `Prenom`, `Domaine`,`Email`,`N_tel`) VALUES (?,?,?,?,?)";

        } else {
            query = "UPDATE `formateur` SET ``Nom`=?,`Prenom`=?,`Domaine`=?,`Email`=?,`N_tel`=? WHERE Code_formateur='" + code + "'";
        }

    }

    @FXML
    private void clean() {
        firstnTextfield.setText(null);
        lastnTextfielf.setText(null);
        mailTextfield.setText(null);
        phoneTextfield.setText(null);


    }

    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstnTextfield.getText());
            preparedStatement.setString(2, lastnTextfielf.getText());
            preparedStatement.setString(3, domainCombo.getSelectionModel().getSelectedItem().toString());
            preparedStatement.setString(4, mailTextfield.getText());
            preparedStatement.setInt(5, Integer.parseInt(phoneTextfield.getText()));
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(addParController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Cancelbutton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    void setUpdate(boolean update) {
        this.update = update;
    }

    void setTextField(String nom, String prenom,String doamin, String mail, Integer phone) {
        firstnTextfield.setText(nom);
        lastnTextfielf.setText(prenom);
        domainCombo.getSelectionModel().select(doamin);
        mailTextfield.setText(mail);
        phoneTextfield.setText(String.valueOf(phone));


    }
    public void Select(ActionEvent event) {
        String s = domainCombo.getSelectionModel().getSelectedItem().toString();
    }
    void getCode(int code) {
        this.code = code;
    }
}
