package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Waypoint;

public class CruiserLoader {

	public static final List<Waypoint> loadWaypoints(File cruiserFile) {
		
		List<Waypoint> list = null;
		
		JSONParser parser = new JSONParser();

        try {     
            Object obj = parser.parse(new FileReader(cruiserFile));

            JSONObject jsonObject =  (JSONObject) obj;
            
            JSONObject routeObject = (JSONObject) jsonObject.get("route");
            JSONArray coordsArray = (JSONArray) routeObject.get("coords");

            @SuppressWarnings("unchecked")
            Iterator<String> coordsIterator = coordsArray.iterator();
            while (coordsIterator.hasNext()) {
            	if (list == null) {
    	    		list = new ArrayList<Waypoint>();
    			}
            	
            	String latLonString = (String) coordsIterator.next();
            	String[] latLonArray = latLonString.split(",");
            	
            	
            	System.out.println("Lat : " + latLonArray[0]);
    			System.out.println("Lon : " + latLonArray[1]);
    			
    			Waypoint waypoint = new Waypoint(latLonArray[0], latLonArray[1]);
    			list.add(waypoint);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
	}
}
