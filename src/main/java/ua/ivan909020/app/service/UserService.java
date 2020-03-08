package ua.ivan909020.app.service;

import java.util.List;
import java.util.Set;

import ua.ivan909020.app.domain.entities.User;

public interface UserService extends DefaultService<User> {

	User authenticate(String username, String password);

	int countByContainsUsername(String username);

	List<User> findContainsUsername(String username, int page, int size);

	int countFollowersByUserId(Integer id);

	Set<User> findFollowersByUserId(Integer id, int page, int size);

	int countFollowingByUserId(Integer id);

	Set<User> findFollowingByUserId(Integer id, int page, int size);

	boolean hasUserFollower(Integer userId, Integer followerId);

	void createUserFollower(Integer userId, Integer followerId);

	void deleteUserFollower(Integer userId, Integer followerId);

	boolean isUsernameExists(String username);

}
