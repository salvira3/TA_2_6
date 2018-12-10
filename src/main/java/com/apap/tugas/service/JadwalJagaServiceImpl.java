package com.apap.tugas.service;

import com.apap.tugas.model.DokterModel;
import com.apap.tugas.model.JadwalJagaModel;
import com.apap.tugas.repository.JadwalJagaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class JadwalJagaServiceImpl implements JadwalJagaService{

    @Autowired
    private JadwalJagaDb jadwalJagaDb;

    @Override
    public JadwalJagaModel getJadwalJagaByIdDokter(long idDokter) {
        System.out.println("adfkjasldf");
        System.out.println("as;dlfkja;lsdf"+idDokter);
        Optional<JadwalJagaModel> tmp = jadwalJagaDb.findByDokter_Id(idDokter);
        System.out.println("last chance" + tmp.toString());
        if (tmp.isPresent()){
            JadwalJagaModel jadwalJaga = tmp.get();
            return jadwalJaga;
        }
        return  null;
    }
}
