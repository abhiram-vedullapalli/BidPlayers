package create.player;

import java.util.LinkedList;
import java.util.Random;


import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobKey;
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
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class CreatePlayer {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	static ImagesService imagesService = ImagesServiceFactory.getImagesService();
	static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();

	// creating a player and storing player data in database
	public static String createNewPlayer(String email, String playerName, String playerAge,BlobKey blobKey) throws EntityNotFoundException {
		String pName = anyCase(playerName);
		
		// checking whether player name already exists or not in data store
		Filter playerFilter = new FilterPredicate("playerName", FilterOperator.EQUAL, pName);
		Query playerQuery = new Query("Players").setFilter(playerFilter);
		PreparedQuery playerpq = ds.prepare(playerQuery);
		Entity checkPlayerName = playerpq.asSingleEntity();
		if(checkPlayerName == null) {
			long salary;
			boolean priceCheck = false;
			do {
				Random r = new Random();
				salary = r.nextInt(15);
				if (salary == 0) {
					priceCheck = false;
				} else {
					priceCheck = true;
				}
			} while (priceCheck == false);

			Key resultKey = KeyFactory.createKey("Users", email);
			/*Filter userFilter = new FilterPredicate("Name", FilterOperator.EQUAL, uName);
 			Query userQuery = new Query("Users").setFilter(userFilter);
			PreparedQuery pq = ds.prepare(userQuery);*/
			Entity result = ds.get(resultKey);
			String uName = (String) result.getProperty("Name");
			String teamName = (String) result.getProperty("Team");
			long balance = (long) result.getProperty("Balance");

			if (balance > salary) {
				// incrementing players count for every owner
				long NumPlayers = (long) result.getProperty("NumPlayers");
				NumPlayers = NumPlayers + 1;

				result.setProperty("NumPlayers", NumPlayers);

				// deducting cost of new player and updating balance
				balance = balance - salary;
				result.setProperty("Balance", balance);
				memcache.put(email, result);
				ds.put(result);

				Entity player = new Entity("Players", pName);
				player.setProperty("playerName", pName);
				player.setProperty("playerImage", blobKey);
				player.setProperty("playerAge", playerAge);
				player.setProperty("playerTeam", teamName);
				player.setProperty("playerOwner", uName);
				player.setProperty("playerPrice", salary);
				Key playerKey = KeyFactory.createKey("Players", pName);
				ds.put(player);
				return " " + pName + " Successfully inducted into " + teamName + "";

			} else {
				return "Insufficient Balance. You don't have sufficient funds to induct " + pName + " into " + teamName + "";
			}

		}
		
		else {
			String nameofplayer = (String) checkPlayerName.getProperty("playerName");
			System.out.println("Name from data store in database operations " + nameofplayer);
			return " " + pName + " already exists. Enter New Name";

		}
		
		

	}

	
	
		//case conversion method for handling name cases
	public static String anyCase(String s) {
		char[] c = s.toCharArray();
		for(int i =0; i < s.length(); i++) {
			c[i] = Character.toLowerCase(c[i]);
		}
		c[0] = Character.toUpperCase(s.charAt(0));
		for (int i = 1; i < s.length(); i++) {
			
			if (c[i] == ' ') {
				c[i + 1] = Character.toUpperCase(c[i + 1]);
			}
		}

		return String.valueOf(c);

	}
}
