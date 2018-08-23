package bidplayers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class MainController {

	@RequestMapping("/")
	public String showPage(Model theModel) {
		Users user = new Users();
		theModel.addAttribute("user", user);
	
		return "mainpage";
	}
	
	@RequestMapping(value = "/register" , method = RequestMethod.POST)
	public String login(@ModelAttribute("user") Users user , HttpServletRequest request, Model messageModel) {

		//checking if user name already exist or not
		boolean checkUserName = UserRegistration.checkUserName(user.getUserName());
		if(checkUserName == true) {
			//user name already exists
			messageModel.addAttribute("message", "Username already taken");
			return "mainpage";
		}else {
			// registering user
			HttpSession session = request.getSession();
			UserRegistration.userRegistration(user.getUserName(), user.getPassWord(), user.getTeamName(),user.getBalance(), user.getNumPlayers(),session);
			return "homepage";
		} 
	}
	
	@RequestMapping(value = "/register" , method = RequestMethod.GET)
	public String reigsterGetRequest(HttpServletRequest req , HttpSession session , @ModelAttribute("user") Users user ) {
		session = req.getSession(false);
		if(session == null) {
			return "mainpage";
		}else {
			return "loginhomepage";
		}
	}
	
	@RequestMapping(value="/homepage", method = RequestMethod.POST)
	public String userAuthentication(HttpServletRequest request , @ModelAttribute("user") Users user , Model messageModel ) {
		String uName = request.getParameter("userName");
		String pWord = request.getParameter("passWord");
		HttpSession session = request.getSession();
		boolean userAuth = UserRegistration.userLogIn(uName, pWord,session);
		if(userAuth == true) {
			return "loginhomepage";
		}
		else {
			messageModel.addAttribute("message2", "Username or Password is incorrect");
			return "mainpage";
		}
	}
	
	@RequestMapping(value="/homepage", method = RequestMethod.GET)
	public String homepageGetRequest(HttpServletRequest req , @ModelAttribute("user") Users user) {
		HttpSession session = req.getSession(false);
		if(session == null) {
			return "mainpage";
		}else {
			return "loginhomepage";
		}
	}
	
	@RequestMapping(value="/loginpage")
	public String loginPage() {
		return "login";
	}
}
