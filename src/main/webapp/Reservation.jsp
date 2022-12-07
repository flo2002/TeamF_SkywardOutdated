<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

<div class="sidenav">
    <a><img src="SkyWardIcon.png" alt="Icon" style=width:70px;height:70px;></a>
    <a href="index.jsp">Home</a>
    <a href="Reservation.jsp">Reservation</a>
    <a href="About.jsp">About</a>
    <a href="Contact.jsp">Contact</a>
</div>
<div class="main">
    <h1>Reservation Form</h1>
    <form class="form" action="./controller" method="get">
        <label for="first-name">First Name:</label>
        <input type="text" id="first-name" name="first-name"><br>
        <label for="last-name">Last Name:</label>
        <input type="text" id="last-name" name="last-name"><br>
        <label for="street">Street:</label>
        <input type="text" id="street" name="street"><br>
        <label for="number">Number:</label>
        <input type="text" id="number" name="number">
        <label for="zip">Zip:</label>
        <input type="text" id="zip" name="zip"><br>
        <label for="city">City:</label>
        <input type="text" id="city" name="city">
        <label for="country">Country:</label>
        <input type="text" id="country" name="country"><br>
        <label for="check-in-date">Check-In Date:</label>
        <input type="date" id="check-in-date" name="check-in-date">
        <label for="check-out-date">Check-Out Date:</label>
        <input type="date" id="check-out-date" name="check-out-date"><br>
        <label for="room-type">Room Type:</label>
        <select id="room-type" name="room-type">
            <option value="single">Single</option>
            <option value="double">Double</option>
            <option value="triple">Triple</option>
            <option value="twin">Twin</option>
            <option value="queen">Queen</option>
        </select>
        <br>
        <button class="confirmbtn" type="submit" action="./controller">Confirm</button>
    </form>

</div>


</body>
</html>
