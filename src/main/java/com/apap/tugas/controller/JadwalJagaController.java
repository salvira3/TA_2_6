package com.apap.tugas.controller;

import com.apap.tugas.configuration.Config;
import com.apap.tugas.model.DokterModel;
import com.apap.tugas.rest.BaseResponse;
import com.apap.tugas.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class JadwalJagaController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Config config;

    @RequestMapping(value = "/jadwal-jaga",method = RequestMethod.GET)
    private String jadwalJaga(Model model) {
        String path = Setting.getAllDokterUrl;
        BaseResponse<List<DokterModel>> response = restTemplate.getForObject(path, BaseResponse.class);
        List<DokterModel> listOfDokter = response.getResult();
        model.addAttribute("listOfDokter",listOfDokter);
        return "jadwal-jaga";
    }
}
