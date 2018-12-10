package com.apap.tugas.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;

import com.apap.tugas.model.DokterModel;
import com.apap.tugas.model.PasienModel;
import com.apap.tugas.model.PemeriksaanModel;
import com.apap.tugas.service.PemeriksaanService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Controller
public class PemeriksaanController {
	RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	PemeriksaanService pemeriksaanService; 
	
	@RequestMapping(value="/penanganan")
	private String penanganan(Model model) throws IOException {
		return "penanganan";
	}
	
	@RequestMapping(value="/penanganan/insert", method = RequestMethod.GET)
	private String insertPenanganan(Model model) throws IOException {
		List<DokterModel> listDokter = getAllDokterDataFromApi();
		List<PasienModel> listPasien = getAllPasienDataFromApi();
		List<PasienModel> listPasienRanap = new ArrayList<PasienModel>();
		for (PasienModel e: listPasien) {
			if(e.getStatusPasien().getJenis().equals("Berada di Rawat Inap")) {
				listPasienRanap.add(e);
			}
		}
		PemeriksaanModel pemeriksaan = new PemeriksaanModel();
		model.addAttribute("pemeriksaan", pemeriksaan);
		model.addAttribute("dokter", listDokter);
		model.addAttribute("pasien", listPasienRanap);
		return "add-penanganan";
	}
	
	@RequestMapping(value = "/penanganan/insert", method = RequestMethod.POST)
	private ModelAndView addPenangananSubmit(@ModelAttribute PemeriksaanModel pemeriksaan, Model model) {
		System.out.println(pemeriksaan.getIdDokter());
		pemeriksaanService.add(pemeriksaan);
		ModelAndView tmp = new ModelAndView("redirect:/penanganan/" + pemeriksaan.getIdPasien()); 
		return tmp;
	}
	
	@RequestMapping(value = "/penanganan/{idPasien}", method = RequestMethod.GET)
	private String penangananPasien(@PathVariable (value ="idPasien") long idPasien , Model model) throws IOException {
		PemeriksaanModel pemeriksaan = pemeriksaanService.getPemeriksaanDetailByIdPasien(idPasien);
		PasienModel pasien = getPasienDataFromApi(idPasien);
		System.out.println("id dokter->"+pemeriksaan.getIdDokter());
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
	
	private List<PasienModel> getAllPasienDataFromApi() throws IOException {
		String path = "http://si-appointment.herokuapp.com/api/2/getAllPasien";
		String responsenya = restTemplate.getForEntity(path, String.class).getBody();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(responsenya);
		JsonNode result = node.get("result");
		System.out.println(result);
		List<PasienModel> pasienAsli = mapper.readValue(result.traverse(), new TypeReference<List<PasienModel>>(){});
		return pasienAsli;
	}
}
