package com.example.gestion_formation;

import com.example.gestion_formation.models.Former;
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

public class formerController implements Initializable {
    @FXML
    public TableView<Former> formerTable;
    @FXML
    public TableColumn<Former, Integer> col_codeformer;
    @FXML
    public TableColumn<Former, String> col_fn;
    @FXML
    public TableColumn<Former, String> col_ln;
    @FXML
    public TableColumn<Former, String> col_domain;
    @FXML
    public TableColumn<Former, String> col_mail;
    @FXML
    public TableColumn<Former, String> col_phone;
    @FXML
    public TableColumn<Former, String> col_action2;
    ObservableList<Former> FormerList = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Former former = null;

    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {


    }

    public void refreshTable() {
        try {
            FormerList.clear();
            query = "SELECT * FROM `formateur`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                FormerList.add(new Former(
                        resultSet.getInt("Code_formateur"),
                        resultSet.getString("Nom"),
                        resultSet.getString("Prenom"),
                        resultSet.getString("Domaine"),
                        resultSet.getString("Email"),
                        resultSet.getInt("N_tel")
                        ));
                formerTable.setItems(FormerList);
            }
        } catch (Exception e) {
            Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, e);

        }
    }


    public void loadFormer() {
        DatabaseConnection connect = new DatabaseConnection();
        connection = connect.getConnection();
        col_codeformer.setCellValueFactory(new PropertyValueFactory<>("code_former"));
        col_fn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_ln.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_domain.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        col_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        refreshTable();
        //add cell of button edit
        Callback<TableColumn<Former, String>, TableCell<Former, String>> cellFoctory = (TableColumn<Former, String> param) -> {
            // make cell containing buttons
            final TableCell<Former, String> cell = new TableCell<Former, String>() {
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
                                former = formerTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `formateur` WHERE Code_formateur =" + former.getCode_former();
                                connection = connect.getConnection();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, ex);
                            }


                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            former = formerTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("addFor.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            addForController addformerController = loader.getController();
                            addformerController.setUpdate(true);
                            addformerController.getCode(former.getCode_former());
                            addformerController.setTextField(former.getNom(),former.getPrenom(),former.getDomaine(),former.getMail(),former.getPhone());


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
        col_action2.setCellFactory(cellFoctory);
        formerTable.setItems(FormerList);
    }
}

