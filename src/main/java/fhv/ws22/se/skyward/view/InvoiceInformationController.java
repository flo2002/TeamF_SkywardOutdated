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
import java.util.List;

public class InvoiceInformationController {
    private static final Logger logger = LogManager.getLogger("BookingController");
    private static final BigInteger clientSessionID = new BigInteger("1");
    private Session session;
    private BookingDto tmpBooking;
    private InvoiceDto tmpInvoice;

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
    private Label invoiceNumberPlaceholder;
    @FXML
    private Label bookingNumberPlaceholder;

    @FXML
    protected void initialize() {
        session = SessionFactory.getInstance().getSession(clientSessionID);
        tmpBooking = session.getTmpBooking();
        tmpInvoice = session.getTmpInvoice();

        updateData();
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        session.resetTmpInvoice();
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/homescreen.fxml", "Home");
    }

    @FXML
    public void onBookingButtonClick(ActionEvent event) {
        session.resetTmpInvoice();
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onInvoicePageButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/invoice-overview.fxml", "Invoice");
    }

    @FXML
    public void onSplitButtonClick(ActionEvent event){

    }

    @FXML
    public void onPayButtonClick(ActionEvent event){
        if (payButton.getText().equals("Pay")) {
            payButton.setText("Unpay");
            tmpInvoice.setIsPaid(true);
            updateData();
        } else if (payButton.getText().equals("Unpay")){
            payButton.setText("Pay");
            tmpInvoice.setIsPaid(false);
            updateData();
        }
    }

    @FXML
    public void onConfirmButtonClick(ActionEvent event){
        session.resetTmpInvoice();
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }
    @FXML
    public void onEditButtonClick(ActionEvent event){

    }
    @FXML
    public void onPrintButtonClick(ActionEvent event){

    }

    public void updateData(){
        tmpBooking.setInvoices(List.of(tmpInvoice));
        session.update(tmpBooking.getId(), tmpBooking);

        checkInDatePlaceholder.setText(tmpBooking.getCheckInDateTime().toLocalDate().toString());
        checkOutDatePlaceholder.setText(tmpBooking.getCheckOutDateTime().toLocalDate().toString());
        namePlaceholder.setText(tmpBooking.getCustomers().get(0).getFirstName() + " " + tmpBooking.getCustomers().get(0).getLastName());

        if (tmpInvoice != null) {
            invoiceDatePlaceholder.setText(tmpInvoice.getInvoiceDateTime().toLocalDate().toString());
            payPlaceholder.setText(tmpInvoice.getIsPaid() ? "Yes" : "No");
            invoiceNumberPlaceholder.setText(tmpInvoice.getInvoiceNumber().toString());
            bookingNumberPlaceholder.setText(tmpBooking.getBookingNumber().toString());
        }
    }
}
