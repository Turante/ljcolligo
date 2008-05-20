/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on 19/05/2008 20:59:09
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
package net.bpfurtado.ljcolligo.persistence.html;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import net.bpfurtado.ljcolligo.LJColligo;
import net.bpfurtado.ljcolligo.LJColligoException;
import net.bpfurtado.ljcolligo.model.Event;
import net.bpfurtado.ljcolligo.util.Conf;

public class EventsCacheReader
{
    private static final Conf conf = Conf.getInstance();
    private static final String filePath = "C:\\Users\\Bruno\\tmp\\ljcolligo\\events.dat";

    public static void main(String[] args) throws Exception
    {
        LJColligo ljcolligo = new LJColligo();
        List<Event> events = ljcolligo.downloadEvents(conf.getUserName(), conf.getPassword());

        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(filePath));
        o.writeObject(events);

        events = readEvents();

        System.out.println("events.size = " + events.size());
    }

    public static List<Event> readEvents()
    {
        try {
            List<Event> events;
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            events = (List<Event>) in.readObject();
            return events;
        } catch (Exception e) {
            throw new LJColligoException(e);
        }
    }
}
