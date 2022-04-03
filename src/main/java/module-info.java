module com.example.java_project_2022 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.java_project_2022 to javafx.fxml;
    exports com.example.java_project_2022;
}