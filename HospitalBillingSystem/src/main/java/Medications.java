import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Medications extends Stage {

    public Medications() {
        setTitle("Medications");

        // Create TextFields with CSS styles
        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Enter Patient ID");
        patientIDField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        TextField medicationField = new TextField();
        medicationField.setPromptText("Enter Medication");
        medicationField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        TextField dosageField = new TextField();
        dosageField.setPromptText("Enter Dosage");
        dosageField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Create Save Button with style
        Button saveButton = new Button("Save Medication");
        saveButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: black;");

        // Set the save action
        saveButton.setOnAction(e -> {
            String patientID = patientIDField.getText();
            String medication = medicationField.getText();
            String dosage = dosageField.getText();

            // Input validation
            if (patientID.isEmpty() || medication.isEmpty() || dosage.isEmpty()) {
                showAlert(AlertType.ERROR, "Validation Error", "Please fill out all fields.");
                return;
            }

            // Save medication to the database
            saveMedicationToDatabase(patientID, medication, dosage);
            showAlert(AlertType.INFORMATION, "Success", "Medication saved successfully.");

            // Clear fields after saving
            patientIDField.clear();
            medicationField.clear();
            dosageField.clear();
        });

        // Layout for the medications form
        VBox layout = new VBox(10);
        layout.getChildren().addAll(patientIDField, medicationField, dosageField, saveButton);

        // Set background image with CSS style
        layout.setStyle("-fx-background-image: url('file:/D:/image.jpg');"
                + "-fx-background-size: cover;");

        // Set the scene and window properties
        Scene scene = new Scene(layout, 400, 300);
        setScene(scene);
    }

    // Database connection details
    private Connection connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hospital_management";
        String user = "root";  // replace with your MySQL username
        String password = "kumaran";  // replace with your MySQL password
        return DriverManager.getConnection(url, user, password);
    }

    // Save medication information to the database
    private void saveMedicationToDatabase(String patientID, String medication, String dosage) {
        String sql = "INSERT INTO medications (patient_id, medication, dosage) VALUES (?, ?, ?)";

        try (Connection conn = connectToDatabase();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patientID);
            pstmt.setString(2, medication);
            pstmt.setString(3, dosage);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Failed to save medication: " + e.getMessage());
        }
    }

    // Show alert helper method
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
