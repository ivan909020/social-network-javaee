package ua.ivan909020.app.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import ua.ivan909020.app.DataSourceRunner;
import ua.ivan909020.app.dao.impl.PostDaoImpl;
import ua.ivan909020.app.dao.impl.UserDaoImpl;
import ua.ivan909020.app.domain.entities.Post;
import ua.ivan909020.app.domain.entities.User;

@RunWith(DataSourceRunner.class)
public class PostDaoTest {

	private final UserDao userDao = UserDaoImpl.getInstance();
	private final PostDao postDao = PostDaoImpl.getInstance();

	private User createStubUser(String username) {
		User user = new User();
		user.setUsername(username);
		user.setPassword("");
		user.setInformation("");
		return user;
	}

	private Post createStubPost(Integer userId, String title) {
		Post post = new Post();
		post.setUserId(userId);
		post.setTitle(title);
		post.setDescription("");
		post.setCreated(LocalDateTime.now());
		return post;
	}

	@Test
	public void findById() {
		User createdUser = userDao.create(createStubUser("username1"));
		Post createdPost = postDao.create(createStubPost(createdUser.getId(), "title1"));

		Post receivedPost = postDao.findById(createdPost.getId());

		assertEquals(createdPost, receivedPost);
	}

	@Test
	public void findById_notExists() {
		Post receivedPost = postDao.findById(999);

		assertNull(receivedPost);
	}

	@Test
	public void create() {
		User createdUser = userDao.create(createStubUser("username2"));
		Post createdPost = postDao.create(createStubPost(createdUser.getId(), "title2"));

		Post expectedPost = createStubPost(createdUser.getId(), "title2");
		expectedPost.setId(createdPost.getId());
		expectedPost.setCreated(createdPost.getCreated());

		assertNotNull(createdPost.getId());
		assertEquals(expectedPost, createdPost);
	}

	@Test
	public void create_idsAreDifferent() {
		User createdUser = userDao.create(createStubUser("username3"));
		Post createdPost_1 = postDao.create(createStubPost(createdUser.getId(), "title3"));
		Post createdPost_2 = postDao.create(createStubPost(createdUser.getId(), "title4"));

		assertNotEquals(createdPost_1.getId(), createdPost_2.getId());
	}

	@Test
	public void update() {
		User createdUser = userDao.create(createStubUser("username4"));
		Post createdPost = postDao.create(createStubPost(createdUser.getId(), "title5"));

		createdPost.setTitle("updated_title5");
		Post updatedPost = postDao.update(createdPost);

		Post expectedPost = createStubPost(createdUser.getId(), "updated_title5");
		expectedPost.setId(createdPost.getId());
		expectedPost.setCreated(createdPost.getCreated());

		assertEquals(expectedPost, updatedPost);
	}

	@Test
	public void update_notExists() {
		Post postToUpdate = createStubPost(666, "title6");
		postToUpdate.setId(999);

		Post updatedPost = postDao.update(postToUpdate);

		assertNull(updatedPost);
	}

	@Test
	public void delete() {
		User createdUser = userDao.create(createStubUser("username5"));
		Post createdPost = postDao.create(createStubPost(createdUser.getId(), "title7"));

		postDao.deleteById(createdPost.getId());

		Post receivedPost = postDao.findById(createdPost.getId());

		assertNull(receivedPost);
	}

	@Test
	public void countAll() {
		User createdUser = userDao.create(createStubUser("username6"));
		postDao.create(createStubPost(createdUser.getId(), "title8"));
		postDao.create(createStubPost(createdUser.getId(), "title9"));

		int countPosts = postDao.countAll();

		int expectedCountPosts = 2;

		assertTrue(countPosts >= expectedCountPosts);
	}

	@Test
	public void findAll() {
		User createdUser = userDao.create(createStubUser("username7"));
		Post createdPost_1 = postDao.create(createStubPost(createdUser.getId(), "title10"));
		Post createdPost_2 = postDao.create(createStubPost(createdUser.getId(), "title11"));

		List<Post> receivedPosts = postDao.findAll(0, 20);

		List<Post> expectedPosts = Arrays.asList(createdPost_1, createdPost_2);

		assertTrue(receivedPosts.containsAll(expectedPosts));
	}

	@Test
	public void countByUserId() {
		User createdUser = userDao.create(createStubUser("username8"));
		postDao.create(createStubPost(createdUser.getId(), "title12"));
		postDao.create(createStubPost(createdUser.getId(), "title13"));

		int countPosts = postDao.countByUserId(createdUser.getId());

		int expectedCountPosts = 2;

		assertEquals(expectedCountPosts, countPosts);
	}

	@Test
	public void countByUserId_notExists() {
		int countPosts = postDao.countByUserId(999);

		assertEquals(0, countPosts);
	}

	@Test
	public void findByUserId() {
		User createdUser_1 = userDao.create(createStubUser("username9"));
		Post createdPost_1 = postDao.create(createStubPost(createdUser_1.getId(), "title13"));
		Post createdPost_2 = postDao.create(createStubPost(createdUser_1.getId(), "title14"));
		User createdUser_2 = userDao.create(createStubUser("username10"));
		Post createdPost_3 = postDao.create(createStubPost(createdUser_2.getId(), "title15"));

		List<Post> receivedPosts_1 = postDao.findByUserId(createdUser_1.getId(), 0, 20);
		List<Post> receivedPosts_2 = postDao.findByUserId(createdUser_2.getId(), 0, 20);

		List<Post> expectedPosts_1 = Arrays.asList(createdPost_1, createdPost_2);
		List<Post> expectedPosts_2 = Arrays.asList(createdPost_3);

		assertEquals(expectedPosts_1, receivedPosts_1);
		assertEquals(expectedPosts_2, receivedPosts_2);
	}

	@Test
	public void findByUserId_notExists() {
		List<Post> receivedPosts = postDao.findByUserId(666, 0, 20);

		assertTrue(receivedPosts.isEmpty());
	}

}
