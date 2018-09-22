package registration;

import com.google.appengine.api.blobstore.BlobKey;

public class Users {
	private String email;
	private String userName;
	private String passWord;
	private String teamName;
	private String image;
	private long balance;
	private long numPlayers;
	private long sold;
	private long profit;
	private String[] players;
	
	public Users() {
		
	}
	public Users(String email ,String userName , String teamName , String image , long balance , long numPlayers , long sold , long profit) {
		this.email = email;
		this.userName = userName;
		this.teamName = teamName;
		this.image = image;
		this.balance = balance;
		this.numPlayers = numPlayers;
		this.sold = sold;
		this.profit = profit;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public long getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(long numPlayers) {
		this.numPlayers = numPlayers;
	}
	public long getSold() {
		return sold;
	}
	public void setSold(long sold) {
		this.sold = sold;
	}
	public long getProfit() {
		return profit;
	}
	public void setProfit(long profit) {
		this.profit = profit;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
