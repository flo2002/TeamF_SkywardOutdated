module fhv.ws22.se.skyward {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;


    opens fhv.ws22.se.skyward to javafx.fxml;
    exports fhv.ws22.se.skyward;

    opens fhv.ws22.se.skyward.model;
    exports fhv.ws22.se.skyward.view;

    opens fhv.ws22.se.skyward.view to javafx.fxml;

    exports fhv.ws22.se.skyward.persistence;
    exports fhv.ws22.se.skyward.model;
}