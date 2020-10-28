package com.henrysgrocery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.henrysgrocery.model.BasketItem;
import com.henrysgrocery.model.Bill;
import com.henrysgrocery.model.StockItem;

public class BasketServiceIntegrationTest {
	
	/**
	 * Price a basket containing: 3 tins of soup and 2 loaves of bread, bought today,
	 * Expected total cost = 3.15;
	 */
	@Test
	public void givenBasketWith3SoupAnd2BreadBoughtToday_whenSoupOfferApplies_thenApplyDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem soup = new BasketItem(StockItem.SOUP, 3);
		basketItems.add(soup);
		final BasketItem bread = new BasketItem(StockItem.BREAD, 2);
		basketItems.add(bread);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now());
		assertEquals(3.15d, bill.getBillingTotal());
	}
	
	/**
	 * Price a basket containing: 6 apples and a bottle of milk, bought today,
	 * Expected total cost = 1.90;
	 */
	@Test
	public void givenBasketWith6ApplesAndMilkBoughtToday_whenNoOfferApplies_thenApplyNoDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem apple = new BasketItem(StockItem.APPLES, 6);
		basketItems.add(apple);
		final BasketItem milk = new BasketItem(StockItem.MILK, 1);
		basketItems.add(milk);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now());
		assertEquals(1.9d, bill.getBillingTotal());
	}

	/**
	 * Price a basket containing: 6 apples and a bottle of milk, bought in 5 days time,
	 * Expected total cost = 1.84;
	 */
	@Test
	public void givenBasketWith6ApplesAndMilkBoughtIn5Days_whenAppleOfferApplies_thenApplyDiscount() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem apple = new BasketItem(StockItem.APPLES, 6);
		basketItems.add(apple);
		final BasketItem milk = new BasketItem(StockItem.MILK, 1);
		basketItems.add(milk);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now().plusDays(5));
		assertEquals(1.84d, bill.getBillingTotal());
	}

	/**
	 * Price a basket containing: 3 apples, 2 tins of soup and a loaf of bread, bought in 5 days time,
	 * Expected total cost = 1.97;
	 */
	@Test
	public void givenBasketWith3Apples2SoupAndBreadBoughtIn5Days_whenAppleAndSoupOfferApplies_thenApplyDiscounts() {
		final BasketService basketService = new BasketService();
		final List<BasketItem> basketItems = new ArrayList<>();
		final BasketItem apple = new BasketItem(StockItem.APPLES, 3);
		basketItems.add(apple);
		final BasketItem soup = new BasketItem(StockItem.SOUP, 2);
		basketItems.add(soup);
		final BasketItem bread = new BasketItem(StockItem.BREAD, 1);
		basketItems.add(bread);
		final Bill bill = basketService.calculatePricing(basketItems, LocalDate.now().plusDays(5));
		assertEquals(1.97d, bill.getBillingTotal());
	}

}
