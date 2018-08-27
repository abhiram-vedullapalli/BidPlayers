package bidplayers;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
			LinkedList<Players> playersList = DatabaseOperations.listMyTeam(session);
			model.addAttribute("myteam", playersList);
			return "myteam";
		}
	}
	
	//Controller to handle Players who are listed on Trade
	@RequestMapping(value="/listall" , method = RequestMethod.GET)
	public String listAllGetRequest(HttpServletRequest request ,  @ModelAttribute("user") Users user , Model model) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "mainpage";
		}
		else {
			LinkedList<Players> tradeList = DatabaseOperations.listAllPlayers(session);
			model.addAttribute("tradePlayers", tradeList);
			return "listall";
	
		}
		}
	
	
}