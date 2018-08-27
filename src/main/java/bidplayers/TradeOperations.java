package bidplayers;

import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


public class TradeOperations {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	
	//function for Trade
		public static String buyRequest(String playerName , String reqTeamName , String reqTeamBalance , String offerPrice ) {
			Filter playerFilter = new FilterPredicate("playerName", FilterOperator.EQUAL, playerName);
			Query playerQuery = new Query("Players").setFilter(playerFilter);
			PreparedQuery playerpq = ds.prepare(playerQuery);
			Entity player = playerpq.asSingleEntity();
			String playerTeam = (String) player.getProperty("playerTeam");
			long SellingPrice = (long) player.getProperty("SellingPrice");
			
			long requestingTeamBalance = Long.parseLong(reqTeamBalance);
			
			if(SellingPrice > requestingTeamBalance) {
				return "You dont have sufficient balance to buy " + playerName + " ";
			}
			else {
			long offeringPrice = Long.parseLong(offerPrice);	
			Entity trade = new Entity("Trade");
			trade.setProperty("playerName", playerName);
			trade.setProperty("playerTeam",playerTeam);
			trade.setProperty("ReqTeam", reqTeamName);
			trade.setProperty("offeringPrice", offeringPrice);
			trade.setProperty("sellingPrice", SellingPrice);
			trade.setProperty("Status", false);
			ds.put(trade);
			return "Request for " + playerName + " is successfully submited ";
		}
		
	}
		
		//function for listing requests my team received
		public static LinkedList<Trade> requestsForMyPlayers(HttpSession session) {
			LinkedList<Trade> requestsToMe = new LinkedList<>();
			Filter playerFilter = new FilterPredicate("playerTeam" , FilterOperator.EQUAL , session.getAttribute("TeamName"));
			Query playerQuery = new Query("Trade").setFilter(playerFilter);
			PreparedQuery playerpq = ds.prepare(playerQuery);
			for(Entity players : playerpq.asIterable() ) {
				String playerName = (String) players.getProperty("playerName");
				String reqTeamName = (String) players.getProperty("ReqTeam");
				long sellingPrice = (long) players.getProperty("sellingPrice");
				long offeringPrice = (long) players.getProperty("offeringPrice");
				requestsToMe.add(new Trade(playerName,reqTeamName,sellingPrice,offeringPrice));
			}
			return requestsToMe;
		}
		
		//function for requests I made
		public static LinkedList<Trade> requestsIMade(HttpSession session){
			LinkedList<Trade> requestsIMade = new LinkedList<>();
			Filter playerFilter = new FilterPredicate("ReqTeam" , FilterOperator.EQUAL , session.getAttribute("TeamName"));
			Query playerQuery = new Query("Trade").setFilter(playerFilter);
			PreparedQuery playerpq = ds.prepare(playerQuery);
			for(Entity players : playerpq.asIterable() ) {
				String playerName = (String) players.getProperty("playerName");
				String reqTeamName = (String) players.getProperty("playerTeam");
				long sellingPrice = (long) players.getProperty("sellingPrice");
				long offeringPrice = (long) players.getProperty("offeringPrice");
				requestsIMade.add(new Trade(playerName,reqTeamName,sellingPrice,offeringPrice));
			}
			return requestsIMade;
		}
		
		/*In trade.jsp , teams should not over ride. If two teams request for the same player , two teams requests should appear
		 * Owner should have chance to select the request
		 * while submitting the request, teams should put how much money they are going to offer for the player
		 * based on the price , owner should have the ability to accept / reject
		 * 
		 * change the team name of player to reqTeamName in Player Kind
		 * Update the price of player to accepted price
		 */
		//function for accepting trade proposal
		public static String acceptTradeProposal(String playerName , String reqTeamName , String offeringPrice, HttpSession session) {
		
			
			//querying on Users kind for updating the balance and no of players bought
			Filter userFilter = new FilterPredicate("TeamName" , FilterOperator.EQUAL , reqTeamName);
			Query userQuery = new Query("Users").setFilter(userFilter);
			PreparedQuery pq = ds.prepare(userQuery);
			Entity user = pq.asSingleEntity();
			String UserName = (String) user.getProperty("UserName");
			long NumPlayers = (long) user.getProperty("NumPlayers");
			long Balance = (long) user.getProperty("Balance");
			long offerPrice = Long.parseLong(offeringPrice);
			
			if(offerPrice <= Balance) {
				NumPlayers = NumPlayers + 1;
				Balance = Balance - offerPrice ;
				user.setProperty("NumPlayers", NumPlayers);
				user.setProperty("Balance", Balance);
				ds.put(user);
				
				//querying on Player Kind to change the player's team name and owner name to new team name and new owner name
				Filter playerFilter = new FilterPredicate("playerName" , FilterOperator.EQUAL , playerName);
				Query playerQuery = new Query("Players").setFilter(playerFilter);
				PreparedQuery playerpq = ds.prepare(playerQuery);
				Entity player = playerpq.asSingleEntity();
				String previousTeam = (String) player.getProperty("playerTeam");
				long previousPrice = (long) player.getProperty("playerPrice");
				
				//querying on user kind to update the Trade details on behalf of the previous owner
				Filter ownerFilter = new FilterPredicate("TeamName" , FilterOperator.EQUAL , previousTeam);
				Query ownerQuery = new Query("Users").setFilter(ownerFilter);
				PreparedQuery ownerpq = ds.prepare(ownerQuery);
				Entity owner = ownerpq.asSingleEntity();
				long sold = (long) owner.getProperty("Sold");
				long profitMade = (long) owner.getProperty("Profit");
				long currentBalance = (long) owner.getProperty("Balance");
				long profit = offerPrice - previousPrice ;
				currentBalance = currentBalance + profit;
				profitMade = profitMade + profit;
				sold = sold + 1;
				owner.setProperty("Sold", sold);
				owner.setProperty("Profit", profitMade);
				owner.setProperty("Balance", currentBalance);
				session.setAttribute("Balance", currentBalance);
				session.setAttribute("Sold", sold);
				session.setAttribute("Profit", profit);
				ds.put(owner);
				
				
				//querying on Trade kind to remove the Player from the trade list
				Filter delPlayer = new FilterPredicate("playerName" , FilterOperator.EQUAL , playerName);
				Query delQuery = new Query("Trade").setFilter(delPlayer);
				PreparedQuery delPQ = ds.prepare(delQuery);
				for(Entity deleteplayer : delPQ.asIterable()) {
					ds.delete(deleteplayer.getKey());
				}
				
				player.setProperty("playerTeam", reqTeamName);
				player.setProperty("playerPrice", offerPrice);
				player.setProperty("playerOwner", UserName);
				player.setProperty("SellingPrice", null);
				ds.put(player);
				return ""+playerName + " is successfully Transferred to " + reqTeamName+ "";
			}else {
				return "" +reqTeamName+ "doesn't have sufficient funds to buy this player";
			}
			
			
			
		}
		
		//method for handling reject handler
		public static String rejectHandler(String playerName , String reqTeamName) {
			Filter delPlayer = new FilterPredicate("playerName" , FilterOperator.EQUAL , playerName);
			Query delQuery = new Query("Trade").setFilter(delPlayer);
			PreparedQuery delPQ = ds.prepare(delQuery);
			for(Entity player : delPQ.asIterable()) {
				if(player.getProperty("playerName").equals(playerName) && player.getProperty("ReqTeam").equals(reqTeamName)) {
					ds.delete(player.getKey());
					
				}
			}
			return "Trade Proposal from " + reqTeamName + " for " + playerName + " was rejected ";
		}
}

