package ua.ivan909020.app.service.impl;

import java.util.List;

import ua.ivan909020.app.dao.PostDao;
import ua.ivan909020.app.dao.impl.PostDaoImpl;
import ua.ivan909020.app.domain.entities.Post;
import ua.ivan909020.app.exception.ValidationException;
import ua.ivan909020.app.service.PostService;

public class PostServiceImpl implements PostService {

	private static final PostService INSTANCE = new PostServiceImpl();

	private final PostDao postDao = PostDaoImpl.getInstance();

	private PostServiceImpl() {
	}

	public static PostService getInstance() {
		return INSTANCE;
	}

	@Override
	public Post findById(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Id of post must not be null");
		}
		return postDao.findById(id);
	}

	@Override
	public Post create(Post post) {
		if (post == null) {
			throw new IllegalArgumentException("Post must not be null");
		}
		if (post.getId() != null) {
			throw new ValidationException("Id of post must be null");
		}
		return postDao.create(post);
	}

	@Override
	public Post update(Post post) {
		if (post == null) {
			throw new IllegalArgumentException("Post must not be null");
		}
		if (post.getId() == null) {
			throw new ValidationException("Id of post must not be null");
		}
		return postDao.update(post);
	}

	@Override
	public void deleteById(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Id of post must not be null");
		}
		postDao.deleteById(id);
	}

	@Override
	public int countAll() {
		return postDao.countAll();
	}

	@Override
	public List<Post> findAll(int page, int size) {
		if (page < 1) {
			throw new IllegalArgumentException("Page must be larger than 1");
		}
		if (size < 10) {
			throw new IllegalArgumentException("Size must be larger than 10");
		}
		int offset = (page - 1) * size;
		return postDao.findAll(offset, size);
	}

	@Override
	public int countByUserId(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Id of user must not be null");
		}
		return postDao.countByUserId(id);
	}

	@Override
	public List<Post> findByUserId(Integer id, int page, int size) {
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
		return postDao.findByUserId(id, offset, size);
	}

}
