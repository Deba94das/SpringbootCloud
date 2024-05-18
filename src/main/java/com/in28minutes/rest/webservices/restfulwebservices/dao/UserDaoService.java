package com.in28minutes.rest.webservices.restfulwebservices.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.in28minutes.rest.webservices.restfulwebservices.user.User;

@Component
public class UserDaoService {

	private static List<User> users=new ArrayList<User>();
	private static int userCount=0;
	static {
		users.add(new User(++userCount,"Adam",LocalDate.now().minusYears(30)));
		users.add(new User(++userCount,"Steve",LocalDate.now().minusYears(25)));
		users.add(new User(++userCount,"Jim",LocalDate.now().minusYears(20)));
	}
	
	public List<User> findAll(){
		return users;
	}

	public User findUser(int id) {
		
		  Predicate<? super User> predicate= user -> user.getId().equals(id); 
		  return users.stream().filter(predicate).findFirst().get();
		 
		//return users.get(id);
	}
	
	public User saveUser(User user) {
		user.setId(++userCount);
		users.add(user);
		return user;
	}
}
