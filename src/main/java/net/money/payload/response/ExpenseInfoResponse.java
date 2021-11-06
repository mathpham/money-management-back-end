package net.money.payload.response;

import java.util.Date;

public class ExpenseInfoResponse {

	private Long id;
	private String expenseTypeName;
	private String datetime;
	private Long amountOfMoney;
	private String storeName;
	private String name;
	private String image;
	private String memo;
	private Long userId;
	private String userDisplayName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Long getAmountOfMoney() {
		return amountOfMoney;
	}

	public void setAmountOfMoney(Long amountOfMoney) {
		this.amountOfMoney = amountOfMoney;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}

	public ExpenseInfoResponse(Long id, String expenseTypeName, String datetime, Long amountOfMoney, String storeName,
			String name, String image, String memo, Long userId, String userDisplayName) {
		super();
		this.id = id;
		this.expenseTypeName = expenseTypeName;
		this.datetime = datetime;
		this.amountOfMoney = amountOfMoney;
		this.storeName = storeName;
		this.name = name;
		this.image = image;
		this.memo = memo;
		this.userId = userId;
		this.userDisplayName = userDisplayName;
	}
	
	
	
}
