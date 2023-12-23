module org.example.employejdbc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires java.sql;
    requires java.naming;

    opens org.example.employejdbc to javafx.fxml;
    exports org.example.employejdbc;
    exports org.example.employejdbc.Models;
    opens org.example.employejdbc.Models to javafx.fxml;
    exports org.example.employejdbc.Controllers;
    opens org.example.employejdbc.Controllers to javafx.fxml;
    exports org.example.employejdbc.datasource;
    opens org.example.employejdbc.datasource to javafx.fxml;
}