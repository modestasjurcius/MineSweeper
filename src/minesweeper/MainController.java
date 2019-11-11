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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class MainController implements Initializable
{
    private String levelConfigFile = "LevelConfig.json";
    
    private ArrayList<SLevel> levels;
    
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
        this.levels = new ArrayList<SLevel>();
        
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
        
        this.mineField = new CMineField(this.mineFieldHolder, getCurrentLevel());
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
               int fieldsCountInLevel = ((Long)level.get("FieldsCount")).intValue();
               int columnsCountInLevel = ((Long)level.get("MinesColumnsCount")).intValue();
               SLevel sLevel = new SLevel(levelName, fieldsCountInLevel, minesCountInLevel, columnsCountInLevel);
               
               this.levels.add(sLevel);
            }          
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }  
    }
    
    private void setupLevels()
    {
        for(SLevel level : this.levels)
        {
            this.levelSelectComboBox.getItems().add(level.levelName);
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
    
    private SLevel getCurrentLevel()
    { 
        return this.levels.get(this.levelSelectComboBox.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void onPressed(MouseEvent event)
    {
    }
}
