/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

/**
 * FXML Controller class
 *
 * @author Asad
 */
public class RemoveClassLogController implements Initializable {

    EnglishClasses obj = new EnglishClasses();
    boolean isFound;
    String cId;
    String sId;
    String formattedDate;
    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private JFXTextField classId;
    @FXML
    private JFXDatePicker date;
    @FXML
    private Label alert;

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void checkClass(ActionEvent event) throws IOException, SQLException {
        isFound = false;
        sId = obj.getId();
        cId = classId.getText();
        System.out.println("run");

        String date = null;
        String studentFeedback = null;
        Integer payment = null;
        String paymentStatus = null;

        LocalDate d = this.date.getValue();
        System.out.println(d);

        formattedDate = String.valueOf(d);
        System.out.println(formattedDate);

        Connection conn = obj.connect();
        System.out.println("Opened database successfully");

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM CLASSLOG");
        System.out.println("ClassLog accessed successfully");

        while (rs.next()) {

            System.out.println(rs.getString("classid") + " " + cId);

            System.out.println(rs.getString("date") + " " + formattedDate);

            if (rs.getString("studentid").equalsIgnoreCase(sId) && rs.getString("date").equals(formattedDate) && rs.getString("classid").equalsIgnoreCase(cId)) {
                System.out.println("entered");

                isFound = true;
            }
        }

        conn.close();

        if (isFound) {
            obj.removeClassLog(sId, cId, formattedDate);
            closeButtonAction();
        } else {
            alert.setText("No record found");
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
