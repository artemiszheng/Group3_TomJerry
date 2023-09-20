module com.example.group3_tomjerry {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.group3_tomjerry to javafx.fxml;
    exports com.example.group3_tomjerry;
}