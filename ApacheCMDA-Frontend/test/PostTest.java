import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;

public class PostTest {

	public static void main(String[] args) {
		
		//Fill in the backend API
		String url="http://localhost:9034/xxxx";
		URL object;
		try {
			object = new URL(url);
		
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			
			//Fill in the post parameters
			ObjectNode jsonData = Json.newObject();
			jsonData.put("xxxxx", "1");
			
			System.out.println(jsonData.toString());
		
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(jsonData.toString());
			wr.close();
			
			//Print the response
			StringBuilder sb = new StringBuilder();  
			int HttpResult = con.getResponseCode(); 
			if(HttpResult == HttpURLConnection.HTTP_OK){
			    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));  
			    String line = null;  
			    while ((line = br.readLine()) != null) {  
			        sb.append(line + "\n");  
			    }  

			    br.close();  

			    System.out.println(""+sb.toString()); 
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
