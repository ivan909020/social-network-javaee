package ua.ivan909020.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import ua.ivan909020.app.DataSourceRunner;
import ua.ivan909020.app.domain.entities.Post;
import ua.ivan909020.app.domain.entities.User;
import ua.ivan909020.app.exception.ValidationException;
import ua.ivan909020.app.service.impl.PostServiceImpl;
import ua.ivan909020.app.service.impl.UserServiceImpl;

@RunWith(DataSourceRunner.class)
public class PostServiceTest {

	private UserService userService = UserServiceImpl.getInstance();
	private final PostService postService = PostServiceImpl.getInstance();

	private User createStubUser(String username) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(username);
		user.setInformation(username);
		return user;
	}

	private Post createStubPost(Integer userId, String title) {
		Post post = new Post();
		post.setUserId(userId);
		post.setTitle(title);
		post.setDescription(title);
		post.setCreated(LocalDateTime.now());
		return post;
	}

	@Test
	public void findById() {
		User createdUser = userService.create(createStubUser("username1"));
		Post createdPost = postService.create(createStubPost(createdUser.getId(), "title1"));

		Post receivedPost = postService.findById(createdPost.getId());

		assertEquals(createdPost, receivedPost);
	}

	@Test
	public void findById_idIsNull_throwsException() {
		Integer postId = null;
		try {
			postService.findById(postId);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Id of post must not be null", e.getMessage());
		}
	}

	@Test
	public void create() {
		User createdUser = userService.create(createStubUser("username2"));
		Post createdPost = postService.create(createStubPost(createdUser.getId(), "title2"));

		Post expectedPost = createStubPost(createdUser.getId(), "title2");
		expectedPost.setId(createdPost.getId());
		expectedPost.setCreated(createdPost.getCreated());

		assertNotNull(createdPost.getId());
		assertEquals(expectedPost, createdPost);
	}

	@Test
	public void create_null_throwsException() {
		Post post = null;
		try {
			postService.create(post);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Post must not be null", e.getMessage());
		}
	}

	@Test
	public void create_withId_throwsException() {
		Post postToCreate = createStubPost(999, "title3");
		postToCreate.setId(999);
		try {
			postService.create(postToCreate);
			fail();
		} catch (ValidationException e) {
			assertEquals("Id of post must be null", e.getMessage());
		}
	}

	@Test
	public void update() {
		User createdUser = userService.create(createStubUser("username3"));
		Post createdPost = postService.create(createStubPost(createdUser.getId(), "title4"));

		createdPost.setTitle("updated_title4");
		createdPost.setDescription("updated_title4");
		Post updatedPost = postService.update(createdPost);

		Post expectedPost = createStubPost(createdUser.getId(), "updated_title4");
		expectedPost.setId(updatedPost.getId());
		expectedPost.setCreated(updatedPost.getCreated());

		assertEquals(expectedPost, updatedPost);
	}

	@Test
	public void update_null_throwsException() {
		Post post = null;
		try {
			postService.update(post);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Post must not be null", e.getMessage());
		}
	}

	@Test
	public void update_withoutId_throwsException() {
		Post postToCreate = createStubPost(999, "title5");
		try {
			postService.update(postToCreate);
			fail();
		} catch (ValidationException e) {
			assertEquals("Id of post must not be null", e.getMessage());
		}
	}

	@Test
	public void deleteById() {
		User createdUser = userService.create(createStubUser("username5"));
		Post createdPost = postService.create(createStubPost(createdUser.getId(), "title6"));

		postService.deleteById(createdPost.getId());

		Post receivedPost = postService.findById(createdPost.getId());

		assertNull(receivedPost);
	}

	@Test
	public void deleteById_idIsNull_throwsException() {
		Integer postId = null;
		try {
			postService.deleteById(postId);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Id of post must not be null", e.getMessage());
		}
	}

	@Test
	public void countAll() {
		User createdUser = userService.create(createStubUser("username6"));
		postService.create(createStubPost(createdUser.getId(), "title7"));
		postService.create(createStubPost(createdUser.getId(), "title8"));

		int countPosts = postService.countAll();

		int expectedCountPosts = 2;

		assertTrue(countPosts >= expectedCountPosts);
	}

	@Test
	public void findAll() {
		User createdUser = userService.create(createStubUser("username7"));
		Post createdPost_1 = postService.create(createStubPost(createdUser.getId(), "title9"));
		Post createdPost_2 = postService.create(createStubPost(createdUser.getId(), "title10"));

		List<Post> receivedPosts = postService.findAll(1, 20);

		List<Post> expectedPosts = Arrays.asList(createdPost_1, createdPost_2);

		assertTrue(receivedPosts.containsAll(expectedPosts));
	}

	@Test
	public void findAll_pageIsLessThanOne_throwsException() {
		Integer page = 0;
		try {
			postService.findAll(page, 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Page must be larger than 1", e.getMessage());
		}
	}

	@Test
	public void findAll_sizeIsLessThanTen_throwsException() {
		Integer size = 9;
		try {
			postService.findAll(1, size);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Size must be larger than 10", e.getMessage());
		}
	}

	@Test
	public void countByUserId() {
		User createdUser = userService.create(createStubUser("username8"));
		postService.create(createStubPost(createdUser.getId(), "title11"));
		postService.create(createStubPost(createdUser.getId(), "title12"));

		int countPosts = postService.countByUserId(createdUser.getId());

		int expectedCountPosts = 2;

		assertEquals(expectedCountPosts, countPosts);
	}

	@Test
	public void countByUserId_idIsNull_throwsException() {
		Integer userId = null;
		try {
			postService.countByUserId(userId);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Id of user must not be null", e.getMessage());
		}
	}

	@Test
	public void findByUserId() {
		User createdUser_1 = userService.create(createStubUser("username9"));
		Post createdPost_1 = postService.create(createStubPost(createdUser_1.getId(), "title13"));
		Post createdPost_2 = postService.create(createStubPost(createdUser_1.getId(), "title14"));
		User createdUser_2 = userService.create(createStubUser("username10"));
		Post createdPost_3 = postService.create(createStubPost(createdUser_2.getId(), "title15"));

		List<Post> receivedPosts_1 = postService.findByUserId(createdUser_1.getId(), 1, 20);
		List<Post> receivedPosts_2 = postService.findByUserId(createdUser_2.getId(), 1, 20);

		List<Post> expectedPosts_1 = Arrays.asList(createdPost_1, createdPost_2);
		List<Post> expectedPosts_2 = Arrays.asList(createdPost_3);

		assertEquals(expectedPosts_1, receivedPosts_1);
		assertEquals(expectedPosts_2, receivedPosts_2);
	}

	@Test
	public void findByUserId_idIsNull_throwsException() {
		Integer userId = null;
		try {
			postService.findByUserId(userId, 1, 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Id of user must not be null", e.getMessage());
		}
	}

	@Test
	public void findByUserId_pageIsLessThanOne_throwsException() {
		Integer page = 0;
		try {
			postService.findByUserId(999, page, 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Page must be larger than 1", e.getMessage());
		}
	}

	@Test
	public void findByUserId_sizeIsLessThanTen_throwsException() {
		Integer size = 9;
		try {
			postService.findByUserId(999, 1, size);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Size must be larger than 10", e.getMessage());
		}
	}

}
