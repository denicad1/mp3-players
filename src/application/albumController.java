package application;
/**
 * Sample Skeleton for 'albums.fxml' Controller Class
 */

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;

public class albumController implements Initializable {
	@FXML
	private MediaView mediaPlayer;
	private MediaPlayer mp;
	private Media me;
	@FXML
	private Button pauseButton;
	@FXML // fx:id="backButton"
	private Button backButton; // Value injected by FXMLLoader
	@FXML
	CheckBox shuffleBox;

	@FXML // fx:id="albumView"
	private ListView<String> albumView; // Value injected by FXMLLoader
	File inputFile = new File("../MP3 Players/src/application/Media.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	Document doc;
	NodeList nList;
	Node thing;
	Element stuff;
	Set<String> multiple;
	ObservableList<String> tunes = FXCollections.observableArrayList();
	static String albumList;

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
				albumList = stuff.getElementsByTagName("album").item(0).getTextContent();
				multiple = new HashSet<>();
				tunes.add(albumList);
				multiple.addAll(tunes);
				tunes.clear();
				tunes.addAll(multiple);
				// System.out.println(stuff.getAttributeNode("name"));

				// System.out.println(tunes.size());

			}
			albumView.setItems(tunes);

		} catch (Exception e) {
		}

	}

	public void albumSelection(MouseEvent click) {
		if (mp != null) {
			mp.stop();
		}

		String selection = albumView.getSelectionModel().getSelectedItem();
		boolean alreadyExecuted = false;
		for (int i = 0; i < nList.getLength(); i++) {
			thing = nList.item(i);
			stuff = (Element) thing;

			if (selection.equals(stuff.getElementsByTagName("album").item(0).getTextContent())) {
				if (!alreadyExecuted) {
					tunes.clear();
					alreadyExecuted = true;
				}

				String path = new String(stuff.getAttribute("name").toString());
				tunes.add(path);
				albumView.setItems(tunes);

			}

		}
		selection = albumView.getSelectionModel().getSelectedItem();
		for (int i = 0; i < nList.getLength(); i++) {
			thing = nList.item(i);
			stuff = (Element) thing;

			if (selection.equals(stuff.getAttribute("name").toString())) {

				String path = new String(nList.item(i).getChildNodes().item(1).getTextContent());
				me = new Media(new File(path).toURI().toString());
				mp = new MediaPlayer(me);
				mp.play();
				break;

			}
		}
	}

	public void back(Event click) {
		if (mp != null) {
			mp.stop();
		}
		try {
			tunes.clear();
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
				multiple = new HashSet<>();
				tunes.add(stuff.getChildNodes().item(7).getTextContent());
				multiple.addAll(tunes);
				tunes.clear();
				tunes.addAll(multiple);
				// System.out.println(stuff.getAttributeNode("name"));

				// System.out.println(tunes.size());

			}
			albumView.setItems(tunes);

		} catch (Exception e) {
		}
		MouseEvent trigger = (MouseEvent) click;
		if (trigger.getClickCount() == 2) {
			Stage stage = (Stage) backButton.getScene().getWindow();
			stage.close();
			try {
				Stage primaryStage = new Stage();
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

	}

	public void pause(MouseEvent click) {
		if (mp.getStatus() == Status.PLAYING) {
			mp.pause();
		} else {
			mp.play();
		}

	}

}
