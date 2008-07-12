/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com] - 2005
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

import static net.bpfurtado.ljcolligo.gui.Util.addHeight;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import net.bpfurtado.commons.io.FileUtils;

public class AboutFrame extends JDialog
{
    private static final long serialVersionUID = -5017889322813683357L;

//    private static final Font TITLE_FONT = new Font("Tahoma", 1, 16);

    public AboutFrame(JFrame invokerFrame)
    {
        initView(invokerFrame);
    }

    private void initView(JFrame invokerFrame)
    {
        widgets();

        setTitle("About - LJColligo");
        Util.centerPosition(invokerFrame, this, 518, 359);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setVisible(true);
    }

    private void widgets()
    {
        JPanel mainPn = new JPanel();
        mainPn.setLayout(new BoxLayout(mainPn, BoxLayout.PAGE_AXIS));

        Util.addHeight(mainPn, 10);

//        JLabel title = new JLabel("LJColligo");
//        title.setFont(TITLE_FONT);
//        title.setAlignmentX(CENTER_ALIGNMENT);
//        mainPn.add(title);

        addHeight(mainPn, 10);
        mainPn.add(createTabsPanel());
        addHeight(mainPn, 5);
        mainPn.add(createCloseBt());
        addHeight(mainPn, 5);

        mainPn.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), DisposeAction.ACTION_NAME);
        mainPn.getActionMap().put(DisposeAction.ACTION_NAME, new DisposeAction(this));

        add(mainPn);
    }

    private JTabbedPane createTabsPanel()
    {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("About", createAboutPanel());
        tabs.addTab("System Properties", createSystemPropertiesPanel());
        tabs.addTab("Environment Variables", createEnvironmentVariablesPanel());
        return tabs;
    }

    private Component createEnvironmentVariablesPanel()
    {
        return createTAPanel(getEnvironmentVars());
    }

    private JPanel createSystemPropertiesPanel()
    {
        return createTAPanel(getSystemProperties());
    }

    private JPanel createTAPanel(String text)
    {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));

        JTextArea ta = new JTextArea(text);
        ta.setEditable(false);
        p.add(new JScrollPane(ta));
        return p;
    }

    private String getSystemProperties()
    {
        Set<Entry<Object, Object>> entrySet = System.getProperties().entrySet();
        StringBuilder b = new StringBuilder();
        for (Entry<Object, Object> e : entrySet) {
            b.append(e.getKey() + " = " + e.getValue() + "\n");
        }
        return b.toString();
    }

    private String getEnvironmentVars()
    {
        Map<String, String> environment = System.getenv();
        StringBuilder b = new StringBuilder();
        for (String var : environment.keySet()) {
            b.append(var + " = " + environment.get(var) + "\n");
        }
        return b.toString();
    }

    private JButton createCloseBt()
    {
        JButton closeBt = new JButton("Close");
        closeBt.setMnemonic('C');
        closeBt.setAlignmentX(CENTER_ALIGNMENT);
        closeBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        getRootPane().setDefaultButton(closeBt);
        return closeBt;
    }

    private JPanel createAboutPanel()
    {
        return createTAPanel(FileUtils.allLinesFromClasspath("/net/bpfurtado/tas/view/about.txt"));
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                JFrame frame = new JFrame();
                frame.setBounds(235, 260, 970, 615);
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.setVisible(true);
                new AboutFrame(frame);
            }
        });
    }
}