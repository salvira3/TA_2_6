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
import org.springframework.web.servlet.ModelAndView;

import com.apap.tugas.model.DokterModel;
import com.apap.tugas.model.MedicalSuppliesModel;
import com.apap.tugas.model.ObatModel;
import com.apap.tugas.model.PasienModel;
import com.apap.tugas.model.PemeriksaanModel;
import com.apap.tugas.model.RequestObatModel;
import com.apap.tugas.repository.PemeriksaanDb;
import com.apap.tugas.rest.BaseResponse;
import com.apap.tugas.rest.Setting;
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
			long tmp = e.getStatusPasien().getId();
			if(tmp==5) {
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
	  ObatModel obat = new ObatModel();
	  obat.setNama(pemeriksaan.getObat());
	  MedicalSuppliesModel med = new MedicalSuppliesModel();
	  med.setMedicalSupplies(obat);
	  ArrayList<MedicalSuppliesModel> medSupp = new ArrayList<MedicalSuppliesModel>();
	  medSupp.add(med);
	  RequestObatModel reqObat = new RequestObatModel();
	  reqObat.setJumlahMedicalSupplies(pemeriksaan.getKuantitas());
	  reqObat.setIdPasien(pemeriksaan.getIdPasien());
	  reqObat.setListPermintaanMedicalSupplies(medSupp);
	  String path = Setting.requestObatUrl;
	  BaseResponse insert = restTemplate.postForObject(path, reqObat, BaseResponse.class);
	  System.out.println("result" + insert.getResult());
	  pemeriksaanService.add(pemeriksaan);
	  ModelAndView tmp = new ModelAndView("redirect:/penanganan/" + pemeriksaan.getIdPasien()); 
	  return tmp;
	 }
	
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
	@RequestMapping(value = "/penanganan/{idPasien}/{idPenanganan}", method = RequestMethod.GET)
	public String tambahPasien (@PathVariable (value ="idPasien") long idPasien , @PathVariable (value ="idPenanganan") long idPenanganan, Model model) throws IOException {
		PemeriksaanModel pemeriksaan = pemeriksaanService.getPemeriksaanDetailByIdPasien(idPasien);
		PasienModel pasien = getPasienDataFromApi(idPasien);
		List<DokterModel> listDokter = getAllDokterDataFromApi();
		String namaDokter = getDokterDataFromApi(pemeriksaan.getIdDokter()).getNama();
		System.out.println(pemeriksaan.getIdPasien());
		System.out.println("nama =>"+pasien.getNama());
		System.out.println("Dokter"+ namaDokter);
		model.addAttribute("dokter" , listDokter);
		model.addAttribute("namaDokter", namaDokter);
		model.addAttribute("pasien" , pasien);
		model.addAttribute("pemeriksaan", pemeriksaan);
		return "ubahPenanganan";
	
	}	
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
