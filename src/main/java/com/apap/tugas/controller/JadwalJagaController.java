package com.apap.tugas.controller;

import com.apap.tugas.configuration.Config;
import com.apap.tugas.model.DokterModel;
import com.apap.tugas.model.JadwalJagaModel;
import com.apap.tugas.rest.BaseResponse;
import com.apap.tugas.rest.Setting;
import com.apap.tugas.service.JadwalJagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class JadwalJagaController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Config config;

    @Autowired
    private JadwalJagaService jadwalJagaService;

    @RequestMapping(value = "/jadwal-jaga",method = RequestMethod.GET)
    private String jadwalJaga(Model model) {
        String path = Setting.getAllDokterUrl;
        BaseResponse<List<LinkedHashMap>> response = restTemplate.getForObject(path, BaseResponse.class);
        List<LinkedHashMap> listOfDokter = response.getResult();

        ArrayList<JadwalJagaModel> listOfJadwalJagaView = new ArrayList<JadwalJagaModel>();
        for (int i = 0; i < listOfDokter.size(); i++) {
            String idDokter =  listOfDokter.get(i).get("id").toString();
            JadwalJagaModel jadwalJagaModel = jadwalJagaService.getJadwalJagaByIdDokter(Long.parseLong(idDokter));
            System.out.println("LALALALALALALALALALA"+idDokter);

            if (jadwalJagaModel == null){
                JadwalJagaModel tmp = new JadwalJagaModel();
                DokterModel tmpDokter = new DokterModel();
                tmpDokter.setId(Long.parseLong(listOfDokter.get(i).get("id").toString()));
                tmpDokter.setNama(listOfDokter.get(i).get("nama").toString());
                tmp.setDokter(tmpDokter);
                listOfJadwalJagaView.add(tmp);
            }else{
                listOfJadwalJagaView.add(jadwalJagaModel);
            }
        }
        model.addAttribute("listOfJadwalJaga",listOfJadwalJagaView);
        return "jadwal-jaga";
    }

    @RequestMapping(value = "/jadwal-jaga/update", method = RequestMethod.GET)
    private String jadwalJagaUpdate(@RequestParam(value = "idDokter") String idDokter, @RequestParam(value = "namaDokter") String namaDokter,Model model){
        model.addAttribute("namaDokter",namaDokter);
        return "jadwal-jaga-update";
    }
}
