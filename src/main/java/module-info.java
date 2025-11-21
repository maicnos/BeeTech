module com.app.projetobeetech {
    requires javafx.controls;
    requires javafx.fxml;
    requires jbcrypt;
    requires java.sql;
    requires java.desktop;


    opens com.app.projetoBeeTech to javafx.fxml;
    exports com.app.projetoBeeTech;
    exports com.app.projetoBeeTech.controller;
    opens com.app.projetoBeeTech.controller to javafx.fxml;
    exports com.app.projetoBeeTech.test;
    opens com.app.projetoBeeTech.test to javafx.fxml;
    opens com.app.projetoBeeTech.model.usuario to javafx.base, javafx.fxml;
    exports com.app.projetoBeeTech.controller.agente;
    opens com.app.projetoBeeTech.controller.agente to javafx.fxml;
    opens com.app.projetoBeeTech.model.producao to javafx.base;

}