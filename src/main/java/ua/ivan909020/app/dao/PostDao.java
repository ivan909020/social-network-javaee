package ua.ivan909020.app.dao;

import java.util.List;

import ua.ivan909020.app.domain.entities.Post;

public interface PostDao extends DefaultDao<Post> {

	void deleteById(Integer id);

	int countAll();

	List<Post> findAll(int offset, int limit);

	int countByUserId(Integer id);

	List<Post> findByUserId(Integer id, int offset, int limit);

}
