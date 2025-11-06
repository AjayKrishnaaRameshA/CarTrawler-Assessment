package com.cartrawler.assessment.view;

import com.cartrawler.assessment.car.CarFilteringAndSortingService;
import com.cartrawler.assessment.car.CarResult;


import java.util.List;
import java.util.Set;
import java.util.Scanner;

public class Display {
    public void render(Set<CarResult> cars) {
//        for (CarResult car : cars) {
//            System.out.println (car);
//        }
    	

        
    
		System.out.printf("%-12s | %-30s | %-6s | %-10s | %-20s%n", "Supplier", "Description", "SIPP", "Cost", "Fuel Policy");
        System.out.println("---------------------------------------------------------------------------------------");

        List<CarResult> result=CarFilteringAndSortingService.findUniqueAndfilteringAboveMedian(cars);
        
        for(CarResult subList:result) {
        	
             System.out.printf ("%-12s | %-30s | %-6s | %-10s | %-20s%n",
                      subList.getSupplierName(), subList.getDescription(), 
                      subList.getSippCode(), subList.getRentalCost(), subList.getFuelPolicy());
          }
        
    }
	
	
}
