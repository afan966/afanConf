package com.afan.conf.enity;

import java.io.Serializable;
import com.afan.dbmgr.DBTable;
import com.afan.dbmgr.DBColumn;

@DBTable(db="afan_config",table="app_user", primaryClumns={"userName"})
public class AppUser implements Serializable{
	private static final long serialVersionUID = 1L;

	@DBColumn
	private String userName;
	@DBColumn
	private String userPazzword;
	@DBColumn
	private int status;
	@DBColumn
	private String mobile;
	@DBColumn
	private String email;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPazzword() {
		return userPazzword;
	}
	public void setUserPazzword(String userPazzword) {
		this.userPazzword = userPazzword;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}