/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on 25/11/2007 10:44:10
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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.bpfurtado.ljcolligo.LJColligoException;

/**
 * This class was initially born in Netbeans using its magical Matisse GUI creator and
 * later on many genetic experiments were made to put it inside this project without a lot
 * of automatic features netbeans added to it.
 *
 * @author Bruno
 */
public class About extends JDialog
{
    private static final long serialVersionUID = 6274009586799124340L;

    private static final String PROJECT_URL_STR = "http://code.google.com/p/ljcolligo";

    private static final Font URL_SELECTED_FONT = new Font("Tahoma", Font.PLAIN, 11);
    private static final Font URL_REGULAR_FONT = new Font("Tahoma", Font.PLAIN, 11);

    private static final Font FONT = new Font("Tahoma", Font.PLAIN, 11);

    private JButton closeBt;
    private JButton envVarsBt;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                JFrame f = new JFrame();
                f.setBounds(100, 100, 660, 430);
                new About(f);
            }
        });
    }

    public About(JFrame parent)
    {
        super(parent);
        initView();
    }

    private void initView()
    {
        widgets();
        getRootPane().setDefaultButton(closeBt);

        setTitle("About - LJ Colligo");
        setResizable(false);
        setBounds(100, 100, 520, 257);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        Util.centerPosition(getOwner(), this, getWidth(), getHeight());

        setVisible(true);
    }

    private void widgets()
    {
        closeBt = new JButton();
        JLabel appTitleLb = new JLabel();
        JLabel versionLabel = new JLabel();
        JLabel appVersionLabel = new JLabel();
        JLabel authorLb = new JLabel();
        JLabel appVendorLabel = new JLabel();
        JLabel projPageLb = new JLabel();
        JLabel appProjLb = new JLabel();
        JLabel appDescLb = new JLabel();
        JLabel aboutImageLb = new JLabel();

        envVarsBt = new JButton();
        envVarsBt.setMnemonic('e');
        JLabel blogLb = new JLabel();
        JLabel appBlogLb = new JLabel();
        JLabel appFeedLb = new JLabel();
        JLabel feedLb = new JLabel();

        aboutImageLb.setIcon(Util.getImage("about.png"));
        addOpenURLEvents(aboutImageLb, PROJECT_URL_STR);

        closeBt.setText("Close");
        closeBt.setMnemonic('c');
        closeBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        appTitleLb.setText("A Livejournal Backup Tool");

        appDescLb.setFont(FONT);
        appDescLb.setText("<html>LJ Colligo is a way to download all your LiveJournal blog entries and comments to your local machine.");

        versionLabel.setText("Product Version:");
        appVersionLabel.setText("0.8");
        appVersionLabel.setFont(FONT);

        authorLb.setText("Author");
        appVendorLabel.setText("Bruno Patini Furtado");
        appVendorLabel.setFont(FONT);

        projPageLb.setText("Project Page:");
        appProjLb.setText(PROJECT_URL_STR);
        appProjLb.setFont(FONT);
        addOpenURLEvents(appProjLb);

        envVarsBt.setText("Environment Vars");

        blogLb.setText("Blog");
        appBlogLb.setText("http://bpfurtado.livejournal.com");
        appBlogLb.setFont(FONT);
        addOpenURLEvents(appBlogLb);

        feedLb.setText("Feed");
        appFeedLb.setText("http://bpfurtado.livejournal.com/data/atom");
        appFeedLb.setFont(FONT);
        addOpenURLEvents(appFeedLb);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                        layout.createSequentialGroup().addContainerGap().addComponent(aboutImageLb).addGap(18, 18, 18).addGroup(
                                        layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(appTitleLb, GroupLayout.Alignment.LEADING).addComponent(appDescLb, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE).addGroup(
                                                        GroupLayout.Alignment.LEADING,
                                                        layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(versionLabel).addComponent(authorLb).addComponent(projPageLb).addComponent(blogLb).addComponent(feedLb)).addPreferredGap(
                                                                        LayoutStyle.ComponentPlacement.RELATED).addGroup(
                                                                        layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(appVersionLabel).addComponent(appVendorLabel).addComponent(appProjLb).addComponent(appBlogLb).addComponent(appFeedLb).addGroup(
                                                                                        GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(envVarsBt).addGap(18, 18, 18).addComponent(closeBt))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                        layout.createSequentialGroup().addContainerGap().addGroup(
                                        layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(aboutImageLb).addGroup(
                                                        layout.createSequentialGroup().addComponent(appTitleLb).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(appDescLb).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(
                                                                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(versionLabel).addComponent(appVersionLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(
                                                                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(authorLb).addComponent(appVendorLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(
                                                                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(projPageLb).addComponent(appProjLb)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(
                                                                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(blogLb).addComponent(appBlogLb)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(
                                                                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(feedLb).addComponent(appFeedLb)).addGap(18, 18, 18).addGroup(
                                                                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(closeBt).addComponent(envVarsBt)))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    }

    private void addOpenURLEvents(final JLabel l)
    {
        addOpenURLEvents(l, l.getText());
    }

    private void addOpenURLEvents(final JLabel l, final String urlStr)
    {
        l.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e)
            {
                l.setFont(URL_SELECTED_FONT);
                l.setForeground(Color.blue);
                l.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                l.setFont(URL_REGULAR_FONT);
                l.setForeground(Color.black);
                l.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseClicked(MouseEvent event)
            {
                try {
                    URI uri = new URI(urlStr);
                    Desktop.getDesktop().browse(uri);
                } catch (Exception e) {
                    throw new LJColligoException(e);
                }
            }
        });
    }
}
