package net.money.payload.request;

public class ExpenseTypeRequest {

	private String name;
	private String memo;
	private Integer familyid;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getFamilyid() {
		return familyid;
	}
	public void setFamilyid(Integer familyid) {
		this.familyid = familyid;
	}
	public ExpenseTypeRequest() {
		
	}
	public ExpenseTypeRequest(String name, String memo, Integer familyid) {
		super();
		this.name = name;
		this.memo = memo;
		this.familyid = familyid;
	}
	
	

}
