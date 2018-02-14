package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Route {
	
	private File routeFile;
	private Boolean forbidHighway;
	private Boolean forbidToll;
	private Boolean forbidFerries;
	private List<Waypoint> waypoints;
	
	public Route(File routeFile) {
		this.routeFile = routeFile;
		this.forbidHighway = true;
		this.forbidToll = true;
		this.forbidFerries = true;
		this.waypoints = new ArrayList<Waypoint>();
	}
	
	public File getFile() {
		return this.routeFile;
	}
	
	public Boolean getForbidHighway() {
		return this.forbidHighway;
	}
	
	public Boolean getForbidToll() {
		return this.forbidToll;
	}
	
	public Boolean getForbidFerries() {
		return this.forbidFerries;
	}
	
	public List<Waypoint> getWaypoints() {
		return this.waypoints;
	}
	
	public void setFile(File routeFile) {
		this.routeFile = routeFile;
	}
	
	public void setForbidHighway(Boolean forbidHighway) {
		this.forbidHighway = forbidHighway;
	}
	
	public void setForbidToll(Boolean forbidToll) {
		this.forbidToll = forbidToll;
	}
	
	public void setForbidFerries(Boolean forbidFerries) {
		this.forbidFerries = forbidFerries;
	}

	public void setWaypoints(List<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}
	
}
