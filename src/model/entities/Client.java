package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String email;
	private String district;
	private String city;
	private Integer cep;
	private Integer rg;
	private Integer cpf;
	private Integer phone;
	
	private Department department;
	
	public Client() {
	}
	
	public Client(Integer id, String name, String email, String district, String city, Integer cep, Integer rg,
			Integer cpf, Integer phone, Department department) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.district = district;
		this.city = city;
		this.cep = cep;
		this.rg = rg;
		this.cpf = cpf;
		this.phone = phone;
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public Integer getRg() {
		return rg;
	}

	public void setRg(Integer rg) {
		this.rg = rg;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", email=" + email + ", district=" + district + ", city=" + city
				+ ", cep=" + cep + ", rg=" + rg + ", cpf=" + cpf + ", phone=" + phone + ", department=" + department
				+ "]";
	}
}
