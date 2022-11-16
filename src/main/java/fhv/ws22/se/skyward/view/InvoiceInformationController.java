package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;

public class InvoiceInformationController {
    private static final Logger logger = LogManager.getLogger("BookingController");
    private static final BigInteger clientSessionID = new BigInteger("1");
    private Session session;
    private BookingDto tmpBooking;

    @FXML
    private Button payButton;

    @FXML
    private Label payPlaceholder;
    @FXML
    private Label checkInDatePlaceholder;

    @FXML
    private Label checkOutDatePlaceholder;

    @FXML
    private Label namePlaceholder;

    @FXML
    protected void initialize() {
        session = SessionFactory.getInstance().getSession(clientSessionID);
        tmpBooking = session.getTmpBooking();
        checkInDatePlaceholder.setText(tmpBooking.getCheckInDateTime().toString());
        checkOutDatePlaceholder.setText(tmpBooking.getCheckOutDateTime().toString());
        namePlaceholder.setText(tmpBooking.getCustomers().get(0).getFirstName() + " " + tmpBooking.getCustomers().get(0).getLastName());
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/homescreen.fxml", "Home");
    }

    @FXML
    public void onBookingButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onSplitButtonClick(ActionEvent event){

    }

    @FXML
    public void onPayButtonClick(ActionEvent event){
        if (payButton.getText().equals("Pay")) {
            payButton.setText("Unpay");
            payPlaceholder.setText("Yes");
        } else if (payButton.getText().equals("Unpay")){
            payButton.setText("Pay");
            payPlaceholder.setText("No");
        }
    }

    @FXML
    public void onConfirmButtonClick(ActionEvent event){
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }
    @FXML
    public void onEditButtonClick(ActionEvent event){

    }
    @FXML
    public void onPrintButtonClick(ActionEvent event){

    }
}
