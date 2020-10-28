package com.henrysgrocery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.henrysgrocery.model.BasketItem;
import com.henrysgrocery.model.StockItem;
import com.henrysgrocery.service.BasketService;

public class ShoppingCartApplication {
	private BasketService basketService = new BasketService();

	public ShoppingCartApplication(BasketService basketService) {
		this.basketService = basketService;
	}
	
	public void acceptInputAndPerformCalculations() {
		final List<BasketItem> basket = new ArrayList<>();

		System.out.println(
				"===============================================================================================");
		System.out.println("                                    H E N R Y 'S   G R O C E R Y");
		System.out.println(
				"===============================================================================================");
		System.out.println("Select the item id from the list below to add to the cart:");
		System.out.println(
				"-----------------------------------------------------------------------------------------------");
		System.out.println("ITEM ID: |     PRODUCT");
		System.out.println("---------|----------------------------");
		for (final DisplayStockItem displayStockItem : DisplayStockItem.values()) {
			System.out.println("   " + displayStockItem.id + ".    |  " + displayStockItem.getStockItem().getName()
					+ " " + displayStockItem.getStockItem().getUnits());
		}
		System.out.println("---------|----------------------------");
		System.out.println("Press -1 to complete items in basket.");
		System.out.println(
				"===============================================================================================");
		int itemId = 0;
		boolean flag = true;
		while (flag) {
			System.out.println("Please enter an item id:");
			final Scanner scanner = new Scanner(System.in);
			itemId = scanner.nextInt();
			if (itemId == -1) {
				flag = false;
				break;
			}
			final StockItem stockItem = DisplayStockItem.getForId(itemId);
			if (stockItem == null) {
				System.out.println("Invalid Item ID. Please enter a valid Item ID:");
				continue;
			}
			System.out.println("Please enter the quantity for item " + itemId + ":");
			int quantity = scanner.nextInt();
			if (quantity < 1 || quantity > 50) {
				System.out.println("Invalid Quantity Entered. Please enter a valid quantity:");
				continue;
			}
			final BasketItem basketItem = new BasketItem(stockItem, quantity);
			basket.add(basketItem);
		}
		basketService.calculatePricing(basket, LocalDate.now());
	}

	private enum DisplayStockItem {

		SOUP(1, StockItem.SOUP), BREAD(2, StockItem.BREAD), MILK(3, StockItem.MILK), APPLES(4, StockItem.APPLES);

		private int id;
		private StockItem stockItem;

		private DisplayStockItem(int id, StockItem stockItem) {
			this.id = id;
			this.stockItem = stockItem;
		}

		public int getId() {
			return id;
		}

		public StockItem getStockItem() {
			return stockItem;
		}

		public static StockItem getForId(int id) {
			for (final DisplayStockItem displayStockItem : DisplayStockItem.values()) {
				if (displayStockItem.getId() == id) {
					return displayStockItem.getStockItem();
				}
			}
			return null;
		}
	}
	
	public static void main(String[] args) {
		final ShoppingCartApplication application = new ShoppingCartApplication(new BasketService());
		application.acceptInputAndPerformCalculations();
	}
}
