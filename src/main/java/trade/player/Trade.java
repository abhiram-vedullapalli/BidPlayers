package trade.player;

public class Trade {
private String playerName;

private String reqTeamName;
private long sellingPrice;
private long offeringPrice;
public Trade(String playerName, String reqTeamName , long sellingPrice , long offeringPrice) {
	this.playerName = playerName;
	this.reqTeamName = reqTeamName;
	this.sellingPrice = sellingPrice;
	this.offeringPrice = offeringPrice;
}

public String getPlayerName() {
	return playerName;
}

public void setPlayerName(String playerName) {
	this.playerName = playerName;
}

public String getReqTeamName() {
	return reqTeamName;
}

public void setReqTeamName(String reqTeamName) {
	this.reqTeamName = reqTeamName;
}

public long getSellingPrice() {
	return sellingPrice;
}

public void setSellingPrice(long sellingPrice) {
	this.sellingPrice = sellingPrice;
}

public long getOfferingPrice() {
	return offeringPrice;
}

public void setOfferingPrice(long offeringPrice) {
	this.offeringPrice = offeringPrice;
}

//for handling requests to me
public String toString() {
	return "Player : " + playerName + "<br>"
			+ "Requested by : " + reqTeamName + "<br>"
			+ "Selling Price : " + sellingPrice + "<br>"
			+ "Offered Price : " + offeringPrice + "<br>";
}

public String myRequests() {
	return "Player  : " + playerName + "<br>"
			+ "Current Team : " + reqTeamName + "<br>"
			+ "Asking Price : " + sellingPrice + "<br>"
			+ "Price I Offered : " + offeringPrice + "<br>";
}
}
