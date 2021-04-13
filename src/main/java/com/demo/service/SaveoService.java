package com.demo.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;


import org.springframework.web.multipart.MultipartFile;

import com.demo.json.MedicineJson;
import com.demo.mongoentity.Order;

public interface SaveoService {

	public boolean uploadCSV(MultipartFile file , Model model) throws Exception;
	public List<String> searchMedicine(String name);
	public MedicineJson getMedicineDetails(String c_unique_id);
	public Order placeOrder(List<Map<String , String>> orderMedicines);
	
}