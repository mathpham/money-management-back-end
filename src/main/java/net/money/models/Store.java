package net.money.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "store")
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "family_id")
	@JsonIgnore
	private Family family;
	
	@Column(name = "store_info")
	private String storeInfo;
	
	@Column(name = "memo")
	private String memo;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "using_times")
	private Integer usingTimes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public String getStoreInfo() {
		return storeInfo;
	}

	public void setStoreInfo(String storeInfo) {
		this.storeInfo = storeInfo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getUsingTimes() {
		return usingTimes;
	}

	public void setUsingTimes(Integer usingTimes) {
		this.usingTimes = usingTimes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Store() {
		
	}
	public Store(Family family, String storeInfo, String memo, String name, Integer usingTimes) {
		super();
		this.family = family;
		this.storeInfo = storeInfo;
		this.memo = memo;
		this.name = name;
		this.usingTimes = usingTimes;
	}
	
	
}
