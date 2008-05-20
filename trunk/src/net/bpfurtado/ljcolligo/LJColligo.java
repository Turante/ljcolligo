/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on 19/11/2007 20:32:09
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

package net.bpfurtado.ljcolligo;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.bpfurtado.ljcolligo.model.Comment;
import net.bpfurtado.ljcolligo.model.Event;
import net.bpfurtado.ljcolligo.model.LJColligoObservable;
import net.bpfurtado.ljcolligo.persistence.xml.Events2XMLWriter;
import net.bpfurtado.ljcolligo.util.Util;

import org.apache.log4j.Logger;

/**
 * Main Facade
 *
 * @author Bruno Patini Furtado
 */
public class LJColligo extends LJColligoObservable
{
    private static final Logger logger = Logger.getLogger(LJColligo.class);

    public LJColligo()
    {
        super();
    }

    public LJColligo(LJColligoObservable other)
    {
        super(other);
    }

    public File downloadAndPersist(String userName, String password, String outputPath)
    {
        logger.debug("Start");
        sendMessageToListeners("Start");

        Collection<Event> events = downloadEvents(userName, password);

        if (!outputPath.endsWith(File.separator)) {
            outputPath += File.separator;
        }

        File outputFile = Util.genFile(userName, outputPath, ".xml");

        Events2XMLWriter events2XMLWriter = new Events2XMLWriter();
        events2XMLWriter.addAllListenersFrom(this);
        events2XMLWriter.write(events, outputFile);

        logger.debug("End");
        sendMessageToListeners("End");

        return outputFile;
    }

    public List<Event> downloadEvents(String userName, String password)
    {
        GetEvents getEvents = new GetEvents(this);

        List<Event> events = getEvents.download(userName, password);

        GetComments getComments = new GetComments(this);
        Collection<Comment> comments = getComments.download(userName, password);

        merge(events, comments);

        return events;
    }

    private void merge(Collection<Event> events, Collection<Comment> comments)
    {
        Map<Integer, Event> m = new HashMap<Integer, Event>();
        for (Event e : events) {
            m.put(e.getId(), e);
        }

        for (Comment c : comments) {
            Event event = m.get(c.getEventId());
            if (event != null) {
                event.add(c);
            } else {
                String msg = "Event with id [" + c.getEventId() + "] not found, comment=" + c;
                logger.warn(msg);
                sendMessageToListeners(msg);
            }
        }
    }
}
