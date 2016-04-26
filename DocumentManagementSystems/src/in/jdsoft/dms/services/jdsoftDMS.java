package in.jdsoft.dms.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.jdsoft.dms.model.*;


import in.jdsoft.dms.services.CommonMethods;

@WebService
public class jdsoftDMS 
{
	

	@WebMethod
public void createFolder(String FolderName)throws Exception
{
	if(FolderName=="")
	{
	throw new Exception("Null pointer exception");
	}
	
    String RootFolderName=CommonMethods.UsersName;   
    Folder folder1=new Folder();
    if(CommonMethods.UsersName.equals("jdsoftAdmin"))
	{
    	folder1=CommonMethods.folderdao.getFolderDetailsByFolderName(RootFolderName,0);		
	}
    else 
	{
    	folder1=CommonMethods.folderdao.getFolderCodeByUser(CommonMethods.FolderCode);
		
	}
	
	String[] str=FolderName.split("/");
	String RootFolderCode=null;
	for (int i=0;i<str.length;i++)
	{
	
	Random rand=new Random();
	Integer d=rand.nextInt(999999999);
	Integer parentFolderId=folder1.getFolderId();
	Folder folder=new Folder(str[i].toString(),String.valueOf(d),new Timestamp(Calendar.getInstance().getTime().getTime()),new Timestamp(Calendar.getInstance().getTime().getTime()),CommonMethods.UsersName,CommonMethods.UsersName,parentFolderId);
	CommonMethods.folderdao.persist(folder);
	RootFolderCode=String.valueOf(d);
	folder1=CommonMethods.folderdao.getFolderCodeByUser(RootFolderCode);	
	}

}


	@WebMethod
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
	folder1=CommonMethods.folderdao.getFolderCodeByUser(RootFolderCode);		
	Integer parentFolderId=folder1.getFolderId();	
	Folder folder=new Folder(str[i].toString(),String.valueOf(d),new Timestamp(Calendar.getInstance().getTime().getTime()),new Timestamp(Calendar.getInstance().getTime().getTime()),CommonMethods.UsersName,CommonMethods.UsersName,parentFolderId);
	CommonMethods.folderdao.persist(folder);
	RootFolderCode=String.valueOf(d);
	}
}

	@WebMethod
public void deleteFolder(String FolderCode)throws Exception
{
	if(FolderCode=="")
	{
	throw new Exception("Null pointer exception");
	}
	Folder folder=new Folder();
	DocumentHistory documenthistory=new DocumentHistory();
	String str=CommonMethods.deletePath(FolderCode);
	String[] str1=str.split("/");
	for(int i=0;i<str1.length;i++)
	{
		folder=CommonMethods.folderdao.getFolderCodeByUser(str1[i].toString());
		ArrayList<Document> dock1= (ArrayList<Document>)CommonMethods.documentdao.getPathByDocument("root:jdsoftdms/"+CommonMethods.Path(folder.getFolderCode()));
		
		if(folder!=null)
		{
			for(Document s1:dock1 )
			{
				
				if(s1!=null)
				{		
					documenthistory=CommonMethods.documenthistorydao.getNameByDocumentHistory(s1.getDocumentId());
					CommonMethods.documenthistorydao.delete(documenthistory);
					CommonMethods.documentdao.delete(s1);
				
				}
			}
			CommonMethods.folderdao.delete(folder);
		
		}
	}
	
}

	@WebMethod
