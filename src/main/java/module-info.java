module com.example.java_project_2022 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.java_project_2022 to javafx.fxml;
    exports com.example.java_project_2022;
}