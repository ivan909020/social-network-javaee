package ua.ivan909020.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import ua.ivan909020.app.DataSourceRunner;
import ua.ivan909020.app.dao.UserDao;
import ua.ivan909020.app.dao.impl.UserDaoImpl;
import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.exception.ValidationException;
import ua.ivan909020.app.service.impl.UserServiceImpl;

@RunWith(DataSourceRunner.class)
public class UserServiceTest {

	private final UserDao userDao = UserDaoImpl.getInstance();
	private final UserService userService = UserServiceImpl.getInstance();

	private User createStubUser(String username) {
		User user = new User();
		user.setUsername(username);
		user.setPassword("");
		user.setInformation("");
		return user;
	}

	@Test
	public void findById() {
		User createdUser = userService.create(createStubUser("username1"));

		User receivedUser = userService.findById(createdUser.getId());

		assertEquals(createdUser, receivedUser);
	}

	@Test
	public void findById_idIsNull_throwsException() {
		Integer userId = null;
		try {
			userService.findById(userId);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Id of user must not be null", e.getMessage());
		}
	}

	@Test
	public void create() {
		User createdUser = userService.create(createStubUser("username2"));

		User expectedUser = createStubUser("username2");
		expectedUser.setId(createdUser.getId());

		assertNotNull(createdUser.getId());
		assertEquals(expectedUser, createdUser);
	}

	@Test
	public void create_null_throwsException() {
		User user = null;
		try {
			userService.create(user);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("User must not be null", e.getMessage());
		}
	}

	@Test
	public void create_withId_throwsException() {
		User userToCreate = createStubUser("username3");
		userToCreate.setId(666);
		try {
			userService.create(userToCreate);
			fail();
		} catch (ValidationException e) {
			assertEquals("Id of user must be null", e.getMessage());
		}
	}

	@Test
	public void update() {
		User createdUser = userService.create(createStubUser("username4"));

		createdUser.setUsername("updated_username4");
		User updatedUser = userService.update(createdUser);

		User expectedUser = createStubUser("updated_username4");
		expectedUser.setId(createdUser.getId());

		assertEquals(expectedUser, updatedUser);
	}

	@Test
	public void update_null_throwsException() {
		User user = null;
		try {
			userService.update(user);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("User must not be null", e.getMessage());
		}
	}

	@Test
	public void update_withoutId_throwsException() {
		User userToUpdate = createStubUser("username5");
		try {
			userService.update(userToUpdate);
			fail();
		} catch (ValidationException e) {
			assertEquals("Id of user must not be null", e.getMessage());
		}
	}

	@Test
	public void authenticate() {
		User createdUser = userService.create(createStubUser("username6"));

		User authhenticatedUser = userService.authenticate("username6", createdUser.getPassword());

		User expectedUser = createStubUser("username6");
		expectedUser.setId(createdUser.getId());

		assertEquals(expectedUser, authhenticatedUser);
	}

	@Test
	public void authenticate_usernameIsNull_throws() {
		try {
			userService.authenticate(null, "666");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Username of user must not be null", e.getMessage());
		}
	}

