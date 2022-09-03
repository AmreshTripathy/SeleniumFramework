import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.HashMap;

import org.apache.log4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * @Amresh Tripathy
 */

public class CPA_Selenium extends MyAgentLauncher{
	
	protected final static Base64.Encoder encoder = Base64.getEncoder();
	protected final static Base64.Decoder decoder = Base64.getDecoder();
	protected final static String userId = "userId";
	protected final static String password = "password";
	protected final static String login = "login";
	protected static Logger log = Logger.getLogger("CPA_Selenium.class");
	
	protected HashMap<String, String> connect() {
		Connection con = null;
		Statement s;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:4466/selenium", "root", "3838");
			log.debug("Selenium :: Connection Successful");
			
			s = con.createStatement();
			ResultSet account_statement = s.executeQuery("SELECT * FROM `account` WHERE account_id = " + config.get(account_id));
			if(account_statement.next()) {
				credentials.put(account_name, account_statement.getString("shortName"));
			}
			
			ResultSet datatype_statement = s.executeQuery("SELECT * FROM data_type WHERE data_type_id = " + config.get(datatype_id));
			if (datatype_statement.next()) {
				credentials.put(datatype_name, datatype_statement.getString("shortName"));
			}
			
			ResultSet config_statement = s.executeQuery("SELECT fk_login_credential_id, isSelenium FROM config_scheduler WHERE account_id = " + config.get(account_id) + " AND fk_data_type_id = "+ config.get(datatype_id) +" AND scheduler_class = \"" + config.get(scheduler_class) +"\"");
			if(config_statement.next()) {
				credentials.put(fk_login_credential_id, config_statement.getString(fk_login_credential_id));
				credentials.put(isSelenium, config_statement.getString(isSelenium));
			}
			
			ResultSet login_statement = s.executeQuery("SELECT * FROM login_credential WHERE pk_login_credential_id = " + credentials.get(fk_login_credential_id));
			if(login_statement.next()) {
				credentials.put(url, login_statement.getString(url));
				credentials.put(userId, login_statement.getString(userId));
				credentials.put(password, new String(decoder.decode(login_statement.getString(password))));
			}
			
		}catch(Exception e) {
			log.error("Selenium :: Got error while connecting Database"+ e.getMessage(), e);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				log.error("Selenium :: Unable to close Connection"+ e.getMessage(), e);
			}
		}
	
		return credentials;
	}
	
	protected String config_scheduler() {
		try {
			log.debug("Selenium :: Execution of " + config.get(scheduler_class) + ".class started");
			WebDriverManager.firefoxdriver().setup();
			Class<?> agent = Class.forName(config.get(scheduler_class));
			Object agentInstance = (Object) agent.newInstance();
			Method login_scheduler = agent.getDeclaredMethod(login, String.class);
			login_scheduler.invoke(agentInstance, credentials.get(account_name));
			
		}catch(Exception e) {
			log.error("Selenium :: Exception Occured inside config_scheduler method" + e.getMessage(), e);
		}		
		return "Selenium :: config_scheduler completed successfully for account_id " + config.get(account_id) + " Agent :: " + config.get(scheduler_class);
	}
}
