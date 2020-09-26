package com.rakt.Database;

public class CurrentUser {
    private String uid,phoneNumber,userName,userEmail,bloodGroup,userAge,userAddress,donor;

    public CurrentUser() {
    }

    public CurrentUser(String uid, String phoneNumber, String userName, String userEmail, String bloodGroup, String userAge, String userAddress, String donor) {
        this.uid = uid;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.userEmail = userEmail;
        this.bloodGroup = bloodGroup;
        this.userAge = userAge;
        this.userAddress = userAddress;
        this.donor = donor;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }
}
