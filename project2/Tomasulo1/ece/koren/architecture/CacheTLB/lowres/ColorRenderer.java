import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.*;
import javax.swing.table.*;
/**
 * @author Rakesh Kothari
 * @author Siddhartha Bunga
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

class ColorRenderer extends DefaultTableCellRenderer implements ActionListener
{
    private JTable table;
    private AbstractTableModel model;
    private Map colors;
    private boolean isBlinking = true;
    private Timer timer;
    private Point location;

    public ColorRenderer(JTable table)
    {
        this.table = table;
        model = (AbstractTableModel)table.getModel();
        colors = new HashMap();
        location = new Point();
    }

    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (hasFocus || !isBlinking )
            setBackground( table.getBackground() );
        else
            setBackground( getCellColor(row, column) );

        return this;
    }

    public Color getCellColor(int row, int column)
    {
        column = table.convertColumnIndexToModel(column);

        //  Get cell color

        Object key = getKey(row, column);
        Object o = colors.get( key );

        if (o != null)
            return (Color)o;

        //  Get row color

        key = getKey(row, -1);
        o = colors.get( key );

        if (o != null)
            return (Color)o;

        //  Get column color

        key = getKey(-1, column);
        o = colors.get( key );

        if (o != null)
            return (Color)o;

        //  Use default
        return null;
    }

    public void setCellColor(int row, int column, Color color)
    {
        Point key = new Point(row, column);
        colors.put(key, color);
    }

    public void setColumnColor(int column, Color color)
    {
        setCellColor(-1, column, color);
    }

    public void setRowColor(int row, Color color)
    {
        setCellColor(row, -1, color);
    }

    private Object getKey(int row, int column)
    {
    	location.x = row;
    	location.y = column;
    	return location;
    }

    public void startBlinking(int interval)
    {
        timer = new Timer(interval, this);
        timer.start();
    }

    public void stopBlinking()
    {
    	if(timer!=null)
    		timer.stop();
    }

    public void actionPerformed(ActionEvent e)
    {
        isBlinking = !isBlinking;

        Iterator it = colors.keySet().iterator();

        while ( it.hasNext() )
        {
            Point key = (Point)it.next();
            int row = key.x;
            int column = key.y;

            if (column == -1)
            {
                model.fireTableRowsUpdated(row, row);
            }
            else if (row == -1)
            {
                int rows = table.getRowCount();

                for (int i = 0; i < rows; i++)
                {
                    model.fireTableCellUpdated(i, column);
                }
            }
            else
            {
                model.fireTableCellUpdated(row, column);
            }
        }
    }
}

