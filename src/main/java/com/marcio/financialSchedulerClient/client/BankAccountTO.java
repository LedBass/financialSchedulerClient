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
public class BankAccountTO implements Serializable {
	private Long id;
	private UserTO user;
	private Double balance;
	private Boolean valid;

	@GwtIncompatible
	public static BankAccountTO buildBankAccountToFromJSONObject(JSONObject jsonObject) {
		BankAccountTO result = new BankAccountTO();
		UserTO user = UserTO.buildUserTOFromJSONObject(jsonObject.getJSONObject("user"));

		result.setId(jsonObject.getLong("id"));
		result.setUser(user);
		result.setBalance(jsonObject.getDouble("balance"));
		result.setValid(jsonObject.getBoolean("valid"));

		return result;
	}

	public BankAccountTO() {}

	public BankAccountTO(Long id, UserTO user, Double balance, Boolean valid) {
		this.id = id;
		this.user = user;
		this.balance = balance;
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "BankAccountTO{" +
				"id=" + id +
				", user=" + user +
				", balance='" + balance + '\'' +
				", valid=" + valid +
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}
}
