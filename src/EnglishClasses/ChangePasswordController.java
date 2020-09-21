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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ChangePasswordController implements Initializable {

    EnglishClasses obj = new EnglishClasses();

    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private JFXTextField newPassword, oldPassword;
    @FXML
    private Label message;

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void changePassword(ActionEvent event) throws IOException {

        if (obj.checkPassword(oldPassword.getText())) {
            obj.deletePassword();
            obj.setPassword(newPassword.getText());
            closeButtonAction();
        } else {
            message.setText("Incorrect Password");
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
