/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AddClassController implements Initializable {

    EnglishClasses obj = new EnglishClasses();

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private JFXTextField fee, description, duration;

    @FXML
    private void completeEntry(ActionEvent event) throws SQLException {
        if (isClassFull()) {
            String i = (duration.getText()).trim();
            String d = fee.getText().trim();
            if (obj.validateClass(i, d)) {
                addClass();
                closeButtonAction();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, " ", ButtonType.OK);
            alert.setTitle("New Class Entry");
            alert.setHeaderText("Emppty Text Field");
            alert.setContentText("Please fiil all text fields");
            alert.showAndWait();
        }
    }

    public boolean isClassFull() {
        String f = fee.getText().trim();
        String d = (duration.getText()).trim();
        String s = description.getText().trim();
        return !f.equals("") && !d.equals("") && !s.equals("");
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

    public void addClass() throws SQLException {
        double f = Double.parseDouble(fee.getText());
        int d = Integer.parseInt(duration.getText());

        String classId = "c" + (new Random().nextInt(99999 - 10000) + 10000);
        obj.addClass(classId, f, description.getText().trim(), d);
        System.out.println("Class Added");
    }

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
    }
}
