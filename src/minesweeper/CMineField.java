/**
 *
 * @author Modestas
 */

package minesweeper;

import java.util.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CMineField
{
    private AnchorPane mineFieldHolder;
    
    private ArrayList<CMine> mines;
    
    private SLevel currentLevel;
    
    
    public CMineField(AnchorPane mineFieldHolder, SLevel level)
    {
        this.mineFieldHolder = mineFieldHolder;
        this.currentLevel = level;
        
        setupMineField();
    }
    
    private void setupMineField()
    {
        int rows = this.currentLevel.fieldsCount / this.currentLevel.columnsCount;
        
        double width = this.mineFieldHolder.getWidth() / this.currentLevel.columnsCount;
        double height = this.mineFieldHolder.getHeight() / rows;
        
        Rectangle rect = null;
        
        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < this.currentLevel.columnsCount; ++j)
            {
                rect = new Rectangle(width*j, height*i, width, height);
                
                rect.setStroke(Color.RED);

                this.mineFieldHolder.getChildren().add(rect);
            }
        }
    }
}
