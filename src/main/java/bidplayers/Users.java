package bidplayers;

public class Users {
	private String userName;
	private String passWord;
	private String teamName;
	private long balance = 100;
	private long numPlayers = 0;
	private String[] players;
	
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
}
