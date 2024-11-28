import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class Services extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Services");

        // Create TextFields with styling
        TextField serviceNameField = new TextField();
        serviceNameField.setPromptText("Enter Service Name");
        serviceNameField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter Description");
        descriptionField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Cr`eate Save Button with styling
        Button saveButton = new Button("Save Service");
        saveButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: black;");

        // Layout for the Services form
        VBox layout = new VBox(10);
        layout.getChildren().addAll(serviceNameField, descriptionField, saveButton);

        // Set background image and additional styles
        layout.setStyle("-fx-background-image: url('file:/D:/image.jpg');"
                + "-fx-background-size: cover;");

        // Set the scene and window properties
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Set action for Save Service button
        saveButton.setOnAction(e -> {
            String serviceName = serviceNameField.getText();
            String description = descriptionField.getText();

            // Insert the service into the database
            String query = "INSERT INTO services (service_name, description) VALUES (?, ?)";
            try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setString(1, serviceName);
                stmt.setString(2, description);

                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " service(s) added.");

                // Display success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Service Registration");
                alert.setHeaderText("Service Saved Successfully");
                alert.setContentText("The service has been added to the system.");
                alert.showAndWait();

                // Clear fields after saving
                serviceNameField.clear();
                descriptionField.clear();

            } catch (SQLException ex) {
                System.out.println("Error inserting service: " + ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Service Registration Failed");
                alert.setContentText("There was an error while saving the service.");
                alert.showAndWait();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Database connection class
    public static class DatabaseConnection {
        public static Connection connect() throws SQLException {
            // Replace with your MySQL connection details
            String url = "jdbc:mysql://localhost:3306/your_database";
            String username = "root";
            String password = "kumaran";
            
            return java.sql.DriverManager.getConnection(url, username, password);
        }
    }

    public void show() {
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }
}
