package application;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/startWindow.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("iBucks MP3 Player");
			primaryStage.setAlwaysOnTop(true);
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
		/**
		 * below is the parser for the xml file. it will read out the contents of the
		 * xml file. have to see if it can retrieve mp3 data from xml. try to get a link
		 * to a mp3 file to run from this xml file if possible. figure out a way for it
		 * to run the mp3 file and grab all the information on the file at the same
		 * time. will need that info to help with playlists and shuffle.
		 */
		// File inputFile = new File("../MP3 Players/src/application/Media.xml");
		// DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		// DocumentBuilder dBuilder;
		// try {
		// dBuilder = dbFactory.newDocumentBuilder();
		// Document doc;
		// doc = dBuilder.parse(inputFile);
		// doc.getDocumentElement().normalize();
		// System.out.println("Root element :" +
		// doc.getDocumentElement().getNodeName());
		// NodeList nList = doc.getElementsByTagName("song");
		// for (int i = 0; i < nList.getLength(); i++) {
		// Node thing = nList.item(i);
		// // System.out.println(thing.getNodeName());
		// if (thing.getNodeType() == Node.ELEMENT_NODE) {
		// Element stuff = (Element) thing;
		// System.out.println(stuff.getElementsByTagName("path").item(0).getTextContent());
		// System.out.println(stuff.getElementsByTagName("genre").item(0).getTextContent());
		// System.out.println(stuff.getElementsByTagName("artist").item(0).getTextContent());
		// System.out.println(stuff.getElementsByTagName("album").item(0).getTextContent());
		//
		// }
		// }
		//
		// } catch (ParserConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SAXException | IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public void handle() {

	}
}
