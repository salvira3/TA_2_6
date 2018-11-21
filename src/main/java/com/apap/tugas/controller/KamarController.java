package com.apap.tugas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apap.tugas.service.KamarService;
import com.apap.tugas.service.PaviliunService;

@Controller
@RequestMapping("/kamar")
public class KamarController {
	@Autowired
	private KamarService kamarService;
	
	@Autowired
	private PaviliunService paviliunService;
}
