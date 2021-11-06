package net.money.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "expense_info")
public class ExpenseInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "expense_type_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private ExpenseType expenseType;
	
	@OneToOne
	@JoinColumn(name = "family_id")
	private Family family;
	
	@Column(name = "datetime")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date datetime;
	
	@Column(name = "amount_of_money")
	private Long amountOfMoney;
	
	@OneToOne
	@JoinColumn(name = "store_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private Store store;
	
	@Column(name = "name")
	private String name;
	
	private String image;
	
	private String memo;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Long getAmountOfMoney() {
		return amountOfMoney;
	}

	public void setAmountOfMoney(Long amountOfMoney) {
		this.amountOfMoney = amountOfMoney;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public ExpenseInfo() {
		
	}
	public ExpenseInfo(ExpenseType expenseType, Family family, Date datetime, Long amountOfMoney, Store store,
			String name, String image, String memo, User user) {
		super();
		this.expenseType = expenseType;
		this.family = family;
		this.datetime = datetime;
		this.amountOfMoney = amountOfMoney;
		this.store = store;
		this.name = name;
		this.image = image;
		this.memo = memo;
		this.user = user;
	}
	
	
	
	
	
	

}
