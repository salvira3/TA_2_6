package com.apap.tugas.controller;

import java.io.IOException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tugas.model.KamarModel;
import com.apap.tugas.model.PasienModel;
import com.apap.tugas.model.PaviliunModel;
import com.apap.tugas.service.KamarService;
import com.apap.tugas.service.PaviliunService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/kamar")
public class KamarController {
	@Autowired
	private KamarService kamarService;
	
	@Autowired
	private PaviliunService paviliunService;
	
	
	RestTemplate restTemplate = new RestTemplate();
	

	@RequestMapping(value = "")
	private String listKamar(Model model) {
		List<PaviliunModel> listPaviliun = paviliunService.getAll();
		model.addAttribute("listPaviliun" , listPaviliun);
		List<KamarModel> listKamar = kamarService.getAll();
		model.addAttribute("listKamar" , listKamar);
		//model.addAttribute("message" , "hidden");
		return "list-kamar";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	private ModelAndView addKamar(@ModelAttribute KamarModel kamar , Model model, RedirectAttributes redir) {
		kamarService.addKamar(kamar);
		ModelAndView modelAndView = new ModelAndView("redirect:/kamar");
		redir.addFlashAttribute("message" , "addKamar");
		return modelAndView;
	}
	
	@RequestMapping(value = "/{idKamar}", method = RequestMethod.GET)
	private String detailKamar(@PathVariable(value = "idKamar") Long id, Model model) throws IOException {
		KamarModel kamar = kamarService.getKamarById(id);
		if(kamar.getStatusKamar().equals(0)) {
			model.addAttribute("pasien" , "-");
		}
		else {
			String pasien = getPasienDataFromApi(kamar.getIdPasien()).getNama();
			model.addAttribute("pasien" , pasien);
		}
		model.addAttribute("kamar" , kamar);
		return "detail-kamar";
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
	
	@RequestMapping(value = "/{idKamar}", method = RequestMethod.POST)
	private String formUpdateKamar(@PathVariable(value = "idKamar") Long id, Model model) throws IOException {
		KamarModel kamar = kamarService.getKamarById(id);
		if(kamar.getStatusKamar().equals(0)) {
			model.addAttribute("pasien" , "-");
		}
		else {
			String pasien = getPasienDataFromApi(kamar.getIdPasien()).getNama();
			model.addAttribute("pasien" , pasien);
		}
		List<PaviliunModel> listPaviliun = paviliunService.getAll();
		model.addAttribute("listPaviliun" , listPaviliun);
		model.addAttribute("kamar" , kamar);
		return "update-kamar";
	}
	
	@RequestMapping(value = "/submit/{idKamar}", method = RequestMethod.POST)
	private ModelAndView updateKamar(@ModelAttribute KamarModel kamar, @PathVariable(value = "idKamar") Long id, Model model, RedirectAttributes redir) {
		kamarService.updateKamar(kamar, id);
		ModelAndView modelAndView = new ModelAndView("redirect:/kamar");
		redir.addFlashAttribute("message" , "updateKamar");
		return modelAndView;
	}
}
