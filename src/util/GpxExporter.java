package util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Route;
import model.Waypoint;

public class GpxExporter {

	public static final void saveRoute(File gpxFile, Route route) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("gpx");
			doc.appendChild(rootElement);

			Attr attr = doc.createAttribute("xmlns");
			attr.setValue("http://www.topografix.com/GPX/1/1");
			rootElement.setAttributeNode(attr);

			attr = doc.createAttribute("version");
			attr.setValue("1.1");
			rootElement.setAttributeNode(attr);

			attr = doc.createAttribute("creator");
			attr.setValue("Rhoenschrat - GPX2Cruiser");
			rootElement.setAttributeNode(attr);
				
			attr = doc.createAttribute("xmlns:xsi");
			attr.setValue("http://www.w3.org/2001/XMLSchema-instance");
			rootElement.setAttributeNode(attr);
				
			attr = doc.createAttribute("xsi:schemaLocation");
			attr.setValue("http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd");
			rootElement.setAttributeNode(attr);

			Element metaElement = doc.createElement("metadata");
			rootElement.appendChild(metaElement);

			Element linkElement = doc.createElement("link");
			metaElement.appendChild(linkElement);
			attr = doc.createAttribute("href");
			attr.setValue("http://www.youtube.com/c/rhoenschrat");
			linkElement.setAttributeNode(attr);

			Element textElement = doc.createElement("text");
			linkElement.appendChild(textElement);
			textElement.appendChild(doc.createTextNode("Rhoenschrat | Einfach nur Mobbedfahr'n"));

			Element routeElement = doc.createElement("rte");
			rootElement.appendChild(routeElement);

			Element nameElement = doc.createElement("name");
			routeElement.appendChild(nameElement);
			nameElement.appendChild(doc.createTextNode(gpxFile.getName()));
				
			Integer waypointCount = 0;
			Element waypointElement;
			Element symElement;
			for (Waypoint waypoint : route.getWaypoints()) {
				waypointCount++;
				waypointElement = doc.createElement("rtept");
					
				attr = doc.createAttribute("lat");
				attr.setValue(waypoint.getLat());
				waypointElement.setAttributeNode(attr);
					
				attr = doc.createAttribute("lon");
				attr.setValue(waypoint.getLon());
				waypointElement.setAttributeNode(attr);

				nameElement = doc.createElement("name");
				waypointElement.appendChild(nameElement);
				nameElement.appendChild(doc.createTextNode("Wpt" + waypointCount));

				symElement = doc.createElement("sym");
				waypointElement.appendChild(symElement);
				symElement.appendChild(doc.createTextNode("Flag, Red"));
					
				routeElement.appendChild(waypointElement);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(gpxFile);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} 
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		catch (TransformerException tfe) {
				tfe.printStackTrace();
		}
	}
		
}
