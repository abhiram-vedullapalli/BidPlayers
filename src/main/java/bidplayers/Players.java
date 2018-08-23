package bidplayers;

public class Players {
 private String playerName ;
 private String playerAge;
 private String playerTeam;
 private Users playerOwner;
 private long playerPrice;
 
 
public Players(String playerName, String playerAge, String playerTeam, Users playerOwner, long playerPrice) {
	this.playerName = playerName;
	this.playerAge = playerAge;
	this.playerTeam = playerTeam;
	this.playerOwner = playerOwner;
	this.playerPrice = playerPrice;
}
public Players(String playerName, String playerAge, long playerPrice) {
	this.playerName = playerName;
	this.playerAge = playerAge;
	this.playerPrice = playerPrice;
}
public String getPlayerName() {
	return playerName;
}
public void setPlayerName(String playerName) {
	this.playerName = playerName;
}
public String getPlayerAge() {
	return playerAge;
}
public void setPlayerAge(String playerAge) {
	this.playerAge = playerAge;
}
public String getPlayerTeam() {
	return playerTeam;
}
public void setPlayerTeam(String playerTeam) {
	this.playerTeam = playerTeam;
}
public Users getPlayerOwner() {
	return playerOwner;
}
public void setPlayerOwner(Users playerOwner) {
	this.playerOwner = playerOwner;
}
public long getPlayerPrice() {
	return playerPrice;
}
public void setPlayerPrice(long playerPrice) {
	this.playerPrice = playerPrice;
}
@Override
public String toString() {
	return "Name of Player : " + playerName + "<br>"
			+ "Age : " + playerAge + "<br>"
			+ "Price : " + playerPrice + " crores <br>"	
			;
}


}