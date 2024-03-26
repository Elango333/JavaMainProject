package com.UserDetails;

import javax.management.relation.Role;

public class User {
  private String name;
  private String email;
  private String mobileNumber;
  private int userID;
  private String password;
  private Role role;
  private String address;
  private String sessionID;
  enum role {
    Customer,
    Admin,
    DeliveryPartner
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public void setSessionID(String sessionID) {
    this.sessionID = sessionID;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public String getPassword() {
    return password;
  }

  public int getUserID() {
    return userID;
  }

  public Role getRole() {
    return role;
  }

  public String getSessonID() {
    return sessionID;
  }
  public String getAddress() {
    return address;
  }

}
