package bidplayers;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class UserRegistration {

	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	//function for checking if a user name is already taken or not
	
	public static boolean checkUserName(String userName) {
		String uName = DatabaseOperations.anyCase(userName);
		

		Filter userFilter = new FilterPredicate("UserName", FilterOperator.EQUAL, uName);
		try {
			Query query = new Query("Users").setFilter(userFilter);
			PreparedQuery pq = ds.prepare(query);
			Entity result = pq.asSingleEntity();
			if (result.getProperty("UserName").equals(uName)) {

				return true;
			} else {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}

	}

	//function for registering user details in database
	
	public static void userRegistration(String userName, String passWord, String teamName, long balance, long numPlayers,HttpSession session) {
		String uName = DatabaseOperations.anyCase(userName);
		String pWord = DatabaseOperations.anyCase(passWord);
		String tName = DatabaseOperations.anyCase(teamName);
		
		Entity userEntity = new Entity("Users", uName);
		userEntity.setProperty("UserName", uName);
		userEntity.setProperty("PassWord", pWord);
		userEntity.setProperty("TeamName", tName);
		userEntity.setProperty("Balance", balance);
		userEntity.setProperty("NumPlayers", numPlayers);
		session.setAttribute("UserName", uName);
		session.setAttribute("TeamName", tName);
		session.setAttribute("Balance", balance);
		session.setAttribute("NumPlayers", numPlayers);
		ds.put(userEntity);
	}
	
	//function for setting session attributes
	public static void setSessionAttributes(HttpSession session) {
		
	}
	
	//function for authenticating user
	public static boolean userLogIn(String userName , String passWord , HttpSession session) {
		String uName = DatabaseOperations.anyCase(userName);
		String pWord = DatabaseOperations.anyCase(passWord);
		Filter userFilter = new FilterPredicate("UserName", FilterOperator.EQUAL, uName);
		try {
			Query query = new Query("Users").setFilter(userFilter);
			PreparedQuery pq = ds.prepare(query);
			Entity result = pq.asSingleEntity();
			if (result.getProperty("UserName").equals(uName) && result.getProperty("PassWord").equals(pWord)) {
				String tName = (String) result.getProperty("TeamName");
				long balance = (long) result.getProperty("Balance");
				long numPlayers = (long) result.getProperty("NumPlayers");
				session.setAttribute("UserName", uName);
				session.setAttribute("TeamName", tName);
				session.setAttribute("Balance", balance);
				session.setAttribute("NumPlayers", numPlayers);
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
}
}
