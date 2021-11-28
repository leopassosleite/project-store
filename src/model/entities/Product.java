package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Date saleDate;
	private Double price;
	private Integer quantity;
	
	private Department department;
	
	public Product() {
	}

	public Product(Integer id, String name, Date saleDate, Integer quantity, Double price, Department department) {
		this.id = id;
		this.name = name;
		this.saleDate = saleDate;
		this.price = price;
		this.quantity = quantity;
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

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", saleDate=" + saleDate + ", price=" + price + ", quantity="
				+ quantity + ", department=" + department + "]";
	}
}
