/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on 22/05/2008 22:06:37
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class AboutFoo extends JDialog
{
    private static final long serialVersionUID = -1519186465396436130L;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                JFrame f = new JFrame();
                f.setBounds(100, 100, 660, 430);
                new AboutFoo(f);
            }
        });
    }

    public AboutFoo(JFrame parent)
    {
        super(parent);
        initView();
    }

    private void initView()
    {
        widgets();

        setTitle("About - LJ Colligo");
        setBounds(100, 100, 575, 280);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        Util.centerPosition(getOwner(), this, getWidth(), getHeight());

        setVisible(true);
    }

    private void widgets()
    {
        add(createMainPanel());
    }

    private JPanel createMainPanel()
    {
        JPanel p = new JPanel(new BorderLayout());

        p.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), DisposeAction.ACTION_NAME);
        p.getActionMap().put(DisposeAction.ACTION_NAME, new DisposeAction(this));

        FormLayout layout = new FormLayout("15px, 160px, 19px, 350px, 15px",
                                           "12px, 23px, 15px, 213px, 12px, 275px");

        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        builder.add(new JLabel(Util.getImage("about.png")), cc.xywh(1, 1, 1, 1));
        builder.add(new JLabel(Util.getImage("about.png")), cc.xywh(2, 1, 1, 1));
        builder.add(new JLabel(Util.getImage("about.png")), cc.xywh(3, 1, 1, 1));
        builder.add(new JLabel(Util.getImage("about.png")), cc.xywh(4, 1, 1, 1));
        builder.add(new JLabel(Util.getImage("about.png")), cc.xywh(5, 1, 1, 1));
// builder.add(new JLabel("LJ Colligo - A livejournal Backup tool"), cc.xy(4, 2));

// JTabbedPane t = new JTabbedPane();
// t.setBorder(BorderFactory.createLineBorder(Color.yellow));
// t.addTab("About", new JPanel());
// t.addTab("License", new JPanel());
// t.addTab("Sys props", new JPanel());
// t.addTab("Env vars", new JPanel());
//
// builder.add(t, cc.xy(4, 4));

        p.add(builder.getPanel());
        return p;
    }
}
