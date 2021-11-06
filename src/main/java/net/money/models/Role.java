package net.money.models;

import javax.persistence.*;


@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private ERole name;
	
	public Role() {
		
	}

	public Role(ERole name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}

	public Role(Integer id, ERole name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	

}
