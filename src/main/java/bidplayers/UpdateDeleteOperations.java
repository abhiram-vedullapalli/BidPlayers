package bidplayers;

import javax.servlet.http.HttpSession;

import org.junit.runner.FilterFactoryParams;

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
			balance = balance + playerPrice ;
			NumPlayers = NumPlayers - 1;
			ds.delete(result.getKey());
			session.setAttribute("Balance", balance);
			session.setAttribute("NumPlayers", NumPlayers);
			Filter userFilter = new FilterPredicate("UserName",FilterOperator.EQUAL,session.getAttribute("UserName"));
			Query userQuery = new Query("Users").setFilter(userFilter);
			PreparedQuery userpq = ds.prepare(userQuery);
			Entity update = userpq.asSingleEntity();
			update.setProperty("Balance", balance);
			update.setProperty("NumPlayers", NumPlayers);
			ds.put(update);
			return " " + pName + " Deleted Successfully";
		}
		else {
			return " " + pName + " is not part of our team ";
		}

	}
}
