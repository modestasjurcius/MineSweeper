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
    
    private int minesCount;
    
    public CMineField(AnchorPane mineFieldHolder, int minesCount)
    {
        this.mineFieldHolder = mineFieldHolder;
        this.minesCount = minesCount;
        
        setupMineField();
    }
    
    private void setupMineField()
    {
        int rows = 10;
        int columns = minesCount / rows;
        
        double width = this.mineFieldHolder.getWidth() / rows;
        double height = this.mineFieldHolder.getHeight() / columns;
        
        Rectangle rect = null;
        
        for(int i = 0; i < columns; ++i)
        {
            for(int j = 0; j < rows; ++j)
            {
                rect = new Rectangle(width*j, height*i, width, height);
                
                rect.setStroke(Color.RED);

                this.mineFieldHolder.getChildren().add(rect);
            }
        }
    }
}
