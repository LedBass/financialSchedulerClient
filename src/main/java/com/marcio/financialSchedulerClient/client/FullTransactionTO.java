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
public class FullTransactionTO implements Serializable {
	private Long id;
	private UserTO user;
	private BankAccountTO sourceAccount;
	private BankAccountTO destinationAccount;
	private TransactionTypeTO transactionType;
	private Double transactionValue;
	private Double paidTransactionTax;
	private String transactionSubmitDate;
	private String transactionScheduleDate;
	private Integer daysInterval;

	@GwtIncompatible("Incompatible")
	public static FullTransactionTO buildFullTransactionTOFromJSONObject(JSONObject jsonObject) {
		BankAccountTO sourceAccount =
				BankAccountTO.buildBankAccountToFromJSONObject(jsonObject.getJSONObject("sourceAccount"));
		BankAccountTO destinationAccount =
				BankAccountTO.buildBankAccountToFromJSONObject(jsonObject.getJSONObject("destinationAccount"));
		UserTO user = UserTO.buildUserTOFromJSONObject(jsonObject.getJSONObject("user"));
		TransactionTypeTO transactionType =
				TransactionTypeTO.buildTransactionTypeTOFromJSONObject(jsonObject.getJSONObject("transactionType"));

		FullTransactionTO result = new FullTransactionTO();
		result.setId(jsonObject.getLong("id"));
		result.setUser(user);
		result.setSourceAccount(sourceAccount);
		result.setDestinationAccount(destinationAccount);
		result.setTransactionType(transactionType);
		result.setTransactionValue(jsonObject.getDouble("transactionValue"));
		result.setPaidTransactionTax(jsonObject.getDouble("paidTransactionTax"));
		result.setTransactionSubmitDate(jsonObject.getString("transactionSubmitDate"));
		result.setTransactionScheduleDate(jsonObject.getString("transactionScheduleDate"));
		result.setDaysInterval(jsonObject.getInt("intervalDays"));

		return result;
	}

	public FullTransactionTO() {}

	@Override
	public String toString() {
		return "FullTransactionTO{" +
				"id=" + id +
				", user=" + user +
				", sourceAccount=" + sourceAccount +
				", destinationAccount=" + destinationAccount +
				", transactionType=" + transactionType +
				", transactionValue='" + transactionValue + '\'' +
				", paidTransactionTax='" + paidTransactionTax + '\'' +
				", transactionSubmitDate='" + transactionSubmitDate + '\'' +
				", transactionScheduleDate='" + transactionScheduleDate + '\'' +
				", daysInterval=" + daysInterval +
				'}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserTO getUser() {
		return user;
	}

	public void setUser(UserTO user) {
		this.user = user;
	}

	public BankAccountTO getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(BankAccountTO sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public BankAccountTO getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(BankAccountTO destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public TransactionTypeTO getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionTypeTO transactionType) {
		this.transactionType = transactionType;
	}

	public Double getTransactionValue() {
		return transactionValue;
	}

	public void setTransactionValue(Double transactionValue) {
		this.transactionValue = transactionValue;
	}

	public Double getPaidTransactionTax() {
		return paidTransactionTax;
	}

	public void setPaidTransactionTax(Double paidTransactionTax) {
		this.paidTransactionTax = paidTransactionTax;
	}

	public String getTransactionSubmitDate() {
		return transactionSubmitDate;
	}

	public void setTransactionSubmitDate(String transactionSubmitDate) {
		this.transactionSubmitDate = transactionSubmitDate;
	}

	public String getTransactionScheduleDate() {
		return transactionScheduleDate;
	}

	public void setTransactionScheduleDate(String transactionScheduleDate) {
		this.transactionScheduleDate = transactionScheduleDate;
	}

	public Integer getDaysInterval() {
		return daysInterval;
	}

	public void setDaysInterval(Integer daysInterval) {
		this.daysInterval = daysInterval;
	}
}
