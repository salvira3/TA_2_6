package com.apap.tugas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apap.tugas.model.PasienIgdModel;
import com.apap.tugas.model.RequestPasienModel;
import com.apap.tugas.repository.RequestPasienDb;
import com.apap.tugas.rest.BaseResponse;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	RequestPasienDb requestPasienDb;
	
	@PostMapping(value = "/daftar-ranap")
	public BaseResponse postPasienFromIgd(@RequestBody PasienIgdModel pasienMasuk, BindingResult bindingResult) {
		BaseResponse response = new BaseResponse();
		System.out.println("masuk api daftar ranap");
		if (bindingResult.hasErrors() || pasienMasuk.getKey() != 10) { //key proposed 10
			response.setStatus(500);
            response.setMessage("error data");
		}
		else {
			RequestPasienModel reqPasien = new RequestPasienModel();
			reqPasien.setAssignStatus(0); //0 belum diassign
			reqPasien.setIdPasien(pasienMasuk.getId());
			requestPasienDb.save(reqPasien);
			
			response.setStatus(200);
			response.setMessage("success");
		}
		
		
		
		return response;
	}

}
