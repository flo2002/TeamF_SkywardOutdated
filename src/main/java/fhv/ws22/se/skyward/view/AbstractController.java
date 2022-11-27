package fhv.ws22.se.skyward.view;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractController {
    protected static final Logger logger = LogManager.getLogger("AddGuestController");

    @Inject
    protected Session session;
    @Inject
    protected ControllerNavigationUtil controllerNavigationUtil;

    protected BookingDto tmpBooking;
    protected InvoiceDto tmpInvoice;

    @FXML
    public void onHomeButtonClick(Event event) {
        if (tmpBooking != null) {
            session.update(tmpBooking.getId(), tmpBooking);
        }
        session.resetTmpInvoice();
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/dashboard.fxml", "Home");
    }

    @FXML
    public void onBookingButtonClick(Event event) {
        if (tmpBooking != null) {
            session.update(tmpBooking.getId(), tmpBooking);
        }
        session.resetTmpInvoice();
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onInvoicePageButtonClick(Event event) {
        if (tmpBooking != null) {
            session.update(tmpBooking.getId(), tmpBooking);
        }
        session.resetTmpInvoice();
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/invoice-overview.fxml", "Invoice");
    }
}
