package ua.ivan909020.app.dao;

import java.util.List;

import ua.ivan909020.app.domain.entities.User;

public interface UserDao extends DefaultDao<User> {

	User findByUsername(String username);

	int countByContainsUsername(String username);

	List<User> findContainsUsername(String username, int offset, int limit);
	
	int countFollowersByUserId(Integer id);
	
	int countFollowingByUserId(Integer id);

}
