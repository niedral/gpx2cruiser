package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Route;
import util.CruiserExporter;
import util.CruiserLoader;
import util.GpxExporter;
import util.GpxLoader;

public class MainWindowController {
	
	//Views
	@FXML private Button openCruiserButton;
	@FXML private Button openGpxButton;
	@FXML private ImageView routeCruiserImage;
	@FXML private ImageView routeGpxImage;
	@FXML private Label waypointCounterCruiserLabel;
	@FXML private Label waypointCounterGpxLabel;
	@FXML private CheckBox highwayCheckbox;
	@FXML private CheckBox tollCheckbox;
	@FXML private CheckBox ferryCheckbox;
	@FXML private Button saveCruiserButton;
	@FXML private Button saveGpxButton;
	
	public Route routeGpx;
	public Route routeCruiser;
	
	private Main main;
	private Stage primaryStage;
	
	public void setMain(Main main) {
		this.main = main;
		this.primaryStage = this.main.getPrimaryStage();
		this.primaryStage.setTitle("GPX2Cruiser");
		this.routeGpx = null;
		this.routeCruiser = null;
	}
	
	@FXML public void handleOpenGpxButton() {
		System.out.println("Open button clicked");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open GPX file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("GPX", "*.gpx"));
		File file = fileChooser.showOpenDialog(primaryStage);
		if (file != null) {
			this.routeGpx = new Route(file);
			this.routeGpx.setWaypoints(GpxLoader.loadWaypoints(this.routeGpx.getFile()));
			if (this.routeGpx.getWaypoints() != null) {
				Image image = new Image(getClass().getClassLoader().getResource("ok.png").toString(), true);
				routeGpxImage.setImage(image);
				this.waypointCounterGpxLabel.setText(Integer.toString(this.routeGpx.getWaypoints().size()));
				if (this.routeGpx.getWaypoints().size() <= 100) {
					this.waypointCounterGpxLabel.setTextFill(Color.web("#608b32"));
					this.saveCruiserButton.setDisable(false);
					this.highwayCheckbox.setDisable(false);
					this.highwayCheckbox.setSelected(this.routeGpx.getForbidHighway());
					this.tollCheckbox.setDisable(false);
					this.tollCheckbox.setSelected(this.routeGpx.getForbidToll());
					this.ferryCheckbox.setDisable(false);
					this.ferryCheckbox.setSelected(this.routeGpx.getForbidFerries());
				}
				else {
					this.waypointCounterGpxLabel.setTextFill(Color.web("#961c00"));
					this.saveCruiserButton.setDisable(true);
					this.highwayCheckbox.setDisable(true);
					this.tollCheckbox.setDisable(true);
					this.ferryCheckbox.setDisable(true);
				}
			}
			else {
				Image image = new Image(getClass().getClassLoader().getResource("error.png").toString(), true);
				routeGpxImage.setImage(image);
			}
		};
	}

	@FXML public void handleOpenCruiserButton() {
		System.out.println("Open cruiser button clicked");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Navigon Cruiser file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CRUISER", "*.cruiser"));
		File file = fileChooser.showOpenDialog(primaryStage);
		if (file != null) {
			this.routeCruiser = new Route(file);
			this.routeCruiser.setWaypoints(CruiserLoader.loadWaypoints(this.routeCruiser.getFile()));
			if (this.routeCruiser.getWaypoints() != null) {
				Image image = new Image(getClass().getClassLoader().getResource("ok.png").toString(), true);
				routeCruiserImage.setImage(image);
				this.waypointCounterCruiserLabel.setText(Integer.toString(this.routeCruiser.getWaypoints().size()));
				this.waypointCounterCruiserLabel.setTextFill(Color.web("#608b32"));
				this.saveGpxButton.setDisable(false);
			}
			else {
				Image image = new Image(getClass().getClassLoader().getResource("error.png").toString(), true);
				routeCruiserImage.setImage(image);
			}
		};
	}

	@FXML public void handleHighwayCheckbox() {
		System.out.println("Set forbid highway to " + this.highwayCheckbox.isSelected());
		this.routeGpx.setForbidHighway(this.highwayCheckbox.isSelected());
	}

	@FXML public void handleTollCheckbox() {
		System.out.println("Set forbid toll roads to " + this.tollCheckbox.isSelected());
		this.routeGpx.setForbidToll(this.tollCheckbox.isSelected());
	}

	@FXML public void handleFerryCheckbox() {
		System.out.println("Set forbid ferries to " + this.ferryCheckbox.isSelected());
		this.routeGpx.setForbidFerries(this.ferryCheckbox.isSelected());
	}

	@FXML public void handleSaveCruiserButton() {
		System.out.println("Save Cruiser button clicked");
		String targetFilename = routeGpx.getFile().getName();
		int pos = targetFilename.lastIndexOf(".");
		if (pos > 0) {
		    targetFilename = targetFilename.substring(0, pos);
		}
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Navigon Cruiser file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CRUISER", "*.cruiser"));
		fileChooser.setInitialFileName(targetFilename + ".cruiser");
		File file = fileChooser.showSaveDialog(primaryStage);
		if (file != null) {
			CruiserExporter.saveRoute(file, this.routeGpx);
		};
	}

	@FXML public void handleSaveGpxButton() {
		System.out.println("Save GPX button clicked");
		String targetFilename = routeCruiser.getFile().getName();
		int pos = targetFilename.lastIndexOf(".");
		if (pos > 0) {
		    targetFilename = targetFilename.substring(0, pos);
		}
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save GPX file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("GPX", "*.gpx"));
		fileChooser.setInitialFileName(targetFilename + ".gpx");
		File file = fileChooser.showSaveDialog(primaryStage);
		if (file != null) {
			GpxExporter.saveRoute(file, this.routeCruiser);
		};
	}

}
