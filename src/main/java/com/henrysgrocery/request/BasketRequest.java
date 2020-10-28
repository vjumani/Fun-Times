package com.henrysgrocery.request;

import java.util.List;

import com.henrysgrocery.model.BasketItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class BasketRequest {
	private final List<BasketItem> basketItems;
}
