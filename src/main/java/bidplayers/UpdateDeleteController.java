package bidplayers;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Controller
public class UpdateDeleteController {

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateGetRequest(HttpServletRequest request, @ModelAttribute("user") Users user) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			return "update";

		}
	}

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
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePostRequest(HttpServletRequest request , Model model) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			
			String updelMessage = UpdateDeleteOperations.updatePlayerDetails(request.getParameter("playerName"),request.getParameter("playerAge"), request.getParameter("playerPrice"));
			LinkedList<Players> playersList = DatabaseOperations.listMyTeam(session);
			model.addAttribute("myteam", playersList);
			model.addAttribute("updelMessage", updelMessage);
			return "myteam";
		}
	}

	

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deletePostRequest(HttpServletRequest request, @ModelAttribute("user") Users user, Model model) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			String pName = request.getParameter("playerName");
	        
			
			try {
				String updelMessage = UpdateDeleteOperations.deletePlayer(pName, session);
			

				LinkedList<Players> playersList = DatabaseOperations.listMyTeam(session);
				model.addAttribute("myteam", playersList);
				model.addAttribute("updelMessage", updelMessage);
				return "myteam";
			} catch (EntityNotFoundException e) {
				LinkedList<Players> playersList = DatabaseOperations.listMyTeam(session);
				model.addAttribute("myteam", playersList);
				return "myteam";
				
			}

		}
	}

}
