/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on 25/11/2007 11:01:14
 *
 * This file is part of LJColligo.
 *
 * LJColligo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LJColligo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LJColligo.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Project page: http://sourceforge.net/projects/ljcolligo/
 */

package net.bpfurtado.ljcolligo.gui;

import java.awt.Window;

import javax.swing.ImageIcon;

import net.bpfurtado.ljcolligo.LJColligoException;

public class Util
{
    public static ImageIcon getImage(String imageName)
    {
        try {
            return new ImageIcon(LJColligoFrame.class.getResource("/net/bpfurtado/ljcolligo/gui/" + imageName));
        } catch (NullPointerException npe) {
            throw new LJColligoException("[" + imageName + "] could no be loaded", npe);
        }
    }

    public static void centerPosition(Window window, Window w, int width, int height)
    {
        int x = window.getX() + (window.getWidth() - width) / 2;
        int y = window.getY() + (window.getHeight() - height) / 2;

        w.setBounds(x, y, width, height);
    }
}
