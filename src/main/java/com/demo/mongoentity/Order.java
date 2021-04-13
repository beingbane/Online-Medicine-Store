package com.demo.mongoentity;

import java.util.List;
import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private ObjectId order_id;
	
	private Map<String,Integer> medicines;

	public ObjectId getOrder_id() {
		return order_id;
	}

	public void setOrder_id(ObjectId order_id) {
		this.order_id = order_id;
	}

	public Map<String,Integer> getMedicines() {
		return medicines;
	}

	public void setMedicines(Map<String,Integer> medicines) {
		this.medicines = medicines;
	}
	
	
	
}
