package ua.ivan909020.app.dao;

import java.util.List;

import ua.ivan909020.app.domain.entities.Post;

public interface PostDao extends DefaultDao<Post> {

	int countAll();

	List<Post> findAll(int offset, int limit);
	
	int countByUserId(Integer id);

}
