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

public class Billing {

    public Billing() {
        Stage billingStage = new Stage();  // Create a new Stage instance for the Billing window
        billingStage.setTitle("Billing");

        // Create TextFields
        TextField patientIDField = new TextField();
        patientIDField.setPromptText("Enter Patient ID");
        patientIDField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        TextField serviceField = new TextField();
        serviceField.setPromptText("Enter Service");
        serviceField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter Amount");
        amountField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Create a Save Button
        Button saveButton = new Button("Save Billing");
        saveButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: black;");

        // Set the save action
        saveButton.setOnAction(e -> {
            String patientID = patientIDField.getText();
            String service = serviceField.getText();
            String amountText = amountField.getText();

            // Input validation
            if (patientID.isEmpty() || service.isEmpty() || amountText.isEmpty()) {
                showAlert(AlertType.ERROR, "Validation Error", "Please fill out all fields.");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                saveBillingToDatabase(patientID, service, amount);
                showAlert(AlertType.INFORMATION, "Success", "Billing information saved successfully.");
                
                // Clear fields after saving
                patientIDField.clear();
                serviceField.clear();
                amountField.clear();
            } catch (NumberFormatException ex) {
                showAlert(AlertType.ERROR, "Invalid Input", "Amount must be a valid number.");
            }
        });

        // Layout for the billing form
        VBox layout = new VBox(10);
        layout.getChildren().addAll(patientIDField, serviceField, amountField, saveButton);

        // Set background image with CSS
        layout.setStyle("-fx-background-image: url('file:/D:/image.jpg');"
                + "-fx-background-size: cover;");

        // Set the scene and window properties
        Scene scene = new Scene(layout, 400, 300);
        billingStage.setScene(scene);
        billingStage.show(); // Show the Billing window
    }

    // Database connection details
    private Connection connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hospital_management";
        String user = "root";  // replace with your MySQL username
        String password = "kumaran";  // replace with your MySQL password
        return DriverManager.getConnection(url, user, password);
    }

    // Save billing information to database
    private void saveBillingToDatabase(String patientID, String service, double amount) {
        String sql = "INSERT INTO billing (patient_id, service, amount) VALUES (?, ?, ?)";

        try (Connection conn = connectToDatabase();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, patientID);
            pstmt.setString(2, service);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Failed to save billing information: " + e.getMessage());
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

    public void show() {
        // Assuming this method is supposed to show the billing window
    }
}
