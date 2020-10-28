package com.henrysgrocery.service;

import static com.henrysgrocery.utils.Utilities.formatTo2Digits;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.henrysgrocery.model.BasketItem;
import com.henrysgrocery.model.Bill;
import com.henrysgrocery.model.BillingItem;
import com.henrysgrocery.model.Discount;
import com.henrysgrocery.model.StockItem;

public class BasketService {
	
	/**
	 * Constructor
	 */
	public BasketService() {
	}

	/**
	 * This method is the bulk of the service, and applies the pricing rules to the input list of BasketItems 
	 * based on the currentDate sent in as a parameter. 
	 * 
	 * @param basketItems - The list of BasketItems for which the pricing needs to be calculated.
	 * @param currentDate - The date to be used to apply the calculations.
	 * @return
	 */
	public Bill calculatePricing(final List<BasketItem> basketItems, final LocalDate currentDate) {
		final List<BillingItem> billingItems = consolidateBasketAndApplyPricing(basketItems, currentDate);
		double billingTotal = billingItems.stream().map(billingItem -> billingItem.getBasketItemPrice()).reduce(0d,
				(a, b) -> a + b);
		billingTotal = formatTo2Digits(billingTotal);
		Bill bill = new Bill(billingItems, billingTotal);
		System.out.println(bill);
		return bill;
	}

	/**
	 * Since the same item can be added multiple times, this method consolidates all the items and then 
	 * applies the pricing
	 * 
	 * @param basketItems - The list of BasketItems for which the pricing needs to be calculated.
	 * @param currentDate - The date to be used to apply the calculations.
	 * 
	 * @return - List of BillingItems, with the price applied for each item. 
	 * 			The total cart price will then have to be totalled up
	 */
	private List<BillingItem> consolidateBasketAndApplyPricing(final List<BasketItem> basketItems,
			final LocalDate currentDate) {
		final Map<StockItem, Integer> basketMap = new HashMap<>();
		basketItems.forEach(basketItem -> {
			if (basketMap.containsKey(basketItem.getStockItem())) {
				int currentQuantity = basketMap.get(basketItem.getStockItem());
				currentQuantity += basketItem.getQuantity();
				basketMap.put(basketItem.getStockItem(), currentQuantity);
			} else {
				basketMap.put(basketItem.getStockItem(), basketItem.getQuantity());
			}
		});

		// Create Billing Item/Invoice Item.
		final List<BillingItem> billingItems = new ArrayList<>();

		basketMap.keySet().forEach(stockItem -> {
			final Discount itemDiscountAvailable = stockItem.getDiscount();
			// No Discounts Available - Apply normal pricing.
			if (itemDiscountAvailable == null) {
				applyNormalBilling(basketMap, billingItems, stockItem);
			} else {
				// Discount Available for Item - Check date validity.
				if (isDiscountValidForDate(itemDiscountAvailable, currentDate)) {
					// Is there a pre requisite item to buy and is it available in the basket.
					StockItem preRequisiteItem = itemDiscountAvailable.getApplicableItemToBuyForDiscount();
					int quantityEligibleForDiscount = 0;
					int quantityToBuy = basketMap.get(stockItem);
					// Calculate eligible items for discount
					if (basketMap.containsKey(preRequisiteItem)) {
						int preRequisiteItemQuantity = basketMap
								.get(itemDiscountAvailable.getApplicableItemToBuyForDiscount());

						quantityEligibleForDiscount = preRequisiteItemQuantity
								/ itemDiscountAvailable.getCountApplicableItemToBuy();
						// Compare Number of Items in basket with quantity eligible for discount.
						// If all the items are eligible for discount - apply discount to all items in
						// basket.
						if (quantityToBuy <= quantityEligibleForDiscount) {
							// Apply discount for eligible items
							double itemPrice = (stockItem.getCost() * itemDiscountAvailable.getDiscountedRate())
									* quantityToBuy;
							billingItems.add(new BillingItem(new BasketItem(stockItem, quantityToBuy), formatTo2Digits(itemPrice)));
						}
						// Else apply discount for eligible items
						// Then apply pricing for non eligible items at regular pricing.
						else {

							// Apply discount for eligible items
							double itemPrice = (stockItem.getCost() * itemDiscountAvailable.getDiscountedRate())
									* quantityEligibleForDiscount;
							// Apply normal pricing for not eligible items
							itemPrice += stockItem.getCost() * (quantityToBuy - quantityEligibleForDiscount);
							billingItems.add(new BillingItem(new BasketItem(stockItem, quantityToBuy), formatTo2Digits(itemPrice)));
						}
					} // Apply normal discount on item
					else if (preRequisiteItem == null) {
						double itemPrice = (stockItem.getCost() * itemDiscountAvailable.getDiscountedRate())
								* quantityToBuy;
						billingItems.add(new BillingItem(new BasketItem(stockItem, quantityToBuy), formatTo2Digits(itemPrice)));
					} else {
						applyNormalBilling(basketMap, billingItems, stockItem);
					}
				} else {
					applyNormalBilling(basketMap, billingItems, stockItem);
				}
			}
		});

		return billingItems;
	}

	/**
	 * This method works on the logic that there is no discount available and applies a flat pricing 
	 * based on quantity and price per item.
	 * 
	 * @param basketMap - The Map that contains the quantity per stock item.
	 * @param billingItems - The list to which the BillingItem needs to be added.
	 * @param stockItem - The stockItem that needs to be billed.
	 */
	private void applyNormalBilling(final Map<StockItem, Integer> basketMap, final List<BillingItem> billingItems,
			final StockItem stockItem) {
		int itemQuantity = basketMap.get(stockItem);
		double itemPrice = stockItem.getCost() * itemQuantity;
		billingItems.add(new BillingItem(new BasketItem(stockItem, basketMap.get(stockItem)), formatTo2Digits(itemPrice)));
	}

	/**
	 * This method verifies if the currentDate being applied is valid for the discount or not including 
	 * both start and end days of the offer
	 * @param itemDiscountAvailable
	 * @param currentDate
	 * @return
	 */
	public boolean isDiscountValidForDate(final Discount itemDiscountAvailable, final LocalDate currentDate) {
		if ((currentDate.isEqual(itemDiscountAvailable.getValidFrom())
				|| currentDate.isAfter(itemDiscountAvailable.getValidFrom()))
				&& (currentDate.isBefore(itemDiscountAvailable.getValidTo())
						|| currentDate.isEqual(itemDiscountAvailable.getValidTo()))) {
			return true;
		}
		return false;
	}
}