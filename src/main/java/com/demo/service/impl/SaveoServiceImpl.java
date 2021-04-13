package com.demo.service.impl;

import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.demo.json.MedicineJson;
import com.demo.mongoentity.Medicine;
import com.demo.mongoentity.Order;
import com.demo.service.SaveoService;
import com.mongodb.WriteResult;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class SaveoServiceImpl implements SaveoService{
	
	@Autowired
	private MongoTemplate mongoTemplate; 
	
	public boolean uploadCSV(MultipartFile file , Model model) throws Exception{
		if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
            return false;
        } 
		
		else {
			
			try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				CsvToBean<Medicine> csvToBean = new CsvToBeanBuilder(reader).withType(Medicine.class).withIgnoreLeadingWhiteSpace(true).build();
				
				List<Medicine> medicines = csvToBean.parse();
				
				for(Medicine medicine : medicines) {
					this.mongoTemplate.save(medicine);
				}
				
//				Iterator<Medicine> medicines = csvToBean.iterator();
				
				model.addAttribute("medicines", medicines);
                model.addAttribute("status", true);
				
			}
			catch(Exception e) {
				model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
			}
		}
		
		return true;
	}

	@Override
	public List<String> searchMedicine(String name) {

		List<Medicine> allMedicines = this.mongoTemplate.findAll(Medicine.class);
		List<Medicine> selectedMedicines = allMedicines.stream().parallel().filter(medicine -> medicine.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
		
		List<String> returnedMedicines = new ArrayList<>();
		
		for(Medicine selectedMedicine : selectedMedicines) {
			returnedMedicines.add(selectedMedicine.getName());
		}
 		
		return returnedMedicines;
	}

	@Override
	public MedicineJson getMedicineDetails(String c_unique_id) {
		
		List<Medicine> allMedicines = this.mongoTemplate.findAll(Medicine.class);
		List<Medicine> selectedMedicines = allMedicines.stream().parallel().filter(medicine -> medicine.getId().equals(c_unique_id)).collect(Collectors.toList());
		
		MedicineJson medicineJson = new MedicineJson();
		
		medicineJson.setBatch_no(selectedMedicines.get(0).getBatch_no());
		medicineJson.setCode(selectedMedicines.get(0).getCode());
		medicineJson.setExpiry_date(selectedMedicines.get(0).getExpiry_date());
		medicineJson.setId(selectedMedicines.get(0).getId());
		medicineJson.setManufacturer(selectedMedicines.get(0).getManufacturer());
		medicineJson.setMrp(selectedMedicines.get(0).getMrp());
		medicineJson.setName(selectedMedicines.get(0).getName());
		medicineJson.setPackaging(selectedMedicines.get(0).getPackaging());
		medicineJson.setQuantity(selectedMedicines.get(0).getQuantity());
		medicineJson.setScheme(selectedMedicines.get(0).getScheme());
		
		return medicineJson;
	}

	@Override
	public Order placeOrder(List<Map<String, String>> orderMedicines) {
		
		Map<String,Integer> medicinesToBeOrdered = new HashMap<>();
		
		for(Map<String, String> orderMedicine : orderMedicines) {
			String unique_Id = orderMedicine.get("c_unique_id");
			
			Query medicineQuery = new Query();
			medicineQuery.addCriteria(Criteria.where("id").is(unique_Id));
			List<Medicine> medicines = this.mongoTemplate.find(medicineQuery, Medicine.class);
			
			if(medicines != null) {
				if(medicines.get(0).getQuantity() >= Integer.parseInt(orderMedicine.get("quantity")) && medicines.get(0).getName().equalsIgnoreCase(orderMedicine.get("c_name"))) {
					medicinesToBeOrdered.put(medicines.get(0).getName(), Integer.parseInt(orderMedicine.get("quantity")));
					int quantity = medicines.get(0).getQuantity();
					quantity = quantity - Integer.parseInt(orderMedicine.get("quantity"));
					medicines.get(0).setQuantity(quantity);
					this.mongoTemplate.save(medicines.get(0));
				}
				else {
					return null;
				}
			}
			else {
				return null;
			}
		}
		
		Order order = new Order();
		order.setMedicines(medicinesToBeOrdered);
		this.mongoTemplate.save(order);
		
		return order;
		
	}

}