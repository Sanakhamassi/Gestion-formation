package com.example.gestion_formation;

import com.example.gestion_formation.models.Course;
import com.example.gestion_formation.models.Course;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class courceController implements Initializable {
    @FXML
    public TableView<Course> courseTable;
    @FXML
    public TableColumn<Course, Integer> col_traincode;
    @FXML
    public TableColumn<Course, String> col_lib;
    @FXML
    public TableColumn<Course, String> col_dom;
    @FXML
    public TableColumn<Course, Integer> col_nbj;
    @FXML
    public TableColumn<Course, String> col_year;
    @FXML
    public TableColumn<Course, Integer> col_month;
    @FXML
    public TableColumn<Course, String> col_former;
    @FXML
    public TableColumn<Course, Integer> col_nbpar;
    @FXML
    public TableColumn<Course, String> col_action3;
    ObservableList<Course> courList = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Course Course = null;
    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {
    }

    public void refreshTable() {
        try {
            courList.clear();
            query = "SELECT * FROM `formation`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courList.add(new Course(
                        resultSet.getInt("Code_formation"),
                        resultSet.getString("Intitulé"),
                        resultSet.getString("Domaine"),
                        resultSet.getInt("Nbr_jours"),
                        resultSet.getString("Annee"),
                        resultSet.getInt("mois"),
                        resultSet.getString("Formatteur"),
                        resultSet.getInt("Nbr_part")));
                courseTable.setItems(courList);
            }
        } catch (Exception e) {
            Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, e);

        }
    }


    public void loadCourse() {
        DatabaseConnection connect = new DatabaseConnection();
        connection = connect.getConnection();
        col_traincode.setCellValueFactory(new PropertyValueFactory<>("code_formation"));
        col_lib.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_dom.setCellValueFactory(new PropertyValueFactory<>("dom"));
        col_nbj.setCellValueFactory(new PropertyValueFactory<>("nbj"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_month.setCellValueFactory(new PropertyValueFactory<>("month"));
        col_former.setCellValueFactory(new PropertyValueFactory<>("former"));
        col_nbpar.setCellValueFactory(new PropertyValueFactory<>("nbp"));

        refreshTable();
        //add cell of button edit
        Callback<TableColumn<Course, String>, TableCell<Course, String>> cellFoctory = (TableColumn<Course, String> param) -> {
            // make cell containing buttons
            final TableCell<Course, String> cell = new TableCell<Course, String>() {
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
                                Course = courseTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `formation` WHERE Code_formation =" + Course.getCode_formation();
                                connection = connect.getConnection();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, ex);
                            }


                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Course = courseTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("addDom.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            addDomController addDomController = loader.getController();
                            addDomController.setUpdate(true);

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
        col_action3.setCellFactory(cellFoctory);
        //tableCourse.setItems(DomtList);
    }
}

