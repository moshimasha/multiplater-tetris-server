package com.catsandcheese.servlets;
import java.io.*;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;
class Thread {
	
	public String id;
	public long time;
	
	public Thread(String id, long time) {
		this.id = id;
		this.time = time;
	}
}

public class Threads extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public static final String kThreads = "threads";
	public void init() throws ServletException {

	   }
	//reset makes array null
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws IOException , ServletException{
		
		String threadID = request.getParameter("id");
		long time = Long.parseLong(request.getParameter("time"));
		ArrayList<Thread> threads = (ArrayList<Thread>)this.getServletContext().getAttribute(kThreads);
		if (threads == null) {
			threads = new ArrayList<Thread>();
		}
		Thread thread = new Thread(threadID, time);
		threads.add(thread);
		this.getServletContext().setAttribute(kThreads, threads);
		
		
	}
		   
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String myID = request.getParameter("id");
		ArrayList<Thread> threads = (ArrayList<Thread>)this.getServletContext().getAttribute(kThreads);
		Thread modelThread = threads.get(threads.size()-1);
		Thread myThread = null;
		for (Thread t : threads) {
			if(myID.equals(t.id)) {
				myThread = t;
			}
		}
		
		int dif =(int)(modelThread.time-myThread.time);
		PrintWriter out = response.getWriter();
	    response.setContentType("text/json");
		out.print("{\"wait\": \"" + dif + "\"}");
	
		
	}
}
