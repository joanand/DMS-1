package in.jdsoft.dms.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ApplicationContextHandler{

	ApplicationContext appContext;
	
	/*
	private static ApplicationContext appContext;*/

	
	/*public void setApplicationContext(ApplicationContext context) throws BeansException {
		appContext=context;
	}*/

	private static ApplicationContextHandler ctxHandler=new ApplicationContextHandler();
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

	private ApplicationContextHandler() {
		// TODO Auto-generated constructor stub
	}
	public static ApplicationContextHandler getInstance(){
		return ctxHandler;
	}
	
	public static ApplicationContext getApplicationContext(){
		return ctx;
	}
}


