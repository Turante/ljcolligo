/**                                                                           
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]         
 * Created on 04/05/2008 22:40:53
 *                                                                            
 * This file is part of the Text Adventures Suite.                            
 *                                                                            
 * Text Adventures Suite is free software: you can redistribute it and/or modify          
 * it under the terms of the GNU General Public License as published by       
 * the Free Software Foundation, either version 3 of the License, or          
 * (at your option) any later version.                                        
 *                                                                            
 * Text Adventures Suite is distributed in the hope that it will be useful,               
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              
 * GNU General Public License for more details.                               
 *                                                                            
 * You should have received a copy of the GNU General Public License          
 * along with Text Adventures Suite.  If not, see <http://www.gnu.org/licenses/>.         
 *                                                                            
 * Project page: http://code.google.com/p/text-adventures-suite/              
 */

package net.bpfurtado.ljcolligo.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

public class DisposeAction implements Action
{
    public static final String ACTION_NAME = "net.bpfurtado.tas.view.DisposeAction";
    
    private Window w;

    public DisposeAction(Window w)
    {
        this.w = w;
    }

    public void actionPerformed(ActionEvent e)
    {
        w.dispose();
    }

    public boolean isEnabled()
    {
        return true;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
    }

    public Object getValue(String key)
    {
        return null;
    }

    public void putValue(String key, Object value)
    {
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
    }

    public void setEnabled(boolean b)
    {
    }
}