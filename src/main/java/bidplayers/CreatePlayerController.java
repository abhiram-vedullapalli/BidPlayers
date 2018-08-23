package bidplayers;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


@Controller
public class CreatePlayerController {
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	@RequestMapping(value = "/create" , method = RequestMethod.GET)
	public String createGetRequest(HttpServletRequest request , @ModelAttribute("user") Users user) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "mainpage";
		}
		else {
			return "create";
	
		}
	}
	
	@RequestMapping(value = "/create" , method = RequestMethod.POST)
	public String createPlayer(HttpServletRequest request , Model model ) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "mainpage";
		}else {
			
			String uName = (String) session.getAttribute("UserName");
			
			
			String playerName = request.getParameter("playerName");
			String playerAge = request.getParameter("playerAge");
			
			boolean playerCreated = DatabaseOperations.createNewPlayer(uName,playerName,playerAge,session);
			if(playerCreated == true) {
				model.addAttribute("message", "Player Successfully Created");
				return "create";
			}else {
				model.addAttribute("message", "Insufficient Balance.");
				return "create";
			}
		
		}
		
	}
}
