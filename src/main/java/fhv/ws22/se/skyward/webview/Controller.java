package fhv.ws22.se.skyward.webview;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.persistence.entity.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

    private Session session;

    public void init() {
        session = SessionFactory.getInstance().getSession(new BigInteger("2"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();

        response.setContentType("text/html");

        String checkIn = request.getParameter("check-in-date");
        String checkOut = request.getParameter("check-out-date");
        String room = request.getParameter("room-type");
        String fName = request.getParameter("first-name");
        String lName = request.getParameter("last-name");
        String street = request.getParameter("street");
        String number = request.getParameter("number");
        String zip = request.getParameter("zip");
        String city = request.getParameter("city");
        String country = request.getParameter("country");

        pw.println("<link rel=\"stylesheet\" href=\"stylesheet.css\" type=\"text/css\" media=\"all\">");
        pw.println("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n");
        pw.println("<div class=\"main\">");
        pw.println("<div class=\"container\">");
        pw.println("<table class=\"table\" border='1'>");
        pw.println(" <thead>\n" +
                "    <tr>\n" +
                "    </tr>\n" +
                "  </thead>");

            pw.println("<tr>");
            pw.println("<td>"+ checkIn + "</td>");
            pw.println("<td>"+ checkOut + "</td>");
            pw.println("<td>"+ room + "</td>");
            pw.println("<td>"+ fName + "</td>");
            pw.println("<td>"+ lName + "</td>");
            pw.println("<td>"+ street + "</td>");
            pw.println("<td>"+ number + "</td>");
            pw.println("<td>"+ zip + "</td>");
            pw.println("<td>"+ city + "</td>");
            pw.println("<td>"+ country + "</td>");
            pw.println("</tr>");

            BookingDto booking = new BookingDto();
            CustomerDto customer = new CustomerDto();
            //booking.setCheckInDateTime(new LocalDateTime(new LocalDate(checkIn)));
            session.add(booking);
            customer.setFirstName("Florian");
            customer.setLastName("Schiemer");
            session.add(customer);

        pw.println("</div>");
        pw.println("</div>");


    }

}