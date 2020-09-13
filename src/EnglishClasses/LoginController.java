/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Asad
 */
public class LoginController implements Initializable {

    EnglishClasses obj = new EnglishClasses();

    @FXML
    private Label message;

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private JFXTextField password;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void Login(ActionEvent event) throws IOException, SQLException {
        if (obj.checkPassword(password.getText())) {
            createTables();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("main.fxml"));
            rootPane.getChildren().setAll(pane);
        } else {
            message.setText("Incorrect Password");
        }
    }

    public static void createStudentTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:database.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS STUDENTS "
                + "(STUDENTID      TEXT    NOT NULL,"
                + " FIRSTNAME      TEXT    NOT NULL, "
                + " LASTNAME       TEXT    NOT NULL, "
                + " STANDARD       TEXT    NOT NULL, "
                + " DOB            TEXT    NOT NULL, "
                + " SCHOOL         TEXT    NOT NULL, "
                + " NOTES          TEXT)";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createParentTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:database.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS PARENTS "
                + "(PARENTID      TEXT     NOT NULL,"
                + " FIRSTNAME      TEXT    NOT NULL, "
                + " LASTNAME       TEXT    NOT NULL, "
                + " CONTACT        TEXT    NOT NULL, "
                + " EMAIL          TEXT    NOT NULL)";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createStudentParentRelationshipTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:database.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS SPRELATION "
                + "(STUDENTID      TEXT     NOT NULL,"
                + " PARENTID       TEXT    NOT NULL)";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createClassDetailsTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:database.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS CLASSES "
                + "(CLASSID      TEXT     NOT NULL,"
                + " FEE          REAL     NOT NULL,"
                + " SUMMARY      TEXT,"
                + " DURATION     INTEGER  NOT NULL)";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createClassLogTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:database.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS CLASSLOG "
                    + "(STUDENTID        TEXT     NOT NULL,"
                    + " CLASSID          TEXT     NOT NULL,"
                    + " DATE             TEXT     NOT NULL,"
                    + " STUDENTFEEDBACK  TEXT,"
                    + " PAYMENTSTATUS    NUMERIC  NOT NULL)";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTables() throws SQLException {
        createStudentTable();
        createStudentParentRelationshipTable();
        createClassDetailsTable();
        createParentTable();
        createClassLogTable();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
