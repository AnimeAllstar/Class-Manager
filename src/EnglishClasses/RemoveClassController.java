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
public class RemoveClassController implements Initializable {

    EnglishClasses obj = new EnglishClasses();

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private Label alert;

    @FXML
    private JFXTextField idField;

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void removeClass(ActionEvent event) throws SQLException {
        System.out.println("run");
        String enteredId = idField.getText();
        boolean isSuccessful = obj.removeClass(enteredId);
        if (isSuccessful) {
            alert.setText("Incorrect ID entered");
        } else {
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
