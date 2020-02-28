package ua.ivan909020.app.dao;

import ua.ivan909020.app.domain.entities.User;

public interface UserDao extends DefaultDao<User> {

	User findByUsername(String username);

}
