package com.example.gestion_formation;

import com.example.gestion_formation.models.Domain;
import com.example.gestion_formation.models.Participant;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class domainController implements Initializable {
    @FXML
    public TableView<Domain> tableDomain;
    @FXML
    public TableColumn<Domain, String> col_name;

    @FXML
    public TableColumn<Domain, Integer> col_code;
    @FXML
    public TableColumn<Domain, String> col_action4;
    ObservableList<Domain> DomtList = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Domain domain = null;
    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {


    }

    public void refreshTable() {
        try {
            DomtList.clear();
            query = "SELECT * FROM `domaine`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DomtList.add(new Domain(
                        resultSet.getInt("Code_domaine"),
                        resultSet.getString("libelle")));
                tableDomain.setItems(DomtList);
            }
        } catch (Exception e) {
            Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, e);

        }
    }


    public void loadDomain() {
        DatabaseConnection connect = new DatabaseConnection();
        connection = connect.getConnection();
        col_code.setCellValueFactory(new PropertyValueFactory<>("Code_domaine"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        refreshTable();
        //add cell of button edit
        Callback<TableColumn<Domain, String>, TableCell<Domain, String>> cellFoctory = (TableColumn<Domain, String> param) -> {
            // make cell containing buttons
            final TableCell<Domain, String> cell = new TableCell<Domain, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle("-fx-font-family: FontAwesome; -fx-fill: #ff9b40; -fx-font-size: 24px; -fx-cursor: hand")
                        ;
                        editIcon.setStyle("-fx-font-family: FontAwesome; -fx-fill: #ff9b40; -fx-font-size: 24px; -fx-cursor: hand");
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                domain = tableDomain.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `domaine` WHERE Code_domaine =" + domain.getCode_domaine();
                                connection = connect.getConnection();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, ex);
                            }


                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            domain = tableDomain.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("addDom.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            addDomController addDomController = loader.getController();
                            addDomController.setUpdate(true);
                            addDomController.getCode(domain.getCode_domaine());
                            addDomController.setTextField(domain.getLibelle());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        col_action4.setCellFactory(cellFoctory);
       //tableDomain.setItems(DomtList);
    }
}

