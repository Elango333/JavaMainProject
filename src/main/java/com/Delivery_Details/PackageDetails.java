package com.Delivery_Details;

public class PackageDetails {
  private String itemName;
  private int weight;
  private String typeofDelivery;
  private String description;
  private String typeOfPayment;
  private java.sql.Date sqlDate;
  private int receiverID;
  private String customerName;

  public void setItemname(String itemName) {
    this.itemName = itemName;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public void setDeliveryType(String typeofDelivery) {
    this.typeofDelivery = typeofDelivery;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPaymentType(String typeOfPayment) {
    this.typeOfPayment = typeOfPayment;
  }

  public void setDate() {
	  java.util.Date currentDate = new java.util.Date();
	  sqlDate = new java.sql.Date(currentDate.getTime());
  }
  public void setReceiverID(int receiverID) {
    this.receiverID = receiverID;
  }
  
  public void setCustomerName(String customerName) {
	    this.customerName = customerName;
}

  public String getCustomerName() {
	    return customerName;
  }

  public String getItemname() {
    return itemName;
  }

  public int getWeight() {
    return weight;
  }

  public String getDeliveryType() {
    return typeofDelivery;
  }

  public String getDescription() {
    return description;
  }

  public String getPaymentType() {
    return typeOfPayment;
  }
  public java.sql.Date getDate() {
    return sqlDate;
  }
  public int getReceiverID() {
    return receiverID;
  }

}