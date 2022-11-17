package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.*;
import fhv.ws22.se.skyward.domain.model.AddressModel;
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
import java.time.LocalDateTime;

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
    private Label invoiceDatePlaceholder;

    @FXML
    protected void initialize() {
        session = SessionFactory.getInstance().getSession(clientSessionID);
        tmpBooking = session.getTmpBooking();

        if (tmpBooking.getInvoices() == null) {
            AddressDto hotelAddress = new AddressDto("ExampleStreet", "2", "1234", "New York", "United States");
            InvoiceDto invoice = new InvoiceDto("Skyward International", LocalDateTime.now(), false, hotelAddress, tmpBooking);
        }

        updateData();
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

    public void updateData(){
        if (tmpBooking.getCheckInDateTime() != null) {
            checkInDatePlaceholder.setText(tmpBooking.getCheckInDateTime().toLocalDate().toString());
        }
        if (tmpBooking.getCheckOutDateTime() != null) {
            checkOutDatePlaceholder.setText(tmpBooking.getCheckOutDateTime().toLocalDate().toString());
        }
        if (tmpBooking.getCustomers() != null) {
            namePlaceholder.setText(tmpBooking.getCustomers().get(0).getFirstName() + " " + tmpBooking.getCustomers().get(0).getLastName());
        }
        if (tmpBooking.getInvoices() != null) {
            invoiceDatePlaceholder.setText(tmpBooking.getInvoices().get(0).getInvoiceDateTime().toLocalDate().toString());
            payPlaceholder.setText(tmpBooking.getInvoices().get(0).getIsPaid() ? "Yes" : "No");
        }
    }
}
