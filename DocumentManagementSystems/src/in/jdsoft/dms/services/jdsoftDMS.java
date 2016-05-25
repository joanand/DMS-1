package in.jdsoft.dms.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import in.jdsoft.dms.model.*;
import in.jdsoft.dms.services.CommonMethods;

public class jdsoftDMS                                             
{

	CommonMethods commonMethods=new CommonMethods();
	
public void createFolder(String FolderName)throws Exception
{
	if(FolderName=="")
	{
	throw new Exception("Null pointer exception");
	}
	
    Folder folder1=new Folder();
    folder1=commonMethods.folderdao.getFolderCodeByUser(commonMethods.FolderCode);
	String[] str=FolderName.split("/");
	String RootFolderCode=null;
	for (int i=0;i<str.length;i++)
	{	
	Random rand=new Random();
	Integer d=rand.nextInt(999999999);
	Integer parentFolderId=folder1.getFolderId();
	Folder folder=new Folder(str[i].toString(),String.valueOf(d),new Timestamp(Calendar.getInstance().getTime().getTime()),new Timestamp(Calendar.getInstance().getTime().getTime()),commonMethods.UsersName,commonMethods.UsersName,parentFolderId);
	commonMethods.folderdao.persist(folder);
	RootFolderCode=String.valueOf(d);
	folder1=commonMethods.folderdao.getFolderCodeByUser(RootFolderCode);	
	}

}
public void createSubFolder(String RootFolderCode,String SubFolderName)throws Exception
{
	if(RootFolderCode==""||SubFolderName=="")
	{
	throw new Exception("Null pointer exception");
	}	
	String[] str=SubFolderName.split("/");
	for (int i=0;i<str.length;i++)
	{
	Folder folder1=new Folder();	
	Random rand=new Random();
	Integer d=rand.nextInt(999999999);
	folder1=commonMethods.folderdao.getFolderCodeByUser(RootFolderCode);		
	Integer parentFolderId=folder1.getFolderId();	
	Folder folder=new Folder(str[i].toString(),String.valueOf(d),new Timestamp(Calendar.getInstance().getTime().getTime()),new Timestamp(Calendar.getInstance().getTime().getTime()),commonMethods.UsersName,commonMethods.UsersName,parentFolderId);
	commonMethods.folderdao.persist(folder);
	RootFolderCode=String.valueOf(d);
	}
}

	
public void deleteFolder(String FolderCode)throws Exception
{
	if(FolderCode=="")
	{
	throw new Exception("Null pointer exception");
	}
	Folder folder=new Folder();
	DocumentHistory documenthistory=new DocumentHistory();
	String str=commonMethods.deletePath(FolderCode);
	String[] str1=str.split("/");
	for(int i=0;i<str1.length;i++)
	{
		folder=commonMethods.folderdao.getFolderCodeByUser(str1[i].toString());
		ArrayList<Document> dock1= (ArrayList<Document>)commonMethods.documentdao.getPathByDocument("root:jdsoftdms/"+commonMethods.Path(folder.getFolderCode()));
		if(folder!=null)
		{
			for(Document s1:dock1 )
			{	
				if(s1!=null)
				{		
					documenthistory=commonMethods.documenthistorydao.getNameByDocumentHistory(s1.getDocumentId());
					commonMethods.documenthistorydao.delete(documenthistory);
					commonMethods.documentdao.delete(s1);
				}
			}
			commonMethods.folderdao.delete(folder);
		}
	}	
}
public void renameFolder(String OldFolderCode,String NewFolderName)throws Exception
{
	if(OldFolderCode==""||NewFolderName=="")
	{
	throw new Exception("Null pointer exception");
	}
	Folder folder=new Folder();
	folder=commonMethods.folderdao.getFolderCodeByUser(OldFolderCode);	
	String curefolder="root:jdsoftdms/"+commonMethods.Path(OldFolderCode); 
	ArrayList<Document> dock= (ArrayList<Document>)commonMethods.documentdao.getPathByDocument(curefolder);
	curefolder=curefolder.replace(folder.getFolderName(), NewFolderName);
	folder.setFolderName(NewFolderName);
	folder.setModifiedBy(commonMethods.UsersName);
	folder.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
	for(Document s:dock )
	{	
		s.setPath(curefolder);
		commonMethods.documentdao.update(s);
	}
	commonMethods.folderdao.update(folder);
}	
public void createDocument(String DocumentName,String FolderCode,byte[] bytes)throws Exception
{
	if(DocumentName==""||FolderCode=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	Document document1=new Document();
	DocumentHistory documenthistory=new DocumentHistory();
	Random rand=new Random();
	Integer d=rand.nextInt(999999999);
	document.setDocumentName(DocumentName);
	document.setDocumentUuid(String.valueOf(d));
	document.setLockStatus(0);
	document.setEditStatus(0);
	document.setPath("root:jdsoftdms/"+commonMethods.Path(FolderCode));
	document.setDocumentAccess("#");
	commonMethods.documentdao.persist(document);
	document1=commonMethods.documentdao.getUuidByDocument(String.valueOf(d));
	documenthistory.setAuthor(commonMethods.UsersName);
	documenthistory.setDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
	documenthistory.setDocumentId(document1.getDocumentId());
	documenthistory.setVersion(1.0);
	documenthistory.setContent(bytes);
	commonMethods.documenthistorydao.persist(documenthistory);	
}	
public void renameDocument(String NewDocumentName, String DocumentUuid) throws Exception
{
	if(NewDocumentName==""||DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	document.setDocumentName(NewDocumentName);
	commonMethods.documentdao.update(document);	
}	
public void deleteDocument(String DocumentUuid)throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	DocumentHistory documenthistory=new DocumentHistory();
	documenthistory=commonMethods.documenthistorydao.getNameByDocumentHistory(document.getDocumentId());
	commonMethods.documentdao.delete(document);
	commonMethods.documenthistorydao.delete(documenthistory);
}	
public void createUser(String userName, String userPassword, String userEmail, Integer userStatus, String userRole) throws Exception
{
	if(userName==""||userPassword=="")
	{
	throw new Exception("Null pointer exception");
	}
	if(commonMethods.UserRole.equals("Admin"))
	{
		if(	(userRole.equals("Admin")||userRole.equals("User"))&&(userStatus.equals(1)||userStatus.equals(0)))
		{
				Users users=new Users();
				users.setUserName(userName);
				users.setUserPassword(userPassword);
				users.setUserEmail(userEmail);
				users.setUserStatus(userStatus);
				users.setUserRole(userRole);				
				commonMethods.usersdao.persist(users);				
				createFolder(userName);
				Folder folder=commonMethods.folderdao.getFolderDetailsByUserName(userName);
				users.setFolderCode(folder.getFolderCode());
				commonMethods.usersdao.update(users);
		}
		else
		{	
			throw new Exception("UserRole Such As Admin or User And UserStatus Such As 1 or 0");
		}
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}	
}	
public void deleteUser(String UserName) throws Exception
{
	if(commonMethods.UserRole.equals("Admin"))
	{
	Users users=new Users();
	users=commonMethods.usersdao.getUserByName(UserName);
	commonMethods.usersdao.delete(users);
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
}
public void lockDocument(String DocumentUuid) throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	if(document.getLockStatus()==0&&document.getEditStatus()==0)
	{
		document.setLockStatus(1);
		document.setLockInformation(commonMethods.UsersName);
		commonMethods.documentdao.update(document);
	}
	else if((document.getLockStatus()==0&&document.getEditStatus()==0)&&commonMethods.UserRole.equals("Admin"))
	{
		document.setLockStatus(1);
		document.setLockInformation(commonMethods.UsersName);
		commonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Document locked by another user...");
	}	
}	
public void unlockDocument(String DocumentUuid) throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	if((document.getLockStatus()==1) && (document.getLockInformation().equals(commonMethods.UsersName)))
	{
		document.setLockStatus(0);
		document.setLockInformation(null);
		commonMethods.documentdao.update(document);
	}
	else if(commonMethods.UserRole.equals("Admin")&&document.getLockStatus()==1)
	{
		document.setLockStatus(0);
		document.setLockInformation(null);
		commonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Document locked by another user...");	
	}	
}	
public void checkIn(String DocumentUuid) throws Exception
{
	 if(DocumentUuid=="")
		{
		throw new Exception("Null pointer exception");
		}
		Document document=new Document();
		document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
		if(document.getEditStatus()==0&&document.getLockStatus()==0)
		{
			document.setEditStatus(1);
			document.setEditInformation(commonMethods.UsersName);
			commonMethods.documentdao.update(document);
		}
		else
		{
			throw new Exception("Current document edit with other user...");
		}
}	
public void checkOut(String DocumentUuid,byte[] bytes) throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	DocumentHistory documenthistory=new DocumentHistory();
	ArrayList<DocumentHistory> dh=(ArrayList<DocumentHistory>)commonMethods.documenthistorydao.getIdByDocumentHistory(document.getDocumentId());
	for(DocumentHistory s:dh )
	{
		commonMethods.version=s.getVersion();	
	}
	if((document.getEditStatus()==1)&& (document.getEditInformation().equals(commonMethods.UsersName)))
	{
		document.setEditStatus(0);
		document.setEditInformation(null);
		documenthistory.setDocumentId(document.getDocumentId());
		documenthistory.setContent(bytes);
		documenthistory.setAuthor(commonMethods.UsersName);
		documenthistory.setDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		documenthistory.setVersion(commonMethods.version+0.1);
		commonMethods.documentdao.update(document);
		commonMethods.documenthistorydao.persist(documenthistory);
	}
	else
	{
		throw new Exception("Document already checkout..");	
	}
}
public void cancelCheckIn(String DocumentUuid) throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	if(document.getEditStatus()==1&&commonMethods.UserRole.equals("Admin"))
	{
		document.setEditStatus(0);
		document.setEditInformation(null);
		commonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
}
public byte[] getContent(String DocumentUuid) throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);	
	byte[] b=null;
	if(document.getEditStatus()==1)
	{
		ArrayList<DocumentHistory> dh=(ArrayList<DocumentHistory>)commonMethods.documenthistorydao.getIdByDocumentHistory(document.getDocumentId());
		for(DocumentHistory s:dh )
		{
			b=s.getContent();
		}
	}
	else
	{
		throw new Exception("Please CheckIn Current Document...");
	}
	return  b;
}	
public void Retore(Double Version,String DocumentUuid)throws Exception
{
	if(DocumentUuid==""||Version==null)
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	byte[] b=null;
	byte[] temp=null;
	byte[] b1=null;
	Integer documenthistorycurrentid=null;
	Integer documenthistorylastid=null;
	Integer documentcurrentid=null;
	Integer documentlastid=null;
	String documenthistorycurrentidauthor=null;
	String documenthistorylastidauthor=null;
	Double currentverion=null;
	Double lastversion=null;
	Date currentdate=null;
	Date lastedate=null;
	DocumentHistory documenthistory=new DocumentHistory();
	ArrayList<DocumentHistory> dh=(ArrayList<DocumentHistory>)commonMethods.documenthistorydao.getIdByDocumentHistory(document.getDocumentId());
	String version=Double.toString(Version);
	for(DocumentHistory s:dh )
		{
			String testversion=Double.toString(s.getVersion());
			if(testversion.startsWith(version))
			{
				b=s.getContent();
				documenthistorycurrentid=s.getDocumentHistoryId();
				documenthistorycurrentidauthor=s.getAuthor();
				documentcurrentid=s.getDocumentId();
				currentverion=s.getVersion();
				currentdate=s.getDate();
			}
			b1=s.getContent();
			documenthistorylastid=s.getDocumentHistoryId();
			documenthistorylastidauthor=s.getAuthor();
			documentlastid=s.getDocumentId();
			lastversion=s.getVersion();
			lastedate=s.getDate();
		}
	temp=b;
	b=b1;
	b1=temp;
	documenthistory.setDocumentHistoryId(documenthistorycurrentid);
	documenthistory.setContent(b);
	documenthistory.setAuthor(documenthistorycurrentidauthor);
	documenthistory.setDocumentId(documentcurrentid);
	documenthistory.setDate(currentdate);
	documenthistory.setVersion(currentverion);
	commonMethods.documenthistorydao.update(documenthistory);
	documenthistory.setDocumentHistoryId(documenthistorylastid);
	documenthistory.setContent(b1);
	documenthistory.setAuthor(documenthistorylastidauthor);
	documenthistory.setDocumentId(documentlastid);
	documenthistory.setDate(lastedate);
	documenthistory.setVersion(lastversion);
	commonMethods.documenthistorydao.update(documenthistory);
}
public void moveDocument(String DocumentUuid,String DestinationFolderCode)throws Exception
{
	if(DocumentUuid==""||DestinationFolderCode=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	if((document.getEditStatus()==0&&document.getLockStatus()==0))
	{
	document.setPath("root:jdsoftdms/"+commonMethods.Path(DestinationFolderCode));
	commonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Document working with user...");
	}
}
public void copyDocument(String DocumentUuid,String DestinationFolderCode)throws Exception
{
	if(DocumentUuid==""||DestinationFolderCode=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	byte[] b=null;
	ArrayList<DocumentHistory> dh=(ArrayList<DocumentHistory>)commonMethods.documenthistorydao.getIdByDocumentHistory(document.getDocumentId());
		for(DocumentHistory s:dh )
		{
			b=s.getContent();
		
		}
		if((document.getEditStatus()==0&&document.getLockStatus()==0))
		{	
			createDocument(document.getDocumentName(), DestinationFolderCode, b);
		}
		else
		{
			throw new Exception("Document working with user...");
		}
}
public void documentAllocation(String DocumentUuid,Integer UserId)throws Exception
{
	if(DocumentUuid==""||UserId==null)
	{
	throw new Exception("Null pointer exception");
	}
	if(commonMethods.UserRole.equals("Admin"))
	{
	Document document=new Document();
	
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	String access=document.getDocumentAccess();
	Users users=new Users();
	users=commonMethods.usersdao.getUserById(UserId);	
		String[] str=access.split("#");
		for(int i=0;i<str.length;i++)
		{
					if(str[i].equals(""))
					{
					}else if(UserId==Integer.valueOf(str[i]))
					{	
					commonMethods.istr=true;
					}
				
		}
		if((users!=null&&document!=null))
		{
					if(commonMethods.istr)
					{
					throw new Exception("Please Check DocumentUuid and UserId...");	
					}	
					else
					{
					document.setDocumentAccess(access+String.valueOf(UserId)+"#");
					commonMethods.documentdao.update(document);	
					}
		}
		else
		{
		throw new Exception("Please Check DocumentUuid and UserId...");
		}
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
}
public void cancelParticularUserAllocation(String DocumentUuid,Integer UserId)throws Exception
{
	if(DocumentUuid==""||UserId==null)
	{
	throw new Exception("Null pointer exception");
	}
	if(commonMethods.UserRole.equals("Admin"))
	{
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	String access=document.getDocumentAccess();
	String str2="";
	String[] str=access.split("#");
	for(int i=0;i<str.length;i++)
	{
		if(str[i].equals(String.valueOf(UserId)))
		{	
			str[i]="";
		}
		
		if(str[i]=="")
		{
		str2+=str[i];
		}
		else
		{
		str2+=str[i]+"#";
		}
	}
	document.setDocumentAccess(str2);
	commonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
}	
public void cancelAllAllocation(String DocumentUuid)throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	if(commonMethods.UserRole.equals("Admin"))
	{
	Document document=new Document();
	document=commonMethods.documentdao.getUuidByDocument(DocumentUuid);
	document.setDocumentAccess("#");
	commonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
}	
public ArrayList<Document> viewAllocatedDocumentsListByUser() throws Exception
{
	if(commonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	Users users=new Users();
	users=commonMethods.usersdao.getUserByName(commonMethods.UsersName);	
	ArrayList<Document> dock1=  (ArrayList<Document>) commonMethods.documentdao.getDocumentList();
	ArrayList<Document> dock2= new ArrayList<Document>();
	for(Document s:dock1 )
	{ 
		String Str="";
		Str=s.getDocumentAccess();
		String[] str1=Str.split("#");
		String str2="";
		for(int i=0;i<str1.length;i++)
		{
			ArrayList<Users> dock=(ArrayList<Users>) commonMethods.usersdao.getUsersList();
			for(Users s1:dock )
			{
				if(str1[i].equals(String.valueOf(users.getUserId()))&&s1.getUserId().equals(users.getUserId()))
				{
					str2=s1.getUserName();
				}
			}
		}	
		if(str2.equals(""))
		{
			commonMethods.istr=true;
		}
		else
		{
		s.setDocumentAccess(str2);
		dock2.add(s);
		commonMethods.istr=false;
		}
	}
	if(commonMethods.istr)
		throw new Exception("No Records Found...");
	
	return dock2 ;
}	
public ArrayList<Document> viewAllocatedDocument() throws Exception
{
	if(commonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	ArrayList<Document> dock1=  getDocumentsList();
	ArrayList<Document> dock2= new ArrayList<Document>();
	for(Document s:dock1 )
	{ String Str="";
		Str=s.getDocumentAccess();
		String[] str1=Str.split("#");
		String str2="";
		for(int i=0;i<str1.length;i++)
		{
			ArrayList<Users> dock= (ArrayList<Users>) commonMethods.usersdao.getUsersList();
			for(Users s1:dock )
			{
				if(str1[i].equals(String.valueOf(s1.getUserId())))
				{
					str2+=s1.getUserName()+",";
				}
			}
		}
		s.setDocumentAccess(str2);
		dock2.add(s);
	}
	return dock2 ;
}
public ArrayList<Users> getUsersList() throws Exception
{
	if(commonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	if(commonMethods.UserRole.equals("Admin"))
	{	
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
	return (ArrayList<Users>) commonMethods.usersdao.getUsersList();
}
public ArrayList<Document> getDocumentsList() throws Exception
{
	if(commonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	if(commonMethods.UserRole.equals("Admin"))
	{
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
	return (ArrayList<Document>) commonMethods.documentdao.getDocumentList();
}
public ArrayList<Folder> getFolersList() throws Exception
{
	if(commonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	if(commonMethods.UserRole.equals("Admin"))
	{	
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
	return (ArrayList<Folder>) commonMethods.folderdao.getFolderList();
}
public ArrayList<Document> getDocumentsListByUser() throws Exception
{
	if(commonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	String Path=null;
	Users users=new Users();
	users=commonMethods.usersdao.getUserByName(commonMethods.UsersName);	
	ArrayList<Folder> dock=  getFolersList();
	for(Folder s:dock )
	{
		if(users.getUserName().equals(s.getFolderName()))
		{
			Path="root:jdsoftdms/"+commonMethods.Path(s.getFolderCode());
		}
	}
	return (ArrayList<Document>) commonMethods.documentdao.getDocumentListByUser(Path);
}
public ArrayList<Folder> getFolersListByUser()throws Exception
{
	if(commonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	return (ArrayList<Folder>) commonMethods.folderdao.getFoldersListByUsers(commonMethods.UsersName);
}
public ArrayList<Document> getDocumentsByFolder(String FolderCode)throws Exception
{
	if(commonMethods.UsersName=="")
	{
	throw new Exception();
	}
	Folder folder=commonMethods.folderdao.getFolderCodeByUser(FolderCode);
	String	Path="root:jdsoftdms/"+commonMethods.Path(folder.getFolderCode());
	return (ArrayList<Document>) commonMethods.documentdao.getPathByDocument(Path);
}
public jdsoftDMS(String UserName,String Password) throws Exception
{	
	if(commonMethods.checkuser())
	{
		Users users=new Users();
		users.setUserName("jdsoftAdmin");
		users.setUserPassword("admin");
		users.setUserEmail("jdsoftAdmin@gmail.com");
		users.setUserStatus(1);
		users.setUserRole("Admin");
		users.setFolderCode("123456");
		commonMethods.usersdao.persist(users);
		Folder folder=new Folder("jdsoftAdmin","123456",new Timestamp(Calendar.getInstance().getTime().getTime()),new Timestamp(Calendar.getInstance().getTime().getTime()),"jdsoftAdmin","jdsoftAdmin",0);
		commonMethods.folderdao.persist(folder);		
		UsersRole userrole=new UsersRole();
		userrole.setRoleName("Admin");
		commonMethods.usersroledao.persist(userrole);
		UsersRole userrole1=new UsersRole();
		userrole1.setRoleName("User");
		commonMethods.usersroledao.persist(userrole1);		
		UsersAccess usersaccess=new UsersAccess();
		usersaccess.setUserControl("#read#write#delete#update#");
		usersaccess.setUserRoleId(1);
		commonMethods.usersaccessdao.persist(usersaccess);
		UsersAccess usersaccess1=new UsersAccess();
		usersaccess1.setUserControl("#read#write#update#");
		usersaccess1.setUserRoleId(2);
		commonMethods.usersaccessdao.persist(usersaccess1);
	}
		if(commonMethods.Login(UserName,Password))
		{
		
		}
		else
		{
			throw new Exception("Invalid Login");
		}
}

public static void main(String[] args) throws Exception {
	jdsoftDMS jds=new jdsoftDMS("jdsoftAdmin", "admin");
}

}
