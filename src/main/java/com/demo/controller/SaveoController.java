package com.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.json.MedicineJson;
import com.demo.mongoentity.Medicine;
import com.demo.mongoentity.Order;
import com.demo.service.SaveoService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


@CrossOrigin
@RestController
@RequestMapping("/saveo")
public class SaveoController {
	
	@Autowired
	 private SaveoService videoUplaodServiceImpl;  
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@PostMapping("/uploadCSV")
	public ResponseEntity<Boolean> uploadCSV(@RequestParam("file") MultipartFile file, Model model) {
		
		HttpStatus statusCode = null;
		Boolean uploaded = false;
		try {
			uploaded = this.videoUplaodServiceImpl.uploadCSV(file, model);
			statusCode = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Boolean>(uploaded,statusCode);
		
	}
	
	@GetMapping("/searchMedicine")
	public @ResponseBody List<String> searchMedicine(@RequestParam String name){
		
		return this.searchMedicine(name);
		
	}
	
	@GetMapping("/{c_unique_id}/getMedicineDetails")
	public ResponseEntity<MedicineJson> getMedicineDetails(@PathVariable String c_unique_id){
		
		HttpStatus statusCode =  null;
		MedicineJson medicineJson = this.videoUplaodServiceImpl.getMedicineDetails(c_unique_id);
		statusCode = HttpStatus.OK;
		
		return new ResponseEntity<MedicineJson>(medicineJson,statusCode);
		
	}
	
	@PostMapping("/placeOrder")
	public ResponseEntity<ObjectId> placeOrder(@RequestBody List<Map<String, String>> orderMedicines){
		HttpStatus statusCode =  null;
		ObjectId orderId;
		
		Order order = this.videoUplaodServiceImpl.placeOrder(orderMedicines);
		
		if(order != null) {
			statusCode = HttpStatus.OK;
			orderId = order.getOrder_id();
		}
		else {
			orderId = new ObjectId("null");
			statusCode = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<ObjectId>(orderId,statusCode);
	}
	    
}
