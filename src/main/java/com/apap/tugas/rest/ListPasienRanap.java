package com.apap.tugas.rest;

import java.io.Serializable;
import java.util.List;

import com.apap.tugas.model.PasienModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListPasienRanap implements Serializable{
	private int status;
	private String message;
	private List<PasienModel> result;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<PasienModel> getResult() {
		return result;
	}
	public void setResult(List<PasienModel> result) {
		this.result = result;
	}
	
	
}
