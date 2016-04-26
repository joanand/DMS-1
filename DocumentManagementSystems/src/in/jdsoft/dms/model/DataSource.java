package in.jdsoft.dms.model;


public class DataSource {
	
	private String DriverClassName="com.mysql.jdbc.Driver";
	private String URL="jdbc:mysql://localhost:3306/jdsoftdms?createDatabaseIfNotExist=true";
	private String UserName="root";
	private String Password="";
	
	public DataSource() {
		// TODO Auto-generated constructor stub
	}
	
	public DataSource(String driverClassName, String uRL, String userName, String password)
	{
	
		this.DriverClassName = driverClassName;
		this.URL = uRL;
		this.UserName = userName;
		this.Password = password;
	}


	/**
	 * @return the driverClassName
	 */
	public String getDriverClassName() {
		return this.DriverClassName;
	}


	/**
	 * @param driverClassName the driverClassName to set
	 */
	public void setDriverClassName(String driverClassName) {
		this.DriverClassName = driverClassName;
	}


	/**
	 * @return the uRL
	 */
	public String getURL() {
		return this.URL;
	}


	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String uRL) {
		this.URL = uRL;
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.UserName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.UserName = userName;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.Password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.Password = password;
	}







	
}
