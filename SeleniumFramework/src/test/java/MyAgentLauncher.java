import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/*
 * @Amresh Tripathy
 */

public class MyAgentLauncher {

	protected static Logger log = Logger.getLogger("MyAgentLauncher.class");
	
	protected static HashMap<String, String> config = new HashMap<String, String>();
	protected static HashMap<String, String> credentials = new HashMap<String, String>();
	protected final static String account_id = "account_id";
	protected final static String account_name = "account_name";
	protected final static String datatype_id = "fk_data_type_id";
	protected final static String datatype_name = "datatype_name";
	protected final static String scheduler_class = "scheduler_class";
	protected final static String processor = "processor";
	protected final static String fk_login_credential_id = "fk_login_credential_id";
	protected final static String isSelenium = "isSelenium";
	protected final static String url = "url";
	protected final static String web = "web";
	protected final static String email = "email";
	static CPA_Selenium cp = new CPA_Selenium();
	
//	public MyAgentLauncher() {
//		try {
//			
////			log.debug("MyAgentLauncher :: Starting the connection to the data base");
////			cp.connect();
////			Class<?> sel = Class.forName("CPA_Selenium");
////			for (Method m : sel.getDeclaredMethods()) {
////				   if(m.getName() == "connect") {
////					   m.invoke(sel, null);
////				   }
////				}
////			Method method = (Method) sel.getConstructor().newInstance();
////			method.
//			
//		}catch(Exception e) {
//			log.error("MyAgentLauncher :: Got exception inside MyAgentLauncher constructor" + e.getMessage(), e);
//		}
//	}
	
	protected void run() {
		try {
			
			if(StringUtils.equalsIgnoreCase(credentials.get(isSelenium), "1") && StringUtils.equalsIgnoreCase(credentials.get(datatype_name), web)) {
				log.debug("MYAgentLauncher :: Web type data type detected");
				log.debug(cp.config_scheduler());
			}else if(StringUtils.equalsIgnoreCase(credentials.get(datatype_name), email)){
				log.debug("MyAgentLauncher :: Email data type detected");
			}
			
		}catch(Exception e) {
			log.error("MyAgentLauncher :: Got exception inside run method" + e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		/*
		 * This is a auto config input.
		 * 
		 * Be Sure of the entries while entering.
		 * 
		 * */
		
		config.put(account_id, "1");
		config.put(datatype_id, "1");
		config.put(scheduler_class, "BrowserTest");
		config.put(processor, "webExecutor");
		log.debug("MyAgentLauncher :: Starting the connection to the data base");
		cp.connect();
		MyAgentLauncher luncher = new MyAgentLauncher();
		luncher.run();
		

	}

}
