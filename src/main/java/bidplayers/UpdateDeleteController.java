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

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePostRequest(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {

			return "myteam";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteGetRequest(HttpServletRequest request, @ModelAttribute("user") Users user) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			return "delete";

		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deletePostRequest(HttpServletRequest request, @ModelAttribute("user") Users user, Model model) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			String pName = request.getParameter("playerName");
			System.out.println("Delete player name " + pName);
			
			try {
				String delOpMsg = UpdateDeleteOperations.deletePlayer(pName, session);
				System.out.println(delOpMsg);

				LinkedList<Players> playersList = DatabaseOperations.listMyTeam(session);
				model.addAttribute("myteam", playersList);
				model.addAttribute("delOpMsg", delOpMsg);
				return "myteam";
			} catch (EntityNotFoundException e) {
				LinkedList<Players> playersList = DatabaseOperations.listMyTeam(session);
				model.addAttribute("myteam", playersList);
				return "myteam";
				
			}

		}
	}

}
