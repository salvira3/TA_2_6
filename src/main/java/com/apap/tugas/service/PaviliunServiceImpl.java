package com.apap.tugas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas.model.PaviliunModel;
import com.apap.tugas.repository.PaviliunDb;

@Service
@Transactional
public class PaviliunServiceImpl implements PaviliunService{
	@Autowired 
	private PaviliunDb paviliunDb;

	@Override
	public List<PaviliunModel> getAll() {
		// TODO Auto-generated method stub
		return paviliunDb.findAll();
	}

	@Override
	public Optional<PaviliunModel> getPaviliunDetailById(long id) {
		// TODO Auto-generated method stub
		return paviliunDb.findById(id);
	}
	
}
