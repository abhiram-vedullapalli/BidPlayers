package updatedelete.player;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import create.player.CreatePlayer;
import create.player.Players;

public class UpdateDeleteOperations {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	// function for checking if a player exists or not and deleting the player
	public static String deletePlayer(String pName) throws EntityNotFoundException {
		long balance , playerPrice , NumPlayers;
		Key playerKey = KeyFactory.createKey("Players", pName);
		Entity result;
		try {
			result = ds.get(playerKey);
			String playerOwner = (String) result.getProperty("playerOwner");
			playerPrice = (long) result.getProperty("playerPrice");
			
			Filter userFilter = new FilterPredicate("Name", FilterOperator.EQUAL,playerOwner );
			Query userQuery = new Query("Users").setFilter(userFilter);
			PreparedQuery userpq = ds.prepare(userQuery);
			Entity update = userpq.asSingleEntity();
			balance = (long) update.getProperty("Balance");
			NumPlayers = (long) update.getProperty("NumPlayers");
			balance = balance + playerPrice;
			NumPlayers = NumPlayers - 1;
			update.setProperty("Balance", balance);
			update.setProperty("NumPlayers", NumPlayers);
			memcache.put(update.getKey().getName(), update);
			ds.put(update);
			
			ds.delete(playerKey);
			return " " + pName + " Deleted Successfully";

		} catch (EntityNotFoundException e) {
			return " " + pName + " is not part of our team ";

		}
	
	}

	

	// function for fetching player details for updating
	public static Players fetchPlayerDetails(String playerName) {
		String pName = CreatePlayer.anyCase(playerName);

		// checking whether player name already exists or not in data store
		Filter playerFilter = new FilterPredicate("playerName", FilterOperator.EQUAL, pName);
		Query playerQuery = new Query("Players").setFilter(playerFilter);
		PreparedQuery playerpq = ds.prepare(playerQuery);
		Entity player = playerpq.asSingleEntity();
		String name = (String) player.getProperty("playerName");
		String age = (String) player.getProperty("playerAge");
		long price = (long) player.getProperty("playerPrice");
		Players p = new Players(name,null, age, price);
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