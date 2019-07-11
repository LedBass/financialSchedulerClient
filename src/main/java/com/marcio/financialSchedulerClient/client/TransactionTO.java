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
public class TransactionTO implements Serializable {
	private Long sourceAccountId;
	private Long destinationAccountId;
	private String scheduleDate;
	private String transactionValue;

	@GwtIncompatible("Incompatible")
	public static TransactionTO buildTransactionTOFromJSONObject(JSONObject jsonObject) {
		return new TransactionTO(jsonObject.getLong("sourceAccountId"),
								 jsonObject.getLong("destinationAccountId"),
								 jsonObject.getString("scheduleDate"),
								 jsonObject.getString("transactionValue"));
	}

	public TransactionTO() {}

	public TransactionTO(Long sourceAccountId, Long destinationAccountId, String scheduleDate, String transactionValue) {
		this.sourceAccountId = sourceAccountId;
		this.destinationAccountId = destinationAccountId;
		this.scheduleDate = scheduleDate;
		this.transactionValue = transactionValue;
	}

	public Long getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(Long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public Long getDestinationAccountId() {
		return destinationAccountId;
	}

	public void setDestinationAccountId(Long destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getTransactionValue() {
		return transactionValue;
	}

	public void setTransactionValue(String transactionValue) {
		this.transactionValue = transactionValue;
	}
}