	@Test
	public void authenticate_passwordIsNull_throws() {
		try {
			userService.authenticate("666", null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Password of user must not be null", e.getMessage());
		}
	}

	@Test
	public void countByContainsUsername() {
		userService.create(createStubUser("username7"));
		userService.create(createStubUser("username8"));

		int countUsers = userService.countByContainsUsername("username");

		int expectedCountUsers = 2;

		assertTrue(countUsers >= expectedCountUsers);
	}

	@Test
	public void countByContainsUsername_null_throwsException() {
		String username = null;
		try {
			userService.countByContainsUsername(username);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Username of user must not be null", e.getMessage());
		}
	}

	@Test
	public void findContainsUsername() {
		User createdUser_1 = userService.create(createStubUser("username9"));
		User createdUser_2 = userService.create(createStubUser("username10"));
		User createdUser_3 = userService.create(createStubUser("username11"));

		List<User> receivedUsers_1 = userService.findContainsUsername("username", 1, 20);
		List<User> receivedUsers_2 = userService.findContainsUsername("username11", 1, 20);

		List<User> expectedUsers_1 = Arrays.asList(createdUser_1, createdUser_2, createdUser_3);
		List<User> expectedUsers_2 = Arrays.asList(createdUser_3);

		assertTrue(receivedUsers_1.containsAll(expectedUsers_1));
		assertTrue(receivedUsers_2.containsAll(expectedUsers_2));
	}

	@Test
	public void findContainsUsername_usernameIsNull_throwsException() {
		String username = null;
		try {
			userService.findContainsUsername(username, 1, 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Username of user must not be null", e.getMessage());
		}
	}

	@Test
	public void findContainsUsername_pageIsLessThanOne_throwsException() {
		Integer page = 0;
		try {
			userService.findContainsUsername("666", page, 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Page must be larger than 1", e.getMessage());
		}
	}

	@Test
	public void findContainsUsername_sizeIsLessThanTen_throwsException() {
		Integer size = 9;
		try {
			userService.findContainsUsername("666", size, size);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Size must be larger than 10", e.getMessage());
		}
	}

	@Test
	public void countFollowersByUserId() {
		User createdUser = userService.create(createStubUser("username12"));
		User createdFollower_1 = userService.create(createStubUser("username13"));
		User createdFollower_2 = userService.create(createStubUser("username14"));
		userService.createUserFollower(createdUser.getId(), createdFollower_1.getId());
		userService.createUserFollower(createdUser.getId(), createdFollower_2.getId());

		int countFollowers = userService.countFollowersByUserId(createdUser.getId());

		int expectedCountFollowers = 2;

		assertEquals(expectedCountFollowers, countFollowers);
	}

	@Test
	public void countFollowersByUserId_null_throwsException() {
		Integer userId = null;
		try {
			userService.countFollowersByUserId(userId);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Id of user must not be null", e.getMessage());
		}
	}

	@Test
	public void findFollowersByUserId() {
		User createdUser = userService.create(createStubUser("username15"));
		User createdFollower_1 = userService.create(createStubUser("username16"));
		User createdFollower_2 = userService.create(createStubUser("username17"));
		userService.createUserFollower(createdUser.getId(), createdFollower_1.getId());
		userService.createUserFollower(createdUser.getId(), createdFollower_2.getId());

		Set<User> receivedFollowers = userService.findFollowersByUserId(createdUser.getId(), 1, 20);

		Set<User> expectedFollowers = new HashSet<>(Arrays.asList(createdFollower_1, createdFollower_2));

		assertEquals(expectedFollowers, receivedFollowers);
	}

	@Test
	public void findFollowersByUserId_idIsNull_throwsException() {
		Integer userId = null;
		try {
			userService.findFollowersByUserId(userId, 1, 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Id of user must not be null", e.getMessage());
		}
	}

	@Test
	public void findFollowersByUserId_pageIsLessThanOne_throwsException() {
		Integer page = 0;
		try {
			userService.findFollowersByUserId(666, page, 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Page must be larger than 1", e.getMessage());
		}
	}

	@Test
	public void findFollowersByUserId_sizeIsLessThanTen_throwsException() {
		Integer size = 9;
		try {
			userService.findFollowersByUserId(666, size, size);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Size must be larger than 10", e.getMessage());
		}
	}

	@Test
	public void countFollowingByUserId() {
		User createdFollower = userService.create(createStubUser("username18"));
		User createdUser_1 = userService.create(createStubUser("username19"));
		User createdUser_2 = userService.create(createStubUser("username20"));
		userService.createUserFollower(createdUser_1.getId(), createdFollower.getId());
		userService.createUserFollower(createdUser_2.getId(), createdFollower.getId());

		int countFollowing = userService.countFollowingByUserId(createdFollower.getId());

		int expectedCountFollowing = 2;

		assertEquals(expectedCountFollowing, countFollowing);
	}

	@Test
	public void countFollowingByUserId_null_throwsException() {
		Integer userId = null;
		try {
			userService.countFollowingByUserId(userId);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Id of user must not be null", e.getMessage());
		}
	}

	@Test
	public void findFollowingByUserId() {
		User createdFollower = userService.create(createStubUser("username21"));
		User createdUser_1 = userService.create(createStubUser("username22"));
		User createdUser_2 = userService.create(createStubUser("username23"));
		userService.createUserFollower(createdUser_1.getId(), createdFollower.getId());
		userService.createUserFollower(createdUser_2.getId(), createdFollower.getId());

		Set<User> receivedFollowing = userService.findFollowingByUserId(createdFollower.getId(), 1, 20);

		Set<User> expectedFollowing = new HashSet<>(Arrays.asList(createdUser_1, createdUser_2));

		assertEquals(expectedFollowing, receivedFollowing);
	}

	@Test
	public void findFollowingByUserId_idIsNull_throwsException() {
		Integer userId = null;
		try {
			userService.findFollowingByUserId(userId, 1, 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Id of user must not be null", e.getMessage());
		}
	}

	@Test
	public void findFollowingByUserId_pageIsLessThanOne_throwsException() {
		Integer page = 0;
		try {
			userService.findFollowingByUserId(666, page, 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Page must be larger than 1", e.getMessage());
		}
	}

	@Test
	public void findFollowingByUserId_sizeIsLessThanTen_throwsException() {
		Integer size = 9;
		try {
			userService.findFollowingByUserId(666, size, size);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Size must be larger than 10", e.getMessage());
		}
	}

	@Test
	public void hasUserFollower() {
		User createdUser = userService.create(createStubUser("username24"));
		User createdFollower = userService.create(createStubUser("username25"));
		userService.createUserFollower(createdUser.getId(), createdFollower.getId());

		boolean hasUserFollower = userService.hasUserFollower(createdUser.getId(), createdFollower.getId());

		assertTrue(hasUserFollower);
	}

	@Test
	public void hasUserFollower_userIdIsNull_throwsException() {
		Integer userId = null;
		try {
			userService.hasUserFollower(userId, 666);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("User Id must not be null", e.getMessage());
		}
	}

	@Test
	public void hasUserFollower_followerIdIsNull_throwsException() {
		Integer followerId = null;
		try {
			userService.hasUserFollower(666, followerId);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Follower Id must not be null", e.getMessage());
		}
	}

	@Test
	public void hasUserFollower_userIdAndfollowerIdAreEquals_throwsException() {
		try {
			userService.hasUserFollower(666, 666);
			fail();
		} catch (ValidationException e) {
			assertEquals("User Id and Follower Id must be different", e.getMessage());
		}
	}

	@Test
	public void createUserFollower() {
		User createdUser = userService.create(createStubUser("username26"));
		User createdFollower = userService.create(createStubUser("username27"));
		userService.createUserFollower(createdUser.getId(), createdFollower.getId());

		User receivedUserFollower = userDao.findUserFollower(createdUser.getId(), createdFollower.getId());

		User expectedUserFollower = createStubUser("username27");
		expectedUserFollower.setId(createdFollower.getId());

		assertNotNull(receivedUserFollower.getId());
		assertEquals(expectedUserFollower, receivedUserFollower);
	}

	@Test
	public void createUserFollower_userIdIsNull_thowsException() {
		Integer userId = null;
		try {
			userService.createUserFollower(userId, 666);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("User Id must not be null", e.getMessage());
		}
	}

	@Test
	public void createUserFollower_followerIdIsNull_thowsException() {
		Integer followerId = null;
		try {
			userService.createUserFollower(666, followerId);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Follower Id must not be null", e.getMessage());
		}
	}

	@Test
	public void createUserFollower_userIdAndfollowerIdAreEquals_throwsException() {
		try {
			userService.createUserFollower(666, 666);
			fail();
		} catch (ValidationException e) {
			assertEquals("User Id and Follower Id must be different", e.getMessage());
		}
	}

	@Test
	public void deleteUserFollower() {
		User createdUser = userService.create(createStubUser("username28"));
		User createdFollower = userService.create(createStubUser("username29"));
		userService.createUserFollower(createdUser.getId(), createdFollower.getId());

		userService.deleteUserFollower(createdUser.getId(), createdFollower.getId());

		User receivedUserFollower = userDao.findUserFollower(createdUser.getId(), createdFollower.getId());

		assertNull(receivedUserFollower);
	}

	@Test
	public void deleteUserFollower_userIdIsNull_thowsException() {
		Integer userId = null;
		try {
			userService.deleteUserFollower(userId, 666);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("User Id must not be null", e.getMessage());
		}
	}

	@Test
	public void deleteUserFollower_followerIdIsNull_thowsException() {
		Integer followerId = null;
		try {
			userService.deleteUserFollower(666, followerId);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Follower Id must not be null", e.getMessage());
		}
	}

	@Test
	public void deleteUserFollower_userIdAndfollowerIdAreEquals_throwsException() {
		try {
			userService.deleteUserFollower(666, 666);
			fail();
		} catch (ValidationException e) {
			assertEquals("User Id and Follower Id must be different", e.getMessage());
		}
	}

}
