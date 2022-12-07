package fhv.ws22.se.skyward.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet(urlPatterns = {"/servlet"})
public class Servlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        //
        response.setContentType("text/html");

        String checkIn = request.getParameter("check-in-date");
        String checkOut = request.getParameter("check-out-date");
        String room = request.getParameter("room-type");
        String fName = request.getParameter("first-name");
        String lName = request.getParameter("last-name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        pw.println("<link rel=\"stylesheet\" href=\"stylesheet.css\" type=\"text/css\" media=\"all\">");
        pw.println("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n");
        pw.println("<div class=\"main\">");
        pw.println("<div class=\"container\">");
        pw.println("<table class=\"table\" border='1'>");
        pw.println(" <thead>\n" +
                "    <tr>\n" +
                "      <th scope=\"col\">Username</th>\n" +
                "      <th scope=\"col\">Email</th>\n" +
                "      <th scope=\"col\">Message</th>\n" +
                "    </tr>\n" +
                "  </thead>");

            pw.println("<tr>");
            pw.println("<td>"+ checkIn + "</td>");
            pw.println("<td>"+ checkOut + "</td>");
            pw.println("<td>"+ room + "</td>");
            pw.println("<td>"+ fName + "</td>");
            pw.println("<td>"+ lName + "</td>");
            pw.println("<td>"+ email + "</td>");
            pw.println("<td>"+ phone + "</td>");
            pw.println("</tr>");

        pw.println("</div>");
        pw.println("</div>");


        // Do something with the date, room, and guest information...
    }

}