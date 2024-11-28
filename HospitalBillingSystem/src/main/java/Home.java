import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Home extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Call method to open the main window after successful login
        openMainWindow(primaryStage);
    }

    // Method to open the main window with buttons for different sections
    private void openMainWindow(Stage primaryStage) {
        // Create buttons for different sections (Patient Registration, Billing, etc.)
        Button patientRegistrationButton = new Button("Patient Registration");
        Button billingButton = new Button("Billing");
        Button invoiceButton = new Button("Invoices");
        Button medicationButton = new Button("Medications");
        Button servicesButton = new Button("Services");
        Button paymentsButton = new Button("Payments");

        // Set button styles (fonts, size, etc.)
        patientRegistrationButton.setFont(new Font("Arial", 14));
        billingButton.setFont(new Font("Arial", 14));
        invoiceButton.setFont(new Font("Arial", 14));
        medicationButton.setFont(new Font("Arial", 14));
        servicesButton.setFont(new Font("Arial", 14));
        paymentsButton.setFont(new Font("Arial", 14));

        // Event handlers for buttons to open the corresponding sections
        patientRegistrationButton.setOnAction(e -> openPatientRegistration());
        billingButton.setOnAction(e -> openBilling());
        invoiceButton.setOnAction(e -> openInvoices());
        medicationButton.setOnAction(e -> openMedications());
        servicesButton.setOnAction(e -> openServices());
        paymentsButton.setOnAction(e -> openPayments());

        // Layout for the main window with vertical arrangement of buttons
        VBox layout = new VBox(20); // Adjust spacing between buttons
        layout.getChildren().addAll(
                patientRegistrationButton,
                billingButton,
                invoiceButton,
                medicationButton,
                servicesButton,
                paymentsButton
        );
        layout.setAlignment(Pos.CENTER); // Center-align buttons

        // Set background image
        StackPane root = new StackPane();
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:/D:/image.jpg"),  // Replace with your image path
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(backgroundImage));

        // Add layout to the root pane
        root.getChildren().add(layout);

        // Set the scene for the main window
        Scene scene = new Scene(root, 600, 400);  // Increase window size for better visibility
        primaryStage.setTitle("Hospital Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Methods to handle button clicks for each section
    private void openPatientRegistration() {
        // Logic to open the Patient Registration page
        System.out.println("Patient Registration Page Opened");
    }

    private void openBilling() {
        // Logic to open the Billing page
        System.out.println("Billing Page Opened");
    }

    private void openInvoices() {
        // Logic to open the Invoices page
        System.out.println("Invoices Page Opened");
    }

    private void openMedications() {
        // Logic to open the Medications page
        System.out.println("Medications Page Opened");
    }

    private void openServices() {
        // Logic to open the Services page
        System.out.println("Services Page Opened");
    }

    private void openPayments() {
        // Logic to open the Payments page
        System.out.println("Payments Page Opened");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
