package fhv.ws22.se.skyward.view;

import javafx.fxml.FXML;

public class NavBarController {
    public NavBarController() {

    }
    @FXML
    public void onHomeButtonClick() {
        System.out.println("This works, right?");
    }

    @FXML
    public void onBookingButtonClick() {
        System.out.println("To Bookings we go!");
    }
}
