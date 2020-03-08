package ua.ivan909020.app.service.impl;

import java.util.List;
import java.util.Set;

import ua.ivan909020.app.dao.UserDao;
import ua.ivan909020.app.dao.impl.UserDaoImpl;
import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.exception.ValidationException;
import ua.ivan909020.app.service.UserService;

public class UserServiceImpl implements UserService {

	private static final UserService INSTANCE = new UserServiceImpl();

	private final UserDao userDao = UserDaoImpl.getInstance();

	private UserServiceImpl() {
	}

	public static UserService getInstance() {
		return INSTANCE;
	}

	@Override
	public User findById(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Id of user must not be null");
		}
		return userDao.findById(id);
	}

	@Override
	public User create(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User must not be null");
		}
		if (user.getId() != null) {
			throw new ValidationException("Id of user must be null");
		}
		return userDao.create(user);
	}

	@Override
	public User update(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User must not be null");
		}
		if (user.getId() == null) {
			throw new ValidationException("Id of user must not be null");
		}
		return userDao.update(user);
	}

	@Override
	public User authenticate(String username, String password) {
		if (username == null) {
			throw new IllegalArgumentException("Username of user must not be null");
		}
		if (password == null) {
			throw new IllegalArgumentException("Password of user must not be null");
		}
		User user = userDao.findByUsername(username);
		if (user == null || !password.equals(user.getPassword())) {
			user = null;
		}
		return user;
	}

	@Override
	public int countByContainsUsername(String username) {
		if (username == null) {
			throw new IllegalArgumentException("Username of user must not be null");
		}
		return userDao.countByContainsUsername(username);
	}

	@Override
	public List<User> findContainsUsername(String username, int page, int size) {
		if (username == null) {
			throw new IllegalArgumentException("Username of user must not be null");
		}
		if (page < 1) {
			throw new IllegalArgumentException("Page must be larger than 1");
		}
		if (size < 10) {
			throw new IllegalArgumentException("Size must be larger than 10");
		}
		int offset = (page - 1) * size;
		return userDao.findContainsUsername(username, offset, size);
	}

	@Override
	public int countFollowersByUserId(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Id of user must not be null");
		}
		return userDao.countFollowersByUserId(id);
	}

	@Override
	public Set<User> findFollowersByUserId(Integer id, int page, int size) {
		if (id == null) {
			throw new IllegalArgumentException("Id of user must not be null");
		}
		if (page < 1) {
			throw new IllegalArgumentException("Page must be larger than 1");
		}
		if (size < 10) {
			throw new IllegalArgumentException("Size must be larger than 10");
		}
		int offset = (page - 1) * size;
		return userDao.findFollowersByUserId(id, offset, size);
	}

	@Override
	public int countFollowingByUserId(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Id of user must not be null");
		}
		return userDao.countFollowingByUserId(id);
	}

	@Override
	public Set<User> findFollowingByUserId(Integer id, int page, int size) {
		if (id == null) {
			throw new IllegalArgumentException("Id of user must not be null");
		}
		if (page < 1) {
			throw new IllegalArgumentException("Page must be larger than 1");
		}
		if (size < 10) {
			throw new IllegalArgumentException("Size must be larger than 10");
		}
		int offset = (page - 1) * size;
		return userDao.findFollowingByUserId(id, offset, size);
	}

	@Override
	public boolean hasUserFollower(Integer userId, Integer followerId) {
		if (userId == null) {
			throw new IllegalArgumentException("User Id must not be null");
		}
		if (followerId == null) {
			throw new IllegalArgumentException("Follower Id must not be null");
		}
		if (userId.equals(followerId)) {
			throw new ValidationException("User Id and Follower Id must be different");
		}
		return userDao.findUserFollower(userId, followerId) != null;
	}

	@Override
	public void createUserFollower(Integer userId, Integer followerId) {
		if (userId == null) {
			throw new IllegalArgumentException("User Id must not be null");
		}
		if (followerId == null) {
			throw new IllegalArgumentException("Follower Id must not be null");
		}
		if (userId.equals(followerId)) {
			throw new ValidationException("User Id and Follower Id must be different");
		}
		userDao.createUserFollower(userId, followerId);
	}

	@Override
	public void deleteUserFollower(Integer userId, Integer followerId) {
		if (userId == null) {
			throw new IllegalArgumentException("User Id must not be null");
		}
		if (followerId == null) {
			throw new IllegalArgumentException("Follower Id must not be null");
		}
		if (userId.equals(followerId)) {
			throw new ValidationException("User Id and Follower Id must be different");
		}
		userDao.deleteUserFollower(userId, followerId);
	}

	@Override
	public boolean isUsernameExists(String username) {
		if (username == null) {
			throw new IllegalArgumentException("Username of user must not be null");
		}
		return userDao.findByUsername(username) != null;
	}

}