public void renameFolder(String OldFolderCode,String NewFolderName)throws Exception
{
	if(OldFolderCode==""||NewFolderName=="")
	{
	throw new Exception("Null pointer exception");
	}
	Folder folder=new Folder();
	folder=CommonMethods.folderdao.getFolderCodeByUser(OldFolderCode);	
	
	String curefolder="root:jdsoftdms/"+CommonMethods.Path(OldFolderCode);
	 
	ArrayList<Document> dock= (ArrayList<Document>)CommonMethods.documentdao.getPathByDocument(curefolder);
	
	curefolder=curefolder.replace(folder.getFolderName(), NewFolderName);
	
	folder.setFolderName(NewFolderName);
	folder.setModifiedBy(CommonMethods.UsersName);
	folder.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));

	for(Document s:dock )
	{
		
		s.setPath(curefolder);
		CommonMethods.documentdao.update(s);
	}
	
	CommonMethods.folderdao.update(folder);
	
}

	@WebMethod
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

	document.setPath("root:jdsoftdms/"+CommonMethods.Path(FolderCode));
	document.setDocumentAccess("#");

	CommonMethods.documentdao.persist(document);
	
	document1=CommonMethods.documentdao.getUuidByDocument(String.valueOf(d));
	documenthistory.setAuthor(CommonMethods.UsersName);
	documenthistory.setDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
	documenthistory.setDocumentId(document1.getDocumentId());
	documenthistory.setVersion(1.0);
	documenthistory.setContent(bytes);
	
	CommonMethods.documenthistorydao.persist(documenthistory);
	
}


	@WebMethod
public void renameDocument(String NewDocumentName, String DocumentUuid) throws Exception
{
	if(NewDocumentName==""||DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
	document.setDocumentName(NewDocumentName);
	CommonMethods.documentdao.update(document);	
	
	
}



	@WebMethod
public void deleteDocument(String DocumentUuid)throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
	DocumentHistory documenthistory=new DocumentHistory();
	
	documenthistory=CommonMethods.documenthistorydao.getNameByDocumentHistory(document.getDocumentId());
	CommonMethods.documentdao.delete(document);
	CommonMethods.documenthistorydao.delete(documenthistory);
	
}

	@WebMethod
