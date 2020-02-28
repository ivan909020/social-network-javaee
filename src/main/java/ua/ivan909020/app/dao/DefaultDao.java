package ua.ivan909020.app.dao;

interface DefaultDao<T> {

	T findById(Integer id);

	T create(T entity);

	T update(T entity);

}
