package fhv.ws22.se.skyward.view;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceOverviewController {
    @Inject
    private Session session;
    @Inject
    private ControllerNavigationUtil controllerNavigationUtil;

    @FXML
    private TableView<InvoiceDto> table;
    @FXML
    private TableColumn<InvoiceDto, BigInteger> invoiceNumberCol;
    @FXML
    private TableColumn<InvoiceDto, LocalDateTime> invoiceDateTimeCol;
    @FXML
    private TableColumn<InvoiceDto, Boolean> isPaidCol;

    @FXML
    protected void initialize() {
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        invoiceDateTimeCol.setCellValueFactory(new PropertyValueFactory<InvoiceDto, LocalDateTime>("invoiceDateTime"));
        isPaidCol.setCellValueFactory(new PropertyValueFactory<InvoiceDto, Boolean>("isPaid"));

        updateTable();
        table.setRowFactory(invoiceDtoTableView -> {
            TableRow<InvoiceDto> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (! row.isEmpty()) ) {
                    InvoiceDto rowData = row.getItem();
                    session.setTmpInvoice(rowData);
                    controllerNavigationUtil.navigate(mouseEvent,"src/main/resources/fhv/ws22/se/skyward/invoice-information.fxml", "Invoice");
                }
            });
            return row;
        });
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/homescreen.fxml", "Home");
    }

    @FXML
    public void onBookingButtonClick(ActionEvent event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onInvoicePageButtonClick(ActionEvent event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/invoice-overview.fxml", "Invoice");
    }

    public void updateTable() {
        table.getItems().clear();
        List<InvoiceDto> invoices = session.getAll(InvoiceDto.class);
        for (InvoiceDto invoice : invoices) {
            table.getItems().add(invoice);
        }
    }
}
