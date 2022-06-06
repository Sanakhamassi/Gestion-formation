package com.example.gestion_formation;

import com.example.gestion_formation.models.Domain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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

public class addDomController implements Initializable {
    @FXML
    private Button cancelBtn;
    @FXML
    private Button confirmBtn;

    @FXML
     private TextField libTextfield;
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    private boolean update;
    int code;
    Domain domain = null;

    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {
    }
    public void confirmbutoon(ActionEvent event) {
        DatabaseConnection connect = new DatabaseConnection();
        connection = connect.getConnection();
        String lib = libTextfield.getText();

        if (lib.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            setQuery();
            insert();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            if (update = true) {
                alert.setContentText("Domain updated successfully");

            } else {
                alert.setContentText("Domain Added successfully");
            }
            alert.showAndWait();
            clean();


        }

    }

    private void setQuery() {

        if (update == false) {

            query = "INSERT INTO `domaine`( `libelle`) VALUES (?)";

        } else {
            query = "UPDATE `domaine` SET `libelle`=? WHERE Code_domaine='" + code + "'";
        }

    }

    @FXML
    private void clean() {
        libTextfield.setText(null);

    }
    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, libTextfield.getText());
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

    void setTextField(String libelle) {
        libTextfield.setText(libelle);

    }

    void getCode(int code) {
        this.code = code;
    }
}
