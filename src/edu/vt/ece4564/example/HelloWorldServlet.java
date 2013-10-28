package edu.vt.ece4564.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.http.entity.StringEntity;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

//import android.util.Log;

public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String tag;
	int count = 0;
	int num=0;
	//static File newFile = new File("Logdata.txt");		
	JSONObject dataSave = new JSONObject(); 
	
	/** Starts server using embedded Jetty webserver */
	public static void main(String[] args) throws Exception {
		Server server = new Server(8094);

		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/");
		server.setHandler(context);
		

	//	newFile.createNewFile();
		
		server.start();
		server.join();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		synchronized(this) {
			  count++;
			 
			}
		doPost(request, response);
		// PrintWriter out = response.getWriter();
		// out.println("Hello Android !!!!");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		response.setContentType("application/json");
		try {
			String nameString = "";
			String counterString = "";

			// @Yong - getParameter only works for HTTP parameters, which you're
			// not
			// using. Your post has no parameters, but it does have content.
			// This
			// content is a string that has JSON formatting. I've commented this
			// out
			// and fixed the app below
			// nameString = request.getParameter("name");
			// counterString = request.getParameter("counter");

			// First I read in the string that was posted
			BufferedReader contentReader = new BufferedReader(
					new InputStreamReader(request.getInputStream()));
			StringBuilder json = new StringBuilder();
			String line;
			while ((line = contentReader.readLine()) != null)
				json.append(line);
			System.out.println("Received from client: " + json.toString());

			// Then I extract the values that you sent from the JSON string
			JSONObject o = (JSONObject) JSONValue.parse(json.toString());
			if (!o.containsKey("name") || !o.containsKey("counter")) {
				
			}
			nameString = (String) o.get("name");
			counterString = (String) o.get("counter");
			String i="";
			
			if (nameString != null)
				if (counterString != null) {
					i=Integer.toString(num);
					
					System.out.println("Received name " + nameString);
					System.out.println("Received counter " + counterString);
					out.println(nameString);
					out.println(counterString);
					
					dataSave.put("name"+i,nameString);
					dataSave.put("Steps"+i, counterString);
					//dataSave.put(nameString,counterString);
//					BufferedWriter in = new BufferedWriter(new FileWriter(newFile));
//					in.write(nameString);
//					in.write(counterString);
//					in.close();
					num++;
					
		       //     json = jsonObject.toString();
		        //    Log.d(tag, "Sending json to server:" + json);
		 
//		            String backJson= dataSave.toString();
//		            //  set json to StringEntity
//		            StringEntity se = new StringEntity(backJson);
//		 
//		            // set httpPost Entity
//		            httpPost.setEntity(se);
//		 
//		            // Set some headers to inform server about the type of the content   
//		            httpPost.setHeader("Accept", "application/json");
//		            httpPost.setHeader("Content-type", "application/json");
//		            
		            
					PrintWriter sendBack = response.getWriter();
					sendBack.println(dataSave.toString());
				}
			out.println("nothing");
			
		} catch (Exception e) {
			//out.println("{\"message\":\"Error - caught exception in ExportServlet\"}");
			//out.println(dataSave.toString());
			if(dataSave.isEmpty())
			{
				out.println("nothing inside the data");
			}
			else{
				for(int z=0; z<num; z++){
					JSONObject webprint = (JSONObject) JSONValue.parse(dataSave.toString());
					String namePrint = (String) webprint.get("name"+z);
					String counterPrint = (String) webprint.get("Steps"+z);
					out.println("Received name: " + namePrint);
					out.println("Received counter: " + counterPrint);
				}
			}
			} finally {
			out.flush();
			out.close();
		}

	}

}