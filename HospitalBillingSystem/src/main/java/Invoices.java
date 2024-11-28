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

public class Invoices extends Stage {

    public Invoices() {
        setTitle("Invoices");

        // Create TextFields with CSS styles
        TextField billingIDField = new TextField();
        billingIDField.setPromptText("Enter Billing ID");
        billingIDField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        TextField dateField = new TextField();
        dateField.setPromptText("Enter Date (YYYY-MM-DD)");
        dateField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        TextField totalField = new TextField();
        totalField.setPromptText("Enter Total");
        totalField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Create Save Button with style
        Button saveButton = new Button("Save Invoice");
        saveButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: black;");

        // Set the save action
        saveButton.setOnAction(e -> {
            String billingID = billingIDField.getText();
            String date = dateField.getText();
            String totalText = totalField.getText();

            // Input validation
            if (billingID.isEmpty() || date.isEmpty() || totalText.isEmpty()) {
                showAlert(AlertType.ERROR, "Validation Error", "Please fill out all fields.");
                return;
            }

            try {
                double total = Double.parseDouble(totalText);
                saveInvoiceToDatabase(billingID, date, total);
                showAlert(AlertType.INFORMATION, "Success", "Invoice saved successfully.");
                
                // Clear fields after saving
                billingIDField.clear();
                dateField.clear();
                totalField.clear();
            } catch (NumberFormatException ex) {
                showAlert(AlertType.ERROR, "Invalid Input", "Total must be a valid number.");
            }
        });

        // Layout for the invoice form
        VBox layout = new VBox(10);
        layout.getChildren().addAll(billingIDField, dateField, totalField, saveButton);

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

    // Save invoice information to the database
    private void saveInvoiceToDatabase(String billingID, String date, double total) {
        String sql = "INSERT INTO invoices (billing_id, date, total) VALUES (?, ?, ?)";

        try (Connection conn = connectToDatabase();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, billingID);
            pstmt.setString(2, date);
            pstmt.setDouble(3, total);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Failed to save invoice: " + e.getMessage());
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
