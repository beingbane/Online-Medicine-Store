package com.demo.json;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class MedicineJson {
	
	@JsonProperty("c_unique_code")
	private String id;
	
	@JsonProperty("c_name")
	private String name;
	
	@JsonProperty("c_batch_no")
	private String batch_no;
	
	@JsonProperty("d_expiry_date")
	private Date expiry_date;
	
	@JsonProperty("n_balance_qty")
	private String quantity;
	
	@JsonProperty("c_packaging")
	private String packaging;
	
	@JsonProperty("c_schemes")
	private String scheme;
	
	
	@JsonProperty("n_mrp")
	private double mrp;
	
	@JsonProperty("c_manufacturer")
	private String manufacturer;
	
	@JsonProperty("hsn_code")
	private String code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public Date getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = Integer.toString(quantity);
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
	