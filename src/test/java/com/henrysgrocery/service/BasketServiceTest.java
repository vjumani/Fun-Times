package com.henrysgrocery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.henrysgrocery.model.BasketItem;
import com.henrysgrocery.model.Bill;
import com.henrysgrocery.model.StockItem;

public class BasketServiceTest {

	@Test
	public void givenSimpleBaskWithSoup_whenNoDiscountsApply_thenCalculatePricing() {
		final BasketService basketService = new BasketService();
		final BasketItem soup = new BasketItem(StockItem.SOUP, 1);
		final List<BasketItem> basketItems = new ArrayList<>();
		basketItems.add(soup);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now());
		assertEquals(0.65d, bill.getBillingTotal());
	}

	@Test
	public void givenSimpleBaskWithBread_whenNoDiscountsApply_thenCalculatePricing() {
		final BasketService basketService = new BasketService();
		final BasketItem bread = new BasketItem(StockItem.BREAD, 1);
		final List<BasketItem> basketItems = new ArrayList<>();
		basketItems.add(bread);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now());
		assertEquals(0.80d, bill.getBillingTotal());
	}

	@Test
	public void givenSimpleBaskWithMilk_whenNoDiscountsApply_thenCalculatePricing() {
		final BasketService basketService = new BasketService();
		final BasketItem milk = new BasketItem(StockItem.MILK, 1);
		final List<BasketItem> basketItems = new ArrayList<>();
		basketItems.add(milk);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now());
		assertEquals(1.3d, bill.getBillingTotal());
	}

	@Test
	public void givenSimpleBaskWithApple_whenNoDiscountsApply_thenCalculatePricing() {
		final BasketService basketService = new BasketService();
		final BasketItem apple = new BasketItem(StockItem.APPLES, 1);
		final List<BasketItem> basketItems = new ArrayList<>();
		basketItems.add(apple);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now());
		assertEquals(0.1d, bill.getBillingTotal());
	}

	@Test
	public void givenBasketOfSoupAndBread_whenBoughtBeforeYesterday_thenApplyNoDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem soup = new BasketItem(StockItem.SOUP, 2);
		basketItems.add(soup);
		final BasketItem bread = new BasketItem(StockItem.BREAD, 1);
		basketItems.add(bread);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now().minusDays(2));
		assertEquals(2.1d, bill.getBillingTotal());
	}

	@Test
	public void givenBasketOfSoupAndBread_whenBoughtYesterday_thenApplyDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem soup = new BasketItem(StockItem.SOUP, 2);
		basketItems.add(soup);
		final BasketItem bread = new BasketItem(StockItem.BREAD, 1);
		basketItems.add(bread);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now().minusDays(1));
		assertEquals(1.7d, bill.getBillingTotal());
	}

	@Test
	public void givenBasketOfSoupAndBread_whenBoughtToday_thenApplyDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem soup = new BasketItem(StockItem.SOUP, 2);
		basketItems.add(soup);
		final BasketItem bread = new BasketItem(StockItem.BREAD, 1);
		basketItems.add(bread);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now());
		assertEquals(1.7d, bill.getBillingTotal());
	}
	
	@Test
	public void givenBasketOfSoupAndBread_whenMoreBreadIsEligible_thenApplyDiscountForAll() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem soup = new BasketItem(StockItem.SOUP, 10);
		basketItems.add(soup);
		final BasketItem bread = new BasketItem(StockItem.BREAD, 2);
		basketItems.add(bread);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now());
		assertEquals(7.3d, bill.getBillingTotal());
	}
	
	@Test
	public void givenBasketOfSoupAndBread_whenSomeBreadIsEligible_thenApplyDiscountSelectively() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem soup = new BasketItem(StockItem.SOUP, 6);
		basketItems.add(soup);
		final BasketItem bread = new BasketItem(StockItem.BREAD, 4);
		basketItems.add(bread);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now());
		assertEquals(5.9d, bill.getBillingTotal());
	}

	@Test
	public void givenBasketOfSoupAndBread_whenBoughtOnSeventhDay_thenApplyDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem soup = new BasketItem(StockItem.SOUP, 2);
		basketItems.add(soup);
		final BasketItem bread = new BasketItem(StockItem.BREAD, 1);
		basketItems.add(bread);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now().minusDays(1).plusDays(7));
		assertEquals(1.7d, bill.getBillingTotal());
	}

	@Test
	public void givenBasketOfSoupAndBread_whenBoughtAfterLastDay_thenApplyNoDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem soup = new BasketItem(StockItem.SOUP, 2);
		basketItems.add(soup);
		final BasketItem bread = new BasketItem(StockItem.BREAD, 1);
		basketItems.add(bread);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now().plusDays(7));
		assertEquals(2.1d, bill.getBillingTotal());
	}
	
	@Test
	public void givenBasketOfApples_whenBoughtToday_thenApplyNoDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem apple = new BasketItem(StockItem.APPLES, 1);
		basketItems.add(apple);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now());
		assertEquals(0.1d, bill.getBillingTotal());
	}
	
	@Test
	public void givenBasketOfApples_whenBoughtIn3Days_thenApplyDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem apple = new BasketItem(StockItem.APPLES, 1);
		basketItems.add(apple);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now().plusDays(3));
		assertEquals(0.09d, bill.getBillingTotal());
	}
	
	@Test
	public void givenBasketOfApples_whenBoughtAfter3Days_thenApplyDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem apple = new BasketItem(StockItem.APPLES, 1);
		basketItems.add(apple);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now().plusDays(3).plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()));
		assertEquals(0.09d, bill.getBillingTotal());
	}

	@Test
	public void givenBasketOfApples_whenBoughtAfterLastDay_thenApplyNoDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem apple = new BasketItem(StockItem.APPLES, 1);
		basketItems.add(apple);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now().plusDays(3).plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).plusDays(1));
		assertEquals(0.1d, bill.getBillingTotal());
	}
}