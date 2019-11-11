/**
 *
 * @author Modestas
 */
package minesweeper;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CFieldUnit
{
    private int columnId;
    private int rowId;
    private int mineCount;
    
    private boolean isMine;
    private boolean isDigged;
    
    private Rectangle rectangle;
    private Label mineTextLabel;
    
    public CFieldUnit(int rId, int cId, int mCnt, Rectangle rec, Label txtLabel)
    {
        this.columnId = cId;
        this.rowId = rId;
        this.mineCount = mCnt;
        this.rectangle = rec;
        this.mineTextLabel = txtLabel;
        
        this.isDigged = false;
        
        setMine(false);
    }
    
    public void setMine(boolean isMine)
    {
        this.isMine = isMine;
        if(this.isMine)
        {
            this.mineTextLabel.setText("B");
        }
    }
    
    public boolean isMine()
    {
        return this.isMine;
    }
    
    public void onPressed()
    {
        if(this.isMine)
        {
            this.rectangle.setFill(Color.RED);
        }
        else
        {
            this.rectangle.setFill(Color.BURLYWOOD);
            this.mineTextLabel.setVisible(true);
            this.isDigged = true;
        }
    }
    
    public boolean isDigged()
    {
        return this.isDigged;
    }
    
    public int getMineCount()
    {
        return this.mineCount;
    }
    
    public int getRow()
    {
        return this.rowId;
    }
    
    public int getColumn()
    {
        return this.columnId;
    }
}
