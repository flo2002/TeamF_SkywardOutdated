package fhv.ws22.se.skyward.view.util;

public class RoomCapacity {
    private String roomNumber;
    private String day1;
    private String day2;
    private String day3;
    private String day4;
    private String day5;

    public RoomCapacity(String roomNumber, String day1, String day2, String day3, String day4, String day5) {
        this.roomNumber = roomNumber;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDay1() {
        return day1;
    }
    public void setDay1(String day1) {
        this.day1 = day1;
    }

    public String getDay2() {
        return day2;
    }
    public void setDay2(String day2) {
        this.day2 = day2;
    }

    public String getDay3() {
        return day3;
    }
    public void setDay3(String day3) {
        this.day3 = day3;
    }

    public String getDay4() {
        return day4;
    }
    public void setDay4(String day4) {
        this.day4 = day4;
    }

    public String getDay5() {
        return day5;
    }
    public void setDay5(String day5) {
        this.day5 = day5;
    }
}
