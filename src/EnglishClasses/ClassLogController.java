/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asad
 */
public class ClassLogController implements Initializable {

    EnglishClasses obj = new EnglishClasses();

    @FXML
    private javafx.scene.control.Button closeButton;

    public ObservableList<ClassLog> ClassLogData = FXCollections.observableArrayList();

    @FXML
    private TableView<ClassLog> classLogTableView;
    @FXML
    private TableColumn<ClassLog, String> sIdCol;
    @FXML
    private TableColumn<ClassLog, String> cIdCol;
    @FXML
    private TableColumn<ClassLog, String> sNameCol;
    @FXML
    private TableColumn<ClassLog, String> cDescriptionCol;
    @FXML
    private TableColumn<ClassLog, String> cPaymentCol;
    @FXML
    private TableColumn<ClassLog, Double> cFeeCol;
    @FXML
    private TableColumn<ClassLog, String> cDateCol;
    
    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void loadClassLogData() throws SQLException, ClassNotFoundException {

        Connection c = obj.connect();

        ClassLogData.clear();

        try {
            String sql = "Select students.studentid, classes.classid, classlog.date, "
                    + "classlog.paymentstatus, students.firstname, students.lastname, "
                    + "classes.fee, classes.summary from students INNER JOIN classlog ON students.studentid = classlog.studentid "
                    + "INNER JOIN classes ON classlog.classid = classes.classid";

            System.out.println("Opened database successfully");

            ResultSet rs = c.createStatement().executeQuery(sql);

            while (rs.next()) {
                String fullName = rs.getString("firstname") + " " + rs.getString("lastname");
                String isPaid;
                if (rs.getInt("paymentstatus") == 1) {
                    isPaid = "Paid";
                } else {
                    isPaid = "Not Paid";
                }

                this.ClassLogData.add(new ClassLog(rs.getString("studentid"), rs.getString("classid"), fullName,
                        rs.getString("summary"), rs.getDouble("fee"), rs.getString("date"), isPaid));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        // Set up the table data
        sIdCol.setCellValueFactory(
                new PropertyValueFactory<ClassLog, String>("studentId")
        );
        sNameCol.setCellValueFactory(
                new PropertyValueFactory<ClassLog, String>("fullName")
        );
        cIdCol.setCellValueFactory(
                new PropertyValueFactory<ClassLog, String>("classId")
        );
        cDescriptionCol.setCellValueFactory(
                new PropertyValueFactory<ClassLog, String>("classDescription")
        );
        cFeeCol.setCellValueFactory(
                new PropertyValueFactory<ClassLog, Double>("classFee")
        );
        cPaymentCol.setCellValueFactory(
                new PropertyValueFactory<ClassLog, String>("classPayment")
        );
        cDateCol.setCellValueFactory(
                new PropertyValueFactory<ClassLog, String>("classDate")
        );

        classLogTableView.setItems(ClassLogData);
        c.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadClassLogData();
        } catch (SQLException ex) {
            Logger.getLogger(ClassLogController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassLogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
