package com.amazonaws.lambda.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatedJson {

	private String Key;
	private String Value;
	
	@JsonProperty("Key")
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	
	@JsonProperty("Value")
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
}
