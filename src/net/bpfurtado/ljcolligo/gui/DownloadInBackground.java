/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
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

import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingWorker;

import net.bpfurtado.ljcolligo.LJColligo;
import net.bpfurtado.ljcolligo.LJColligoListener;

public class DownloadInBackground extends SwingWorker<Collection<String>, String> implements LJColligoListener
{
    private LJColligo facade;

    private String userName;
    private String outputPath;
    private String password;

    private DownloadInBackgroundClient listener;

    private File outputFile;

    DownloadInBackground(DownloadInBackgroundClient ta, LJColligo facade, String userName, String password, String outputPath)
    {
        this.listener = ta;
        this.facade = facade;
        this.userName = userName;
        this.password = password;
        this.outputPath = outputPath;
    }

    @Override
    public Collection<String> doInBackground()
    {
        this.outputFile = facade.downloadAndPersist(userName, password, outputPath);
        return null;
    }

    /**
     * Apparently it's not working here
     */
    @Override
    protected void process(List<String> chunks)
    {
        for (String s : chunks) {
            listener.append("TA:" + s);
        }
    }

    /**
     * Apparently it's not working here
     */
    @Override
    public void receiveFromLJColligo(String message)
    {
        publish(message);
    }

    @Override
    protected void done()
    {
        listener.downloadDone(outputFile);
    }
}