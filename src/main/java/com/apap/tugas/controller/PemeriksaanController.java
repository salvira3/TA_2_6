package com.apap.tugas.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.apap.tugas.model.DokterModel;
import com.apap.tugas.model.PasienModel;
import com.apap.tugas.model.PemeriksaanModel;
import com.apap.tugas.service.PemeriksaanService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PemeriksaanController {
	RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	PemeriksaanService pemeriksaanService; 
	
	@RequestMapping(value = "/penanganan/{idPasien}", method = RequestMethod.GET)
	private String daftarRequest(@PathVariable (value ="idPasien") long idPasien , Model model) throws IOException {
		PemeriksaanModel pemeriksaan = pemeriksaanService.getPemeriksaanDetailByIdPasien(idPasien);
		PasienModel pasien = getPasienDataFromApi(idPasien);
		DokterModel dokter = getDokterDataFromApi(pemeriksaan.getIdDokter());
		
		model.addAttribute("dokter" , dokter);
		model.addAttribute("pasien" , pasien);
		model.addAttribute("pemeriksaan", pemeriksaan);
		return "lihat-pemeriksaan";
	
	}	
	private PasienModel getPasienDataFromApi(long id) throws IOException {
		String path = "http://si-appointment.herokuapp.com/api/getPasien/"+ id;
		String responsenya = restTemplate.getForEntity(path, String.class).getBody();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(responsenya);
		JsonNode result = node.get("result");
		System.out.println(result);
		PasienModel pasienAsli = mapper.treeToValue(result, PasienModel.class);
		return pasienAsli;
	}
	
	private DokterModel getDokterDataFromApi(long id) throws IOException {
		String path = "http://si-appointment.herokuapp.com/api/getDokter/"+ id;
		String responsenya = restTemplate.getForEntity(path, String.class).getBody();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(responsenya);
		JsonNode result = node.get("result");
		System.out.println(result);
		DokterModel dokterAsli = mapper.treeToValue(result, DokterModel.class);
		return dokterAsli;
	}
}
