/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import com.jfoenix.controls.JFXButton;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Asad
 */
public class EditClassController implements Initializable {

    EnglishClasses obj = new EnglishClasses();
    String enteredId;
    Boolean isFound;
    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private JFXTextField fee, description, duration, idField;
    @FXML
    private JFXButton edit, search;
    @FXML
    private Label alert;
    @FXML
    private Pane mainPane;

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void changePane(ActionEvent event) throws SQLException, IOException {
        JFXButton source = (JFXButton) event.getSource();
        if (source == edit) {
            String i = (duration.getText()).trim();
            String d = fee.getText().trim();
            if (isClassFull()) {
                if (obj.validateClass(i, d)) {
                    exeUpdate();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " ", ButtonType.OK);
                alert.setTitle("Edit Class Entry");
                alert.setHeaderText("Emppty Text Field");
                alert.setContentText("Please fiil all text fields");
                alert.showAndWait();
            }
        } else if (source == search) {
            editClass();
        }
    }

    public boolean isClassFull() {
        String f = fee.getText().trim();
        String d = (duration.getText()).trim();
        String s = description.getText().trim();
        return !f.equals("") && !d.equals("") && !s.equals("");
    }

    @FXML
    public void editClass() throws SQLException {
        isFound = false;
        System.out.println("run");
        enteredId = idField.getText();

        Double price = null;
        String summary = null;
        Integer dur = null;

        Connection conn = obj.connect();
        System.out.println("Opened database successfully");

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM CLASSES;");
        System.out.println("Class accessed successfully");

        while (rs.next()) {

            if (rs.getString("classid").equalsIgnoreCase(enteredId)) {
                price = rs.getDouble("fee");
                summary = rs.getString("summary");
                dur = rs.getInt("duration");

                isFound = true;
            }
        }

        conn.close();

        if (isFound) {
            fee.setText(String.valueOf(price));
            description.setText(summary);
            duration.setText(String.valueOf(dur));
            mainPane.toFront();
        } else {
            alert.setText("Incorrect ID entered");
        }
    }

    public void exeUpdate() throws SQLException {

        System.out.println("run");

        String enteredId = idField.getText();

        obj.removeClass(enteredId);

        double f = Double.parseDouble(fee.getText());
        int d = Integer.parseInt(duration.getText());

        obj.addClass(enteredId, f, description.getText(), d);

        closeButtonAction();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
