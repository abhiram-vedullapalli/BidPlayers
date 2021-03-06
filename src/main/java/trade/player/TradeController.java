package trade.player;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import create.player.CreatePlayer;
import create.player.Players;
import list.player.ListPlayers;
import registration.Users;

@Controller
public class TradeController {
	
	//Controller to which handles buy requests
		@RequestMapping(value="/buy" , method = RequestMethod.POST)
		public String buyRequest(HttpServletRequest request , Model model) {
			HttpSession session = request.getSession(false);
			if(session == null) {
				return "mainpage";
			}else {
				String playerName = request.getParameter("playerName");
				String reqTeamName = request.getParameter("reqTeamName");
				String reqTeamBalance = request.getParameter("reqTeamBalance");
				String offeringPrice = request.getParameter("offeringPrice");
				String message = TradeOperations.buyRequest(playerName, reqTeamName, reqTeamBalance,offeringPrice);
				model.addAttribute("message", message);
				String username = (String) session.getAttribute("UserName");
				LinkedList<Players> tradeList = ListPlayers.listAllPlayers(username);
				model.addAttribute("tradePlayers", tradeList);
				return "listall";
			}
	}
		
		//method to handle trade method
		@RequestMapping(value="/trade", method = RequestMethod.GET)
		public String tradeRequest(HttpServletRequest request , Model model , @ModelAttribute("user") Users user) {
			HttpSession session = request.getSession(false);
			if(session == null) {
				return "mainpage";
			}else {
				String teamName = (String) session.getAttribute("Team");
				LinkedList<Trade> requestsIMade = TradeOperations.requestsIMade(teamName);
				model.addAttribute("requestsIMade", requestsIMade);
				
				LinkedList<Trade> requestsToMe = TradeOperations.requestsForMyPlayers(teamName);
				model.addAttribute("requestsToMe", requestsToMe);
			return "trade";
		}
}
		//method for handling accept trade proposal
		@RequestMapping(value="/accept" , method = RequestMethod.POST)
		public String acceptHandler(HttpServletRequest request , Model model) {
			HttpSession session = request.getSession(false);
			if(session == null) {
				return "mainpage";
			}else {
				String teamName = (String) session.getAttribute("Team");
				String playerName = request.getParameter("playerName");
				String reqTeamName = request.getParameter("reqTeamName");
				String offeringPrice = request.getParameter("offeringPrice");
				String message = TradeOperations.acceptTradeProposal(playerName, reqTeamName, offeringPrice);
				model.addAttribute("message", message);
				LinkedList<Trade> requestsIMade = TradeOperations.requestsIMade(teamName);
				model.addAttribute("requestsIMade", requestsIMade);
				
				LinkedList<Trade> requestsToMe = TradeOperations.requestsForMyPlayers(teamName);
				model.addAttribute("requestsToMe", requestsToMe);
				return "trade";
			}
		}
		
		//method for handling reject trade proposal
		@RequestMapping(value="/reject" , method = RequestMethod.POST)
		public String rejectHandler(HttpServletRequest request , Model model) {
			HttpSession session = request.getSession(false);
			if(session == null) {
				return "mainpage";
			}else {
				String teamName = (String) session.getAttribute("Team");
				String playerName = request.getParameter("playerName");
				String reqTeamName = request.getParameter("reqTeamName");
				String message = TradeOperations.rejectHandler(playerName, reqTeamName);
				model.addAttribute("message", message);
				LinkedList<Trade> requestsIMade = TradeOperations.requestsIMade(teamName);
				model.addAttribute("requestsIMade", requestsIMade);
				
				LinkedList<Trade> requestsToMe = TradeOperations.requestsForMyPlayers(teamName);
				model.addAttribute("requestsToMe", requestsToMe);
				return "trade";
			}
		}
		
		@RequestMapping(value = "/reject", method = RequestMethod.GET)
		public String updateGetRequest(HttpServletRequest request, @ModelAttribute("user") Users user , Model model) {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return "mainpage";
			} else {
				String teamName = (String) session.getAttribute("Team");
				LinkedList<Trade> requestsIMade = TradeOperations.requestsIMade(teamName);
				model.addAttribute("requestsIMade", requestsIMade);
				
				LinkedList<Trade> requestsToMe = TradeOperations.requestsForMyPlayers(teamName);
				model.addAttribute("requestsToMe", requestsToMe);
				return "trade";

			}
		}
		
		@RequestMapping(value = "/accept", method = RequestMethod.GET)
		public String acceptGetRequest(HttpServletRequest request, @ModelAttribute("user") Users user,Model model) {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return "mainpage";
			} else {
				String teamName = (String) session.getAttribute("Team");
				LinkedList<Trade> requestsIMade = TradeOperations.requestsIMade(teamName);
				model.addAttribute("requestsIMade", requestsIMade);
				
				LinkedList<Trade> requestsToMe = TradeOperations.requestsForMyPlayers(teamName);
				model.addAttribute("requestsToMe", requestsToMe);
				return "trade";

			}
		}
		
		@RequestMapping(value = "/buy", method = RequestMethod.GET)
		public String buyGetRequest(HttpServletRequest request, @ModelAttribute("user") Users user,Model model) {
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
}
