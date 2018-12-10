
package com.apap.tugas.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tugas.configuration.Config;
import com.apap.tugas.model.KamarModel;
import com.apap.tugas.model.PasienModel;
import com.apap.tugas.model.PaviliunModel;
import com.apap.tugas.model.RequestPasienModel;
import com.apap.tugas.model.StatusPasienModel;
import com.apap.tugas.repository.RequestPasienDb;
import com.apap.tugas.rest.BaseResponse;
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
	private RequestPasienDb reqPasienDb;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Config config;


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
			List<PaviliunModel> paviliunKosong = new ArrayList<PaviliunModel>();
			List<KamarModel> kamar = kamarService.getAll();
			List<KamarModel> kamarKosong = new ArrayList<KamarModel>();
			for (KamarModel e: kamar) {
				int tmp = e.getStatusKamar();
				if(tmp==0) {
					paviliunKosong.add(e.getPaviliun());
					e.getPaviliun().setStatusPaviliun(1);
					kamarKosong.add(e);
					}
				}
			Set<PaviliunModel> hs = new HashSet<>();
			hs.addAll(paviliunKosong);
			paviliunKosong.clear();
			paviliunKosong.addAll(hs);
			model.addAttribute("pasien", pasien);
			model.addAttribute("paviliun", paviliunKosong);
			model.addAttribute("kamar", kamarKosong);
			return "assign-pasien";
	}
	
	@RequestMapping(value="/daftar-ranap", method = RequestMethod.POST)
	private ModelAndView assignPasienSubmit(@RequestParam(value="idPasien") Long idPasien, @RequestParam(value="idPaviliun") Long idPaviliun, @RequestParam(value="idKamar") Long idKamar, Model model, RedirectAttributes redir) throws IOException {
			KamarModel kamar = kamarService.getKamarDetailById(idKamar).get();
			kamar.setIdPasien(idPasien);
			kamar.setStatusKamar(1);
			PaviliunModel paviliun = paviliunService.getPaviliunDetailById(idPaviliun).get();
			if(paviliun.getStatusPaviliun()==0) {
				paviliun.setStatusPaviliun(1);
			}
			PasienModel pasien = getPasienDataFromApi(idPasien);
			pasien.getStatusPasien().setId(5);
			List<RequestPasienModel> listReq = requestPasienService.getAll();
			for(RequestPasienModel e: listReq) {
				if(e.getIdPasien()==idPasien) {
					e.setAssignStatus(1);
					reqPasienDb.save(e);
				}
			}
			String path = "http://si-appointment.herokuapp.com/api/2/updatePasien";
			BaseResponse updated = restTemplate.postForObject(path, pasien, BaseResponse.class);
			System.out.println(updated.getResult());
			String msg = "Pasien " + pasien.getNama() + " berhasil dimasukkan ke Paviliun " + kamar.getPaviliun().getNamaPaviliun() + ", di Kamar nomor " + kamar.getId();
			ModelAndView modelAndView = new ModelAndView("redirect:/daftar-ranap");
			redir.addFlashAttribute("msg", msg);
			return modelAndView;
	}
	
	@RequestMapping(value="/daftar-ranap", method = RequestMethod.GET)
	private String daftarRanap(Model model) throws IOException {
		Map<PasienModel, KamarModel> listRanap = new HashMap<PasienModel, KamarModel>();	
		for(KamarModel e: kamarService.getAll()) {
			if(e.getStatusKamar()==1) {
				listRanap.put(getPasienDataFromApi(e.getIdPasien()), e);
			}
		}
		model.addAttribute("listPasien", listRanap);
		return "assign-pasien-success";
	}

	@RequestMapping(value="/daftar-pasien/pulang/{idKamar}", method = RequestMethod.GET)
	private ModelAndView pasienPulang(@PathVariable(value="idKamar") long idKamar, RedirectAttributes redir, Model model) throws IOException {
		KamarModel kamar = kamarService.getKamarById(idKamar);
		kamar.setStatusKamar(0);
		int tmp = 0;
		for(KamarModel e: kamarService.getAll()) {
			if(e.getPaviliun().equals(kamar.getPaviliun()) && (e.getStatusKamar()==1)){
				tmp++;
			}
		}
		if(tmp==0) {
			kamar.getPaviliun().setStatusPaviliun(0);
		}
		
		String path = "http://si-appointment.herokuapp.com/api/2/updatePasien";
		PasienModel pasienUpdate = getPasienDataFromApi(kamar.getIdPasien());
		pasienUpdate.getStatusPasien().setId(6);
		pasienUpdate.getStatusPasien().setJenis("Selesai di Rawat Inap");
		BaseResponse updated = restTemplate.postForObject(path, pasienUpdate, BaseResponse.class);
		String msg = "Pasien " + pasienUpdate.getNama() + " berhasil dipulangkan.";
		ModelAndView modelAndView = new ModelAndView("redirect:/daftar-ranap");
		redir.addFlashAttribute("msg", msg);
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/paviliun/getKamarByPaviliun", method = RequestMethod.GET)
	@ResponseBody
	public List<KamarModel> getKamar(@RequestParam (value = "idPaviliun", required = true) Long idPaviliun) {
		PaviliunModel paviliun = paviliunService.getPaviliunDetailById(idPaviliun).get();
		List<KamarModel> selectedKamar = kamarService.getKamarByPaviliun(paviliun);
	    return selectedKamar;
	}
	
	private PasienModel getPasienDataFromApi(long id) throws IOException {
		System.out.println("masuk api");
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