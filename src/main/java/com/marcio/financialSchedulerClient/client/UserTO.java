/**
 * 
 */
package com.marcio.financialSchedulerClient.client;

import com.google.gwt.core.shared.GwtIncompatible;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author marcio
 *
 */
public class UserTO implements Serializable {

	private Long id;
	private String name;
	private Boolean valid;

	public UserTO() {}

	public UserTO(Long id, String name, Boolean valid) {
		this.setId(id);
		this.setName(name);
		this.setValid(valid);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserTO userTO = (UserTO) o;
		return Objects.equals(getId(), userTO.getId()) &&
				Objects.equals(getName(), userTO.getName()) &&
				Objects.equals(getValid(), userTO.getValid());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getValid());
	}

	@Override
	public String toString() {
		return "UserTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", valid=" + valid +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@GwtIncompatible
	public static UserTO buildUserTOFromJSONObject(JSONObject jsonObject) {
		return new UserTO(jsonObject.getLong("id"),
				jsonObject.getString("name"),
				jsonObject.getBoolean("valid"));
	}
}
