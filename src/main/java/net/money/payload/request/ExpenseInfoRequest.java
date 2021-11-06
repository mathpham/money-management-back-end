package net.money.payload.request;

import java.util.Date;

public class ExpenseInfoRequest {
	
	private Long expenseTypeId;
	private Integer familyId;
	private Date datatime;
	private Long amountOfMoney;
	private Long storeId;
	private String name;
	private String image;
	private String memo;
	private Long userId;
	public Long getExpenseTypeId() {
		return expenseTypeId;
	}
	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}
	public Integer getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}
	public Date getDatatime() {
		return datatime;
	}
	public void setDatatime(Date datatime) {
		this.datatime = datatime;
	}
	public Long getAmountOfMoney() {
		return amountOfMoney;
	}
	public void setAmountOfMoney(Long amountOfMoney) {
		this.amountOfMoney = amountOfMoney;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
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
	
	public ExpenseInfoRequest() {
		
	}
	public ExpenseInfoRequest(Long expenseTypeId, Integer familyId, Date datatime, Long amountOfMoney, Long storeId,
			String name, String image, String memo, Long userId) {
		super();
		this.expenseTypeId = expenseTypeId;
		this.familyId = familyId;
		this.datatime = datatime;
		this.amountOfMoney = amountOfMoney;
		this.storeId = storeId;
		this.name = name;
		this.image = image;
		this.memo = memo;
		this.userId = userId;
	}
	
	

}
