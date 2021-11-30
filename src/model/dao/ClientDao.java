package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Client;

public interface ClientDao {
	
	void insert(Client obj);
	void update(Client obj);
	void deleteById(Integer id);
	Client findById(Integer id);
	List<Client> findAll();
	List<Client> findByDepartment(Department department);
	

}
