package br.com.ims.tool.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class User implements Serializable {
	/**
	 * 
	 */
	private Integer id;
	private String login;
	private String password;
	private String name;
	private String email;
	
	List<UserSystemArtifactPermission> access;

	
	public User() {
		access =  null;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<UserSystemArtifactPermission> getAccess() {
		return access;
	}

	public void setAccess(List<UserSystemArtifactPermission> access) {
		this.access = access;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	
	
}
