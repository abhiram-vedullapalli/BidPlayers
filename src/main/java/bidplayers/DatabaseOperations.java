package bidplayers;

import java.util.LinkedList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


public class DatabaseOperations {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	//creating a player and storing player data in database
	public static boolean createNewPlayer(String uName , String playerName , String playerAge , HttpSession session){
		
		long salary;
		boolean priceCheck = false;
		do {
			Random r = new Random();
			salary = r.nextInt(15);
			if(salary == 0) {
				priceCheck = false;
			}else {
				priceCheck = true;
			}
		}while(priceCheck == false);
		
		Filter userFilter = new FilterPredicate("UserName",FilterOperator.EQUAL,uName);
		Query userQuery = new Query("Users").setFilter(userFilter);
		PreparedQuery pq = ds.prepare(userQuery);
		Entity result = pq.asSingleEntity();
		String teamName = (String) result.getProperty("TeamName");
		long balance = (long) result.getProperty("Balance");

		if(balance > salary) {
			//incrementing players count for every owner
			long NumPlayers = (long) result.getProperty("NumPlayers");
			NumPlayers = NumPlayers + 1 ;
			session.setAttribute("NumPlayers", NumPlayers);
			
			result.setProperty("NumPlayers", NumPlayers);
			
			//deducting cost of new player and updating balance
			balance = balance - salary;
			session.setAttribute("Balance", balance);
			result.setProperty("Balance", balance);
			ds.put(result);
			
			Entity player = new Entity("Players", playerName);
			player.setProperty("playerName", playerName);
			player.setProperty("playerAge", playerAge);
			player.setProperty("playerTeam", teamName);
			player.setProperty("playerOwner", uName);
			player.setProperty("playerPrice", salary);
			ds.put(player);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	//method for listing players in a particular team
	public static LinkedList<Players> listMyTeam(HttpSession session) {
		int counter = 0;
		LinkedList<Players> playersList = new LinkedList<>();
		Filter teamFilter = new FilterPredicate("playerOwner",FilterOperator.EQUAL,session.getAttribute("UserName"));
		System.out.println("Username in filter predicate " + session.getAttribute("UserName"));
		Query teamQuery = new Query("Players").setFilter(teamFilter);
		PreparedQuery pq = ds.prepare(teamQuery);
		for(Entity players:pq.asIterable()) {
			String name = (String) players.getProperty("playerName");
			String age = (String) players.getProperty("playerAge");
			long price = (long) players.getProperty("playerPrice");
			
			if(name.equals(session.getAttribute("UserName"))) {
				counter ++;
			}
			playersList.add(new Players(name,age,price));
		}
		System.out.println("No of iterations " + counter);
		System.out.println("Players list after full creation " + playersList);
		return playersList;
	}
}
