package com.apap.tugas.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.apap.tugas.model.KamarModel;
import com.apap.tugas.model.PasienModel;
import com.apap.tugas.model.PaviliunModel;
import com.apap.tugas.model.RequestPasienModel;
import com.apap.tugas.model.StatusPasienModel;
import com.apap.tugas.service.KamarService;
import com.apap.tugas.service.PaviliunService;
import com.apap.tugas.service.RequestPasienService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PasienController {
	
	@Autowired
	private PaviliunService paviliunService;
	
	@Autowired
	private KamarService kamarService;
	
	@Autowired
	private RequestPasienService requestPasienService;
	
	
	RestTemplate restTemplate = new RestTemplate();
	
	
	
	@RequestMapping(value = "/daftar-request", method = RequestMethod.GET)
		private String daftarRequest(Model model) throws IOException {
		List<RequestPasienModel> listReq = requestPasienService.getAll();
		List<PasienModel> listDataPasien = new ArrayList<PasienModel>();
		

		for(RequestPasienModel e: listReq) {
			if(e.getAssignStatus()==0) {
				Long pasienIdTes = e.getIdPasien();
				PasienModel pasien = getPasienDataFromApi(pasienIdTes);
				listDataPasien.add(pasien);
			}
		}
		model.addAttribute("pasien", listDataPasien);
		return "daftar-request";
	}
	
	@RequestMapping(value="/daftar-request/assign/{id}", method=RequestMethod.GET)
		private String assignPasien(@PathVariable(value = "id") Long idPasien, Model model) throws IOException {
			System.out.println("MASUK");
			PasienModel pasien = getPasienDataFromApi(idPasien);
			List<PaviliunModel> paviliun = paviliunService.getAll();
			List<KamarModel> kamar = kamarService.getAll();
			List<KamarModel> kamarKosong = new ArrayList<KamarModel>();
			for (KamarModel e: kamar) {
				if(e.getIdPasien() == null) {
					kamarKosong.add(e);
				}
			}
			model.addAttribute("pasien", pasien);
			model.addAttribute("paviliun", paviliun);
			model.addAttribute("kamar", kamarKosong);
			return "assign-pasien";
	}
	
	@RequestMapping(value="/daftar-ranap", method = RequestMethod.POST)
	private String assignPasienSubmit(@RequestParam(value="idPasien") Long idPasien, @RequestParam(value="statusPasien") StatusPasienModel statusPasien, @RequestParam(value="idPaviliun") Long idPaviliun, @RequestParam(value="idKamar") Long idKamar) throws IOException {
			KamarModel kamar = kamarService.getKamarDetailById(idKamar).get();
			kamar.setIdPasien(idPasien);
			kamar.setStatusKamar(1);
			PaviliunModel paviliun = paviliunService.getPaviliunDetailById(idPaviliun).get();
			if(paviliun.getStatusPaviliun()==0) {
				paviliun.setStatusPaviliun(1);
			}
			PasienModel pasien = getPasienDataFromApi(idPasien);
			StatusPasienModel status = new StatusPasienModel();
			status.setId(5);
			status.setJenis("Berada di Rawat Inap");
			pasien.setStatusPasien(status);
			List<RequestPasienModel> listReq = requestPasienService.getAll();
			for(RequestPasienModel e: listReq) {
				if(e.getIdPasien()==idPasien) {
					e.setAssignStatus(1);
				}
			}
		return "assign-pasien-success";
	}

	@RequestMapping(value = "/paviliun/getKamarByPaviliun", method = RequestMethod.GET)
	@ResponseBody
	public List<KamarModel> getKamar(@RequestParam (value = "idPaviliun", required = true) Long idPaviliun) {
		PaviliunModel paviliun = paviliunService.getPaviliunDetailById(idPaviliun).get();
		List<KamarModel> selectedKamar = kamarService.getKamarByPaviliun(paviliun);
	    return selectedKamar;
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
	
}