package com.cartrawler.assessment.car;

import java.util.function.Function;
import java.util.stream.Collectors;


import java.text.DecimalFormat;
import java.util.*;

public class CarFilteringAndSortingService {
	
	private static final Set<String> _CORPORATE_SUPPLIER_=Set.of
			("AVIS", "BUDGET","ENTERPRISE","FIREFLY","HERTZ","SIXT", "THRIFTY");
	
	private static boolean isCorporateSupplier(CarResult result) {
		return _CORPORATE_SUPPLIER_.contains(result.getSupplierName());
	}
	
	private static boolean isFuelTypeFullfull(CarResult result) {
		return "FULLFULL".equalsIgnoreCase(String.valueOf(result.getFuelPolicy()));
	}
	
	private static String uniqueKey(CarResult result) {
		return result.getDescription().toUpperCase()
				+" | "+result.getSupplierName().toUpperCase()
				+" | "+result.getSippCode().toUpperCase()
				+" | "+String.valueOf(result.getFuelPolicy()).toUpperCase();
	}

	public static List<CarResult> findUnique(Collection<CarResult> result){
		if(result==null || result.size()==0 || result.isEmpty())return List.of();
		
		List<CarResult> unique=result.stream().
				collect(Collectors.collectingAndThen(
						Collectors.toMap(
								CarFilteringAndSortingService::uniqueKey,
								Function.identity(),
								(first,second)->first,
								LinkedHashMap::new
						),
						e->new ArrayList<>(e.values())
						
			));
		
		Comparator<CarResult> comp=  Comparator
				.comparing(
				CarFilteringAndSortingService::isCorporateSupplier).reversed()
				.thenComparing(c->CarType.fromSIPPcode(c.getSippCode()).getOrder())
				.thenComparingDouble(CarResult::getRentalCost)
				.thenComparing(c->c.getSupplierName().toUpperCase())
				.thenComparing(CarResult::getDescription)
				.thenComparing(c->c.getSippCode())
				.thenComparing(c->String.valueOf(c.getFuelPolicy())
						
						);
		
		unique.sort(comp);
		return unique;		
		
	}
	
	public static double calculateMedian(List<Double> prices) {
		if(prices.isEmpty() || prices==null)return 0d;
		
		List<Double> value=new ArrayList<>(prices);
		int n=value.size();
		if(n%2==1) {
			return value.get(n/2);
		}
		return (value.get((n/2)-1)+value.get(n/2))/2.0;
			
	}
	
	public String outputFormatter(CarResult car) {
		DecimalFormat formatter=new DecimalFormat("0.00");
		return String
				.join(" ",car.getSupplierName().toUpperCase(),
						car.getDescription(),
						car.getSippCode().toUpperCase(),
						formatter.format(car.getRentalCost()),
						String.valueOf(car.getFuelPolicy())
					);
						
				
	}
	
	public static List<CarResult>  findUniqueAndfilteringAboveMedian(Collection<CarResult> result){
		List<CarResult> unique=findUnique(result);
		if(unique.isEmpty()) return unique;
		
		
		Map<CarGroup, Double>  medianForEachGroup=unique.stream().collect(
				Collectors.groupingBy(
						c-> new CarGroup(isCorporateSupplier(c),CarType.fromSIPPcode(c.getSippCode())),
						Collectors.mapping(CarResult::getRentalCost,Collectors.toList())
						
						)).entrySet().stream().collect(Collectors.toMap(
								Map.Entry::getKey,
								b->calculateMedian(b.getValue())
						
						));
	
		return unique.stream().filter(a->!(isFuelTypeFullfull(a) 
				&& a.getRentalCost() > medianForEachGroup.get(new CarGroup(isCorporateSupplier(a),
						CarType.fromSIPPcode(a.getSippCode())
						)))).collect(Collectors.toList());
	}
	
}
