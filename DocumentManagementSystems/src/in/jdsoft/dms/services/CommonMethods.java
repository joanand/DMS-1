package in.jdsoft.dms.services;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

import in.jdsoft.dms.dao.DocumentDAO;
import in.jdsoft.dms.dao.DocumentHistoryDAO;
import in.jdsoft.dms.dao.FolderDAO;
import in.jdsoft.dms.dao.UsersAccessDAO;
import in.jdsoft.dms.dao.UsersDAO;
import in.jdsoft.dms.dao.UsersRoleDAO;
import in.jdsoft.dms.model.Folder;
import in.jdsoft.dms.model.Users;


public  class CommonMethods 
{
	public  String UsersName="";
	public  String FolderCode="";
	public  String Passwords="";
	public   String FolderName="";
	public  String UserRole="";
	public  Double version;
	public  String location="";
	public  String SFolderName="";
	public  boolean istr=false;
	
	public  ApplicationContext context=ApplicationContextHandler.getApplicationContext();
	public  FolderDAO folderdao=(FolderDAO)context.getBean("folderDAO");
	public  UsersDAO usersdao=(UsersDAO)context.getBean("usersDAO");
	public  UsersAccessDAO usersaccessdao=(UsersAccessDAO)context.getBean("usersAccessDAO");
	public  UsersRoleDAO usersroledao=(UsersRoleDAO)context.getBean("usersRoleDAO");
	public  DocumentDAO documentdao=(DocumentDAO)context.getBean("documentDAO");
	public  DocumentHistoryDAO documenthistorydao=(DocumentHistoryDAO)context.getBean("documentHistoryDAO");
	

	public  String deletePath(String FolderCode)
	{

		Folder folder=new Folder();
		
		folder=folderdao.getFolderCodeByUser(FolderCode);
		
		 
		 if(!(folder==null))
		{
			 
			 FolderName=folder.getFolderCode()+"/"+FolderName;
			 folder.setFolderCode(deleteparentId(folder.getFolderId()));
			 deletePath(folder.getFolderCode());
			
		
		}
		 else
		 {
		 SFolderName=FolderName;
		 FolderName="";
		 }
		return SFolderName;
	}


	public  String deleteparentId(Integer parentId)
	{
		
		Folder folder=new Folder();
		Folder folder1=new Folder();
		folder1.setFolderCode(null);
		folder=folderdao.FolderId(parentId);
		if(folder!=null)
		{
			
		}
		else
		{
			folder=folder1;
			
		}
		return folder.getFolderCode();

	}
	

	public  String Path(String FolderCode)
	{
		
		Folder folder=new Folder();
		folder=folderdao.getFolderCodeByUser(FolderCode);
		if(folder.getParentFolderId()!=0)
		{
			
		FolderName=folder.getFolderName()+"/"+FolderName;		
	    folder.setFolderCode(parentId(folder.getParentFolderId()));	
		Path(folder.getFolderCode());

		} 
		else
		{
			
		FolderName=folder.getFolderName()+"/"+FolderName;	
		SFolderName=FolderName;
		FolderName="";

		}

		return SFolderName;
		
	}

	public  String parentId(Integer parentId)
	{
		
		Folder folder=new Folder();
		folder=folderdao.findById(parentId);
		return folder.getFolderCode();

	}
	

	public  boolean Login(String UserName, String Password) throws Exception
	{
		boolean isValid=false;
		Users user=usersdao.getUserByName(UserName);
		if(user.getUserPassword().equals(Password)&&(user.getUserStatus()==1))
		{
			isValid=true; 
			UsersName=UserName;
			Passwords=Password;
			UserRole=user.getUserRole();
			FolderCode=user.getFolderCode();
		}
		else if(!user.getUserPassword().equals(Password)){
			isValid=false;
		}
		return isValid;
	}

	public  boolean checkuser()
	{
		boolean isValid=false;
	
		ArrayList<Users> dock= (ArrayList<Users>)usersdao.getUsers();
		
		if(dock.size()==0)
		{
			isValid=true;
		}
		else
		{
			isValid=false;
		}
		
		return isValid;
	}
	
	
}
