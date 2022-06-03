package com.catsandcheese.servlets;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class Reset extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private final static String kTimestamp = "timestamp";
	
	private Date resetTimestamp() {
	      Date timestamp = new Date();
	      this.getServletContext().setAttribute(kTimestamp, timestamp);
	      this.getServletContext().removeAttribute(Rows.kRows);
	      return timestamp;
	}
	
	   public void init() throws ServletException {
	      // Do required initialization
		  resetTimestamp();
	   }

	   public void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {

		   this.getServletContext().setAttribute(Players.kPlayersReady, 0);
		   this.getServletContext().setAttribute(Players.kGameOver, false);
		   this.getServletContext().setAttribute(Players.kPlayers, null);
		   this.getServletContext().setAttribute(Threads.kThreads, null);

		  int playersPlaying = Integer.valueOf(request.getParameter("playersPlaying"));
		  this.getServletContext().setAttribute(Players.kPlayersPlaying, playersPlaying );
	      
	      Date timestamp = this.resetTimestamp();
		  
	      response.setContentType("text/html");

	      // Actual logic goes here.
	      PrintWriter out = response.getWriter();
	      out.println("<h1>" + timestamp.getTime() + "</h1>");
	      out.println("<h2>" + this.getServletContext().getAttribute(Players.kPlayersPlaying) + "</h2>");
	      out.println("<h3>" + this.getServletContext().getAttribute(Players.kGameOver) + "</h3>");
	      out.println("<h3>" + this.getServletContext().getAttribute(Players.kPlayersReady) + "</h3>");
	   }
	   
	   public void doGet(HttpServletRequest request, HttpServletResponse response)
			      throws ServletException, IOException {
		   
		   Date timestamp = (Date)this.getServletContext().getAttribute(kTimestamp);
			      
		   response.setContentType("text/html");

		   // Actual logic goes here.
		   PrintWriter out = response.getWriter();
		   out.println("<h1>" + timestamp.getTime() + "</h1>");
	   }
	   
}
