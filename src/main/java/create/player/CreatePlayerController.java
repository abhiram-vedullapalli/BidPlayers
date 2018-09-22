package create.player;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;

import list.player.ListPlayers;


@Controller
public class CreatePlayerController {
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	private static BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	
	@RequestMapping(value = "/create" , method = RequestMethod.GET)
	public String createGetRequest(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "mainpage";
		}
		else {
			model.addAttribute("message", model.asMap().get("playerStatus"));
			System.out.println("Redirected message I received is : " +  model.asMap().get("playerStatus"));
			return "create";
	
		}
	}
	
	@RequestMapping(value = "/create" , method = RequestMethod.POST)
	public String createPlayer(HttpServletRequest request , HttpServletResponse response , RedirectAttributes redir ) throws EntityNotFoundException {
		HttpSession session = request.getSession(false);
		
			
			String email = (String) session.getAttribute("Email");
			String playerName = request.getParameter("playerName");
			String playerAge = request.getParameter("playerAge");
			Map<String,List<BlobKey>> blobs = blobstoreService.getUploads(request);
			List<BlobKey> blob = blobs.get("myFile");
			BlobKey blobKey = new BlobKey(blob.get(0).getKeyString());
			
			String playerCreated = CreatePlayer.createNewPlayer(email,playerName,playerAge,blobKey);
			redir.addFlashAttribute("playerStatus", playerCreated);
			return "redirect:/create";
		
		}
		
	}

