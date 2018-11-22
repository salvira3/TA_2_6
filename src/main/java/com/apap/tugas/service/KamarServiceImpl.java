package com.apap.tugas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas.model.KamarModel;
import com.apap.tugas.model.PaviliunModel;
import com.apap.tugas.repository.KamarDb;

@Service
@Transactional
public class KamarServiceImpl implements KamarService{
	@Autowired 
	private KamarDb kamarDb;

	@Override
	public List<KamarModel> getAll() {
		// TODO Auto-generated method stub
		return kamarDb.findAll();
	}

	@Override
	public Optional<KamarModel> getKamarDetailById(long id) {
		// TODO Auto-generated method stub
		return kamarDb.findById(id);
	}

	@Override
	public List<KamarModel> getKamarByPaviliun(PaviliunModel paviliun) {
		// TODO Auto-generated method stub
		List<KamarModel> kamar = kamarDb.findAll();
		List<KamarModel> selectedKamar = new ArrayList<KamarModel>();
		for (KamarModel e:kamar) {
			if(e.getPaviliun().equals(paviliun)) {
				if(e.getStatusKamar()==0) {
					selectedKamar.add(e);
				}
			}
		}
		for(KamarModel e: selectedKamar) {
			System.out.println(e.getStatusKamar());
		}
		return selectedKamar;
	}
		public KamarModel getKamarById(Long id) {
		// TODO Auto-generated method stub
		return kamarDb.findById(id).get();
	}
	
}
