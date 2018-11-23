package com.apap.tugas.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.apap.tugas.model.KamarModel;
import com.apap.tugas.model.PasienModel;
import com.apap.tugas.model.PaviliunModel;
import com.apap.tugas.model.RequestPasienModel;
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
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/daftar-request", method = RequestMethod.GET)
		private String daftarRequest(Model model) throws IOException {
		List<RequestPasienModel> listReq = requestPasienService.getAll();
		List<PasienModel> listDataPasien = new ArrayList<PasienModel>();
		

		for(RequestPasienModel e: listReq) {
			if(e.getAssignStatus()==0) {
				Long pasienIdTes = e.getIdPasien();
				PasienModel pasien = getPasienDataFromApi(pasienIdTes);
//				StatusPasienModel status = new StatusPasienModel();
//				status.setId(5);
//				status.setJenis("Berada di Rawat Inap");
//				pasienAsli.setStatusPasien(status);
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
			model.addAttribute("pasien", pasien);
			model.addAttribute("paviliun", paviliun);
			model.addAttribute("kamar", kamar);
			return "assign-pasien";
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
//	@RequestMapping(value="/daftar-ranap", method = RequestMethod.POST)
//		private String assignPasienSubmit(@ModelAttribute Optional<PasienModel> pasien) {
//			if(pasien.isPresent()) {
//				
//			}
//	}
}
