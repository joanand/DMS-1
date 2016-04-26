package in.jdsoft.dms.model;

// default package
// Generated Apr 26, 2016 11:56:10 AM by Hibernate Tools 4.3.1.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", catalog = "jdsoftdms", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
public class Users implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String folderCode;
	private int userStatus;
	private String userRole;

	public Users() {
	}

	public Users(String userName, String userPassword, int userStatus, String userRole) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userStatus = userStatus;
		this.userRole = userRole;
	}

	public Users(String userName, String userPassword, String userEmail, String folderCode, int userStatus,
			String userRole) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.folderCode = folderCode;
		this.userStatus = userStatus;
		this.userRole = userRole;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_name", unique = true, nullable = false)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_password", nullable = false)
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name = "user_email")
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Column(name = "folder_code")
	public String getFolderCode() {
		return this.folderCode;
	}

	public void setFolderCode(String folderCode) {
		this.folderCode = folderCode;
	}

	@Column(name = "user_status", nullable = false)
	public int getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	@Column(name = "user_role", nullable = false)
	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}
