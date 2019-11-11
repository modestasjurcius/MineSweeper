/**
 *
 * @author Modestas
 */
package minesweeper;


public class SLevel
{
    public String levelName;
    public int fieldsCount;
    public int minesCount;
    public int columnsCount;
    
    public SLevel()
    {}
    
    public SLevel(String name, int fieldsCount, int minesCount, int columnsCount)
    {
        this.levelName = name;
        this.fieldsCount = fieldsCount;
        this.minesCount = minesCount;
        this.columnsCount = columnsCount;
    }
}
