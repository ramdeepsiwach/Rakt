package com.rakt;

public class UserClass {
    private String phoneNumber,userName,userEmail,bloodGroup,userAge,userAddress;
    private boolean isDonor;

    public UserClass(String phoneNumber, String userName, String userEmail, String bloodGroup, String userAge, String userAddress, boolean isDonor) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.userEmail = userEmail;
        this.bloodGroup = bloodGroup;
        this.userAge = userAge;
        this.userAddress = userAddress;
        this.isDonor = isDonor;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public boolean isDonor() {
        return isDonor;
    }

    public void setDonor(boolean donor) {
        isDonor = donor;
    }
}
