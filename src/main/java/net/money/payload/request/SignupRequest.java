package net.money.payload.request;


import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.*;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 30)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    
    @NotNull
	private Integer familyid;
    
    private String displayname;
    
    
    
    public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public Integer getFamilyId() {
		return familyid;
	}

	public void setFamilyId(Integer familyId) {
		this.familyid = familyId;
	}

	public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }

	public SignupRequest(@NotBlank @Size(min = 3, max = 30) String username,
			@NotBlank @Size(max = 50) @Email String email, Set<String> role,
			@NotBlank @Size(min = 6, max = 40) String password, @NotNull Integer familyid, String displayname) {
		super();
		this.username = username;
		this.email = email;
		this.role = role;
		this.password = password;
		this.familyid = familyid;
		this.displayname = displayname;
	}
    
    
}

