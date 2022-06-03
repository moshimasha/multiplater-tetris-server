package com.catsandcheese.servlets;
import java.io.*;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;
class Player {
	
	public String name;
	public int score;
	
	public Player(String name, int score) {
		this.name = name;
		this.score = score;
	}
}

public class Players extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public static final String kPlayers = "players";
	public static final String kPlayersPlaying = "playersPlaying";
	public static String kGameOver = "gameOver";
	public static String kPlayersReady = "playersReady";
	//int playersReady = 0;
	
	public void init() throws ServletException {

	   }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException , ServletException{
	if((boolean)(getServletContext().getAttribute(kGameOver))) {
		String userName = request.getParameter("userName");
		int finalScore = Integer.valueOf(request.getParameter("finalScore"));
		
		ArrayList<Player> players = (ArrayList<Player>)this.getServletContext().getAttribute(kPlayers);
		if (players == null) {
			players = new ArrayList<Player>();
		}
		
		Player player = new Player(userName, finalScore);
		players.add(player);
		this.getServletContext().setAttribute(kPlayers, players);
	}else { 
		int newPlayersReady = (int)(this.getServletContext().getAttribute(kPlayersReady)) + Integer.valueOf(request.getParameter("playersReady"));
		this.getServletContext().setAttribute(kPlayersReady, newPlayersReady);
		
		if(Integer.valueOf(request.getParameter("gameOver")) == 1) {
			this.getServletContext().setAttribute(kGameOver, true);
		}else {
			this.getServletContext().setAttribute(kGameOver, false);
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    out.println(this.getServletContext().getAttribute(kPlayersReady));
	    out.println(this.getServletContext().getAttribute(kGameOver));
	    out.println(this.getServletContext().getAttribute(kPlayersPlaying));
		
		}	
		 
		    
	}
		   
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
	    response.setContentType("text/json");
	    
	    
		if(Integer.valueOf(request.getParameter("gameOver")) == 0) {
		 
	    out.print("{\"playersPlaying\": " + (int)getServletContext().getAttribute(kPlayersPlaying) + ", " + "\"playersReady\":" + (int)(this.getServletContext().getAttribute(kPlayersReady)) +  ", " + "\"gameOver\":" + (boolean)(getServletContext().getAttribute(kGameOver)) +"}");
	     
		}else {
			ArrayList<Player> players = (ArrayList<Player>)this.getServletContext().getAttribute(kPlayers);
			sortPlayers(players);
			out.print("[");
		      if (players != null) {
		    	  int x=0;
			      for (int i=players.size()-1; i>=0; i--) {
			    	  if (x > 0) {
		    			  out.print(",");
		    		  }
		    		  x++;
		    		  Player p = players.get(i);
			    		  out.print("{\"userName\": \"" + p.name + "\", \"finalScore\": " 
			    			  							+ p.score + "}");
			      }
			  }   
		   out.print("]");
		}
		
	}

	   public static void sortPlayers(ArrayList<Player> array){
	      for(int i=0; i<array.size(); i++){
	         int max = maxIndex(array, array.size()-i);
	         swap(array.size()-i-1, max, array);
	      }
	   }
	   public static int maxIndex(ArrayList<Player> array, int upper){
	      int index = 0;
	      for(int i=0; i<upper; i++){
	         if(array.get(index).score < array.get(i).score){
	            index = i;
	         } 
	      }
	      return index;
	   }
	   public static void swap(int index1, int index2, ArrayList<Player> array){
	      Player temp = array.get(index1);
	      array.set(index1, array.get(index2));
	      array.set(index2, temp);
	   }
}
