package com.example.gestion_formation;

import com.example.gestion_formation.models.Domain;
import com.example.gestion_formation.models.Former;
import com.example.gestion_formation.models.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import com.example.gestion_formation.models.Participant;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class dashController implements Initializable {
    @FXML
    private Button btnCour;
    @FXML
    private Button btnDom;
    @FXML
    private Button btnForm;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnPar;
    @FXML
    private ImageView courId;
    @FXML
    private ImageView domId;
    @FXML
    private ImageView homeId;
    @FXML
    private ImageView parId;
    @FXML
    private ImageView plusImg;
    @FXML
    private ImageView searchImg;
    @FXML
    private ImageView teachId;
    @FXML
    private Label title;
    @FXML
    private Label titleLabel;
    @FXML
    private GridPane gridCourses;
    @FXML
    private Button addParbtn;
    @FXML
    private Button addDomBtn;
    @FXML
    private Button refParBtn;
    @FXML
    private Button addForbtn;
    @FXML
    private Button refDomBtn;
    @FXML
    private Button refForbtn;
    @FXML
    private GridPane gridParticipant;
    @FXML
    private GridPane gridDomain;
    @FXML
    private ImageView searchImg1;
    @FXML
    private TableView<Participant> parTable;
    @FXML
    private TableColumn<Participant, LocalDate> col_date;
    @FXML
    public TableView<Domain> tableDomain;
    @FXML
    public TableColumn<Domain, String> col_name;
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
    public TableColumn<Domain, Integer> col_code;
    @FXML
    public TableColumn<Domain, String> col_action4;
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
    @FXML
    private TableColumn<Participant, Integer> col_mat;
    @FXML
    private TableColumn<Participant, String> col_action;
    @FXML
    private TableColumn<Participant, String> col_nom;
    @FXML
    private TableColumn<Participant, String> col_pre;
    @FXML
    private TableColumn<Participant, String> col_profil;
    @FXML
    private ImageView searchImg2;
    @FXML
    private ImageView searchImg3;
    @FXML
    private GridPane gridFormer;
    @FXML
    private ImageView plusImg1;
    @FXML
    private ImageView updateImg;
    @FXML
    private ImageView updateImg1;
    @FXML
    private ImageView updateImg2;
    @FXML
    private ImageView plusImg2;
    FXMLLoader loader = new FXMLLoader();
    @FXML
    private ImageView plusImg3;
    ObservableList<Participant> PartList = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Participant participant = null;
    domainController domController = new domainController();
    formerController formController= new formerController();
    courceController couController = new courceController();
    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {
        File homFile = new File("assets/home.png");
        Image hImg = new Image(homFile.toURI().toString());
        homeId.setImage(hImg);
        File parFile = new File("assets/group.png");
        Image parImg = new Image(parFile.toURI().toString());
        parId.setImage(parImg);
        File courFile = new File("assets/online-learning.png");
        Image courImg = new Image(courFile.toURI().toString());
        courId.setImage(courImg);
        File teachFile = new File("assets/classroom.png");
        Image teachImg = new Image(teachFile.toURI().toString());
        teachId.setImage(teachImg);
        File domFile = new File("assets/profession.png");
        Image domImg = new Image(domFile.toURI().toString());
        domId.setImage(domImg);
        File plusFile = new File("assets/add.png");
        Image pluImg = new Image(plusFile.toURI().toString());
        plusImg.setImage(pluImg);
        plusImg1.setImage(pluImg);
        plusImg2.setImage(pluImg);
        plusImg3.setImage(pluImg);
        File searchFile = new File("assets/search.png");
        Image searcImg = new Image(searchFile.toURI().toString());
        searchImg.setImage(searcImg);
        File updateFile = new File("assets/refresh.png");
        Image refreshImg = new Image(updateFile.toURI().toString());
        updateImg.setImage(refreshImg);
        updateImg1.setImage(refreshImg);
        updateImg2.setImage(refreshImg);
        searchImg1.setImage(searcImg);
        searchImg2.setImage(searcImg);
        searchImg3.setImage(searcImg);
        col_mat.setCellValueFactory(new PropertyValueFactory<>("Matricule_participant"));
        col_nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        col_pre.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        col_date.setCellValueFactory(cellData -> cellData.getValue().date_naissanceProperty());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/DD");

        col_date.setCellFactory(column -> {
            return new TableCell<Participant, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(formatter.format(item));
                    }
                }
            };
        });
        col_profil.setCellValueFactory(new PropertyValueFactory<>("Profil"));
        loadPar();
    }

    public void getAddView(ActionEvent event) {
        try {
            Stage stage = new Stage();
            if (event.getSource() == addParbtn) {
                Parent root = FXMLLoader.load(getClass().getResource("addPar.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Add participant");
                stage.setScene(scene);
            }
            else if(event.getSource() == addDomBtn)
            {
                Parent root = FXMLLoader.load(getClass().getResource("addDom.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Add new domain");
                stage.setScene(scene);
            }
            else if(event.getSource() == addForbtn)
            {
                Parent root = FXMLLoader.load(getClass().getResource("addFor.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Add new former");
                stage.setScene(scene);
            }
            stage.initStyle(StageStyle.UNDECORATED);

            Image icon = new Image(new File("src/online-course.png").toURI().toString());
            stage.getIcons().add(icon);

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
  public void refreshEvent(ActionEvent event)
  {
      if (event.getSource() ==refParBtn )
      {
          refreshTable();
      }
      else if(event.getSource() ==refDomBtn )
      {

          domController.refreshTable();
      }
      else if(event.getSource() ==refForbtn )
      {

          formController.refreshTable();
      }
  }
    public void handleClick(ActionEvent event) {
        if (event.getSource() == btnPar) {
            titleLabel.setText("Participants");
            gridParticipant.toFront();
            loadPar();

        } else if (event.getSource() == btnCour) {
            titleLabel.setText("Courses");
            gridCourses.toFront();
            couController.courseTable=courseTable;
            couController.col_traincode=col_traincode;
            couController.col_lib=col_lib;
            couController.col_dom=col_dom;
            couController.col_month=col_month;
            couController.col_nbj=col_nbj;
            couController.col_year=col_year;
            couController.col_nbpar=col_nbpar;
            couController.col_former=col_former;
            couController.col_action3=col_action3;
couController.loadCourse();
        } else if (event.getSource() == btnForm) {
            titleLabel.setText("Formers");
            gridFormer.toFront();
            formController.formerTable=formerTable;
            formController.col_codeformer=col_codeformer;
            formController.col_fn=col_fn;
            formController.col_ln=col_ln;
            formController.col_mail=col_mail;
            formController.col_phone=col_phone;
            formController.col_domain=col_domain;
            formController.col_action2=col_action2;
            formController.loadFormer();
        } else if (event.getSource() == btnDom) {
            titleLabel.setText("Domains");
            gridDomain.toFront();
            domController.tableDomain = tableDomain;
            domController.col_code = col_code;
            domController.col_name = col_name;
            domController.col_action4 = col_action4;
            domController.loadDomain();


        }
    }

    public void refreshTable() {
        try {
            PartList.clear();
            query = "SELECT * FROM `participant`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PartList.add(new Participant(
                        resultSet.getInt("Matricule_participant"),
                        resultSet.getString("Nom"),
                        resultSet.getString("Prenom"),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getString("Profil")));
                parTable.setItems(PartList);

            }

        } catch (Exception e) {
            Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    private void loadPar() {
        DatabaseConnection connect = new DatabaseConnection();
        connection = connect.getConnection();
        refreshTable();

        //add cell of button edit
        Callback<TableColumn<Participant, String>, TableCell<Participant, String>> cellFoctory = (TableColumn<Participant, String> param) -> {
            // make cell containing buttons
            final TableCell<Participant, String> cell = new TableCell<Participant, String>() {
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
                                participant = parTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `participant` WHERE Matricule_participant =" + participant.getMatricule_participant();
                                connection = connect.getConnection();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, ex);
                            }


                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            participant = parTable.getSelectionModel().getSelectedItem();
                            loader.setLocation(getClass().getResource("addPar.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            addParController addParcontroller = loader.getController();
                            addParcontroller.setUpdate(true);
                            addParcontroller.getMat(participant.getMatricule_participant());
                            addParcontroller.setTextField(participant.getMatricule_participant(), participant.getNom(), participant.getPrenom(),
                                    participant.getDate_naissance(), participant.getProfil());
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
        col_action.setCellFactory(cellFoctory);
        parTable.setItems(PartList);
    }
}
