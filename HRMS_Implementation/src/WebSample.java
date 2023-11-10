package bin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.net.HttpURLConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.io.StringWriter;
import java.util.HashMap;
import java.net.URL;

public class WebSample{
	
	
	public static String publicHolidays(String dateOfJoin) throws ParseException, Exception{
		String day = dateOfJoin.substring(0, 2);
		String month = dateOfJoin.substring(3, 5);
		String newDate = month + "/" + day + "/2020";
		String url = "https://holidays.abstractapi.com/v1/?api_key=6c2625c5fdaa4a2eae8569bacf7a3451&country=IN&year=2020";
  		URL obj = new URL(url);
  		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
  		int responseCode = con.getResponseCode();
  		String holiday = "", sample = "no";
		
  		BufferedReader in =new BufferedReader(new InputStreamReader(con.getInputStream()));
  		String inputLine;
  		String response = "";
   		while ((inputLine = in.readLine()) != null) {
     			response = response + inputLine;
   		} in .close();
		
		HashMap<String, String> hmap = new HashMap<String, String>();
   		JSONParser parser = new JSONParser();
		Object object = parser.parse(response);
		String date = "", name = "";
		JSONArray array = (JSONArray) object;//list form
   		for(int i = 0; i < array.size(); i++){
		 	JSONObject obj2 = (JSONObject)array.get(i);
			date = obj2.get("date").toString();
			name = obj2.get("name").toString();
			hmap.put(date, name);
		}
		boolean flag = true;
		for(int i = 0; i < hmap.size(); i++){
			if(hmap.containsKey(newDate)){
				flag = true;	
				break;
			}
			else{
				flag = false;
				break;
			}
		}

		if(flag == true){
			holiday = hmap.get(newDate);
		}
		else{
			holiday = sample;
		}

		return holiday;
	}
}