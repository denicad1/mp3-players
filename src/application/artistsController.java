/**
 * 
 */
package application;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer.Status;

/**
 * @author Anthony Denicolo
 *
 */
public class artistsController implements Initializable {
	@FXML
	private MediaView mediaPlayer;
	private MediaPlayer mp;
	private Media me;
	@FXML
	Button pauseButton = new Button();
	@FXML
	Button backButton = new Button();
	@FXML
	CheckBox shuffleBox;
	@FXML
	public ListView<String> songs;
	File inputFile = new File("../MP3 Players/src/application/Media.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;
	ObservableList<String> tunes = FXCollections.observableArrayList();
	Document doc;
	NodeList nList;
	Node thing;
	Element stuff;
	Set<String> multiple;
	String artistList;

	public void listSongs() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			nList = doc.getElementsByTagName("song");
			for (int i = 0; i < nList.getLength(); i++) {
				thing = nList.item(i);
				stuff = (Element) thing;
				artistList = stuff.getElementsByTagName("artist").item(0).getTextContent();
				multiple = new HashSet<>();
				tunes.add(artistList);
				multiple.addAll(tunes);
				tunes.clear();
				tunes.addAll(multiple);
			}
			songs.setItems(tunes);

		} catch (Exception e) {
		}

	}

	/**
	 * figure out how to make it pop up all the songs by a certain artist. using a
	 * stack seems to be the best way to do it so they play well
	 */
	public void artistSelection(MouseEvent click) {
		if (mp != null) {
			mp.stop();
		}

		String selection = songs.getSelectionModel().getSelectedItem();
		boolean alreadyExecuted = false;
		for (int i = 0; i < nList.getLength(); i++) {
			thing = nList.item(i);
			stuff = (Element) thing;
			String artistSelect = stuff.getElementsByTagName("artist").item(0).getTextContent();

			if (selection.equals(artistSelect)) {
				if (!alreadyExecuted) {
					tunes.clear();
					alreadyExecuted = true;
				}

				String path = new String(stuff.getAttribute("name").toString());
				tunes.add(path);
				songs.setItems(tunes);

			}

		}

		for (int i = 0; i < nList.getLength(); i++) {
			thing = nList.item(i);
			stuff = (Element) thing;
			String songname = stuff.getElementsByTagName("path").item(0).getTextContent();

			if (selection.equals(stuff.getAttribute("name").toString())) {

				String path = new String(songname);
				// stuff.getChildNodes().item(1).getTextContent());
				me = new Media(new File(path).toURI().toString());
				mp = new MediaPlayer(me);
				mp.play();
				break;

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
				tunes.add(stuff.getChildNodes().item(5).getTextContent());
				multiple.addAll(tunes);
				tunes.clear();
				tunes.addAll(multiple);
				// System.out.println(stuff.getAttributeNode("name"));

				// System.out.println(tunes.size());

			}
			songs.setItems(tunes);

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

	public void shuffle(Event click) {
		if (mp != null) {
			mp.stop();
		}
		Random rand = new Random();

		Stack<String> shuffle = new Stack<>();
		int big = rand.nextInt(tunes.size());
		for (int i = 0; i < tunes.size(); i++) {
			for (int j = 0; j < nList.getLength(); j++) {
				System.out.println(tunes.get(i) + "tunes");
				System.out.println(nList.item(j).getChildNodes().item(0).getTextContent() + "nList");
				if (tunes.get(i).equals(nList.item(j).getAttributes().toString())) {
					shuffle.push(nList.item(j).getChildNodes().item(1).getTextContent());

				}
			}

		}

		ListIterator<String> it = shuffle.listIterator();

		while (it.hasNext()) {
			String path = shuffle.get(rand.nextInt(big)).toString();
			me = new Media(new File(path).toURI().toString());
			mp = new MediaPlayer(me);
			mp.play();

		}
	}

}
