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
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author User
 */
public class RemoveStudentController implements Initializable {

    EnglishClasses obj = new EnglishClasses();

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private Label alert;

    @FXML
    private JFXTextField idField;

    @FXML
    private JFXButton removeStudentButton;

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void removeStudent(ActionEvent event) throws SQLException {
        System.out.println("run");
        String enteredId = idField.getText();
        boolean isSuccessful = obj.removeStudent(enteredId);
        if (isSuccessful) {
            alert.setText("Incorrect ID entered");
        } else {
            obj.removeParent(enteredId);
            closeButtonAction();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
