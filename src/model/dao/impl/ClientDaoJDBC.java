package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.ClientDao;
import model.entities.Client;
import model.entities.Department;
import model.entities.Product;

public class ClientDaoJDBC implements ClientDao {

	private Connection conn;

	public ClientDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Client obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO client " 
					+ "(Name, Email, BirthDate, District, City, Cep, Rg, Cpf, Phone,DepartmentId) "
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setString(4, obj.getDistrict());
			st.setString(5, obj.getCity());
			st.setInt(6, obj.getCep());
			st.setInt(7, obj.getRg());
			st.setInt(8, obj.getCpf());
			st.setInt(9, obj.getPhone());
			st.setInt(10, obj.getDepartment().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inexperado nenhuma linha foi afetada");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Client obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE product "
					+ "SET Name = ?, Email = ?, District = ?, City = ?, Cep = ?, Rg = ?, Cpf = ?, Phone = ?,DepartmentId = ? " 
					+ "WHERE Id = ? ");

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setString(4, obj.getDistrict());
			st.setString(5, obj.getCity());
			st.setInt(6, obj.getCep());
			st.setInt(7, obj.getRg());
			st.setInt(8, obj.getCpf());
			st.setInt(9, obj.getPhone());
			st.setInt(10, obj.getDepartment().getId());
			st.setInt(11, obj.getId());
			
			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM client \r\n" + "WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Client findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT client.*,department.Name as DepName " 
					+ " FROM client INNER JOIN department "
					+ "ON client.DepartmentId = department.Id " 
					+ "WHERE client.Id = ? ");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Client obj = instantiateClient(rs, dep);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Client instantiateClient(ResultSet rs, Department dep) throws SQLException {
		Client obj = new Client();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setDistrict(rs.getString("District"));
		obj.setBirthDate(new java.util.Date(rs.getTimestamp("BirthDate").getTime()));
		obj.setCity(rs.getString("City"));
		obj.setCep(rs.getInt("Cep"));
		obj.setRg(rs.getInt("Rg"));
		obj.setCpf(rs.getInt("Cpf"));
		obj.setPhone(rs.getInt("Phone"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Client> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT client.*,department.Name as DepName " 
					+ "FROM client INNER JOIN department "
					+ "ON client.DepartmentId = department.Id " 
					+ "ORDER BY Name");

			rs = st.executeQuery();

			List<Client> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Client obj = instantiateClient(rs, dep);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Client> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT client.*,department.Name as DepName " 
					+ "FROM client INNER JOIN department "
					+ "ON client.DepartmentId = department.Id " 
					+ "WHERE DepartmentId = ? " + "ORDER BY Name");

			st.setInt(1, department.getId());

			rs = st.executeQuery();

			List<Client> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Client obj = instantiateClient(rs, dep);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
