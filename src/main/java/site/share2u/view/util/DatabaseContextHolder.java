package site.share2u.view.util;

/**
 * 内部使用threadlocal来保证线程安全
 * @author CWM 2017年11月8日
 * 
 */
public class DatabaseContextHolder {
	private static final ThreadLocal<String> contextHolder =  new ThreadLocal<String>();

	public static void setDatabaseContext(String databaseContext){
		contextHolder.set(databaseContext);
	}
	
	public static String getDatabaseContext(){
		return contextHolder.get();
	}
	
	public void clearDatabaseContext(){
		contextHolder.remove();
	}
	

	
}
