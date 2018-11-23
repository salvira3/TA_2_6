package com.apap.tugas.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.apap.tugas.model.PasienIgdModel;
import com.apap.tugas.model.PasienModel;
import com.apap.tugas.model.RequestPasienModel;
import com.apap.tugas.repository.RequestPasienDb;
import com.apap.tugas.rest.BaseResponse;
import com.apap.tugas.rest.ListPasienRanap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	RequestPasienDb requestPasienDb;
	
	
	RestTemplate restTemplate = new RestTemplate();
	
	@GetMapping(value="/get-all-kamar")
	public BaseResponse<List<PasienModel>> getAllPasienRanap() {
		BaseResponse response = new BaseResponse();
		//get all pasien
		String path = "http://si-appointment.herokuapp.com/api/2/getAllPasien";
		ListPasienRanap listPasien = restTemplate.getForObject(path, ListPasienRanap.class);
		List<PasienModel> allPasienRanap = new ArrayList<>();
		for (PasienModel pasien : listPasien.getResult()) {
			if (pasien.getStatusPasien().getId() == 5) {
				allPasienRanap.add(pasien);
			}
		}
		response.setStatus(200);
		response.setMessage("success");
		response.setResult(allPasienRanap);
		return response;
	}
	
	
	@PostMapping(value = "/daftar-ranap")
	public BaseResponse postPasienFromIgd(@RequestBody PasienIgdModel pasienMasuk, BindingResult bindingResult) throws IOException {
		BaseResponse response = new BaseResponse();
		System.out.println("masuk api daftar ranap");
		for (RequestPasienModel listReqPasien : requestPasienDb.findAll()) {
			if (pasienMasuk.getId() == listReqPasien.getIdPasien()) {
				response.setStatus(500);
				response.setMessage("error data");
				return response;
			}
		}
		if (bindingResult.hasErrors()) { 
			response.setStatus(500);
            response.setMessage("error data");
		}
		else {
			
			RequestPasienModel reqPasien = new RequestPasienModel();
			reqPasien.setAssignStatus(0); //0 belum diassign
			reqPasien.setIdPasien(pasienMasuk.getId());
			requestPasienDb.save(reqPasien);
			
			
			//update status pasien asli ke si appointment
			System.out.println(pasienMasuk.getId()+"<-pasienMasuk getId");
			String path = "http://si-appointment.herokuapp.com/api/2/updatePasien";
			PasienModel pasienUpdate = getPasienDataFromApi(pasienMasuk.getId());
			pasienUpdate.getStatusPasien().setId(4);
			pasienUpdate.getStatusPasien().setJenis("Mendaftar di Rawat Inap");
			BaseResponse updated = restTemplate.postForObject(path, pasienUpdate, BaseResponse.class);
			System.out.println(updated.getResult());
			
			response.setStatus(200);
			response.setMessage("success");
			response.setResult(pasienUpdate);
			
		}
		
		
		
		return response;
	}
	
	private PasienModel getPasienDataFromApi(long id) throws IOException {
		String path = "http://si-appointment.herokuapp.com/api/getPasien/"+ id;
		String responsenya = restTemplate.getForEntity(path, String.class).getBody();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(responsenya);
		JsonNode result = node.get("result");
		PasienModel pasienAsli = mapper.treeToValue(result, PasienModel.class);
		return pasienAsli;
	}

}