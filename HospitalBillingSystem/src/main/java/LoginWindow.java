import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Page");

        // Create login form components
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        userField.setPromptText("Enter your username");

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter your password");

        Button loginButton = new Button("Login");
        Label messageLabel = new Label("");  // To display login status

        // Styling components
        userLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        passLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        loginButton.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: black;");
        userField.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        passField.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");

        // Action for login button
        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();
            if (authenticateUser(username, password)) {
                messageLabel.setText("Login Successful!");
                messageLabel.setTextFill(Color.GREEN);
                openMainWindow(primaryStage);  // Open the main window after successful login
            } else {
                messageLabel.setText("Invalid username or password.");
                messageLabel.setTextFill(Color.RED);
            }
        });

        // Arrange nodes in a VBox layout
        VBox vbox = new VBox(10, userLabel, userField, passLabel, passField, loginButton, messageLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // Create a gradient background
        Stop[] stops = new Stop[]{
            new Stop(0, Color.CORNFLOWERBLUE),
            new Stop(1, Color.DARKSLATEBLUE)
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

        // Root pane setup
        BorderPane root = new BorderPane();
        root.setCenter(vbox);
        root.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, javafx.scene.layout.CornerRadii.EMPTY, Insets.EMPTY)));

        // Add background image using CSS style in root node
        root.setStyle("-fx-background-image: url('file:/D:/image.jpg');"
                + "-fx-background-size: cover;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Authenticate user using the database
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

    // Open the main window after successful login
    private void openMainWindow(Stage primaryStage) {
        // Create buttons for different sections (Patient Registration, Billing, etc.)
        Button patientRegistrationButton = new Button("Patient Registration");
        Button billingButton = new Button("Billing");
        Button invoiceButton = new Button("Invoices");
        Button medicationButton = new Button("Medications");
        Button servicesButton = new Button("Services");
        Button paymentsButton = new Button("Payments");

        // Event handlers for buttons
        patientRegistrationButton.setOnAction(e -> openPatientRegistration());
        billingButton.setOnAction(e -> openBilling());
        invoiceButton.setOnAction(e -> openInvoices());
        medicationButton.setOnAction(e -> openMedications());
        servicesButton.setOnAction(e -> openServices());
        paymentsButton.setOnAction(e -> openPayments());

        // Layout for the main window
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                patientRegistrationButton,
                billingButton,
                invoiceButton,
                medicationButton,
                servicesButton,
                paymentsButton
        );

        // Set the scene for the main window
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Methods to handle button clicks for each section
    private void openPatientRegistration() {
        PatientRegistration registration = new PatientRegistration();
        registration.show();
    }

    private void openBilling() {
        Billing billing = new Billing();
        billing.show();
    }

    private void openInvoices() {
        Invoices invoices = new Invoices();
        invoices.show();
    }

    private void openMedications() {
        Medications medications = new Medications();
        medications.show();
    }

    private void openServices() {
        Services services = new Services();
        services.show();
    }

    private void openPayments() {
        Payments payments = new Payments();
        payments.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
