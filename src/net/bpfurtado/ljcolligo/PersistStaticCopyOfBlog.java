/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on 18/05/2008 14:26:33
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
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import net.bpfurtado.ljcolligo.model.Event;
import net.bpfurtado.ljcolligo.util.Conf;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * First code to create a static version of a LiveJournal blog
 *
 * This class is really a mess so far, alpha version.
 *
 * @author Bruno
 */
public class PersistStaticCopyOfBlog
{
    private static final Conf conf = Conf.getInstance();

    public static void main(String[] args)
    {
        LJColligo ljcolligo = new LJColligo();
        List<Event> events = ljcolligo.downloadEvents(conf.getUserName(), conf.getPassword());
        persistWebPages(events, 10);
    }

    private static void persistWebPages(List<Event> events, int entriesPerPage)
    {
        try {
            int lastPage = lastPage(events.size(), entriesPerPage);

            VelocityContext context = buildVelocityContext();
            List<Event> pageEvents = new LinkedList<Event>();

            int page = 0;
            int i = 0;

            for (Event e : events) {
                pageEvents.add(e);
                if ((i + 1) % entriesPerPage == 0) {
                    persist(pageEvents, page, lastPage, context);
                    i = 0;
                    pageEvents.clear();
                    page++;
                }
                i++;
            }
        } catch (Exception e) {
            throw new LJColligoException(e);
        }
    }

    static int lastPage(int total, int entriesPerPage)
    {
        int lastPage = 0;
        int rd = total % entriesPerPage;
        double div = (float) total / (float) entriesPerPage;
        if (rd == 0) {
            lastPage = (int) div;
        } else {
            lastPage = (int) Math.ceil(div) - 2; //-2 because it's zero based
        }
        return lastPage;
    }

    private static void persist(List<Event> pageEvents, int page, int lastPage, VelocityContext context)
    {
        try {
            context.put("events", pageEvents);
            Template template = Velocity.getTemplate("/blog.vm");

            String url = pageEvents.get(0).getUrl().toString();
            context.put("blogName", url.substring(url.indexOf('/') + 2, url.indexOf('.')));

            context.remove("previousPage");
            context.remove("nextPage");
            if (page > 0) {
                context.put("previousPage", pageLink(page - 1));
            }
            if (page != lastPage) {
                context.put("nextPage", pageLink(page + 1));
            }

            StringWriter mergedTemplateStr = new StringWriter();
            template.merge(context, mergedTemplateStr);
            persist(mergedTemplateStr, page);

            System.out.println(mergedTemplateStr);
        } catch (Exception e) {
            throw new LJColligoException(e);
        }
    }

    private static String pageLink(int page)
    {
        return "blog" + page + ".html";
    }

    private static void persist(StringWriter sw, int page) throws IOException
    {
        FileWriter w = new FileWriter(conf.getLJColligoTempDir() + File.separator + "blog" + page + ".html");
        w.write(sw.toString());
        w.flush();
        w.close();
    }

    private static VelocityContext buildVelocityContext() throws Exception
    {
        Properties ps = new Properties();
        ps.setProperty("resource.loader", "class");
        ps.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(ps);
        VelocityContext context = new VelocityContext();
        return context;
    }
}
