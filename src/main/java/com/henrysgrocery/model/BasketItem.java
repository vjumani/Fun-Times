package com.henrysgrocery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class BasketItem {
	private StockItem stockItem;
	private int quantity;
}
