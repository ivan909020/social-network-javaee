package ua.ivan909020.app.service;

interface DefaultService<T> {

	T findById(Integer id);

	T create(T entity);

	T update(T entity);

}
