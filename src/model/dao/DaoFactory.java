package model.dao;

import db.DB;
import model.dao.impl.ClientDaoJDBC;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.ProductDaoJDBC;

public class DaoFactory {
	
	public static ProductDao createProductDao() {
		return new ProductDaoJDBC(DB.getConnection());
	}

	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	
	public static ClientDao createClientDao() {
		return new ClientDaoJDBC(DB.getConnection());
	}

}
