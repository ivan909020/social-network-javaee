package ua.ivan909020.app.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import ua.ivan909020.app.DataSourceRunner;
import ua.ivan909020.app.dao.impl.UserDaoImpl;
import ua.ivan909020.app.domain.entities.User;

@RunWith(DataSourceRunner.class)
public class UserDaoTest {

	private UserDao userDao = UserDaoImpl.getInstance();

	private User createStubUser(String username) {
		User user = new User();
		user.setUsername(username);
		user.setPassword("");
		user.setInformation("");
		return user;
	}

	@Test
	public void findById() {
		User createdUser = userDao.create(createStubUser("username1"));

		User receivedUser = userDao.findById(createdUser.getId());

		assertEquals(createdUser, receivedUser);
	}

	@Test
	public void findById_notExists() {
		User receivedUser = userDao.findById(666);

		assertNull(receivedUser);
	}

	@Test
	public void create() {
		User createdUser = userDao.create(createStubUser("username2"));

		User expectedUser = createStubUser("username2");
		expectedUser.setId(createdUser.getId());

		assertNotNull(createdUser.getId());
		assertEquals(expectedUser, createdUser);
	}

	@Test
	public void create_idsAreDifferent() {
		User createdUser_1 = userDao.create(createStubUser("username3"));
		User createdUser_2 = userDao.create(createStubUser("username4"));

		assertNotEquals(createdUser_1.getId(), createdUser_2.getId());
	}

	@Test
	public void update() {
		User createdUser = userDao.create(createStubUser("username5"));

		createdUser.setUsername("updated_username5");
		User updatedUser = userDao.update(createdUser);

		User expectedUser = createStubUser("updated_username5");
		expectedUser.setId(createdUser.getId());

		assertEquals(expectedUser, updatedUser);
	}

	@Test
	public void update_notExists() {
		User userToUpdate = createStubUser("username6");
		userToUpdate.setId(666);

		User updatedUser = userDao.update(userToUpdate);

		assertNull(updatedUser);
	}

	@Test
	public void findByUsername() {
		User createdUser = userDao.create(createStubUser("username7"));

		User receivedUser = userDao.findByUsername("username7");

		assertEquals(createdUser, receivedUser);
	}

	@Test
	public void findByUsername_notExists() {
		User receivedUser = userDao.findByUsername("username8");

		assertNull(receivedUser);
	}

	@Test
	public void countByContainsUsername() {
		userDao.create(createStubUser("username9"));
		userDao.create(createStubUser("username10"));

		int countUsers = userDao.countByContainsUsername("username");

		int expectedCountUsers = 2;

		assertTrue(countUsers >= expectedCountUsers);
	}

	@Test
	public void countByContainsUsername_notExists() {
		int countUsers = userDao.countByContainsUsername("666");

		assertEquals(0, countUsers);
	}

	@Test
	public void findContainsUsername() {
		User createdUser_1 = userDao.create(createStubUser("username11"));
		User createdUser_2 = userDao.create(createStubUser("username12"));
		User createdUser_3 = userDao.create(createStubUser("username13"));

		List<User> receivedUsers_1 = userDao.findContainsUsername("username", 0, 20);
		List<User> receivedUsers_2 = userDao.findContainsUsername("username13", 0, 20);

		List<User> expectedUsers_1 = Arrays.asList(createdUser_1, createdUser_2, createdUser_3);
		List<User> expectedUsers_2 = Arrays.asList(createdUser_3);

		assertTrue(receivedUsers_1.containsAll(expectedUsers_1));
		assertTrue(receivedUsers_2.containsAll(expectedUsers_2));
	}

	@Test
	public void findContainsUsername_notExists() {
		List<User> receivedUsers = userDao.findContainsUsername("666", 0, 20);

		assertTrue(receivedUsers.isEmpty());
	}

	@Test
	public void countFollowersByUserId() {
		User createdUser = userDao.create(createStubUser("username14"));
		User createdFollower_1 = userDao.create(createStubUser("username15"));
		User createdFollower_2 = userDao.create(createStubUser("username16"));
		userDao.createUserFollower(createdUser.getId(), createdFollower_1.getId());
		userDao.createUserFollower(createdUser.getId(), createdFollower_2.getId());

		int countFollowers = userDao.countFollowersByUserId(createdUser.getId());

		int expectedCountFollowers = 2;

		assertEquals(expectedCountFollowers, countFollowers);
	}

