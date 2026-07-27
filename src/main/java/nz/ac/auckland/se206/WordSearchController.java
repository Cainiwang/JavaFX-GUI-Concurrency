package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.dict.DictionaryLookup;
import nz.ac.auckland.se206.dict.WordInfo;
import nz.ac.auckland.se206.dict.WordNotFoundException;
import nz.ac.auckland.se206.panes.WordPane;

public class WordSearchController {

  @FXML
  private Button searchForDefinitionsButton;
  @FXML
  private ProgressBar progressBar;
  @FXML
  private Accordion resultsAccordion;
  @FXML
  private TextField wordsTextfield;
  @FXML
  private VBox backgroundPane;

  public void searchWords() {
    resultsAccordion.getPanes().clear();
    progressBar.setProgress(0);

    String[] queryWords = wordsTextfield.getText().split("\\s+");

    int numberOfWords = queryWords.length;

    if (numberOfWords == 0) {
      Alert alert = new Alert(AlertType.ERROR, "You need to provide at least one word.");
      alert.showAndWait();
      return;
    }

    long startTime = System.currentTimeMillis();

    for (int s = 0; s < numberOfWords; s++) {
      String query = queryWords[s];
      try {
        WordInfo wordResult = DictionaryLookup.searchWordInfo(query);
        System.out.println("\"" + wordResult.getWord() + "\" has " + wordResult.getNumberOfEntries()
            + " dictionary entries.");

        TitledPane pane = WordPane.generateWordPane(query, wordResult);
        resultsAccordion.getPanes().add(pane);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (WordNotFoundException e) {
        System.out.println("\"" + e.getWord() + "\" has problems: " + e.getMessage());
        TitledPane pane = WordPane.generateErrorPane(e);
        resultsAccordion.getPanes().add(pane);
      }
      progressBar.setProgress((s + 1.0) / numberOfWords);
    }

    long time = System.currentTimeMillis() - startTime;
    System.out.println();
    System.out.println("Search took " + time + "ms");
  }

}
