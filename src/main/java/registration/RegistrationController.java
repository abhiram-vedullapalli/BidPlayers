package registration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Controller
public class RegistrationController {
	private static BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@RequestMapping("/")
	public String showPage(Model theModel) {
		return "mainpage";
	}

	// For Users who loggedin using Google Sign In
	// Checking whether User exist in the datastore or not. If he doesn't exist ,
	// prompting him to Create Team
	// If he exists , returning him to homepage
	@RequestMapping(value = "/tokensignin", method = RequestMethod.POST)
	@ResponseBody
	public String googleSignIn(HttpServletRequest request) {
		String idToken = request.getParameter("id_token");

		System.out.println("IDToken on client side is " + idToken);
		boolean userExists = GoogleSigninVerifier.signinVerifier(idToken, null, null);
		HttpSession session = request.getSession();
		session.setAttribute("IDToken", idToken);
		if (userExists) {
			return "homepage";
		} else {
			return "createteam";

		}
	}

	// For handling users who register via normal form filling

	@RequestMapping(value = "/emailsignin", method = RequestMethod.POST)
	public String emailSignIn() {
		return "";
	}

	// Showing Homepage for already registered users once they visit the site

	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public String alreadyExists(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			if(session.getAttribute("Email") == null) {
				Users user = GoogleSigninVerifier.user;
				session.setAttribute("Email", user.getEmail());
				session.setAttribute("UserName", user.getUserName());
				session.setAttribute("Team", user.getTeamName());
				model.addAttribute("User", user);
			}else {
				String email = (String) session.getAttribute("Email");
				Users currentData = FetchInfo.getUserInfo(email);
				model.addAttribute("User", currentData);
			}
			
			return "homepage";
		}

	}

	// First time visitors are asked to enter team name and upload their Team image
	// here

	@RequestMapping(value = "/createteam", method = RequestMethod.GET)
	public String successful(HttpServletRequest request) {
		return "createteam";
	}

	// For registering User details in datastore . Create Team and Storing Team Logo
	// in Data store

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerTeam(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
			List<BlobKey> blobKeys = blobs.get("myFile");
			BlobKey blobKey = new BlobKey(blobKeys.get(0).getKeyString());
			String username = request.getParameter("UserName");
			String teamName = request.getParameter("TeamName");
			session.setAttribute("Team", teamName);
			String idToken = (String) session.getAttribute("IDToken");
			if (idToken != null) {
				GoogleSigninVerifier.signinVerifier(idToken, teamName, blobKey);
			}
			Users user = GoogleSigninVerifier.user;
			session.setAttribute("Email", user.getEmail());
			session.setAttribute("UserName", user.getUserName());
			return "redirect:/firstTime";
		}
	}

	@RequestMapping(value = "/firstTime", method = RequestMethod.GET)
	public String firstTime(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "mainpage";
		} else {
			String email = (String) session.getAttribute("Email");
			Users user = FetchInfo.getUserInfo(email);

			model.addAttribute("User", user);
			return "homepage";
		}

	}

}
