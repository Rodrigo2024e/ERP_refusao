

package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO {

	private String password;
	
	public UserInsertDTO () {
		super();
	}

	public String getPassword() {
		return password;
	}

}