	@Test
	public void countFollowersByUserId_notExists() {
		int countFollowers = userDao.countFollowersByUserId(666);

		assertEquals(0, countFollowers);
	}

	@Test
	public void findFollowersByUserId() {
		User createdUser = userDao.create(createStubUser("username17"));
		User createdFollower_1 = userDao.create(createStubUser("username18"));
		User createdFollower_2 = userDao.create(createStubUser("username19"));
		userDao.createUserFollower(createdUser.getId(), createdFollower_1.getId());
		userDao.createUserFollower(createdUser.getId(), createdFollower_2.getId());

		Set<User> receivedFollowers = userDao.findFollowersByUserId(createdUser.getId(), 0, 20);

		Set<User> expectedFollowers = new HashSet<>(Arrays.asList(createdFollower_1, createdFollower_2));

		assertEquals(expectedFollowers, receivedFollowers);
	}

	@Test
	public void countFollowingByUserId() {
		User createdFollower = userDao.create(createStubUser("username20"));
		User createdUser_1 = userDao.create(createStubUser("username21"));
		User createdUser_2 = userDao.create(createStubUser("username22"));
		userDao.createUserFollower(createdUser_1.getId(), createdFollower.getId());
		userDao.createUserFollower(createdUser_2.getId(), createdFollower.getId());

		int countFollowing = userDao.countFollowingByUserId(createdFollower.getId());

		int expectedCountFollowing = 2;

		assertEquals(expectedCountFollowing, countFollowing);
	}

	@Test
	public void countFollowingByUserId_notExists() {
		int countFollowing = userDao.countFollowingByUserId(666);

		assertEquals(0, countFollowing);
	}

	@Test
	public void findFollowingByUserId() {
		User createdFollower = userDao.create(createStubUser("username23"));
		User createdUser_1 = userDao.create(createStubUser("username24"));
		User createdUser_2 = userDao.create(createStubUser("username25"));
		userDao.createUserFollower(createdUser_1.getId(), createdFollower.getId());
		userDao.createUserFollower(createdUser_2.getId(), createdFollower.getId());

		Set<User> receivedFollowing = userDao.findFollowingByUserId(createdFollower.getId(), 0, 20);

		Set<User> expectedFollowing = new HashSet<>(Arrays.asList(createdUser_1, createdUser_2));

		assertEquals(expectedFollowing, receivedFollowing);
	}

	@Test
	public void findUserFollower() {
		User createdUser = userDao.create(createStubUser("username26"));
		User createdFollower = userDao.create(createStubUser("username27"));
		userDao.createUserFollower(createdUser.getId(), createdFollower.getId());

		User receivedUserFollower = userDao.findUserFollower(createdUser.getId(), createdFollower.getId());

		assertEquals(createdFollower, receivedUserFollower);
	}

	@Test
	public void findUserFollower_notExists() {
		User receivedUserFollower = userDao.findUserFollower(666, 667);

		assertNull(receivedUserFollower);
	}

	@Test
	public void createUserFollower() {
		User createdUser = userDao.create(createStubUser("username28"));
		User createdFollower = userDao.create(createStubUser("username29"));
		userDao.createUserFollower(createdUser.getId(), createdFollower.getId());

		User receivedUserFollower = userDao.findUserFollower(createdUser.getId(), createdFollower.getId());

		User expectedUserFollower = createStubUser("username29");
		expectedUserFollower.setId(createdFollower.getId());

		assertNotNull(receivedUserFollower.getId());
		assertEquals(expectedUserFollower, receivedUserFollower);
	}

	@Test
	public void deleteUserFollower() {
		User createdUser = userDao.create(createStubUser("username30"));
		User createdFollower = userDao.create(createStubUser("username31"));
		userDao.createUserFollower(createdUser.getId(), createdFollower.getId());

		userDao.deleteUserFollower(createdUser.getId(), createdFollower.getId());

		User receivedUserFollower = userDao.findUserFollower(createdUser.getId(), createdFollower.getId());

		assertNull(receivedUserFollower);
	}

}
