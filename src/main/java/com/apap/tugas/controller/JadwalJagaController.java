package com.apap.tugas.controller;

import com.apap.tugas.configuration.Config;
import com.apap.tugas.rest.BaseResponse;
import com.apap.tugas.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class JadwalJagaController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Config config;

    @RequestMapping(value = "/jadwal-jaga",method = RequestMethod.GET)
    private String jadwalJaga() {
        String path = Setting.getAllDokterUrl;
        BaseResponse response = restTemplate.getForObject(path, BaseResponse.class);
        System.out.println(response.getMessage() + response.getStatus() + response.getResult());
        return "jadwal-jaga";
    }
}
