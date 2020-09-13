/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AddStudentController implements Initializable {

    EnglishClasses obj = new EnglishClasses();

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private Pane paneStudent, paneParent;

    @FXML
    private JFXButton next, back, finish;
    @FXML
    private JFXTextField firstName, lastName, standard, school;
    @FXML
    private JFXTextArea notes;
    @FXML
    private JFXDatePicker DOB;

    @FXML
    private void changePane(ActionEvent event) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException {
        JFXButton source = (JFXButton) event.getSource();
        if (source == next) {
            if (isStudentFull()) {
                if (obj.validateStudent(firstName.getText(), lastName.getText(), DOB.getValue())) {
                    obj.showPane(paneParent);
                }
            }
        } else if (source == back) {
            obj.showPane(paneStudent);
        } else if (source == finish) {
            if (isParentFull()) {
                if (obj.validateParent(parentFirstName.getText(), parentLastName.getText(), parentContactNumber.getText(), parentEmail.getText())) {
                    addStudent();
                    addParent();
                    createStudentParentRelation();
                    closeButtonAction();
                }
            }
        }
    }

    public boolean isParentFull() {
        String pC = parentContactNumber.getText().trim();
        String pE = (parentEmail.getText()).trim();
        String pL = parentLastName.getText().trim();
        String pF = parentFirstName.getText().trim();

        if (pC.equals("") || pE.equals("") || pL.equals("") || pF.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, " ", ButtonType.OK);
            alert.setTitle("New Parent Entry");
            alert.setHeaderText("Empty Text Field");
            alert.setContentText("Please fill all text fields");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    public boolean isStudentFull() {
        if (standard.getText().trim() == null || DOB.getValue() == null || school.getText().trim() == null || lastName.getText().trim() == null || firstName.getText().trim() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, " ", ButtonType.OK);
            alert.setTitle("New Student Entry");
            alert.setHeaderText("Empty Text Field");
            alert.setContentText("Please fill all text fields marked by *");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    /*@FXML
    private void addStudent() throws SQLException {
        obj.showPane(paneParent);
    }*/
    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void addStudent() throws SQLException, FileNotFoundException, IOException, ClassNotFoundException {
        char fn = Character.toLowerCase(firstName.getText().trim().charAt(0));
        char ln = Character.toLowerCase(lastName.getText().trim().charAt(0));
        String sId = new StringBuilder().append(fn).append(ln).append(new Random().nextInt(9999 - 1000) + 1000).toString();
        sIdRelation = sId;

        obj.addstudent(sId, firstName.getText().trim(), lastName.getText().trim(), standard.getText().trim(), DOB.getValue(),
                school.getText().trim(), notes.getText().trim());
        System.out.println("Student Added");
    }

    @FXML
    private JFXTextField parentFirstName, parentLastName, parentContactNumber, parentEmail;

    public String sIdRelation, pIdRelation;

    public void addParent() throws SQLException, IOException {
        char fn = Character.toLowerCase(parentFirstName.getText().trim().charAt(0));
        char ln = Character.toLowerCase(parentLastName.getText().trim().charAt(0));
        String pId = new StringBuilder().append(fn).append(ln).append(new Random().nextInt(9999 - 1000) + 1000).toString();
        pIdRelation = pId;

        checkParent();
        if (!isFound) {
            obj.addparent(pId, parentFirstName.getText().trim(), parentLastName.getText().trim(), parentContactNumber.getText().trim(),
                    parentEmail.getText().trim());
        }

        System.out.println("Student Added");
    }

    boolean isFound = false;

    public void checkParent() throws IOException, SQLException {
        isFound = false;
        System.out.println("run");

        String fName = parentFirstName.getText().trim();
        String lName = parentLastName.getText().trim();
        String contact = parentContactNumber.getText().trim();
        String email = parentEmail.getText().trim();

        Connection conn = obj.connect();
        System.out.println("Opened database successfully");

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM PARENTS");
        System.out.println("Parents accessed successfully");

        while (rs.next()) {
            if (rs.getString("firstname").equalsIgnoreCase(fName) && rs.getString("lastname").equalsIgnoreCase(lName)
                    && rs.getString("contact").equals(contact) && rs.getString("email").equalsIgnoreCase(email)) {
                System.out.println("entered");
                isFound = true;
                pIdRelation = rs.getString("parentid");
            }
        }

        conn.close();
    }

    public void createStudentParentRelation() throws SQLException {
        obj.createStudentParentRelation(sIdRelation, pIdRelation);
        System.out.println("Student Added");
    }

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
    }
}
