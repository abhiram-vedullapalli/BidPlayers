package list.player;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import create.player.CreatePlayer;
import create.player.Players;
import registration.FetchInfo;
import registration.Users;

@Controller
public class ListPlayersController {
	
	@RequestMapping(value = "/logout" , method = RequestMethod.GET)
	public String logout(HttpServletRequest request , HttpServletResponse response , @ModelAttribute("user") Users user) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			
			return "mainpage";
		}
		else {
			response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
			response.setHeader("Pragma", "no-cache");
			session.invalidate();
			return "mainpage";
	
		}
	}
	
	//Controller which handles (prints players of my team)
	@RequestMapping(value = "/myteam" , method = RequestMethod.GET)
	public String createGetRequest(HttpServletRequest request , Model model , @ModelAttribute("user") Users user) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "mainpage";
		}
		else {
			String username = (String) session.getAttribute("UserName");
			LinkedList<Players> playersList = ListPlayers.listMyTeam(username);
			model.addAttribute("myplayers", playersList);
			return "myteam";
		}
	}
	
	
	
	//Controller to handle Players who are listed on Trade
	@RequestMapping(value="/listall" , method = RequestMethod.GET)
	public String listAllGetRequest(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "mainpage";
		}
		else {
			String email = (String) session.getAttribute("Email");
			String username = (String) session.getAttribute("UserName");
			LinkedList<Players> tradeList = ListPlayers.listAllPlayers(username);
			model.addAttribute("tradePlayers", tradeList);
			Users user = FetchInfo.getUserInfo(email);
			model.addAttribute("requestingUser", user);
			return "listall";
	
		}
		}
	
	
}