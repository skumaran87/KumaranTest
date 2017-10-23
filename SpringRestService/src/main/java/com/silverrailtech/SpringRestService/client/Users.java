package com.silverrailtech.SpringRestService.client;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="users")
public class Users {

	public Users() {
		
	}
	
	public Users(Users user) {
		
		this.firstName=user.firstName;
		this.token=user.token;
		this.state=user.state;
		this.active=user.active;
		this.roles=user.roles;
		this.Id=user.Id;
		this.password=user.password;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="user_id")
	private int Id;
	
    @Column(name="user_name")
	private String firstName;
    
    @Column(name="user_token")
	private String token;
    
    @Column(name="user_state")
	private String state;
    
    @Column(name="user_status")
	private String active;
    
    @Column(name="user_pwd")
	private String password;
    
    @Column(name = "created_date")
    private Date date;
    
    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name="user_role" ,joinColumns=@JoinColumn(name="user_id"))
    private Set<Role> roles;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
