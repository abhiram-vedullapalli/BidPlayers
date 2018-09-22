package updatedelete.player;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.datastore.EntityNotFoundException;

import create.player.CreatePlayer;
import create.player.Players;
import list.player.ListPlayers;
import registration.Users;

@Controller
public class UpdateDeleteController {

	

	@RequestMapping(value = "/changevalues" , method = RequestMethod.POST)
	public String changeValues(HttpServletRequest request , Model model) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			String playerName = request.getParameter("playerName");
			Players player = UpdateDeleteOperations.fetchPlayerDetails(playerName);
			model.addAttribute("playerName",player.getPlayerName());
			model.addAttribute("playerAge", player.getPlayerAge());
			model.addAttribute("playerPrice",player.getPlayerPrice());

			return "changevalues";
		}	
	}
	
	@RequestMapping(value = "/changevalues", method = RequestMethod.GET)
	public String changeValuesGetRequest(HttpServletRequest request, @ModelAttribute("user") Users user,Model model) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			String username = (String) session.getAttribute("UserName");
			LinkedList<Players> tradeList = ListPlayers.listAllPlayers(username);
			model.addAttribute("tradePlayers", tradeList);
			return "listall";

		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePostRequest(HttpServletRequest request , Model model) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			String username = (String) session.getAttribute("UserName");
			String updelMessage = UpdateDeleteOperations.updatePlayerDetails(request.getParameter("playerName"),request.getParameter("playerAge"), request.getParameter("playerPrice"));
			LinkedList<Players> playersList = ListPlayers.listMyTeam(username);
			model.addAttribute("myplayers", playersList);
			model.addAttribute("updelMessage", updelMessage);
			return "myteam";
		}
	}

	@RequestMapping(value = "/update" , method = RequestMethod.GET)
	public String updateGetRequest(HttpServletRequest request , Model model , @ModelAttribute("user") Users user) {
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

	

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deletePostRequest(HttpServletRequest request, @ModelAttribute("user") Users user, Model model) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			String username = (String) session.getAttribute("UserName");
			String pName = request.getParameter("playerName");
	        
			
			try {
				String updelMessage = UpdateDeleteOperations.deletePlayer(pName);
				LinkedList<Players> playersList = ListPlayers.listMyTeam(username);
				model.addAttribute("myplayers", playersList);
				model.addAttribute("updelMessage", updelMessage);
				return "myteam";
			} catch (EntityNotFoundException e) {
				LinkedList<Players> playersList = ListPlayers.listMyTeam(username);
				model.addAttribute("myplayers", playersList);
				return "myteam";
				
			}

		}
	}
	
	@RequestMapping(value = "/delete" , method = RequestMethod.GET)
	public String deleteGetRequest(HttpServletRequest request , Model model , @ModelAttribute("user") Users user) {
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


}
