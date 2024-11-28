import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class MainApp extends Application {

    public Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginPage(primaryStage);
    }

    private void showLoginPage(Stage primaryStage) {
        primaryStage.setTitle("Login Page");

        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        userField.setPromptText("Enter your username");

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter your password");

        Button loginButton = new Button("Login");
        Label messageLabel = new Label("");

        userLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        passLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        loginButton.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: black;");
        userField.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        passField.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");

        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();
            if (authenticateUser(username, password)) {
                messageLabel.setText("Login Successful!");
                messageLabel.setTextFill(Color.GREEN);
                showMainWindow(primaryStage);
            } else {
                messageLabel.setText("Invalid username or password.");
                messageLabel.setTextFill(Color.RED);
            }
        });

        VBox vbox = new VBox(10, userLabel, userField, passLabel, passField, loginButton, messageLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Stop[] stops = new Stop[] {
            new Stop(0, Color.CORNFLOWERBLUE),
            new Stop(1, Color.DARKSLATEBLUE)
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);
        root.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, javafx.scene.layout.CornerRadii.EMPTY, Insets.EMPTY)));

        root.setStyle("-fx-background-image: url('file:/D:/image.jpg');"
                + "-fx-background-size: cover;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            return false;
        }
    }

    private void showMainWindow(Stage primaryStage) {
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #333;");
        backButton.setOnAction(e -> showLoginPage(primaryStage));
    
        Button patientRegistrationButton = new Button("Patient Registration");
        Button billingButton = new Button("Billing");
        Button invoiceButton = new Button("Invoices");
        Button medicationButton = new Button("Medications");
        Button servicesButton = new Button("Services");
        Button paymentsButton = new Button("Payments");
    
        Button deleteAllButton = new Button("Delete All Records");
        deleteAllButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: red;");
        deleteAllButton.setOnAction(e -> deleteAllRecords());
    
        patientRegistrationButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        billingButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        invoiceButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        medicationButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        servicesButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        paymentsButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
    
        patientRegistrationButton.setOnAction(e -> openPatientRegistration());
        billingButton.setOnAction(e -> openBilling());
        invoiceButton.setOnAction(e -> openInvoices());
        medicationButton.setOnAction(e -> openMedications());
        servicesButton.setOnAction(e -> openServices());
        paymentsButton.setOnAction(e -> openPayments());
    
        VBox layout = new VBox(20);
        layout.getChildren().addAll(
                backButton,
                patientRegistrationButton,
                billingButton,
                invoiceButton,
                medicationButton,
                servicesButton,
                paymentsButton,
                deleteAllButton // Add the Delete button here
        );
        layout.setAlignment(Pos.CENTER);
    
        StackPane root = new StackPane();
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image("file:/D:/image.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(backgroundImage));
    
        root.getChildren().add(layout);
    
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Hospital Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    private void openPatientRegistration() {
        PatientRegistration registrationWindow = new PatientRegistration();
        Stage registrationStage = new Stage();
        registrationWindow.start(registrationStage);
    }

    private void openBilling() {
        Billing billingWindow = new Billing();
        billingWindow.show();
    }

    private void openInvoices() {
        Invoices invoicesWindow = new Invoices();
        invoicesWindow.show();
    }

    private void openMedications() {
        Medications medicationsWindow = new Medications();
        medicationsWindow.show();
    }

    private void openServices() {
        Services servicesWindow = new Services();
        servicesWindow.show();
    }

    private void openPayments() {
        Payments paymentsWindow = new Payments();
        paymentsWindow.show();
    }
    private void deleteAllRecords() {
        String[] tables = {"patients", "services", "invoices", "payments", "billing", "medications"};
        try (Connection connection = DatabaseConnection.connect()) {
            for (String table : tables) {
                String deleteQuery = "DELETE FROM " + table;
                try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
                    stmt.executeUpdate();
                }
            }
            showAlert(Alert.AlertType.INFORMATION, "Delete All Records", "All records have been deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting records: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Delete All Records", "An error occurred while deleting records.");
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
