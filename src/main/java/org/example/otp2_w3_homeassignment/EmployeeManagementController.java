package org.example.otp2_w3_homeassignment;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmployeeManagementController {
    private Dotenv dotenv = Dotenv.load();
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    Button saveButton;
    @FXML
    private void initialize() {
        addLanguageChangeListener();
        addSaveButtonListener();
    }

    private void addLanguageChangeListener() {
        languageComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("English")) {
                setEn(null);
            } else if (newValue.equals("Farsi")) {
                setIR(null);
            } else if (newValue.equals("Japanese")) {
                setJP(null);
            }
        });
    }

    private void addSaveButtonListener() {
        saveButton.setOnAction(event -> {
            String selectedLanguage = languageComboBox.getPromptText();
            String tableName = "";
            if (selectedLanguage.equals("English")) {
                tableName = "employee_en";
            }
            if (selectedLanguage.equals("Farsi")) {
                tableName = "employee_fa";
            }
            if (selectedLanguage.equals("Japanese")) {
                tableName = "employee_jp";
            }
            updateEmployeeTable(tableName);
        });
    }

    private void updateEmployeeTable(String tableName) {
        String url = dotenv.get("DB_NAME");
        String user = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO " + tableName + " (first_name, last_name, email) VALUES (?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, firstname.getText());
                statement.setString(2, lastname.getText());
                statement.setString(3, email.getText());
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setEn(ActionEvent actionEvent) {
        Locale l = new Locale("en", "EN");
        loadView(l);
    }

    public void setIR(ActionEvent actionEvent) {
        Locale l = new Locale("fa", "IR");
        loadView(l);
    }

    public void setJP(ActionEvent actionEvent) {
        Locale l = new Locale("ja", "JP");
        loadView(l);
    }

    public void loadView(Locale locale) {
        FXMLLoader fxmlLoader = new FXMLLoader(EmployeeManagementController.class.getResource("view.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("ui_language", locale));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) languageComboBox.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 300));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}