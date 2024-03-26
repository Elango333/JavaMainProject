package com.loginPackage;

public class ConstantFile {
	public static String insert_query_for_Receiver_details = "insert into Receiver_Details (Name, Address, EmailId, Phone_number) values (?, ?, ?, ?)";
	public static String insertSqlForRegister = "insert into Customer(Name, EmailID, Address, Phone_number, Password, Role_Id) values(?, ?, ?, ?, ?, ?)";
	public static String select_query_for_Customer_details = "select * FROM Customer WHERE Name = ?";
	public static String select_query_for_Customer_Ordercount = "select count(Customer_ID) from Customer_Orderlist WHERE Customer_ID = ?";
	public static String select_query_for_session = "select * from sessions  where Session_ID = ?";
	public static String select_query_for_Deletesession = "delete FROM sessions WHERE Session_ID = ?";
	public static String select_query_for_ReceiverID = "select Receiver_id from Receiver_Details where EmailId = ?";
	public static String insert_query_for_Package_details = "insert into Package_Details (Item, Type_of_delivery, Type_of_payment, Description, Amount, Weight, Ordered_date, Receiver_details, Partner_Id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static String select_query_for_PackageID = "select Pack_id from Package_Details where Receiver_details = ?";
	public static String select_query_for_role = "select Role from sessions  where Session_ID = ?";
	public static String insert_query_for_Tracking_status = "insert into Tracking_Status (Package_id, Delivery_status, Delivery_date) values (?, ?, ?)";
	public static String select_query_for_TrackingCode = "select Tracking_code from Tracking_Status where Package_id = ?";
	public static String select_query_for_Tracking_details = "select Tracking_code, Delivery_status, Delivery_date, Item, Type_of_delivery, Type_of_payment, Description, Amount, Weight, Ordered_date, Name, Address, EmailId, Phone_number from Tracking_Status join Package_Details on Tracking_Status.Package_id = Package_Details.Pack_id join Receiver_Details on Package_Details.Receiver_details = Receiver_Details.Receiver_id where Tracking_code = ?";		
	public static String select_query_for_customer_id = "select Customer_id from Customer where Name = ?";
	public static String insert_query_for_Customer_orderlist = "insert into Customer_Orderlist (Customer_ID, Package_ID) values (?, ?)";
	public static String insert_query_for_Session = "insert into sessions(Session_ID, username, Role) values(?,?,?)";
	public static String select_query_for_CheckAccount = "select Password FROM Customer WHERE Name = ? LIMIT 1";
	public static String select_query_for_DeliveryPartner = "select Partner_password, Partner_id from deliveryPartner where Partner_name = ?";
	public static String select_query_for_deliveryPartnerDetails = "select Partner_id, Order_count from deliveryPartner ORDER BY Order_count ASC limit 1";
	public static String insert_query_for_deliveryPartner_deliveryList = "insert into deliveryPartner_deliveryList(Partner_id, Package_id, Delivery_status) values(?, ?, ?)";
	public static String insert_query_for_deliveryPartner = "update deliveryPartner set Order_count = ? where Partner_id = ?";
	public static String select_query_for_orderlistMiniDetails = "SELECT Pack_id, Delivery_status, Delivery_date, Amount, Weight, Name FROM Tracking_Status JOIN Package_Details ON Tracking_Status.Package_id = Package_Details.Pack_id JOIN Receiver_Details ON Package_Details.Receiver_details = Receiver_Details.Receiver_id WHERE Partner_Id = ? AND Delivery_status = ?";
	public static String select_query_for_orderlistLargeDetails = "SELECT Delivery_status, Delivery_date, Item, Type_of_delivery, Type_of_payment, Description, Amount, Weight, Ordered_date, Name, Address, EmailId, Phone_number, Partner_Id FROM Tracking_Status JOIN Package_Details ON Tracking_Status.Package_id = Package_Details.Pack_id JOIN Receiver_Details ON Package_Details.Receiver_details = Receiver_Details.Receiver_id WHERE Pack_id = ?";
}
