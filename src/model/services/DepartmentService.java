package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {

	public List<Department> findAll() {
		List<Department> list = new ArrayList<>();
		list.add(new Department(1,"Eletr�nicos"));
		list.add(new Department(2,"Eletrodom�sticos"));
		list.add(new Department(3,"M�veis"));
		list.add(new Department(4,"Variados"));
		return list;
	}
}
