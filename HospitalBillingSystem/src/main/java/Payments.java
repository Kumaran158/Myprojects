import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Payments extends Stage {

    public Payments() {
        setTitle("Payments");

        // Create TextFields with styling
        TextField billingIDField = new TextField();
        billingIDField.setPromptText("Enter Billing ID");
        billingIDField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter Payment Amount");
        amountField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        TextField dateField = new TextField();
        dateField.setPromptText("Enter Payment Date");
        dateField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Create Save Button with styling
        Button saveButton = new Button("Save Payment");
        saveButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: black;");

        // Action for Save Button
        saveButton.setOnAction(e -> savePayment(billingIDField.getText(), amountField.getText(), dateField.getText()));

        // Layout for the payment form
        VBox layout = new VBox(10);
        layout.getChildren().addAll(billingIDField, amountField, dateField, saveButton);

        // Set background image and styling
        layout.setStyle("-fx-background-image: url('file:/D:/image.jpg');"
                + "-fx-background-size: cover;");

        // Set the scene and window properties
        Scene scene = new Scene(layout, 400, 300);
        setScene(scene);
    }

    // Method to save payment information to the database
    private void savePayment(String billingID, String amount, String date) {
        // SQL query to insert payment details
        String query = "INSERT INTO payments (billing_id, amount, payment_date) VALUES (?, ?, ?)";

        // Connect to the database and execute the query
        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, billingID);
            statement.setString(2, amount);
            statement.setString(3, date);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Payment saved successfully.");
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save payment: " + e.getMessage());
        }
    }

    // Helper method to display alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
