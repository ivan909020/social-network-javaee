package ua.ivan909020.app.service.impl;

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

}
