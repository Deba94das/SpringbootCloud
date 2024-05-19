package com.in28minutes.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionaingPersonController {

	//URL versioning
	//http://localhost:8080/v1/person
	//http://localhost:8080/v2/person
	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Bob charlie");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Debashis", "Das"));
	}
	
	
	// Request param versioning - used in Amazon
	//http://localhost:8080/person?version=1
	//http://localhost:8080/person?version=2
	@GetMapping(path="/person", params = "version=1")
	public PersonV1 getFirstVersionOfPersonRequestParameter() {
		return new PersonV1("Bob charlie");
	}
	
	@GetMapping(path="/person", params = "version=2")
	public PersonV2 getSecondVersionOfPersonRequestParameter() {
		return new PersonV2(new Name("Debashis", "Das"));
	}
	
	//Custom Header versioning- used in Microsoft
	// SAME-URL Headers= [X-API-VERSION=1]
	// SAME-URL Headers= [X-API-VERSION=1]
	@GetMapping(path="/person/header", headers = "X-API-VERSION=1")
	public PersonV1 getFirstVersionOfPersonRequestHeader() {
		return new PersonV1("Bob charlie");
	}
	
	@GetMapping(path="/person/header", headers = "X-API-VERSION=2")
	public PersonV2 getSecondVersionOfPersonRequestHeader() {
		return new PersonV2(new Name("Debashis", "Das"));
	}
	
	//Media type Versioning(a.k.a "content negotiation" or "accept Header") - used in GitHub
	//SAME-URL produces= application/vnd.company.app-v1+json
	//SAME-URL produces= application/vnd.company.app-v2+json
	@GetMapping(path="/person/accept", produces = "application/vnd.company.app-v1+json")
	public PersonV1 getFirstVersionOfPersonAcceptHeader() {
		return new PersonV1("Bob charlie");
	}
	
	@GetMapping(path="/person/accept", produces = "application/vnd.company.app-v2+json")
	public PersonV2 getSecondVersionOfPersonAcceptHeader() {
		return new PersonV2(new Name("Debashis", "Das"));
	}
}
