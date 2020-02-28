package ua.ivan909020.app.service;

import java.util.List;

import ua.ivan909020.app.domain.entities.User;

public interface UserService extends DefaultService<User> {

	User authenticate(String username, String password);

	int countByContainsUsername(String username);

	List<User> findContainsUsername(String username, int page, int size);

}
