/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Asad
 */
public class EnglishClasses extends Application {

    public final static String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public final static String phoneRegex = "^[0-9]{10}$";
    public final static String nameRegex = "^[\\p{L} .'-]+$";
    public static Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
    public static Pattern phonePattern = Pattern.compile(phoneRegex);
    public static Pattern namePattern = Pattern.compile(nameRegex, Pattern.CASE_INSENSITIVE);
    public Matcher matcher;
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        newWindow("Login");
    }

    public boolean validateEmail(String email) {
        matcher = emailPattern.matcher(email.trim());
        return matcher.matches();
    }

    public boolean validateName(String name) {
        matcher = namePattern.matcher(name.trim());
        return matcher.matches();
    }

    public boolean validatePhone(String phone) {
        matcher = phonePattern.matcher(phone.trim());
        return matcher.matches();
    }

    public boolean validateInt(String i) {
        try {
            int p = Integer.parseInt(i.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validateDouble(String i) {
        try {
            double p = Double.parseDouble(i.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validateDate(LocalDate d) {

        LocalDate rn = LocalDate.now();

        return !d.isAfter(rn);
    }

    public boolean validateStudent(String f, String l, LocalDate d) {
        if (!validateName(f)) {
            alert("Student", "First Name");
            return false;
        } else if (!validateName(l)) {
            alert("Student", "Last Name");

            return false;
        } else if (!validateDate(d)) {
            alert("Student", "Date of Birth");

            return false;
        } else {
            return true;
        }
    }

    public boolean validateClass(String i, String d) {
        if (!validateDouble(d)) {
            alert("Class", "Fee");
            return false;

        } else if (!validateInt(i)) {
            alert("Class", "Duration");
            return false;

        } else {
            return true;
        }
    }

    public void alert(String obj, String variable) {
        Alert alert = new Alert(Alert.AlertType.ERROR, " ", ButtonType.OK);
        alert.setTitle("New " + obj + " Entry");
        alert.setHeaderText("Invalid " + variable);
        alert.setContentText("Please enter a valid " + variable);
        alert.showAndWait();
    }

    public boolean validateParent(String f, String l, String p, String e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, " ", ButtonType.OK);
        if (!validateName(f)) {
            alert("Parent", "First Name");

            return false;
        } else if (!validateName(l)) {
            alert("Parent", "Last Name");

            return false;
        } else if (!validatePhone(p.trim())) {
            alert("Parent", "Contact Number");

            return false;
        } else if (!validateEmail(e.trim())) {
            alert("Parent", "Email");

            return false;
        } else {
            return true;
        }
    }

    public void newWindow(String name) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(name + ".fxml"));
        Scene scene = new Scene(root, Color.TRANSPARENT);
        root.setStyle("-fx-background-color: transparent;");
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showPane(Pane pane) {
        pane.toFront();
    }

    //Checking Password from RandomAccessFile
    public boolean checkPassword(String entered) throws IOException {

        RandomAccessFile raf = new RandomAccessFile("loginDetails.txt", "r");
        raf.seek(0);

        String pass = raf.readLine();

        raf.close();

        return entered.equals(pass);
    }

    public void deletePassword() throws IOException {
        Scanner fileScanner = new Scanner("loginDetails.txt");
        fileScanner.nextLine();
        FileWriter fileStream = new FileWriter("loginDetails.txt");
        BufferedWriter out = new BufferedWriter(fileStream);
        while (fileScanner.hasNextLine()) {
            String next = fileScanner.nextLine();
            if (next.equals("\n")) {
                out.newLine();
            } else {
                out.write(next);
            }
            out.newLine();
        }
        out.close();
    }

    public void setPassword(String newPassword) throws IOException {

        byte[] pass = newPassword.getBytes();

        System.out.println(Arrays.toString(pass));

        RandomAccessFile raf = new RandomAccessFile("loginDetails.txt", "rw");

        raf.write(pass);

        raf.seek(0);

        System.out.println("" + raf.readByte());
    }

    public Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:database.db";
        Connection conn;
        try {
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void addstudent(String id, String fname, String lname, String standard, LocalDate DOB, String school, String notes) throws SQLException {
        String sql = "INSERT INTO STUDENTS(STUDENTID,FIRSTNAME,LASTNAME,STANDARD,DOB,SCHOOL,NOTES) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setString(4, standard);
            pstmt.setObject(5, DOB);
            pstmt.setString(6, school);
            pstmt.setString(7, notes);

            pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Student added successfully");
        //displayData();

    }

    public void addparent(String id, String fname, String lname, String contact, String email) {
        String sql = "INSERT INTO PARENTS(PARENTID,FIRSTNAME,LASTNAME,CONTACT,EMAIL) VALUES(?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setString(4, contact);
            pstmt.setObject(5, email);

            pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Parent added successfully");
        //displayParentData();

    }

    public void addClass(String classId, double fee, String summary, int duration) {
        String sql = "INSERT INTO CLASSES (CLASSID,FEE,SUMMARY,DURATION) VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classId);
            pstmt.setDouble(2, fee);
            pstmt.setString(3, summary);
            pstmt.setInt(4, duration);

            pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Class added successfully");
        //displayParentData();

    }

    public void createStudentParentRelation(String sId, String pId) {
        String sql = "INSERT INTO SPRELATION(STUDENTID,PARENTID) VALUES(?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println(sId + ", " + pId);
            pstmt.setString(1, sId);
            pstmt.setString(2, pId);

            pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Student-Parent Relation created successfully");
        //displayData();

    }

    public void createStudentClassRelation(String sId, String cId, LocalDate date, String studentFeedback, int PaymentStatus) {

        String sql = "INSERT INTO CLASSLOG(STUDENTID,CLASSID,DATE,STUDENTFEEDBACK,PAYMENTSTATUS) VALUES(?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println(sId + ", " + cId);
            pstmt.setString(1, sId);
            pstmt.setString(2, cId);
            pstmt.setObject(3, date);
            pstmt.setString(4, studentFeedback);
            pstmt.setInt(5, PaymentStatus);

            pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Student-Class Relation created successfully");
        //displayData();

    }

    public boolean removeStudent(String enteredId) {
        String sql = "DELETE FROM STUDENTS WHERE STUDENTID = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, enteredId);
            // execute the delete statement
            pstmt.executeUpdate();

            System.out.println("Student removed successfully");
            displayData();
            pstmt.close();
            conn.close();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

    public boolean removeClass(String enteredId) {
        String sql = "DELETE FROM CLASSES WHERE CLASSID = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, enteredId);
            // execute the delete statement
            pstmt.executeUpdate();

            System.out.println("Class removed successfully");
            displayData();
            pstmt.close();
            conn.close();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean removeClassLog(String sId, String cId, String date) {
        System.out.println(sId);
        String sql = "DELETE FROM CLASSLOG WHERE STUDENTID = ? AND CLASSID = ? AND DATE = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, sId);
            pstmt.setString(2, cId);
            pstmt.setString(3, date);

            // execute the delete statement
            pstmt.executeUpdate();

            System.out.println("ClassLog removed successfully");
            displayData();
            pstmt.close();
            conn.close();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

    public void removeParent(String enteredId) throws SQLException {

        String parentId = null;
        String studentId = enteredId;

        boolean isRepeated = false;
        int count = 0;

        Connection conn = connect();
        System.out.println("Opened database successfully");

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SPRELATION;");
        System.out.println("spRelation accessed successfully");

        while (rs.next()) {

            if (rs.getString("studentid").equals(studentId)) {
                parentId = rs.getString("parentid");
            }
        }

        System.out.println("STUDENT ID = " + studentId);
        System.out.println("PARENT ID = " + parentId);

        //check if parent is repeated
        while (rs.next()) {

            if (rs.getString("parentId").equals(parentId)) {
                count++;
            }
            System.out.println("COUNT " + count);

        }

        if (count >= 2) {
            isRepeated = true;
        }

        //Delete Relations
        String sql = "DELETE FROM SPRELATION WHERE STUDENTID = ?";

        // set the corresponding param
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, studentId);
            // execute the delete statement
            pstmt.executeUpdate();

            System.out.println("spRelation removed successfully");
            displayData();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "DELETE FROM CLASSLOG WHERE STUDENTID = ?";

        // set the corresponding param
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, studentId);
            // execute the delete statement
            pstmt.executeUpdate();

            System.out.println("scRelation removed successfully");
            displayData();
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
                displayData();
                conn.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void displayData() throws SQLException {
        try {
            Connection conn = connect();
            System.out.println("Opened database successfully");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS;");

            while (rs.next()) {
                String id = rs.getString("studentid");
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String standard = rs.getString("standard");
                String dob = rs.getString("dob");
                String sc = rs.getString("SCHOOL");
                String notes = rs.getString("notes");

                System.out.println("STUDENT ID = " + id);
                System.out.println("FIRST NAME = " + fname);
                System.out.println("LAST NAME = " + lname);
                System.out.println("STANDARD = " + standard);
                System.out.println("DOB = " + dob);
                System.out.println("SCHOOL = " + sc);
                System.out.println("NOTES = " + notes);

                System.out.println();
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public void displayParentData() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PARENTS;");

            while (rs.next()) {
                String id = rs.getString("parentid");
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String contact = rs.getString("contact");
                String email = rs.getString("email");

                System.out.println("PARENT ID = " + id);
                System.out.println("FIRST NAME = " + fname);
                System.out.println("LAST NAME = " + lname);
                System.out.println("CONTACT = " + contact);
                System.out.println("EMAIL = " + email);

                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public void displaySpRelationtData() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SPRELATION;");

            while (rs.next()) {
                String SID = rs.getString("studentid");
                String PID = rs.getString("parentid");

                System.out.println("STUDENT ID = " + SID);
                System.out.println("PARENT ID = " + PID);

                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public void displayScRelationtData() {
        Connection c = connect();
        Statement stmt;
        try {
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CLASSLOG;");

            while (rs.next()) {
                String SID = rs.getString("studentid");
                String PID = rs.getString("classid");
                String d = rs.getString("date");
                String sf = rs.getString("studentfeedback");
                String ps = rs.getString("paymentstatus");

                System.out.println("STUDENT ID = " + SID);
                System.out.println("PARENT ID = " + PID);
                System.out.println("DATE = " + d);
                System.out.println("St = " + sf);
                System.out.println("Ps = " + ps);

                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public String getId() throws IOException {

        RandomAccessFile raf = new RandomAccessFile("id.txt", "r");
        raf.seek(0);

        String test = raf.readLine();

        raf.close();

        return test;
    }

    public void setId(String id) {
        try {
            byte[] i;
            i = id.getBytes();

            RandomAccessFile raf = new RandomAccessFile("id.txt", "rw");

            raf.write(i);

            raf.seek(0);

            System.out.println("" + raf.readByte());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteId() throws IOException {
        Scanner fileScanner = new Scanner("id.txt");
        fileScanner.nextLine();
        FileWriter fileStream = new FileWriter("id.txt");
        BufferedWriter out = new BufferedWriter(fileStream);
        while (fileScanner.hasNextLine()) {
            String next = fileScanner.nextLine();
            if (next.equals("\n")) {
                out.newLine();
            } else {
                out.write(next);
            }
            out.newLine();
        }
        out.close();
    }
}
