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
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class EditStudentController implements Initializable {

    EnglishClasses obj = new EnglishClasses();

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private Label alert;

    @FXML
    private JFXTextField idField;

    @FXML
    private Pane paneStudent, paneParent, panId;

    @FXML
    private JFXButton next, back, finish, edit;

    @FXML
    private JFXTextField firstName, lastName, standard, school;

    @FXML
    private JFXTextField parentFirstName, parentLastName, parentContact, parentEmail;

    @FXML
    private JFXTextArea notes;

    @FXML
    private JFXDatePicker DOB;

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void changePane(ActionEvent event) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException {
        JFXButton source = (JFXButton) event.getSource();
        if (source == edit) {
            editStudent();
            if (isFound) {
                obj.showPane(paneStudent);
            }
        }
        if (source == next) {
            if (isStudentFull()) {
                if (obj.validateStudent(firstName.getText(), lastName.getText(), DOB.getValue())) {
                    editParent();
                    obj.showPane(paneParent);
                }
            }
        } else if (source == back) {
            obj.showPane(paneStudent);
        } else if (source == finish) {
            if (isParentFull()) {
                if (obj.validateParent(parentFirstName.getText(), parentLastName.getText(), parentContact.getText(), parentEmail.getText())) {
                    exeUpdate();
                }
            }
        }
    }

    public boolean isStudentFull() {
        String sF = firstName.getText().trim();
        String sL = lastName.getText().trim();
        String sStandard = standard.getText().trim();
        LocalDate sD = DOB.getValue();
        String sSchool = school.getText().trim();

        if (sF.equals("") || sL.equals("") || sStandard.equals("") || sSchool.equals("") || sD.equals(null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, " ", ButtonType.OK);
            alert.setTitle("New Student Entry");
            alert.setHeaderText("Empty Text Field");
            alert.setContentText("Please fiil all text fields");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    public boolean isParentFull() {
        String pC = parentContact.getText().trim();
        String pE = (parentEmail.getText()).trim();
        String pL = parentLastName.getText().trim();
        String pF = parentFirstName.getText().trim();

        if (pC.equals("") || pE.equals("") || pL.equals("") || pF.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, " ", ButtonType.OK);
            alert.setTitle("New Parent Entry");
            alert.setHeaderText("Empty Text Field");
            alert.setContentText("Please fiil all text fields");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    public String enteredId = null;
    public String generatedParentId = null;
    public Boolean isFound = false;

    @FXML
    public void editStudent() throws IOException, SQLException {
        isFound = false;
        System.out.println("run");
        enteredId = idField.getText();

        String id = null;
        String fname = null;
        String lname = null;
        String stan = null;
        String DOB = null;
        String school = null;
        String notes = null;

        Connection conn = obj.connect();
        System.out.println("Opened database successfully");

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS;");
        System.out.println("Student accessed successfully");

        while (rs.next()) {

            if (rs.getString("studentid").equalsIgnoreCase(enteredId)) {
                id = rs.getString("studentid");
                fname = rs.getString("firstname");
                lname = rs.getString("lastname");
                stan = rs.getString("standard");
                DOB = rs.getString("dob");
                school = rs.getString("SCHOOL");
                notes = rs.getString("notes");

                isFound = true;
            }
        }

        conn.close();

        System.out.println("STUDENT ID = " + id);
        System.out.println("FIRST NAME = " + fname);
        System.out.println("LAST NAME = " + lname);
        System.out.println("STANDARD = " + stan);
        System.out.println("DOB = " + DOB);
        System.out.println("SCHOOL = " + school);
        System.out.println("NOTES = " + notes);

        if (isFound) {

            /*String day, month, year;

            day = DOB.substring(0, 2);
            System.out.println(day);
            month = DOB.substring(3, 5);
            System.out.println(month);
            year = DOB.substring(6);
            System.out.println(year);

            String formattedDOB = year.concat("-").concat(month).concat("-").concat(day);

            LocalDate dateOfBirth = LocalDate.parse(formattedDOB);
            System.out.println(dateOfBirth);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
            System.out.println(formatter.format(dateOfBirth));*/
            LocalDate dateOfBirth = LocalDate.parse(DOB);

            firstName.setText(fname);
            lastName.setText(lname);
            standard.setText(stan);
            this.school.setText(school);
            this.DOB.setValue(dateOfBirth);
            this.notes.setText(notes);
        } else {
            alert.setText("Incorrect ID entered");
        }
    }

    @FXML
    public void editParent() throws IOException, SQLException {

        //SCANNING SPRELATION TABLE FOR MATCHING PARNET ID
        String parentId = null, studentId = null;

        Connection conn = obj.connect();
        System.out.println("Opened database successfully");

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SPRELATION;");
        System.out.println("spRelation accessed successfully");

        System.out.println("REQUIRED STUDENT ID = " + enteredId);
        System.out.println("");

        obj.displaySpRelationtData();

        while (rs.next()) {

            if (rs.getString("studentid").equalsIgnoreCase(enteredId)) {
                studentId = rs.getString("studentid");
                parentId = rs.getString("parentid");
            }
        }

        System.out.println("STUDENT ID = " + studentId);
        System.out.println("PARENT ID = " + parentId);

        generatedParentId = parentId;

        //USING SPRELATIONPARENTID to GET PARENT DETAILS FROM PARENT TABLE AND DISPLAY THEM IN THE FXML PANE
        String fname = null;
        String lname = null;
        String contact = null;
        String email = null;

        rs = stmt.executeQuery("SELECT * FROM PARENTS;");
        System.out.println("Parent accessed successfully");

        while (rs.next()) {

            if (rs.getString("parentid").equalsIgnoreCase(generatedParentId)) {
                fname = rs.getString("firstname");
                lname = rs.getString("lastname");
                contact = rs.getString("contact");
                email = rs.getString("email");
            }
        }

        conn.close();

        System.out.println("PARENT ID = " + generatedParentId);
        System.out.println("FIRST NAME = " + fname);
        System.out.println("LAST NAME = " + lname);
        System.out.println("CONTACT = " + contact);
        System.out.println("EMAIL = " + email);

        parentFirstName.setText(fname);
        parentLastName.setText(lname);
        parentContact.setText(contact);
        parentEmail.setText(email);

    }

    public void exeUpdate() throws SQLException {

        System.out.println("run");

        String enteredId = idField.getText();

        obj.removeStudent(enteredId);
        obj.removeParent(enteredId);

        obj.addstudent(enteredId, firstName.getText(), lastName.getText(), standard.getText(), DOB.getValue(), school.getText(), notes.getText());
        obj.addparent(generatedParentId, parentFirstName.getText(), parentLastName.getText(), parentContact.getText(), parentEmail.getText());

        obj.createStudentParentRelation(enteredId, generatedParentId);

        closeButtonAction();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        // TODO
    }

}
