
package com.Delivery_Details;

import java.time.LocalDate;
import java.time.ZoneId;

public class SpeedDelivery implements DeliveryType{

	private long deliveryDate;
	private int amount;
	
	@Override
	public void setDeliveryDate() {
		 LocalDate currentDate = LocalDate.now();
	     LocalDate newDate = currentDate.plusDays(2);
	     this.deliveryDate = newDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
	}
	
	@Override
	public void setAmount(int weight) {
		this.amount = weight/5;
	}
	
	public long getDeliveryDate() {
		return deliveryDate;
	}
	public int getAmount() {
		return amount;
	}

}
