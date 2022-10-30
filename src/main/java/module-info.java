module fhv.ws22.se.skyward {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.apache.logging.log4j;
    requires modelmapper;


    opens fhv.ws22.se.skyward to javafx.fxml;
    exports fhv.ws22.se.skyward;

    opens fhv.ws22.se.skyward.model;
    exports fhv.ws22.se.skyward.view;

    opens fhv.ws22.se.skyward.view to javafx.fxml;

    exports fhv.ws22.se.skyward.persistence;
    exports fhv.ws22.se.skyward.model;
    exports fhv.ws22.se.skyward.model.DTOs;
}