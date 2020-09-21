/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Asad
 */
public class mainController implements Initializable {

    public ObservableList<Student> Studentdata = FXCollections.observableArrayList();
    public ObservableList<Classes> ClassData = FXCollections.observableArrayList();
    EnglishClasses obj = new EnglishClasses();
    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private Pane paneStudents, paneClass, paneSettings;
    @FXML
    private TableView<Classes> classTableView;
    @FXML
    private TableColumn<Classes, String> classIdCol;
    @FXML
    private TableColumn<Classes, String> summaryCol;
    @FXML
    private TableColumn<Classes, Integer> durationCol;
    @FXML
    private TableColumn<Classes, Double> feeCol;

    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> studentIdCol;
    @FXML
    private TableColumn<Student, String> firstNameCol;
    @FXML
    private TableColumn<Student, String> lastNameCol;
    @FXML
    private TableColumn<Student, String> standardCol;
    @FXML
    private TableColumn<Student, String> schoolCol;

    /*
    @FXML
    private TableView<Class> classTableView;
    @FXML
    private TableColumn<Class, String> classIdCol;
    @FXML
    private TableColumn<Class, Double> feeCol;
    @FXML
    private TableColumn<Class, String> summaryCol;
    @FXML
    private TableColumn<Class, Integer> durationCol;*/
    @FXML
    private JFXButton tabStudents, tabClass, tabSettings;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /*
    @FXML
    private void addClass(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        obj.newWindow("AddClass");
        loadClassData();
    }*/
    @FXML
    private void addStudent(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        obj.newWindow("AddStudent");
        loadStudentData();
    }

    @FXML
    private void addClass(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        obj.newWindow("AddClass");
        loadClassData();
    }

    @FXML
    private void addClassLog(ActionEvent event) throws IOException {
        obj.newWindow("AddClassLog");
    }

    @FXML
    private void editStudent(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        obj.newWindow("EditStudent");
        loadStudentData();
    }

    @FXML
    private void editClass(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        obj.newWindow("EditClass");
        loadClassData();
    }

    @FXML
    private void viewStudent(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        obj.newWindow("ViewStudent");
        loadStudentData();
        loadClassData();
    }

    @FXML
    private void viewClassLog(ActionEvent event) throws IOException {
        obj.newWindow("ClassLog");
    }

    @FXML
    private void viewStudentLog(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        obj.newWindow("StudentLog");
        loadStudentData();
        loadClassData();
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException, ClassNotFoundException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void editPassword(ActionEvent event) throws IOException {
        obj.newWindow("ChangePassword");
    }

    @FXML
    private void removeStudent(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        obj.newWindow("RemoveStudent");
        loadStudentData();
    }

    @FXML
    private void removeClass(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        obj.newWindow("RemoveClass");
        loadClassData();
    }

    @FXML
    private void changePane(ActionEvent event) {
        JFXButton source = (JFXButton) event.getSource();
        if (source == tabStudents) {
            obj.showPane(paneStudents);
        } else if (source == tabClass) {
            obj.showPane(paneClass);
        } else {
            obj.showPane(paneSettings);
        }
    }

    public void loadStudentData() throws SQLException, ClassNotFoundException {

        Connection c = obj.connect();

        Studentdata.clear();

        try {
            String sql = "SELECT studentid, firstname, lastname, standard, school FROM students";
            System.out.println("Opened database successfully");

            ResultSet rs = c.createStatement().executeQuery(sql);

            while (rs.next()) {
                this.Studentdata.add(new Student(rs.getString("STUDENTID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
                        rs.getString("STANDARD"), rs.getString("SCHOOL")));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        // Set up the table data
        studentIdCol.setCellValueFactory(
                new PropertyValueFactory<>("studentId")
        );
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );
        standardCol.setCellValueFactory(
                new PropertyValueFactory<>("standard")
        );
        schoolCol.setCellValueFactory(
                new PropertyValueFactory<>("school")
        );

        studentTableView.setItems(Studentdata);
        c.close();
    }

    public void loadClassData() throws SQLException, ClassNotFoundException {

        Connection c = obj.connect();

        ClassData.clear();

        try {
            String sql = "SELECT classid, fee, summary, duration FROM classes";
            System.out.println("Opened database successfully");

            ResultSet rs = c.createStatement().executeQuery(sql);

            while (rs.next()) {
                this.ClassData.add(new Classes(rs.getString("CLASSID"), rs.getDouble("FEE"), rs.getString("SUMMARY"),
                        rs.getInt("DURATION")));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        // Set up the table data
        classIdCol.setCellValueFactory(
                new PropertyValueFactory<>("classId")
        );
        summaryCol.setCellValueFactory(
                new PropertyValueFactory<>("summary")
        );
        durationCol.setCellValueFactory(
                new PropertyValueFactory<>("duration")
        );
        feeCol.setCellValueFactory(
                new PropertyValueFactory<>("fee")
        );

        classTableView.setItems(ClassData);
        c.close();
    }

    /*
    public void loadClassData() throws SQLException, ClassNotFoundException {

        Connection c = null;

        try {
            String sql = "SELECT classid, fee, summary, duration FROM classdetails";

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            this.Classdata = FXCollections.observableArrayList();

            ResultSet rs = c.createStatement().executeQuery(sql);

            while (rs.next()) {
                this.Classdata.add(new Class(rs.getString("CLASSID"), rs.getDouble("FEE"), rs.getString("SUMMARY"), rs.getInt("DURATION")));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        // Set up the table data
        studentIdCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("classid")
        );
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("fee")
        );
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("summary")
        );
        standardCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("duration")
        );

        //classTableView.setItems(data);
        c.close();
    }*/

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadStudentData();
            loadClassData();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
