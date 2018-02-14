package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.Waypoint;
import model.Route;

public class CruiserExporter {
	
	@SuppressWarnings("unchecked")
	public static final void saveRoute(File cruiserFile, Route route) {

        HashMap<String,Object> settingsMap = new HashMap<String,Object>();
        settingsMap.put("VT", new Integer(1));
        settingsMap.put("BE", new Integer(0));
		if (route.getForbidFerries()) {
			settingsMap.put("FR", new Integer(1));
		}
		else {
			settingsMap.put("FR", new Integer(0));
		};
		settingsMap.put("ROUND", new Integer(0));
		settingsMap.put("RT", new Integer(3));
		settingsMap.put("SR", new Integer(0));
		settingsMap.put("HOV", new Integer(0));
		settingsMap.put("HW", new Integer(0));
		if (route.getForbidToll()) {
			settingsMap.put("TR", new Integer(1));
		}
		else {
			settingsMap.put("TR", new Integer(0));
		};
		if (route.getForbidFerries()) {
			settingsMap.put("CU", new Integer(2));
		}
		else {
			settingsMap.put("CU", new Integer(1));
		};
        JSONObject settingsObject = new JSONObject(settingsMap);

		
		JSONArray coordsArray = new JSONArray();
		for (Waypoint waypoint : route.getWaypoints()) {
            coordsArray.add(waypoint.getLat() + "," + waypoint.getLon());
		}

        HashMap<String,Object> routeMap = new HashMap<String,Object>();
        routeMap.put("coords", coordsArray);
        routeMap.put("settings", settingsObject);
        routeMap.put("v", new Integer(1));
        JSONObject routeObject = new JSONObject(routeMap);

        HashMap<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("route", routeObject);
        JSONObject jsonObject = new JSONObject(jsonMap);

        try {
            FileWriter fileWriter = new FileWriter(cruiserFile);
            System.out.println("Writing JSON object to file");
            System.out.println("-----------------------");
            System.out.print(jsonObject);
 
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
