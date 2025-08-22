module com.app.projetobeetech {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.app.projetobeetech to javafx.fxml;
    exports com.app.projetobeetech;
    exports com.app.projetobeetech.controller;
    opens com.app.projetobeetech.controller to javafx.fxml;
}