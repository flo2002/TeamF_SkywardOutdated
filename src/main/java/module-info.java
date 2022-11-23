module fhv.ws22.se.skyward {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.apache.logging.log4j;
    requires modelmapper;
    requires com.google.guice;


    exports fhv.ws22.se.skyward;
    opens fhv.ws22.se.skyward to javafx.fxml;

    exports fhv.ws22.se.skyward.view;
    opens fhv.ws22.se.skyward.view to javafx.fxml, com.google.guice;
    exports fhv.ws22.se.skyward.view.util;
    opens fhv.ws22.se.skyward.view.util to javafx.fxml;

    exports fhv.ws22.se.skyward.persistence;
    exports fhv.ws22.se.skyward.persistence.entity;
    opens fhv.ws22.se.skyward.persistence.entity;

    exports fhv.ws22.se.skyward.domain.dtos;
    exports fhv.ws22.se.skyward.domain;
    opens fhv.ws22.se.skyward.domain to com.google.guice;
    exports fhv.ws22.se.skyward.domain.model;
}