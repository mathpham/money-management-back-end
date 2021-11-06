package net.money.payload.request;


public class StoreRequest {

	private Long id;
	private Integer familyid;
	private String storeInfo;
	private String memo;
	private String name;
	private Integer usingTimes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getFamilyid() {
		return familyid;
	}
	public void setFamilyid(Integer familyid) {
		this.familyid = familyid;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUsingTimes() {
		return usingTimes;
	}
	public void setUsingTimes(Integer usingTimes) {
		this.usingTimes = usingTimes;
	}
	public StoreRequest() {
		
	}
	public StoreRequest(Integer familyid, String storeInfo, String memo, String name, Integer usingTimes) {
		super();
		this.familyid = familyid;
		this.storeInfo = storeInfo;
		this.memo = memo;
		this.name = name;
		this.usingTimes = usingTimes;
	}
	
}
