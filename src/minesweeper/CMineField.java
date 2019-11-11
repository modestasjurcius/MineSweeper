/**
 *
 * @author Modestas
 */

package minesweeper;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class CMineField
{
    private AnchorPane mineFieldHolder;
    
    private ArrayList<CFieldUnit> fields;
    
    private SLevel currentLevel;
    
    private double fieldWidth;
    private double fieldHeight;
    
    private int[][] fieldMatrix;
    private int diggedFieldsCount;
    
    private boolean isGameFinished;
    private boolean isVictory;
    
    public CMineField(AnchorPane mineFieldHolder, SLevel level)
    {
        this.isGameFinished = false;
        this.isVictory = false;
        this.diggedFieldsCount = 0;
        this.mineFieldHolder = mineFieldHolder;
        this.currentLevel = level;
        this.fields = new ArrayList<CFieldUnit>();
        
        setupMineField();
    }
    
    private void setupMineField()
    {
        int rows = this.currentLevel.fieldsCount / this.currentLevel.columnsCount;
        
        setupFieldMatrix(rows, this.currentLevel.columnsCount);
        
        this.fieldWidth = this.mineFieldHolder.getWidth() / this.currentLevel.columnsCount;
        this.fieldHeight = this.mineFieldHolder.getHeight() / rows;
        
        Rectangle rect = null;
        
        for(int i = 0; i < this.currentLevel.rowsCount; ++i)
        {
            for(int j = 0; j < this.currentLevel.columnsCount; ++j)
            {
                try
                {
                    rect = new Rectangle(this.fieldWidth * j, this.fieldHeight * i, this.fieldWidth, this.fieldHeight);
                    rect.setStroke(Color.RED);
                    
                    
                    Label lbl = new Label();
                    lbl.setLayoutX(rect.getX());
                    lbl.setLayoutY(rect.getY());
                    lbl.setText(String.valueOf(this.fieldMatrix[i][j]));
                    lbl.setFont(Font.font(15));
                    lbl.setContentDisplay(ContentDisplay.CENTER);
                    lbl.setTextAlignment(TextAlignment.CENTER);
                    lbl.setVisible(false);

                    this.mineFieldHolder.getChildren().add(rect);
                    this.mineFieldHolder.getChildren().add(lbl);

                    CFieldUnit field = new CFieldUnit(i, j, this.fieldMatrix[i][j], rect, lbl);
                    if (this.fieldMatrix[i][j] == 10)
                    {
                        field.setMine(true);
                    }

                    this.fields.add(field);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private void setupFieldMatrix(int rows, int columns)
    {
        this.fieldMatrix = new int[rows][columns];
        
        setupMinesInMatrix(rows, columns);
        
        setupNumbersInMatrix(rows, columns);
    }
    
    private void setupMinesInMatrix(int rows, int columns)
    {
       int minesCount = 0;
        
        while(minesCount < this.currentLevel.minesCount)
        {
            int randomRow = ThreadLocalRandom.current().nextInt(0, rows);
            int randomColumn = ThreadLocalRandom.current().nextInt(0, columns);
            
            if(this.fieldMatrix[randomRow][randomColumn] != 10)
            {
              this.fieldMatrix[randomRow][randomColumn] = 10;
              minesCount++;
            }
        } 
    }
    
    private void setupNumbersInMatrix(int rows, int columns)
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                if(this.fieldMatrix[i][j] != 10)
                {
                    try
                    {
                       this.fieldMatrix[i][j] = getBombsCountAroundField(i, j); 
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    } 
                }
            }
        }
    }
    
    private int getBombsCountAroundField(int row, int column)
    {
        int count = 0;

        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                int rowToCheck = row + i;
                int columnToCheck = column + j;

                if (i == 0 && j == 0)
                {
                    continue;
                }

                if (rowToCheck > -1 && rowToCheck < this.currentLevel.rowsCount)
                {
                    if (columnToCheck > -1 && columnToCheck < this.currentLevel.columnsCount)
                    {
                        try
                        {
                            if (this.fieldMatrix[rowToCheck][columnToCheck] == 10)
                            {
                                count++;
                            }
                        } catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        return count;
    }
    
    public void cleanUpMineField()
    {
        this.mineFieldHolder.getChildren().clear();
    }
    
    public void onPressed(double x, double y)
    {
        if(this.isGameFinished)
        {
            return;
        }
        
        int column = 0;
        while(x > this.fieldWidth)
        {
            x -= this.fieldWidth;
            column++;
        }
        
        int row = 0;
        while(y > this.fieldHeight)
        {
            y -= this.fieldHeight;
            row++;
        }
        
        int rectIndex = getFieldIndexFromMatrix(row, column);
        
        CFieldUnit field = this.fields.get(rectIndex);
        if(field != null)
        {
            if(field.isMine())
            {
                onGameEnd(false);
            }
            else
            {
                field.onPressed();
                
                if(field.getMineCount() == 0)
                {
                    digEmptyFieldsFrom(field);
                }
                
                this.diggedFieldsCount++;
                
                if(this.diggedFieldsCount == this.currentLevel.emptyFieldsCount)
                {
                    onGameEnd(true);
                }
            }
        }
    }
    
    private void digEmptyFieldsFrom(CFieldUnit field)
    {
        int row = field.getRow();
        int column = field.getColumn();
        
        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                int rowToCheck = row + j;
                int columnToCheck = column + i;

                if (i == 0 && j == 0)
                {
                    continue;
                }

                if (rowToCheck > -1 && rowToCheck < this.currentLevel.rowsCount)
                {
                    if (columnToCheck > -1 && columnToCheck < this.currentLevel.columnsCount)
                    {
                        try
                        {
                            int fieldId = getFieldIndexFromMatrix(rowToCheck, columnToCheck);
                            CFieldUnit fieldToDig = this.fields.get(fieldId);
                            
                            if(fieldToDig != null)
                            {
                                if(!fieldToDig.isDigged())
                                {
                                    fieldToDig.onPressed();
                                    this.diggedFieldsCount++;
                                    
                                    if(fieldToDig.getMineCount() == 0)
                                    {
                                        digEmptyFieldsFrom(fieldToDig);
                                    }
                                }     
                            }
                        } catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    
    public void showAllMinessInField()
    {
        for(CFieldUnit field : this.fields)
        {
            if(field.isMine())
            {
                field.onPressed();
            }
        }
    }
    
    public void onGameEnd(boolean isWin)
    {
        this.isGameFinished = true;
        this.isVictory = isWin;
        
        if(!this.isVictory)
        {
            showAllMinessInField();
        }
    }
    
    public boolean isVictory()
    {
        return this.isVictory;
    }
    
    public boolean isGameFinished()
    {
        return this.isGameFinished;
    }
    
    private int getFieldIndexFromMatrix(int row, int column)
    {
        return row * this.currentLevel.columnsCount + column;
    }
}
