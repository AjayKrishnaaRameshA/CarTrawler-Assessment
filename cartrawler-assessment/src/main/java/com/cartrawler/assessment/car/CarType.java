package com.cartrawler.assessment.car;

public enum CarType {
	
	MINI(0),
	ECONOMY(1),
	COMPACT(2),
	OTHER(3);
	
	private int carOrder;
	
	CarType(int carOrder){
		this.carOrder=carOrder;
	}
	
	public int getOrder() {
		return carOrder;
	}
	
	public static CarType fromSIPPcode(String sippCode) {
		if(sippCode==null || sippCode.isEmpty()) {
			return OTHER;
		}
		return switch(sippCode.toUpperCase().charAt(0)) {
		case 'M'->MINI;
		case 'E'->ECONOMY;
		case 'C'->COMPACT;
		default ->OTHER;
		
		};
	}

}
