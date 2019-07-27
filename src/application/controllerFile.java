/**
 * 
 */
package application;
/**
 * Sample Skeleton for 'createPlaylist.fxml' Controller Class
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class controllerFile implements Initializable {

	@FXML // fx:id="csong"
	private Label csong; // Value injected by FXMLLoader
	@FXML // fx:id="enterButton"
	private Button enterButton; // Value injected by FXMLLoader

	@FXML // fx:id="label1"
	private Label label1; // Value injected by FXMLLoader

	@FXML // fx:id="playlistName"
	private TextField playlistName; // Value injected by FXMLLoader
	@FXML
	private ListView<String> playlistView;
	private ObservableList<String> playlistAdd = FXCollections.observableArrayList();

	@FXML // fx:id="songs"
	private ListView<String> songs; // Value injected by FXMLLoader

	@FXML // fx:id="playlists"
	private ListView<String> playlists; // Value injected by FXMLLoader
	private ObservableList<String> added = FXCollections.observableArrayList();
	@FXML
	private Button removeButton;
	@FXML
	private Button createPlaylist;
	@FXML // fx:id="open"
	private Button add; // Value injected by FXMLLoader
	@FXML
	private Button backButton;
	@FXML
	private MediaView mediaPlayer;
	private MediaPlayer mp;
	private Media me;

	File inputFile = new File("../MP3 Players/src/application/Media.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	ObservableList<String> tunes = FXCollections.observableArrayList();
	Document doc;
	NodeList nList;
	Node thing;
	Element stuff;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());
			nList = doc.getElementsByTagName("song");
			for (int i = 0; i < nList.getLength(); i++) {
				thing = nList.item(i);
				// System.out.println(nList.getLength());
				// System.out.println(thing.getNodeName());
				stuff = (Element) thing;
				tunes.add(stuff.getAttribute("name").toString());
				// System.out.println(stuff.getAttributeNode("name"));

				// System.out.println(tunes.size());

			}
			songs.setItems(tunes);

		} catch (Exception e) {
		}
	}

	public void back(Event click) {
		if (mp != null) {
			mp.stop();
		}
		Stage primaryStage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/startWindow.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("iBucks MP3 Player");
			primaryStage.setAlwaysOnTop(true);
			primaryStage.setResizable(false);
			((javafx.scene.Node) (click.getSource())).getScene().getWindow().hide();

			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void add() {
		if (mp != null) {
			mp.stop();
		}
		String selection = songs.getSelectionModel().getSelectedItem();
		for (int i = 0; i < nList.getLength(); i++) {
			thing = nList.item(i);
			stuff = (Element) thing;

			if (selection.equals(stuff.getAttribute("name").toString())) {

				String path = new String(nList.item(i).getChildNodes().item(1).getTextContent());

				added.add(selection);
				playlists.setItems(added);
				me = new Media(new File(path).toURI().toString());
				mp = new MediaPlayer(me);
				// mp.play();

			}
		}
	}

	public void remove() {
		if (mp != null) {
			mp.stop();
		}
		String selection = playlists.getSelectionModel().getSelectedItem();
		for (int i = 0; i < added.size(); i++) {
			if (selection.equals(added.get(i))) {
				added.remove(i);
				playlists.setItems(added);
			}
		}
	}

	public void createPlaylist() {
		ArrayList<String> stuffs = new ArrayList<>();

		stuffs.add(added.toString());

		playlistAdd.addAll(stuffs);
		playlistView.setItems(playlistAdd);
		if (added != null) {
			added.clear();
		}

	}

	public void playPlaylist() {

		if (playlistView.isPressed()) {

			for (int j = 0; j < playlistView.getItems().size(); j++) {
				for (int i = 0; i < nList.getLength(); i++) {
					thing = nList.item(i);
					stuff = (Element) thing;
					if (stuff.getAttribute("name").equals(playlistView.getItems().get(j))) {

						String path = new String(nList.item(i).getChildNodes().item(1).getTextContent());
						ArrayList<String> burp = new ArrayList<>();
						burp.add(path);
						for (int k = 0; k < burp.size(); k++) {

							me = new Media(new File(burp.get(i)).toURI().toString());
							mp = new MediaPlayer(me);
							mp.play();
						}

					}
				}
			}
		}
	}

}
