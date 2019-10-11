package com.trilogyed.adminapi.viewModels;

import java.time.LocalDate;
import java.util.Objects;

public class CustomerViewModel {

    private int customerId;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String email;
    private String phone;
    private int levelUpId;
    private int points;
    private LocalDate memberDate;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLevelUpId() {
        return levelUpId;
    }

    public void setLevelUpId(int levelUpId) {
        this.levelUpId = levelUpId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public LocalDate getMemeberDate() {
        return memberDate;
    }

    public void setMemeberDate(LocalDate memeberDate) {
        this.memberDate = memeberDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerViewModel that = (CustomerViewModel) o;
        return customerId == that.customerId &&
                levelUpId == that.levelUpId &&
                points == that.points &&
                firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                street.equals(that.street) &&
                city.equals(that.city) &&
                zip.equals(that.zip) &&
                email.equals(that.email) &&
                phone.equals(that.phone) &&
                memberDate.equals(that.memberDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, firstName, lastName, street, city, zip, email, phone, levelUpId, points, memberDate);
    }
}
