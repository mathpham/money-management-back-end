package net.money.models;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 30)
	private String username;
	
	@NotBlank
	@Size(max = 200)
	@JsonIgnore
	private String password;
	
	@NotBlank
	@Size(max = 50)
	private String email;
	
	public User(@NotBlank @Size(max = 30) String username, @NotBlank @Size(max = 200) String password,
			@NotBlank @Size(max = 50) String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull
	@OneToOne
	@JoinColumn(name = "family_id")
	@JsonIgnore
	private Family family;
	
	@NotBlank
	@Column(name = "displayname")
	@Size(max = 30)
	private String displayname;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	public User() {
		
	}
	
	public User(@NotBlank @Size(max = 30) String username, @NotBlank @Size(max = 200) String password,
			@NotBlank @Size(max = 50) String email, @NotBlank Family family,
			@NotBlank @Size(max = 30) String displayname) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.family = family;
		this.displayname = displayname;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamilyId(Family family) {
		this.family = family;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	
	
	
	
}
