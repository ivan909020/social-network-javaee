package ua.ivan909020.app.service;

import java.util.List;

import ua.ivan909020.app.domain.entities.Post;

public interface PostService extends DefaultService<Post> {

	void deleteById(Integer id);

	int countAll();

	List<Post> findAll(int page, int size);

	int countByUserId(Integer id);

	List<Post> findByUserId(Integer userId, int page, int size);

}
