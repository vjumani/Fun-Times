package com.henrysgrocery.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class Bill {
	private List<BillingItem> billingItems;
	private double billingTotal;
}
