/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Asad
 */
public class StudentLogController implements Initializable {

    public Boolean isFound = false;
    public String name;
    public ObservableList<StudentLog> StudentLogData;
    /**
     * Initializes the controller class.
     */
    public Boolean isOpen = false;
    EnglishClasses obj = new EnglishClasses();
    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private TableView<StudentLog> studentLogTable;
    @FXML
    private TableColumn<StudentLog, String> classIdCol;
    @FXML
    private TableColumn<StudentLog, String> classDescriptionCol;
    @FXML
    private TableColumn<StudentLog, String> classDateCol;
    @FXML
    private TableColumn<StudentLog, String> studentFeedbackCol;
    @FXML
    private TableColumn<StudentLog, Integer> classDurationCol;
    @FXML
    private TableColumn<StudentLog, Double> classFeeCol;
    @FXML
    private TableColumn<StudentLog, String> paymentStatusCol;
    @FXML
    private JFXTextField searchId, idField;
    @FXML
    private JFXButton viewLog, viewStudent, view;
    @FXML
    private Pane mainPane;
    @FXML
    private Label alert, studentName;
    private String studentId;

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void changePane(ActionEvent event) throws SQLException, IOException {
        checkStudent(idField.getText());
        System.out.println(idField.getText());
        if (isFound) {
            searchId.setText(idField.getText());
            loadStudentLogData(idField.getText());
            studentId = idField.getText();
            alert.setText("");
            mainPane.toFront();
        }
    }

    public void checkStudent(String enteredId) throws IOException, SQLException {
        isFound = false;

        try (Connection conn = obj.connect()) {
            System.out.println("Opened database successfully");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT studentid, firstname, lastname FROM STUDENTS;");
            System.out.println("Student accessed successfully");

            while (rs.next()) {

                if (rs.getString("studentid").equalsIgnoreCase(enteredId)) {
                    name = rs.getString("firstname") + " " + rs.getString("lastname");
                    isFound = true;
                }
            }
        }

        if (!isFound) {
            alert.setText("Incorrect ID entered");
        }
    }

    public void loadStudentLogData(String id) throws SQLException {
        Connection c = obj.connect();

        try {
            String sql = "Select classes.classid, classlog.date, classlog.paymentstatus, classlog.studentfeedback, "
                    + "classes.fee, classes.summary, classes.duration from classes"
                    + " INNER JOIN classlog ON classes.classid = classlog.classid WHERE classlog.studentid='" + id + "'";

            System.out.println("Opened database successfully");

            this.StudentLogData = FXCollections.observableArrayList();

            ResultSet rs = c.createStatement().executeQuery(sql);

            while (rs.next()) {
                String isPaid;
                if (rs.getInt("paymentstatus") == 1) {
                    isPaid = "Paid";
                } else {
                    isPaid = "Not Paid";
                }

                this.StudentLogData.add(new StudentLog(rs.getString("classid"), rs.getString("date"), isPaid, rs.getString("studentfeedback"),
                        rs.getInt("duration"), rs.getString("summary"), rs.getDouble("fee")));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        // Set up the table data
        classIdCol.setCellValueFactory(
                new PropertyValueFactory<>("classId")
        );
        classDescriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("summary")
        );
        classDateCol.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        studentFeedbackCol.setCellValueFactory(
                new PropertyValueFactory<>("feedback")
        );
        classDurationCol.setCellValueFactory(
                new PropertyValueFactory<>("duration")
        );
        classFeeCol.setCellValueFactory(
                new PropertyValueFactory<>("fee")
        );
        paymentStatusCol.setCellValueFactory(
                new PropertyValueFactory<>("paymentStatus")
        );

        studentLogTable.setItems(StudentLogData);

        studentName.setText("   Student Log - " + name);

        isFound = false;

        c.close();
    }

    @FXML
    private void searchStudent(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        checkStudent(searchId.getText());
        System.out.println(idField.getText());
        if (isFound) {
            loadStudentLogData(searchId.getText());
            alert.setText("");
            studentId = searchId.getText();
        } else {
            alert.setText("Incorrect Student ID");
        }
    }

    @FXML
    private void addRecord(ActionEvent event) throws Exception {
        obj.setId(studentId);
        obj.newWindow("AddClassLog");
        loadStudentLogData(studentId);
    }

    @FXML
    private void editRecord(ActionEvent event) throws Exception {
        obj.setId(studentId);
        obj.newWindow("EditClassLog");
        loadStudentLogData(studentId);
    }

    @FXML
    private void removeRecord(ActionEvent event) throws IOException, SQLException {
        obj.setId(studentId);
        obj.newWindow("RemoveClassLog");
        loadStudentLogData(studentId);
    }

    @FXML
    private void openStudent(ActionEvent event) throws IOException {

        if (isOpen) {
            closeButtonAction();
        } else {
            obj.setId(studentId);
            obj.newWindow("ViewStudent");
        }
    }

    //Student Log Controlller

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println(obj.getId());
            if (obj.getId() != null) {
                try {
                    isOpen = true;
                    checkStudent(obj.getId());
                    loadStudentLogData(obj.getId());

                    searchId.setText(obj.getId());
                    studentId = obj.getId();
                    idField.setText(obj.getId());

                    mainPane.toFront();

                    obj.deleteId();

                } catch (IOException | SQLException ex) {
                    Logger.getLogger(ViewStudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                isOpen = false;
                mainPane.toBack();
            }
        } catch (IOException ex) {
            Logger.getLogger(ViewStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
