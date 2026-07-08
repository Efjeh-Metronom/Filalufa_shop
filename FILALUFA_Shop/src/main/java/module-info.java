module com.example.filalufa_shop {

    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires com.dlsc.formsfx;

    exports application;

    opens application;

    opens controller to javafx.fxml;

    opens model to javafx.base;

}