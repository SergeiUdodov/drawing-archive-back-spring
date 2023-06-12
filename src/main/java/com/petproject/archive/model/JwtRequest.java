package com.petproject.archive.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtRequest implements Serializable {

	private String email;
	private String password;

	// need default constructor for JSON Parsing
	public JwtRequest() {

	}

}
