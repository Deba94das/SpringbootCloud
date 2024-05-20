package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.dao.UserDaoService;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	@Autowired
	private UserDaoService userDaoService;
	

	// GET /users
	@GetMapping(path = "/users")
	public List<User> retrieveAllUser() {
		return userDaoService.findAll();

	}

	// GET /users/1
	@GetMapping(path = "/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = userDaoService.findUser(id);
		if (user == null) {
			throw new UserNotFoundException("id:" + id);
		}
		EntityModel<User> entityModel= EntityModel.of(user);
		WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).retrieveAllUser());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}

	// GET /users/1
	@DeleteMapping(path = "/users/{id}")
	public User deleteUser(@PathVariable int id) {
		User user = userDaoService.deleteById(id);
		if (user == null) {
			throw new UserNotFoundException("id:" + id);
		}
		return user;
	}

	// Post /users
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userDaoService.saveUser(user);

		// /users/4 => /users, user.getId
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