public void createUser(String userName, String userPassword, String userEmail, Integer userStatus, String userRole) throws Exception
{
	if(userName==""||userPassword=="")
	{
	throw new Exception("Null pointer exception");
	}
	if(CommonMethods.UserRole.equals("Admin"))
	{

		if(	(userRole.equals("Admin")||userRole.equals("User"))&&(userStatus.equals(1)||userStatus.equals(0)))
		{
				Users users=new Users();
				users.setUserName(userName);
				users.setUserPassword(userPassword);
				users.setUserEmail(userEmail);
				users.setUserStatus(userStatus);
				users.setUserRole(userRole);				
				CommonMethods.usersdao.persist(users);				
				createFolder(userName);
				Folder folder=CommonMethods.folderdao.getFolderDetailsByUserName(userName);
				users.setFolderCode(folder.getFolderCode());
				CommonMethods.usersdao.update(users);
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


	@WebMethod
public void deleteUser(String UserName) throws Exception
{
	if(CommonMethods.UserRole.equals("Admin"))
	{
	Users users=new Users();
	users=CommonMethods.usersdao.getUserByName(UserName);
	CommonMethods.usersdao.delete(users);
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
}


	@WebMethod
public void lockDocument(String DocumentUuid) throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
	
	if(document.getLockStatus()==0&&document.getEditStatus()==0)
	{
		document.setLockStatus(1);
		document.setLockInformation(CommonMethods.UsersName);
		CommonMethods.documentdao.update(document);
	}
	else if((document.getLockStatus()==0&&document.getEditStatus()==0)&&CommonMethods.UserRole.equals("Admin"))
	{
		document.setLockStatus(1);
		document.setLockInformation(CommonMethods.UsersName);
		CommonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Document locked by another user...");
	}
	
}

	@WebMethod
public void unlockDocument(String DocumentUuid) throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
	

	if((document.getLockStatus()==1) && (document.getLockInformation().equals(CommonMethods.UsersName)))
	{
		document.setLockStatus(0);
		document.setLockInformation(null);
		CommonMethods.documentdao.update(document);
	}
	else if(CommonMethods.UserRole.equals("Admin")&&document.getLockStatus()==1)
	{
		document.setLockStatus(0);
		document.setLockInformation(null);
		CommonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Document locked by another user...");	
	}
	
}

	@WebMethod
 public void checkIn(String DocumentUuid) throws Exception
 {

	 if(DocumentUuid=="")
		{
		throw new Exception("Null pointer exception");
		}
		Document document=new Document();
		document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
		
		if(document.getEditStatus()==0&&document.getLockStatus()==0)
		{
			document.setEditStatus(1);
			document.setEditInformation(CommonMethods.UsersName);
			CommonMethods.documentdao.update(document);
		}
		else
		{
			throw new Exception("Current document edit with other user...");
		}
 }


	@WebMethod
public void checkOut(String DocumentUuid,byte[] bytes) throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
	
	DocumentHistory documenthistory=new DocumentHistory();
	ArrayList<DocumentHistory> dh=(ArrayList<DocumentHistory>)CommonMethods.documenthistorydao.getIdByDocumentHistory(document.getDocumentId());
	for(DocumentHistory s:dh )
		{
		CommonMethods.version=s.getVersion();
		
		}

	
	if((document.getEditStatus()==1)&& (document.getEditInformation().equals(CommonMethods.UsersName)))
	{
		document.setEditStatus(0);
		document.setEditInformation(null);
		
		documenthistory.setDocumentId(document.getDocumentId());
		documenthistory.setContent(bytes);
		documenthistory.setAuthor(CommonMethods.UsersName);
		documenthistory.setDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
	
		documenthistory.setVersion(CommonMethods.version+0.1);
		
		CommonMethods.documentdao.update(document);
		CommonMethods.documenthistorydao.persist(documenthistory);
		
	}
	else
	{
		throw new Exception("Document already checkout..");	
		
	}
}


	@WebMethod
public void cancelCheckIn(String DocumentUuid) throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
	
	if(document.getEditStatus()==1&&CommonMethods.UserRole.equals("Admin"))
	{
		document.setEditStatus(0);
		document.setEditInformation(null);
		CommonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
}

	@WebMethod
public byte[] getContent(String DocumentUuid) throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);	
	byte[] b=null;
	if(document.getEditStatus()==1)
	{
		
	ArrayList<DocumentHistory> dh=(ArrayList<DocumentHistory>)CommonMethods.documenthistorydao.getIdByDocumentHistory(document.getDocumentId());
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


	@WebMethod
public void Retore(Double Version,String DocumentUuid)throws Exception
{
	if(DocumentUuid==""||Version==null)
	{
	throw new Exception("Null pointer exception");
	}
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
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
	ArrayList<DocumentHistory> dh=(ArrayList<DocumentHistory>)CommonMethods.documenthistorydao.getIdByDocumentHistory(document.getDocumentId());
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
	CommonMethods.documenthistorydao.update(documenthistory);
	documenthistory.setDocumentHistoryId(documenthistorylastid);
	documenthistory.setContent(b1);
	documenthistory.setAuthor(documenthistorylastidauthor);
	documenthistory.setDocumentId(documentlastid);
	documenthistory.setDate(lastedate);
	documenthistory.setVersion(lastversion);
	CommonMethods.documenthistorydao.update(documenthistory);
}


	@WebMethod
public void moveDocument(String DocumentUuid,String DestinationFolderCode)throws Exception
{
	if(DocumentUuid==""||DestinationFolderCode=="")
	{
	throw new Exception("Null pointer exception");
	}
	
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
	if((document.getEditStatus()==0&&document.getLockStatus()==0))
	{
	document.setPath("root:jdsoftdms/"+CommonMethods.Path(DestinationFolderCode));
	CommonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Document working with user...");
	}
}
	
	@WebMethod
public void copyDocument(String DocumentUuid,String DestinationFolderCode)throws Exception
{
	if(DocumentUuid==""||DestinationFolderCode=="")
	{
	throw new Exception("Null pointer exception");
	}
	
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
	byte[] b=null;
	ArrayList<DocumentHistory> dh=(ArrayList<DocumentHistory>)CommonMethods.documenthistorydao.getIdByDocumentHistory(document.getDocumentId());
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

	@WebMethod
public void documentAllocation(String DocumentUuid,Integer UserId)throws Exception
{
	if(DocumentUuid==""||UserId==null)
	{
	throw new Exception("Null pointer exception");
	}
	if(CommonMethods.UserRole.equals("Admin"))
	{
	Document document=new Document();
	
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
	String access=document.getDocumentAccess();
	Users users=new Users();
	users=CommonMethods.usersdao.getUserById(UserId);	

		String[] str=access.split("#");
		for(int i=0;i<str.length;i++)
		{
				
					if(str[i].equals(""))
					{
					}else if(UserId==Integer.valueOf(str[i]))
					{
						
					CommonMethods.istr=true;
				
					}
				
		}
		if((users!=null&&document!=null))
		{
			
					if(CommonMethods.istr)
					{
					throw new Exception("Please Check DocumentUuid and UserId...");
						
					}	
					else
					{
					document.setDocumentAccess(access+String.valueOf(UserId)+"#");
					CommonMethods.documentdao.update(document);
						
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


	@WebMethod
public void cancelParticularUserAllocation(String DocumentUuid,Integer UserId)throws Exception
{
	if(DocumentUuid==""||UserId==null)
	{
	throw new Exception("Null pointer exception");
	}
	if(CommonMethods.UserRole.equals("Admin"))
	{
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
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
	CommonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}
}


	@WebMethod
public void cancelAllAllocation(String DocumentUuid)throws Exception
{
	if(DocumentUuid=="")
	{
	throw new Exception("Null pointer exception");
	}
	if(CommonMethods.UserRole.equals("Admin"))
	{
	Document document=new Document();
	document=CommonMethods.documentdao.getUuidByDocument(DocumentUuid);
	document.setDocumentAccess("#");
	CommonMethods.documentdao.update(document);
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}

}


	@WebMethod
public ArrayList<Document> viewAllocatedDocumentsListByUser() throws Exception
{
	if(CommonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	Users users=new Users();
	users=CommonMethods.usersdao.getUserByName(CommonMethods.UsersName);	
	ArrayList<Document> dock1=  (ArrayList<Document>) CommonMethods.documentdao.getDocumentList();
	ArrayList<Document> dock2= new ArrayList<Document>();
	for(Document s:dock1 )
	{ 
		String Str="";
		Str=s.getDocumentAccess();
		String[] str1=Str.split("#");
		String str2="";
		for(int i=0;i<str1.length;i++)
		{
			ArrayList<Users> dock=(ArrayList<Users>) CommonMethods.usersdao.getUsersList();
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
			CommonMethods.istr=true;
		}
		else
		{
		s.setDocumentAccess(str2);
		dock2.add(s);
		CommonMethods.istr=false;
		}
	
	}
	if(CommonMethods.istr)
		throw new Exception("No Records Found...");
	
	
	return dock2 ;
}

	@WebMethod
public ArrayList<Document> viewAllocatedDocument() throws Exception
{
	if(CommonMethods.UsersName=="")
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
			ArrayList<Users> dock= (ArrayList<Users>) CommonMethods.usersdao.getUsersList();
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

	@WebMethod
public ArrayList<Users> getUsersList() throws Exception
{
	if(CommonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	if(CommonMethods.UserRole.equals("Admin"))
	{
		
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}

	return (ArrayList<Users>) CommonMethods.usersdao.getUsersList();

}


	@WebMethod
public ArrayList<Document> getDocumentsList() throws Exception
{
	if(CommonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	if(CommonMethods.UserRole.equals("Admin"))
	{
		
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}

	return (ArrayList<Document>) CommonMethods.documentdao.getDocumentList();

}

	@WebMethod
public ArrayList<Folder> getFolersList() throws Exception
{
	if(CommonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	if(CommonMethods.UserRole.equals("Admin"))
	{
		
	}
	else
	{
		throw new Exception("Users Cannot Access These Method");
	}

	
	return (ArrayList<Folder>) CommonMethods.folderdao.getFolderList();

}


@WebMethod
public ArrayList<Document> getDocumentsListByUser() throws Exception
{
	if(CommonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	String Path=null;
	Users users=new Users();
	users=CommonMethods.usersdao.getUserByName(CommonMethods.UsersName);	
	ArrayList<Folder> dock=  getFolersList();
	for(Folder s:dock )
	{
	
	if(users.getUserName().equals(s.getFolderName()))
	{
	
	 Path="root:jdsoftdms/"+CommonMethods.Path(s.getFolderCode());
	}
	}

	return (ArrayList<Document>) CommonMethods.documentdao.getDocumentListByUser(Path);

}


@WebMethod
public ArrayList<Folder> getFolersListByUser()throws Exception
{
	if(CommonMethods.UsersName=="")
	{
	throw new Exception("Null pointer exception");
	}
	return (ArrayList<Folder>) CommonMethods.folderdao.getFoldersListByUsers(CommonMethods.UsersName);

}

@WebMethod
public ArrayList<Document> getDocumentsByFolder(String FolderCode)throws Exception
{
	if(CommonMethods.UsersName=="")
	{
	throw new Exception();
	}
	
	Folder folder=CommonMethods.folderdao.getFolderCodeByUser(FolderCode);
	String	Path="root:jdsoftdms/"+CommonMethods.Path(folder.getFolderCode());
	
	
	return (ArrayList<Document>) CommonMethods.documentdao.getPathByDocument(Path);

}

public jdsoftDMS(String UserName,String Password) throws Exception
{
	
	
	if(CommonMethods.checkuser())
	{
		Users users=new Users();
		users.setUserName("jdsoftAdmin");
		users.setUserPassword("admin");
		users.setUserEmail("jdsoftAdmin@gmail.com");
		users.setUserStatus(1);
		users.setUserRole("Admin");
		users.setFolderCode("123456");
		CommonMethods.usersdao.persist(users);
		Folder folder=new Folder("jdsoftAdmin","123456",new Timestamp(Calendar.getInstance().getTime().getTime()),new Timestamp(Calendar.getInstance().getTime().getTime()),"jdsoftAdmin","jdsoftAdmin",0);
		CommonMethods.folderdao.persist(folder);
		
		UsersRole userrole=new UsersRole();
		userrole.setRoleName("Admin");
		CommonMethods.usersroledao.persist(userrole);
		UsersRole userrole1=new UsersRole();
		userrole1.setRoleName("User");
		CommonMethods.usersroledao.persist(userrole1);
		
		UsersAccess usersaccess=new UsersAccess();
		usersaccess.setUserControl("#read#write#delete#update#");
		usersaccess.setUserRoleId(1);
		CommonMethods.usersaccessdao.persist(usersaccess);
		UsersAccess usersaccess1=new UsersAccess();
		usersaccess1.setUserControl("#read#write#update#");
		usersaccess1.setUserRoleId(2);
		CommonMethods.usersaccessdao.persist(usersaccess1);
	}
	
		if(CommonMethods.Login(UserName,Password))
		{
		
		}
		else
		{
			throw new Exception("Invalid Login");
		}
	
	
		
		
}

//public static void main(String[] args) throws Exception
//{
//	
//	jdsoftDMS tc=new jdsoftDMS("t3","t3");
//
////	tc.deleteDocument("861660884");
//	//tc.createUser("t3", "t3", "test@gmail.com",1, "User");
//	//tc.deleteUser("t3");
//	//tc.createFolder("t2/t2");
//	//tc.createSubFolder("703191490", "t2");
////tc.cancelCheckIn("140105106");
//	//tc.unlockDocument("140105106");
////tc.cancelParticularUserAllocation("707938647", 6);
////tc.cancelAllAllocation("707938647");
//	//tc.deleteFolder("770995632");
//	//tc.lockDocument("140105106");
//	//tc.renameFolder("330993825","jos");
//	//tc.checkIn("140105106");
//	//tc.moveDocument("140105106","607358698");
////tc.documentAllocation("707938647", 6);
////tc.removeAllocation("707938647");
////	tc.Retore(1.2, "test");
//	//tc.deleteFolder("200481242");
////	tc.Retore(1.2,"93583532");
////	tc.checkIn("test");
////tc.deleteDocument("74979319");
////	
////	tc.renameDocument("j2", "140105106");
////
//	//String[] str={"140105106"};
//
////	for(int i=0;i<str.length;i++)
////	{
//	//	tc.documentAllocation("292685699",11);
//	//	tc.cancelAllAllocation(str[i]);
//		//tc.cancelParticularUserAllocation(str[i], 6);
//	//}
//	
//	FileInputStream fileInputStream=null;
//    
//	
//    File file = new File("C:\\Users\\administrator\\Documents\\Floor3-BOQ.pdf");
// // File file = new File("C:\\Users\\administrator\\Documents\\beginner_printable.pdf");
//    
//    byte[] bFile = new byte[(int) file.length()];
//    
//    try {
//        //convert file into array of bytes
//    fileInputStream = new FileInputStream(file);
//    fileInputStream.read(bFile);
//    fileInputStream.close();
//       
//    for (int i = 0; i < bFile.length; i++) {
//     //  	System.out.print((char)bFile[i]);
//        }
//		
//   // System.out.println("Done");
//    }catch(Exception e){
//    	e.printStackTrace();
//    }
////	
////tc.createDocument("jo","457130755",bFile );
////  tc.createDocument("jo","703191490", bFile);
////  tc.createDocument("jo","239492758", bFile);
////  tc.createDocument("jo","605722109", bFile);
//   
//
//   
////tc.checkOut("140105106", bFile);
//	
//	//tc.checkOut("93583532", bFile);
////	
////	tc.checkIn("93583532");
////	
//	//tc.Retore(1.3,"140105106" );
//    
////    byte[] test=tc.getContent("140105106");      
////   try
////   {
////    FileOutputStream fos = new FileOutputStream("C:\\Users\\administrator\\Documents\\testjo1.pdf");
////    
////  fos.write(test);
//// fos.close();
////  }
////   catch(Exception e)
////  {
////  	
////   }
////    
////	tc.createDocument("test", "kumar", bFile);
//    
//    
////	tc.createDocument("kjkj","anand", null);
////	tc.renameFolder("kumar", "kumarh");
////	tc.lockDocument("kjkj");
////	
//	//tc.unlockDocument("kjkj");
//	
//	
////	tc.createUser("jeeva1", "jeeva1", "jeeva1@gmail.com", 1, "Admin");
//	
////	ArrayList<Users> dock=  tc.getUsersList();
////	for(Users s:dock )
////	{
////	System.out.println(s.getUserName());
////	}
////
//   
////	ArrayList<Document> dock1=  tc.getDocumentsList();
////	for(Document s:dock1 )
////	{ String Str="";
////		Str=s.getDocumentAccess();
////		String[] str1=Str.split("#");
////		String str2="";
////		for(int i=0;i<str1.length;i++)
////		{
////			ArrayList<Users> dock=  tc.getUsersList();
////			for(Users s1:dock )
////			{
////				if(str1[i].equals(String.valueOf(s1.getUserId())))
////				{
////					str2+=s1.getUserName()+",";
////				}
////			}
////		}
////		System.out.println(s.getDocumentName()+"\t"+s.getDocumentUuid()+"\t"+str2);
////	}
//
////    
////	ArrayList<Document> dock=  tc.viewAllocatedDocumentsListByUser();
////	for(Document s:dock )
////	{
////	System.out.println(s.getDocumentName()+"\t"+s.getDocumentUuid()+"\t"+s.getDocumentAccess());
////	}
//
////
////	ArrayList<Folder> dock2=  tc.getFolersListByUser();
////	for(Folder s:dock2 )
////	{
////	System.out.println(s.getFolderName());
////	}
//// 
//
//}

}
