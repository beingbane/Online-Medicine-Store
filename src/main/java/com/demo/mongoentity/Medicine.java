package com.demo.mongoentity;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.Null;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.xspec.NULL;
import com.opencsv.*;
import com.opencsv.bean.CsvBindByName;

@Component
@Document(collection = "Medicine")
public class Medicine {
	
	@CsvBindByName(column = "c_name")
	private String name;
	
	@CsvBindByName(column = "c_batch_no")
	private String batch_no;
	
	@CsvBindByName(column = "d_expiry_date")
	private Date expiry_date;
	
	@CsvBindByName(column = "n_balance_qty")
	private int quantity;
	
	@CsvBindByName(column = "c_packaging")
	private String packaging;
	
	@Id
	@CsvBindByName(column = "c_unique_code")
	private String id;

	@CsvBindByName(column = "c_schemes" , required = false)
	private String scheme;
	
	@CsvBindByName(column = "n_mrp")
	private double mrp;
	
	@CsvBindByName(column = "c_manufacturer")
	private String manufacturer;
	
	@CsvBindByName(column = "hsn_code")
	private String code;
	
	public Medicine(String name, String batch_no, Date expiry_date, int quantity, String packaging, String id,
			String scheme, double mrp, String manufacturer, String code) {
		this.name = name;
		this.batch_no = batch_no;
		this.expiry_date = expiry_date;
		this.quantity = quantity;
		this.packaging = packaging;
		this.id = id;
		this.scheme = scheme;
		this.mrp = mrp;
		this.manufacturer = manufacturer;
		this.code = code;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
