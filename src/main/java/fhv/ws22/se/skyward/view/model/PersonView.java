package fhv.ws22.se.skyward.view.model;

public class PersonView implements ViewEntity {
    private String firstName;
    private String lastName;

    public PersonView() {
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
