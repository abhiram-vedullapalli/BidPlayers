package list.player;

import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import create.player.Players;

public class ListPlayers {
	
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	static ImagesService imagesService = ImagesServiceFactory.getImagesService();

	// method for listing players in a particular team
		public static LinkedList<Players> listMyTeam(String userName) {
			
			LinkedList<Players> playersList = new LinkedList<>();
			//Filter teamFilter = new FilterPredicate("playerOwner", FilterOperator.EQUAL, userName);
			Query teamQuery = new Query("Players");
			teamQuery.setFilter(Query.CompositeFilterOperator.and(Query.FilterOperator.GREATER_THAN_OR_EQUAL.of("playerPrice", 1),Query.FilterOperator.EQUAL.of("playerOwner", userName)));
			teamQuery.addProjection(new PropertyProjection("playerName", String.class));
			teamQuery.addProjection(new PropertyProjection("playerAge", String.class));
			teamQuery.addProjection(new PropertyProjection("playerPrice", Long.class));
			teamQuery.addProjection(new PropertyProjection("playerImage", BlobKey.class));
			PreparedQuery pq = ds.prepare(teamQuery);
			for (Entity players : pq.asIterable()) {
				String name = (String) players.getProperty("playerName");
				String age = (String) players.getProperty("playerAge");
				long price = (long) players.getProperty("playerPrice");
				BlobKey blobKey = (BlobKey) players.getProperty("playerImage");
				ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey).secureUrl(true);
				String url = imagesService.getServingUrl(options);
				playersList.add(new Players(name, url,age, price));
			}
			
			return playersList;
		}
		
		// method for listing players for trade in Players directory
		
		public static LinkedList<Players> listAllPlayers(String username) {
			LinkedList<Players> tradeList = new LinkedList<>();
			Filter teamFilter = new FilterPredicate("playerOwner", FilterOperator.NOT_EQUAL, username);
			Query teamQuery = new Query("Players");
			teamQuery.addProjection(new PropertyProjection("playerName", String.class));
			teamQuery.addProjection(new PropertyProjection("playerAge", String.class));
			teamQuery.addProjection(new PropertyProjection("SellingPrice",Long.class));
			teamQuery.addProjection(new PropertyProjection("playerTeam",String.class));
			teamQuery.addProjection(new PropertyProjection("playerOwner",String.class));
			teamQuery.addProjection(new PropertyProjection("playerImage",BlobKey.class));
			teamQuery.setFilter(teamFilter);
			PreparedQuery teamPQ = ds.prepare(teamQuery);
			for(Entity tradePlayers : teamPQ.asIterable()) {
				if(!(tradePlayers.getProperty("SellingPrice") == null)) {
					String name = (String) tradePlayers.getProperty("playerName");
					String age = (String) tradePlayers.getProperty("playerAge");
					long price = (long) tradePlayers.getProperty("SellingPrice");
					String team = (String) tradePlayers.getProperty("playerTeam");
					String owner = (String) tradePlayers.getProperty("playerOwner");
					BlobKey blobKey = (BlobKey) tradePlayers.getProperty("playerImage");
					ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey).secureUrl(true);
					String imageurl = imagesService.getServingUrl(options);
					System.out.println("Image of : " + name + " is " + imageurl);
					tradeList.add(new Players(name, age, price,team,owner,imageurl));
				}
			
			
			
			/*Filter teamFilter = new FilterPredicate("playerOwner", FilterOperator.NOT_EQUAL,username);
			Query teamQuery = new Query("Players").setFilter(teamFilter);
			PreparedQuery pq = ds.prepare(teamQuery);
			for(Entity tradePlayers : pq.asIterable()) {
				if(!(tradePlayers.getProperty("SellingPrice") == null)) {
					String name = (String) tradePlayers.getProperty("playerName");
					String age = (String) tradePlayers.getProperty("playerAge");
					long price = (long) tradePlayers.getProperty("SellingPrice");
					String team = (String) tradePlayers.getProperty("playerTeam");
					String owner = (String) tradePlayers.getProperty("playerOwner");
					tradeList.add(new Players(name, age, price,team,owner));
				}
			}*/
		}
			return tradeList;

		}
}
