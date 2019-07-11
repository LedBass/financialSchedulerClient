/**
 * 
 */
package com.marcio.financialSchedulerClient.client;

import com.google.gwt.core.shared.GwtIncompatible;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author marcio
 *
 */
public class TransactionTypeTO implements Serializable {
	private Long id;
	private String daysToTransactionInterval;
	private Double transactionTax;
	private Boolean perDayFixedTax;
	private Double transactionTaxPercentage;
	private Boolean perDayPercentageTax;
	private Double minimalTransactionValue;
	private Double maxTransactionValue;
	private String description;

	@GwtIncompatible
	public static TransactionTypeTO buildTransactionTypeTOFromJSONObject(JSONObject jsonObject) {
		TransactionTypeTO result = new TransactionTypeTO();

		result.setId(jsonObject.getLong("id"));
		result.setDaysToTransactionInterval(jsonObject.getString("daysToTransactionInterval"));

		if (jsonObject.has("transactionTax") && !jsonObject.isNull("transactionTax")) {
			result.setTransactionTax(jsonObject.getDouble("transactionTax"));

		} else {
			result.setTransactionTax(0.0);
		}

		if (jsonObject.has("transactionTaxPercentage") && !jsonObject.isNull("transactionTaxPercentage")) {
			result.setTransactionTaxPercentage(jsonObject.getDouble("transactionTaxPercentage"));

		} else {
			result.setTransactionTaxPercentage(0.0);
		}

		if (jsonObject.has("minimalTransactionValue") && !jsonObject.isNull("minimalTransactionValue")) {
			result.setMinimalTransactionValue(jsonObject.getDouble("minimalTransactionValue"));

		} else {
			result.setMinimalTransactionValue(0.0);
		}

		if (jsonObject.has("maxTransactionValue") && !jsonObject.isNull("maxTransactionValue")) {
			result.setMaxTransactionValue(jsonObject.getDouble("maxTransactionValue"));

		} else {
			result.setMaxTransactionValue(0.0);
		}

		result.setPerDayFixedTax(jsonObject.getBoolean("perDayFixedTax"));
		result.setPerDayPercentageTax(jsonObject.getBoolean("perDayPercentageTax"));
		result.setDescription(jsonObject.getString("description"));

		return result;
	}

	@Override
	public String toString() {
		return "TransactionTypeTO{" +
				"id=" + id +
				", daysToTransactionInterval='" + daysToTransactionInterval + '\'' +
				", transactionTax='" + transactionTax + '\'' +
				", perDayFixedTax=" + perDayFixedTax +
				", transactionTaxPercentage='" + transactionTaxPercentage + '\'' +
				", perDayPercentageTax=" + perDayPercentageTax +
				", minimalTransactionValue='" + minimalTransactionValue + '\'' +
				", maxTransactionValue='" + maxTransactionValue + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	public TransactionTypeTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDaysToTransactionInterval() {
		return daysToTransactionInterval;
	}

	public void setDaysToTransactionInterval(String daysToTransactionInterval) {
		this.daysToTransactionInterval = daysToTransactionInterval;
	}

	public Double getTransactionTax() {
		return transactionTax;
	}

	public void setTransactionTax(Double transactionTax) {
		this.transactionTax = transactionTax;
	}

	public Boolean getPerDayFixedTax() {
		return perDayFixedTax;
	}

	public void setPerDayFixedTax(Boolean perDayFixedTax) {
		this.perDayFixedTax = perDayFixedTax;
	}

	public Double getTransactionTaxPercentage() {
		return transactionTaxPercentage;
	}

	public void setTransactionTaxPercentage(Double transactionTaxPercentage) {
		this.transactionTaxPercentage = transactionTaxPercentage;
	}

	public Boolean getPerDayPercentageTax() {
		return perDayPercentageTax;
	}

	public void setPerDayPercentageTax(Boolean perDayPercentageTax) {
		this.perDayPercentageTax = perDayPercentageTax;
	}

	public Double getMinimalTransactionValue() {
		return minimalTransactionValue;
	}

	public void setMinimalTransactionValue(Double minimalTransactionValue) {
		this.minimalTransactionValue = minimalTransactionValue;
	}

	public Double getMaxTransactionValue() {
		return maxTransactionValue;
	}

	public void setMaxTransactionValue(Double maxTransactionValue) {
		this.maxTransactionValue = maxTransactionValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}