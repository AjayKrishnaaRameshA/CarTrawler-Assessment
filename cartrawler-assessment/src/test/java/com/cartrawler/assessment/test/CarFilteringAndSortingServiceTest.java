package com.cartrawler.assessment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cartrawler.assessment.car.CarFilteringAndSortingService;
import com.cartrawler.assessment.car.CarGroup;
import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.car.CarResult.FuelPolicy;
import com.cartrawler.assessment.car.CarType;


@ExtendWith(MockitoExtension.class)
public class CarFilteringAndSortingServiceTest {

	@BeforeAll
	public CarResult mockcar(String supplier, String desc, String sipp,
			double rent, CarResult.FuelPolicy fuelPolicy) {
		CarResult mockCar=mock(CarResult.class);
		when(mockCar.getSupplierName()).thenReturn(supplier);
		when(mockCar.getDescription()).thenReturn(sipp);
		when(mockCar.getSippCode()).thenReturn(sipp);
		when(mockCar.getRentalCost()).thenReturn(rent);
		when(mockCar.getFuelPolicy()).thenReturn(fuelPolicy);
		return mockCar;
	}
	
	@Test
	public  void isCorporateBeforeNonCorporateTest() {
		var a=mockcar("HERTZ", "Citroen Berlingo", "MCMR", 100d, FuelPolicy.FULLFULL);
		var e=mockcar("CENTAURO", "Opel Astra", "MCMR", 85d, FuelPolicy.FULLFULL);
		
		var result=CarFilteringAndSortingService.
				findUnique(List.of(a,e));


		assertSame(a,result.get(0));
		assertSame(e, result.get(1));
		
	}
	
	
	@Test 
	public void test1() {
		var a=mockcar("HERTZ", "Citroen Berlingo", "MCMR", 100d, FuelPolicy.FULLFULL);
		var b=mockcar("SIXT", "Volkswagen Polo", "MCMR", 200d, FuelPolicy.FULLFULL);
		var c=mockcar("AVIS", "Citroen Berlingo", "MCMR", 95d, FuelPolicy.FULLEMPTY);
		var d=mockcar("BUDGET", "Opel Astra", "MCMR", 90d, FuelPolicy.FULLEMPTY);
		var e=mockcar("CENTAURO", "Opel Astra", "MCMR", 85d, FuelPolicy.FULLFULL);
		var f=mockcar("NIZA", "Opel Astra", "MCMR", 100d, FuelPolicy.FULLEMPTY);
		
		var result=CarFilteringAndSortingService.
				findUniqueAndfilteringAboveMedian(List.of(a, b, c,d,e,f));
		
		assertTrue(result.contains(a));//rent is more than median and corporate supplier so will be removed
		assertTrue(result.contains(b)); //rent is more than median and corporate supplier so will be removed
		assertTrue(result.contains(c));
		assertTrue(result.contains(d));//FULLEMPTY CARS STAY
		assertTrue(result.contains(c));
		assertTrue(result.contains(c));
		
	}
	
