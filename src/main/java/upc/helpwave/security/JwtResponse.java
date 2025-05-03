package upc.helpwave.security;

import java.io.Serializable;

/*
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

clase 5
@AllArgsConstructor
@Getter*/
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	private final String jwttoken;
	private final String role;
	private final int idUser;

	public JwtResponse(String jwttoken, String role, int idUser) {
		this.jwttoken = jwttoken;
		this.role = role;
		this.idUser = idUser;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public String getRole() {
		return role;
	}

	public int getIdUser() {
		return idUser;
	}
}
