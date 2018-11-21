package com.apap.tugas.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
	
	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
	
	@RequestMapping(value = "/daftar-request", method = RequestMethod.GET)
		private String daftarRequest(Model model) throws IOException {
		List<RequestPasienModel> listReq = requestPasienService.getAll();
		List<PasienModel> listDataPasien = new ArrayList<PasienModel>();
		
//		List<String> status = new ArrayList<String>();
		
		//DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		//sDealerDetail detail = restTemplate.postForObject(path, dealer, DealerDetail.class);
		for(RequestPasienModel e: listReq) {
			Long pasienIdTes = e.getIdPasien();
			String path = "http://si-appointment.herokuapp.com/api/getPasien/"+pasienIdTes;
			String responsenya = restTemplate.getForEntity(path, String.class).getBody();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(responsenya);
			JsonNode result = node.get("result");
			System.out.println(result);
			PasienModel pasienAsli = mapper.treeToValue(result, PasienModel.class);
			System.out.println(pasienAsli.getNama());
			listDataPasien.add(pasienAsli);
			//listDataPasien.add(pasiennya);
			//System.out.println(t.getJenisStatus());
//			if(e.getAssign()==0) {
//				status.add("pending");
//			}
//			if(e.getAssign()==1) {
//				status.add("assigned");
//			}
		}
//		model.addAttribute("request", status);
		model.addAttribute("pasien", listDataPasien);
		return "daftar-request";
	}
	
	@RequestMapping(value="/daftar-request/assign/{id}", method=RequestMethod.POST)
		private String assignPasien(@PathVariable(value = "id") Long idPasien, Model model) {
			System.out.println("MASUK");
			//PasienModel pasien = pasienService.getPasienDetailById(idPasien).get();
			List<PaviliunModel> paviliun = paviliunService.getAll();
			List<KamarModel> kamar = kamarService.getAll();
			//model.addAttribute("pasien", pasien);
			model.addAttribute("paviliun", paviliun);
			model.addAttribute("kamar", kamar);
			return "assign-pasien";
	}
	
//	@RequestMapping(value="/daftar-ranap", method = RequestMethod.POST)
//		private String assignPasienSubmit(@ModelAttribute Optional<PasienModel> pasien) {
//			if(pasien.isPresent()) {
//				
//			}
//	}
}
