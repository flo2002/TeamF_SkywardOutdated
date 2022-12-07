<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <title>JSP - Hello World</title>
</head>
<body>

<h1>Reservation Form</h1>
<form class="form" action="./controller" method="get">
    <label for="check-in-date">Check-In Date:</label>
    <input type="date" id="check-in-date" name="check-in-date">
    <label for="check-out-date">Check-Out Date:</label>
    <input type="date" id="check-out-date" name="check-out-date">
    <label for="room-type">Room Type:</label>
    <select id="room-type" name="room-type">
        <option value="standard">Standard</option>
        <option value="deluxe">Deluxe</option>
        <option value="suite">Suite</option>
    </select>
    <label for="first-name">First Name:</label>
    <input type="text" id="first-name" name="first-name">
    <label for="last-name">Last Name:</label>
    <input type="text" id="last-name" name="last-name">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email">
    <label for="phone">Phone:</label>
    <input type="tel" id="phone" name="phone">
    <button type="submit" action="./servlet">Confirm</button>
</form>

</body>
</html>
