package bidplayers;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class UpdateDeleteOperations {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	// function for checking if a player exists or not and deleting the player
	public static String deletePlayer(String pName, HttpSession session) throws EntityNotFoundException {
		long balance = (long) session.getAttribute("Balance");
		long playerPrice;
		long NumPlayers = (long) session.getAttribute("NumPlayers");
		Filter teamFilter = new FilterPredicate("playerName", FilterOperator.EQUAL, pName);
		Query playerQuery = new Query("Players").setFilter(teamFilter);
		PreparedQuery pq = ds.prepare(playerQuery);
		Entity result = pq.asSingleEntity();

		if (result.getProperty("playerName").equals(pName)) {
			playerPrice = (long) result.getProperty("playerPrice");
			balance = balance + playerPrice;
			NumPlayers = NumPlayers - 1;
			ds.delete(result.getKey());
			session.setAttribute("Balance", balance);
			session.setAttribute("NumPlayers", NumPlayers);
			Filter userFilter = new FilterPredicate("UserName", FilterOperator.EQUAL, session.getAttribute("UserName"));
			Query userQuery = new Query("Users").setFilter(userFilter);
			PreparedQuery userpq = ds.prepare(userQuery);
			Entity update = userpq.asSingleEntity();
			update.setProperty("Balance", balance);
			update.setProperty("NumPlayers", NumPlayers);
			ds.put(update);
			return " " + pName + " Deleted Successfully";
		} else {
			return " " + pName + " is not part of our team ";
		}

	}

	// function for fetching player details for updating
	public static Players fetchPlayerDetails(String playerName) {
		String pName = DatabaseOperations.anyCase(playerName);

		// checking whether player name already exists or not in data store
		Filter playerFilter = new FilterPredicate("playerName", FilterOperator.EQUAL, pName);
		Query playerQuery = new Query("Players").setFilter(playerFilter);
		PreparedQuery playerpq = ds.prepare(playerQuery);
		Entity player = playerpq.asSingleEntity();
		String name = (String) player.getProperty("playerName");
		String age = (String) player.getProperty("playerAge");
		long price = (long) player.getProperty("playerPrice");
		Players p = new Players(name, age, price);
		return p;
	}

	// function for updating player details
	public static String updatePlayerDetails(String pName, String pAge, String pPrice) {
		Filter playerFilter = new FilterPredicate("playerName", FilterOperator.EQUAL, pName);
		Query playerQuery = new Query("Players").setFilter(playerFilter);
		PreparedQuery playerpq = ds.prepare(playerQuery);
		Entity player = playerpq.asSingleEntity();
		long playerPrice = (long) player.getProperty("playerPrice");
		long sellingPrice ;
	
		if(pPrice.equals("") || pAge.equals("")) {
			if(pPrice.equals("") && !pAge.equals("")) {
				return "Selling Price can not be null , Enter right value";
			}
			if(pAge.equals("") && !pPrice.equals("")) {
				return "Age can not be null. He is living on this planet for quite some time";
			}if(pAge.equals("") && pPrice.equals("")) {
				return "Why you want to Update, If you can't provide the values ?";
			}
		}
		
		sellingPrice = Long.parseLong(pPrice);
		if(sellingPrice < playerPrice) {
			return "Price is not acceptable to Player";
		}
		 else {
			player.setProperty("playerAge", pAge);
			player.setProperty("SellingPrice", sellingPrice);

			ds.put(player);
			
			return "" + pName + " details successfully updated";
		}

	}
	
	}