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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Asad
 */
public class ViewStudentController implements Initializable {

    @FXML
    public JFXTextField idField;
    public String enteredId = null;
    public String generatedParentId = null;
    public Boolean isFound = false;
    /**
     * Initializes the controller class.
     */
    // ViewStudnetController
    public boolean isOpen = false;
    EnglishClasses obj = new EnglishClasses();
    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private JFXButton edit, remove, log, enter, reset;
    @FXML
    private JFXTextField sFirstName, sLastName, sSchool, sStandard;
    @FXML
    private JFXTextArea sNotes;
    @FXML
    private JFXDatePicker sDOB;
    @FXML
    private JFXTextField pFirstName, pLastName, pContact, pEmail;
    @FXML
    private Pane mainPane;
    @FXML
    private Label alert, sId, pId;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void newPane(ActionEvent event) throws SQLException, IOException {
        JFXButton source = (JFXButton) event.getSource();
        if (source == edit) {
            if (isStudentFull() && isParentFull()) {
                if (obj.validateStudent(sFirstName.getText(), sLastName.getText(), sDOB.getValue())
                        && obj.validateParent(pFirstName.getText(), pLastName.getText(), pContact.getText(), pEmail.getText())) {
                    exeUpdate();
                }
            }

        } else if (source == remove) {
            removeStudent(idField.getText());
            removeParent(idField.getText());
            closeButtonAction();
        } else if (source == log) {
            if (isOpen) {
                closeButtonAction();
            } else {
                obj.setId(idField.getText());
                obj.newWindow("StudentLog");
            }
        }
    }

    public boolean isStudentFull() {
        String sF = sFirstName.getText().trim();
        String sL = sLastName.getText().trim();
        String sStan = this.sStandard.getText().trim();
        LocalDate sD = sDOB.getValue();
        String sSch = this.sSchool.getText().trim();

        if (sF.equals("") || sL.equals("") || sStan.equals("") || sSch.equals("") || sD == null) {
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
        String pC = pContact.getText().trim();
        String pE = (pEmail.getText()).trim();
        String pL = pLastName.getText().trim();
        String pF = pFirstName.getText().trim();

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

    public void exeUpdate() throws SQLException {

        System.out.println("run");

        String enteredId = idField.getText();

        obj.removeStudent(enteredId);
        obj.removeParent(enteredId);

        obj.addstudent(enteredId, sFirstName.getText(), sLastName.getText(), sStandard.getText(), sDOB.getValue(), sSchool.getText(), sNotes.getText());
        obj.addparent(generatedParentId, pFirstName.getText(), pLastName.getText(), pContact.getText(), pEmail.getText());

        obj.createStudentParentRelation(enteredId, generatedParentId);

    }

    @FXML
    private void reloadData(ActionEvent event) throws SQLException, IOException {
        getStudent(idField.getText());
        getParent(idField.getText());
    }

    @FXML
    public void getStudent(String enteredId) throws IOException, SQLException {
        System.out.println("run");
        isFound = false;

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
            sId.setText("Student Information - " + id);
            sFirstName.setText(fname);
            sLastName.setText(lname);
            sStandard.setText(stan);
            sSchool.setText(school);
            LocalDate dateOfBirth = LocalDate.parse(DOB);
            sDOB.setValue(dateOfBirth);
            sNotes.setText(notes);
        } else {
            alert.setText("Incorrect ID entered");
        }
    }

    public void getParent(String enteredId) throws IOException, SQLException {

        //SCANNING SPRELATION TABLE FOR MATCHING PARNET ID
        String parentId = null, studentId = null;

        Connection conn = obj.connect();
        System.out.println("Opened database successfully");

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SPRELATION;");
        System.out.println("spRelation accessed successfully");

        System.out.println("REQUIRED STUDENT ID = " + enteredId);
        System.out.println();

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

        pId.setText("Parent Information - " + generatedParentId);
        pFirstName.setText(fname);
        pLastName.setText(lname);
        pContact.setText(contact);
        pEmail.setText(email);

    }

    public void removeStudent(String enteredId) {

        String sql = "DELETE FROM STUDENTS WHERE STUDENTID = ?";

        try (Connection conn = obj.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, enteredId);
            // execute the delete statement
            pstmt.executeUpdate();

            System.out.println("Student removed successfully");
            obj.displayData();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeParent(String enteredId) throws SQLException {

        String parentId = null;

        boolean isRepeated = false;
        int count = 0;

        Connection conn = obj.connect();
        System.out.println("Opened database successfully");

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SPRELATION;");
        System.out.println("spRelation accessed successfully");

        while (rs.next()) {

            if (rs.getString("studentid").equalsIgnoreCase(enteredId)) {
                parentId = rs.getString("parentid");
            }
        }

        System.out.println("STUDENT ID = " + enteredId);
        System.out.println("FIRST NAME = " + parentId);

        //check is parent is repeated
        while (rs.next()) {

            if (rs.getString("parentId").equalsIgnoreCase(parentId)) {
                count++;
            }
        }

        if (count >= 2) {
            isRepeated = true;
        }

        //Delete Relation
        String sql = "DELETE FROM SPRELATION WHERE STUDENTID = ?";

        // set the corresponding param
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, enteredId);
            // execute the delete statement
            pstmt.executeUpdate();

            System.out.println("spRelation removed successfully");
            obj.displayData();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //Delete Parent table reocrd
        if (!isRepeated) {
            sql = "DELETE FROM PARENTS WHERE PARENTID = ?";

            // set the corresponding param
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // set the corresponding param
                pstmt.setString(1, parentId);
                // execute the delete statement
                pstmt.executeUpdate();

                System.out.println("parent removed successfully");
                obj.displayData();
                conn.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void changePane(ActionEvent event) throws IOException, SQLException {
        getStudent(idField.getText());
        if (isFound) {
            getParent(idField.getText());
            mainPane.toFront();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println(obj.getId());
            if (obj.getId() != null) {
                try {
                    isOpen = true;
                    getStudent(obj.getId());
                    getParent(obj.getId());
                    mainPane.toFront();
                    idField.setText(obj.getId());
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
