package com.apap.tugas.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas.model.RequestPasienModel;
import com.apap.tugas.repository.RequestPasienDb;

@Service
@Transactional
public class RequestPasienServiceImpl implements RequestPasienService{
	@Autowired
	private RequestPasienDb requestPasienDb;

	@Override
	public List<RequestPasienModel> getAll() {
		// TODO Auto-generated method stub
		return requestPasienDb.findAll();
	}

}
