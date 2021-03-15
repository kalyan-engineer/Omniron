package com.bank.user.DTO;

import java.util.List;

public class AllUsers {

	private Pagination page;
	private long totalRecords;
	private List<UserDTO> userRecords;
	public Pagination getPage() {
		return page;
	}
	public void setPage(Pagination page) {
		this.page = page;
	}
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<UserDTO> getUserRecords() {
		return userRecords;
	}
	public void setUserRecords(List<UserDTO> userRecords) {
		this.userRecords = userRecords;
	}
	
	
	
}
