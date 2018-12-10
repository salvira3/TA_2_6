package com.apap.tugas.service;

import java.util.ArrayList;
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

	@Override
	public List<RequestPasienModel> getPasienAssigned() {
		// TODO Auto-generated method stub
		List<RequestPasienModel> pasienAssigned = new ArrayList<RequestPasienModel>();
		for(RequestPasienModel e: requestPasienDb.findAll()) {
			if(e.getAssignStatus()==1) {
				pasienAssigned.add(e);
			}
		}
		return pasienAssigned;
	}

}
