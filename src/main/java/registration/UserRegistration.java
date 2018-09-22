package registration;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import create.player.CreatePlayer;

public class UserRegistration {

	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	//function for checking if a user name is already taken or not
	
	public static boolean checkUserName(String userName) {
		String uName = CreatePlayer.anyCase(userName);
		

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
	
	public static void userRegistration(String userName, String passWord, String teamName, long balance, long numPlayers,long sold,long profit,HttpSession session) {
		String uName = CreatePlayer.anyCase(userName);
		String pWord = CreatePlayer.anyCase(passWord);
		String tName = CreatePlayer.anyCase(teamName);
		
		Entity userEntity = new Entity("Users", uName);
		userEntity.setProperty("UserName", uName);
		userEntity.setProperty("PassWord", pWord);
		userEntity.setProperty("TeamName", tName);
		userEntity.setProperty("Balance", balance);
		userEntity.setProperty("NumPlayers", numPlayers);
		userEntity.setProperty("Sold",sold );
		userEntity.setProperty("Profit", profit);
		session.setAttribute("UserName", uName);
		session.setAttribute("TeamName", tName);
		session.setAttribute("Balance", balance);
		session.setAttribute("NumPlayers", numPlayers);
		session.setAttribute("Sold", sold);
		session.setAttribute("Profit", profit);
		ds.put(userEntity);
	}
	
	//function for setting session attributes
	public static void setSessionAttributes(HttpSession session) {
		
	}
	
	//function for authenticating user
	public static boolean userLogIn(String userName , String passWord , HttpSession session) {
		String uName = CreatePlayer.anyCase(userName);
		String pWord = CreatePlayer.anyCase(passWord);
		Filter userFilter = new FilterPredicate("UserName", FilterOperator.EQUAL, uName);
		try {
			Query query = new Query("Users").setFilter(userFilter);
			PreparedQuery pq = ds.prepare(query);
			Entity result = pq.asSingleEntity();
			if (result.getProperty("UserName").equals(uName) && result.getProperty("PassWord").equals(pWord)) {
				String tName = (String) result.getProperty("TeamName");
				long balance = (long) result.getProperty("Balance");
				long numPlayers = (long) result.getProperty("NumPlayers");
				long sold = (long) result.getProperty("Sold");
				long profit = (long) result.getProperty("Profit");
				session.setAttribute("UserName", uName);
				session.setAttribute("TeamName", tName);
				session.setAttribute("Balance", balance);
				session.setAttribute("NumPlayers", numPlayers);
				session.setAttribute("Sold", sold);
				session.setAttribute("Profit", profit);
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
}
}
