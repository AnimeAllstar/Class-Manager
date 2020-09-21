/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AddClassLogController implements Initializable {

    public boolean isFound;
    public String enteredId;
    EnglishClasses obj = new EnglishClasses();
    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private JFXTextField classId, studentFeedback;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXCheckBox paymentStatus;
    @FXML
    private Label alert;
    @FXML
    private JFXComboBox studentId;

    @FXML
    private void completeEntry(ActionEvent event) throws SQLException, IOException {
        if (isFull() && checkClassID()) {
            createStudentClassRelation();
            closeButtonAction();
        }
    }

    public boolean isFull() {
        boolean isComboBoxEmpty = studentId.getSelectionModel().isEmpty();

        LocalDate d = date.getValue();
        boolean isSelected = false;

        String c = classId.getText().trim();

        if (studentId.isDisabled()) {
            isSelected = true;
        } else {
            if (isComboBoxEmpty) isSelected = false;
        }

        if (studentId.isDisabled()) {
            if (c.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, " ", ButtonType.OK);
                alert.setTitle("New Class Log Entry");
                alert.setHeaderText("Empty Text Field");
                alert.setContentText("Please fiil all text fields marked by *");
                alert.showAndWait();
                return false;
            } else {
                return true;
            }
        }

        if (isSelected || c.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, " ", ButtonType.OK);
            alert.setTitle("New Class Log Entry");
            alert.setHeaderText("Empty Text Field");
            alert.setContentText("Please fiil all text fields marked by *");
            alert.showAndWait();
            System.out.println("run");

            return false;
        } else {
            System.out.println("run");

            return true;
        }

    }

    @FXML
    public boolean checkClassID() throws SQLException {
        isFound = false;
        System.out.println("run");
        enteredId = classId.getText();
        String notes = null;

        Connection conn = obj.connect();
        System.out.println("Opened database successfully");

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM CLASSES;");
        System.out.println("CLASSES accessed successfully");

        while (rs.next()) {

            if (rs.getString("classid").equalsIgnoreCase(enteredId)) {
                isFound = true;
            }
        }

        conn.close();

        if (!isFound) {
            alert.setText("Incorrect Class ID");
        }

        return isFound;
    }

    @FXML
    private void closeButtonAction() throws IOException {
        obj.deleteId();
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void createStudentClassRelation() throws SQLException, IOException {

        String sId;

        if (obj.getId() != null) {
            sId = obj.getId();
        } else {
            String selectedItem = (String) studentId.getValue();
            sId = selectedItem.substring(selectedItem.length() - 6);
        }

        int paid = 0;
        if (paymentStatus.isSelected()) {
            paid = 1;
        }
        obj.createStudentClassRelation(sId, classId.getText(), date.getValue(), studentFeedback.getText(), paid);
        obj.deleteId();
    }

    public void loadComboBox() throws SQLException {
        Connection c = obj.connect();

        try {
            String sql = "SELECT studentid, firstname, lastname FROM students";
            System.out.println("Opened database successfully");

            ResultSet rs = c.createStatement().executeQuery(sql);

            while (rs.next()) {
                String item = rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME") +
                        " - " + rs.getString("STUDENTID");
                studentId.getItems().add(item);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        c.close();
    }

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (obj.getId() != null) {
                studentId.setDisable(true);
            } else {
                loadComboBox();
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AddClassLogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
