/**
 * FXML Controller class
 *
 * @author Modestas
 */
package minesweeper;

import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class MainController implements Initializable
{
    private String levelConfigFile = "LevelConfig.json";
    
    private HashMap<String, Integer> levelsMap;
    
    private CMineField mineField;
    
    @FXML
    private Button startGameButton;
    @FXML
    private ChoiceBox<String> levelSelectComboBox;
    @FXML
    private Label selectLevelLabel;
    @FXML
    private AnchorPane mineFieldHolder; 
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.levelsMap = new HashMap();
        
        parseConfig(this.levelConfigFile);
        
        setupLevels();
    }    

    @FXML
    private void onStartNewGame(ActionEvent event)
    {
        this.startGameButton.setVisible(false);
        this.selectLevelLabel.setVisible(false);
        this.levelSelectComboBox.setVisible(false);
        
        this.backButton.setVisible(true);
        this.mineFieldHolder.setVisible(true);
              
        int minesCount = this.levelsMap.get(this.levelSelectComboBox.getSelectionModel().getSelectedItem());
        this.mineField = new CMineField(this.mineFieldHolder, minesCount);
    }
    
    private void parseConfig(String configPath)
    {
        try
        {
            FileReader reader = new FileReader(configPath);
            
            JSONParser jsonParser = new JSONParser();
            JSONObject config = (JSONObject) jsonParser.parse(reader);
            
            JSONArray levels = (JSONArray) config.get("Levels");
            
            for (Object obj : levels)
            {
               JSONObject level = (JSONObject) obj;
               
               String levelName = (String) level.get("LevelName");
               int minesCountInLevel =  ((Long)level.get("MinesCount")).intValue();
               
               this.levelsMap.put(levelName, minesCountInLevel);
            }          
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }  
    }
    
    private void setupLevels()
    {
        for(String levelName : this.levelsMap.keySet())
        {
            this.levelSelectComboBox.getItems().add(levelName);
        }
        
        this.levelSelectComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void onBack(ActionEvent event)
    {
        this.startGameButton.setVisible(true);
        this.selectLevelLabel.setVisible(true);
        this.levelSelectComboBox.setVisible(true);
        
        this.backButton.setVisible(false);
        this.mineFieldHolder.setVisible(false);
    }
}
