package com.sampleproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ResponseOrderDTO {
	private double amount;
	private int invoiceNumber;
	private String date;
	private String OrderDescription;
	private int orderId;
}
