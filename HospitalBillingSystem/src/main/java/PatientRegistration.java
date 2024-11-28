import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientRegistration extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Patient Registration");

        // Create the root layout (GridPane)
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Set the background image
        grid.setStyle("-fx-background-image: url(file:/D:/image.jpg);" 
                    + "-fx-background-size: cover; -fx-background-position: center;");

        // Create and add form fields for patient registration
        Label nameLabel = new Label("Patient Name:");
        nameLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(nameLabel, 0, 0);

        TextField nameField = new TextField();
        nameField.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(nameField, 1, 0);

        Label ageLabel = new Label("Age:");
        ageLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(ageLabel, 0, 1);

        TextField ageField = new TextField();
        ageField.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(ageField, 1, 1);

        Label genderLabel = new Label("Gender:");
        genderLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(genderLabel, 0, 2);

        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setValue("Male");  // Default value
        genderComboBox.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(genderComboBox, 1, 2);

        Label contactLabel = new Label("Contact Number:");
        contactLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(contactLabel, 0, 3);

        TextField contactField = new TextField();
        contactField.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(contactField, 1, 3);

        // Create an address field
        Label addressLabel = new Label("Address:");
        addressLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(addressLabel, 0, 4);

        TextField addressField = new TextField();
        addressField.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(addressField, 1, 4);

        // Create a medical history field
        Label medicalHistoryLabel = new Label("Medical History:");
        medicalHistoryLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(medicalHistoryLabel, 0, 5);

        TextField medicalHistoryField = new TextField();
        medicalHistoryField.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: black;");
        grid.add(medicalHistoryField, 1, 5);

        // Register button setup
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-font-family: 'Roboto'; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: black;");
        grid.add(registerButton, 1, 6);

        // Set action for Register button
        registerButton.setOnAction(e -> {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderComboBox.getValue();
            String contactInfo = contactField.getText();
            String address = addressField.getText();
            String medicalHistory = medicalHistoryField.getText();

            // Insert the patient into the database
            String query = "INSERT INTO patients (name, age, gender, contact_info, address, medical_history) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setString(3, gender);
                stmt.setString(4, contactInfo);
                stmt.setString(5, address);
                stmt.setString(6, medicalHistory);

                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " patient(s) added.");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText("Patient Registered Successfully");
                alert.setContentText("The patient has been added to the system.");
                alert.showAndWait();

                // Clear the fields after registration
                nameField.clear();
                ageField.clear();
                contactField.clear();
                addressField.clear();
                medicalHistoryField.clear();

            } catch (SQLException ex) {
                System.out.println("Error inserting patient: " + ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Patient Registration Failed");
                alert.setContentText("There was an error while registering the patient.");
                alert.showAndWait();
            }
        });

        // Create the scene and display it
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void show() {
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }
}
