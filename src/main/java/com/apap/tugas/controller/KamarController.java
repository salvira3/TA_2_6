package com.apap.tugas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apap.tugas.model.KamarModel;
import com.apap.tugas.model.PaviliunModel;
import com.apap.tugas.service.KamarService;
import com.apap.tugas.service.PaviliunService;

@Controller
@RequestMapping("/kamar")
public class KamarController {
	@Autowired
	private KamarService kamarService;
	
	@Autowired
	private PaviliunService paviliunService;
	
	@RequestMapping(value = "")
	private String listKamar(Model model) {
		List<KamarModel> listKamar = kamarService.getAll();
		model.addAttribute("listKamar" , listKamar);
		return "list-kamar";
	}
	
	@RequestMapping(value = "/insert")
	private String addKamar(Model model) {
		List<PaviliunModel> listPaviliun = paviliunService.getAll();
		model.addAttribute("listPaviliun" , listPaviliun);
		return "add-kamar";
	}
}
