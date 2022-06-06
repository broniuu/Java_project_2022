module java.com.example.java_project_2022 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    requires com.google.zxing;
    requires kernel;
    requires io;
    requires com.google.zxing.javase;
    requires layout;
    requires org.apache.logging.log4j.slf4j;
    requires java.mail;
    requires com.jfoenix;


    opens com.example.java_project_2022 to  javafx.fxml;

    exports com.example.java_project_2022;
    exports com.example.java_project_2022.Controlers;
    opens com.example.java_project_2022.Controlers to javafx.fxml;
}
