package fhv.ws22.se.skyward.view;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.dtos.*;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceController extends AbstractController {
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
    private Label hotelNamePlaceholder;
    @FXML
    private Label hotelStreetPlaceholder;
    @FXML
    private Label hotelCityPlaceholder;
    @FXML
    private Label hotelCountryPlaceholder;

    @FXML
    private Label customerStreetPlaceholder;
    @FXML
    private Label customerCityPlaceholder;
    @FXML
    private Label customerCountryPlaceholder;


    @FXML
    private TableView<ChargeableItemDto> chargeableItemTable;
    @FXML
    private TableColumn<ChargeableItemDto, String> itemNameCol;
    @FXML
    private TableColumn<ChargeableItemDto, BigDecimal> itemPriceCol;
    @FXML
    private TableColumn<ChargeableItemDto, Integer> itemQuantityCol;

    @FXML
    private Label totalPricePlaceholder;

    @FXML
    protected void initialize() {
        tmpBooking = session.getTmpBooking();
        tmpInvoice = session.getTmpInvoice();

        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        updateData();
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
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }
    @FXML
    public void onEditButtonClick(ActionEvent event){

    }
    @FXML
    public void onPrintButtonClick(ActionEvent event){

    }

    @FXML
    public void backButtonClick(ActionEvent event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    public void updateData(){
        tmpBooking.setInvoices(List.of(tmpInvoice));
        session.update(tmpBooking.getId(), tmpBooking);

        hotelNamePlaceholder.setText(tmpInvoice.getCompanyName());
        hotelStreetPlaceholder.setText(tmpInvoice.getHotelAddress().getStreet() + " " + tmpInvoice.getHotelAddress().getHouseNumber());
        hotelCityPlaceholder.setText(tmpInvoice.getHotelAddress().getZipCode() + " " + tmpInvoice.getHotelAddress().getCity());
        hotelCountryPlaceholder.setText(tmpInvoice.getHotelAddress().getCountry());

        customerStreetPlaceholder.setText(tmpInvoice.getCustomerAddress().getStreet() + " " + tmpInvoice.getCustomerAddress().getHouseNumber());
        customerCityPlaceholder.setText(tmpInvoice.getCustomerAddress().getZipCode() + " " + tmpInvoice.getCustomerAddress().getCity());
        customerCountryPlaceholder.setText(tmpInvoice.getCustomerAddress().getCountry());

        checkInDatePlaceholder.setText(tmpBooking.getCheckInDateTime().toLocalDate().toString());
        checkOutDatePlaceholder.setText(tmpBooking.getCheckOutDateTime().toLocalDate().toString());
        namePlaceholder.setText(tmpBooking.getCustomers().get(0).getFirstName() + " " + tmpBooking.getCustomers().get(0).getLastName());

        if (tmpInvoice != null) {
            invoiceDatePlaceholder.setText(tmpInvoice.getInvoiceDateTime().toLocalDate().toString());
            payPlaceholder.setText(tmpInvoice.getIsPaid() ? "Yes" : "No");
            invoiceNumberPlaceholder.setText(tmpInvoice.getInvoiceNumber().toString());
            bookingNumberPlaceholder.setText(tmpBooking.getBookingNumber().toString());
        }

        chargeableItemTable.getItems().clear();
        List<ChargeableItemDto> chargeableItems = tmpBooking.getChargeableItems();
        BigDecimal totalPrice = new BigDecimal(0);
        for (ChargeableItemDto chargeableItem : chargeableItems) {
            chargeableItemTable.getItems().add(chargeableItem);
            totalPrice = totalPrice.add(chargeableItem.getPrice().multiply(BigDecimal.valueOf(chargeableItem.getQuantity())));
        }

        totalPricePlaceholder.setText(totalPrice.toString());
    }
}
