package com.catsandcheese.servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

class Row {
	
	public Date timestamp;
	public String player;
	
	public Row(Date timestamp, String player) {
		this.timestamp = timestamp;
		this.player = player;
	}
}

public class Rows extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	//	private ArrayList<Row> rows;
	public static final String kRows = "rows";
	
	public void init() throws ServletException {
//		
//		this.rows = new ArrayList<Row>();
//		this.rows.add(0, new Row(new Date(), "Masha"));
//	    this.rows.add(0, new Row(new Date(), "Mama"));
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			      throws ServletException, IOException {
		
		String player = request.getParameter("player");
		Integer number = Integer.valueOf(request.getParameter("count"));
		//System.out.println(number);
		
		ArrayList<Row> rows = (ArrayList<Row>)this.getServletContext().getAttribute(kRows);
		if (rows == null) {
			rows = new ArrayList<Row>();
		}
		Date timestamp = new Date();
		for (int i = 0; i < number; i++) {
			Row row = new Row(timestamp, player);
			rows.add(row);
		}
		this.getServletContext().setAttribute(kRows, rows);
		
	      PrintWriter out = response.getWriter();
	      out.println("OK");
	      response.setContentType("text/html");
	}
	
			   
	public void doGet(HttpServletRequest request, HttpServletResponse response)
					      throws ServletException, IOException {

		String sinceTimestampString = request.getParameter("timestamp");
		Date sinceTimestamp = new Date(new Long(sinceTimestampString));
		ArrayList<Row> rows = (ArrayList<Row>)this.getServletContext().getAttribute(kRows);
		
		
	      PrintWriter out = response.getWriter();
//	      out.println("since: " + sinceTimestamp);
	      response.setContentType("text/json");
	      
	      out.print("[");
	      if (rows != null) {
	    	  int i = 0;
		      for (Row r : rows) {
		    	  if (r.timestamp.after(sinceTimestamp)) {
		    		  if (i > 0) {
		    			  out.print(",");
		    		  }
		    		  i++;
		    		  out.print("{\"player\": \"" + r.player + "\", \"timestamp\": " 
		    			  							+ r.timestamp.getTime() + "}");
		    	  }
		      }
	      }
	      out.print("]");
	      
//	      out.println("That's it.");
	}

}
