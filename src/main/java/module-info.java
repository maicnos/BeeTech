module com.app.projetobeetech {
    requires javafx.controls;
    requires javafx.fxml;
    requires jbcrypt;


    opens com.app.projetoBeeTech to javafx.fxml;
    exports com.app.projetoBeeTech;
    exports com.app.projetoBeeTech.controller;
    opens com.app.projetoBeeTech.controller to javafx.fxml;
}