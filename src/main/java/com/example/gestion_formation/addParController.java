package com.example.gestion_formation;

import com.example.gestion_formation.models.Participant;
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

public class addParController implements Initializable {
    @FXML
    private Button cancelBtn;
    @FXML
    private Button confirmBtn;
    @FXML
    private DatePicker datepicker;
    @FXML
    private TextField nomTextfield;

    @FXML
    private TextField preTextfielf;

    @FXML
    private TextField proTextfield;

    @FXML
    private TextField regNumTextfield;
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    private boolean update;
    int mat;
    Participant participant = null;

    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {
    }

    public void confirmbutoon(ActionEvent event) {
        DatabaseConnection connect = new DatabaseConnection();
        connection = connect.getConnection();
        int mat = Integer.parseInt(regNumTextfield.getText());
        String name = nomTextfield.getText();
        String prenom = preTextfielf.getText();
        String birth = String.valueOf(datepicker.getValue());
        String profil = proTextfield.getText();

        if (name.isEmpty() || birth.isEmpty() || prenom.isEmpty() || profil.isEmpty()) {
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
                alert.setContentText("Par updated successfully");

            } else {
                alert.setContentText("Par Added successfully");
            }
            alert.showAndWait();
            clean();


        }

    }

    private void setQuery() {

        if (update == false) {

            query = "INSERT INTO `participant`( `Matricule_participant`, `Nom`, `Prenom`, `Date_naissance`,`Profil`) VALUES (?,?,?,?,?)";

        } else {
            query = "UPDATE `participant` SET `Matricule_participant`=?,`Nom`=?,`Prenom`=?,`Date_naissance`=?,`Profil`=? WHERE Matricule_participant='" + mat + "'";
        }

    }

    @FXML
    private void clean() {
        nomTextfield.setText(null);
        datepicker.setValue(null);
        regNumTextfield.setText(null);
        proTextfield.setText(null);
        preTextfielf.setText(null);

    }

    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(regNumTextfield.getText()));
            preparedStatement.setString(2, nomTextfield.getText());
            preparedStatement.setString(3, preTextfielf.getText());
            preparedStatement.setString(4, String.valueOf(datepicker.getValue()));
            preparedStatement.setString(5, proTextfield.getText());
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

    void setTextField(int mat, String name, String prenom, LocalDate date_birth, String Profil) {
        regNumTextfield.setText(String.valueOf(mat));
        nomTextfield.setText(name);
        preTextfielf.setText(prenom);
        datepicker.setValue(date_birth);
        proTextfield.setText(Profil);

    }

    void getMat(int mat) {
        this.mat = mat;
    }
}
