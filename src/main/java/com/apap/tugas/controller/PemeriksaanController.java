package com.apap.tugas.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.apap.tugas.model.DokterModel;
import com.apap.tugas.model.PasienModel;
import com.apap.tugas.model.PemeriksaanDataModel;
import com.apap.tugas.model.PemeriksaanModel;
import com.apap.tugas.repository.PemeriksaanDb;
import com.apap.tugas.service.PemeriksaanService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class PemeriksaanController {
	RestTemplate restTemplate = new RestTemplate();
	@Autowired
	PemeriksaanService pemeriksaanService; 
	@Autowired
	PemeriksaanDb pemeriksaanDB;
	
	@RequestMapping(value = "/penanganan/{idPasien}", method = RequestMethod.GET)
	private String daftarRequest(@PathVariable (value ="idPasien") long idPasien , Model model) throws IOException {
		//PemeriksaanModel pemeriksaan = pemeriksaanService.getPemeriksaanDetailByIdPasien(idPasien);
		
		List<PemeriksaanDataModel> myPemeriksaan = new ArrayList<>();
		for (PemeriksaanModel pemeriksaannya : pemeriksaanDB.findAll()) {
			if (pemeriksaannya.getIdPasien().equals(idPasien)) {
				
				PemeriksaanDataModel data = new PemeriksaanDataModel();
				data.setDeskripsi(pemeriksaannya.getDeskripsi());
				data.setWaktu(pemeriksaannya.getWaktu());
				data.setDokter(getDokterDataFromApi(pemeriksaannya.getIdDokter()));
				data.setIdPasien(pemeriksaannya.getIdPasien());
				data.setIdPemeriksaan(pemeriksaannya.getId());
				myPemeriksaan.add(data);
			}
		}
		PasienModel pasien = getPasienDataFromApi(idPasien);
		model.addAttribute("listPemeriksaan", myPemeriksaan);
		return "lihat-pemeriksaan";
	
	}	
	@RequestMapping(value = "/penanganan/{idPasien}/{idPenanganan}", method = RequestMethod.GET)
	public String tambahPasien (@PathVariable (value ="idPasien") long idPasien , @PathVariable (value ="idPenanganan") long idPenanganan, Model model) throws IOException {
		PemeriksaanModel pemeriksaan = pemeriksaanService.getPemeriksaanDetailByIdPasien(idPasien);
		PasienModel pasien = getPasienDataFromApi(idPasien);
		List<DokterModel> listDokter = getAllDokterDataFromApi();
		String namaDokter = getDokterDataFromApi(pemeriksaan.getIdDokter()).getNama();
		System.out.println(pemeriksaan.getIdPasien());
		System.out.println("nama =>"+pasien.getNama());
		model.addAttribute("dokter" , listDokter);
		model.addAttribute("namaDokter", namaDokter);
		model.addAttribute("pasien" , pasien);
		model.addAttribute("pemeriksaan", pemeriksaan);
		return "ubahPenanganan";
	
	}	
/*	@RequestMapping(value = "/penanganan/{idPasien}/{idPenanganan}", method = RequestMethod.POST)
	public String tambahPasienDone (@PathVariable (value ="idPasien") long idPasien ,  Model model) throws IOException {
		PemeriksaanModel pemeriksaan = pemeriksaanService.getPemeriksaanDetailByIdPasien(idPasien);
		PasienModel pasien = getPasienDataFromApi(idPasien);
		DokterModel dokter = getDokterDataFromApi(pemeriksaan.getIdDokter());
		
		
		model.addAttribute("dokter" , dokter);
		model.addAttribute("pasien" , pasien);
		model.addAttribute("pemeriksaan", pemeriksaan);
		return "UbahPenanganan";
	
	}*/	
		@RequestMapping(value = "/penanganan/{idPasien}/{idPenanganan}", method = RequestMethod.POST)
		public String tambahPasienn (@ModelAttribute PemeriksaanModel pemeriksa, @PathVariable (value ="idPasien") long idPasien, @PathVariable (value ="idPenanganan") long idPenanganan, Model model) throws IOException {
		pemeriksaanService.updatePenanganan(idPasien, pemeriksa);
		PasienModel pasien = getPasienDataFromApi(idPasien);
		DokterModel dokter = getDokterDataFromApi(pemeriksa.getIdDokter());		
		model.addAttribute("dokter" , dokter);
		model.addAttribute("pasien" , pasien);
		model.addAttribute("pemeriksa", pemeriksa);
		return "redirect:/penanganan/{idPasien}";
	
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
	
	private List<DokterModel> getAllDokterDataFromApi() throws IOException {
		String path = "http://si-appointment.herokuapp.com/api/2/getAllDokter";
		String responsenya = restTemplate.getForEntity(path, String.class).getBody();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(responsenya);
		JsonNode result = node.get("result");
		System.out.println(result);
		List<DokterModel> dokterAsli = mapper.readValue(result.traverse(), new TypeReference<List<DokterModel>>(){});
		return dokterAsli;
	}
}
