package ua.ivan909020.app.dao;

import java.util.List;
import java.util.Set;

import ua.ivan909020.app.domain.entities.User;

public interface UserDao extends DefaultDao<User> {

	User findByUsername(String username);

	int countByContainsUsername(String username);

	List<User> findContainsUsername(String username, int offset, int limit);

	int countFollowersByUserId(Integer id);

	Set<User> findFollowersByUserId(Integer id, int offset, int limit);

	int countFollowingByUserId(Integer id);

	Set<User> findFollowingByUserId(Integer id, int offset, int limit);

	User findUserFollower(Integer userId, Integer followerId);

	void createUserFollower(Integer userId, Integer followerId);

	void deleteUserFollower(Integer userId, Integer followerId);

}
