package task.lab11;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Student {
    String name;
    String id;
    String email;

    public Student(String name, String id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }
}

class Course {
    String courseName;

    public Course(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return courseName;
    }
}

public class RegistrationSystem extends Application {
    List<Course> courses = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Student Registration System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label courseLabel = new Label("Courses:");
        ListView<Course> courseListView = new ListView<>();
        courseListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Button registerButton = new Button("Register");

        courses.add(new Course("OOP"));
        courses.add(new Course("ISE"));
        courses.add(new Course("IS"));
        courses.add(new Course("ECS"));
        courseListView.getItems().addAll(courses);

        registerButton.setOnAction(e -> {
            Student student = new Student(nameField.getText(), idField.getText(), emailField.getText());
            List<Course> selectedCourses = courseListView.getSelectionModel().getSelectedItems();

            try (BufferedReader reader = new BufferedReader(new FileReader("C:/Users/DELL PRECISION 5530/OneDrive/Desktop/REGISTER.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("ID: " + student.id)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Registration Error");
                        alert.setHeaderText(null);
                        alert.setContentText("A student with this ID is already registered.");
                        alert.showAndWait();
                        return;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/DELL PRECISION 5530/OneDrive/Desktop/REGISTER.txt", true))) {
                writer.write("Name: " + student.name + ", ID: " + student.id + ", Email: " + student.email);
                writer.newLine();
                for (Course course : selectedCourses) {
                    writer.write("Registered for: " + course.courseName);
                    writer.newLine();
                }
                writer.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            nameField.clear();
            idField.clear();
            emailField.clear();
            courseListView.getSelectionModel().clearSelection();
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(idLabel, 0, 2);
        gridPane.add(idField, 1, 2);
        gridPane.add(emailLabel, 0, 3);
        gridPane.add(emailField, 1, 3);
        gridPane.add(courseLabel, 0, 4);
        gridPane.add(courseListView, 1, 4);
        gridPane.add(registerButton, 0, 5, 2, 1);
        Scene scene = new Scene(gridPane, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
