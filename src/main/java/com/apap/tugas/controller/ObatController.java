package com.apap.tugas.controller;

import com.apap.tugas.configuration.Config;
import com.apap.tugas.model.RequestObatModel;
import com.apap.tugas.rest.BaseResponse;
import com.apap.tugas.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/obat")
public class ObatController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Config config;

    @RequestMapping(value = "/request",method = RequestMethod.GET)
    public String requestObat() {
        return "obat-request";
    }

    @RequestMapping(value = "/request",method = RequestMethod.POST)
    public String submitRequestObat(@ModelAttribute RequestObatModel obat) {
        String path = Setting.requestObatUrl;
        BaseResponse response = restTemplate.postForObject(path,obat, BaseResponse.class);
        System.out.println(response.getMessage() + response.getStatus() + response.getResult());
        return "obat-request";
    }
}
