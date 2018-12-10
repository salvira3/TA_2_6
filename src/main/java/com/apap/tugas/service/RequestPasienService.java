package com.apap.tugas.service;
import java.util.List;
import com.apap.tugas.model.RequestPasienModel;

public interface RequestPasienService {
	List<RequestPasienModel> getAll();
	
	List<RequestPasienModel> getPasienAssigned();
}
