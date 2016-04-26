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
	public static String UsersName="";
	public static String FolderCode="";
	public static String Passwords="";
	public static  String FolderName="";
	public static String UserRole="";
	public static Double version;
	public static String location="";
	public static String SFolderName="";
	public static boolean istr=false;
	
	public static ApplicationContext context=ApplicationContextHandler.getApplicationContext();
	public static FolderDAO folderdao=(FolderDAO)context.getBean("folderDAO");
	public static UsersDAO usersdao=(UsersDAO)context.getBean("usersDAO");
	public static UsersAccessDAO usersaccessdao=(UsersAccessDAO)context.getBean("usersAccessDAO");
	public static UsersRoleDAO usersroledao=(UsersRoleDAO)context.getBean("usersRoleDAO");
	public static DocumentDAO documentdao=(DocumentDAO)context.getBean("documentDAO");
	public static DocumentHistoryDAO documenthistorydao=(DocumentHistoryDAO)context.getBean("documentHistoryDAO");
	

	public static String deletePath(String FolderCode)
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


	public static String deleteparentId(Integer parentId)
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
	

	public static String Path(String FolderCode)
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

	public static String parentId(Integer parentId)
	{
		
		Folder folder=new Folder();
		folder=folderdao.findById(parentId);
		return folder.getFolderCode();

	}
	

	public static boolean Login(String UserName, String Password) throws Exception
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

	public static boolean checkuser()
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
