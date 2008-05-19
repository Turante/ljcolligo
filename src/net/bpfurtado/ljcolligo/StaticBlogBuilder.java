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

import org.apache.log4j.Logger;
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
public class StaticBlogBuilder
{
    private static final Logger logger = Logger.getLogger(StaticBlogBuilder.class);

    private static final Conf conf = Conf.getInstance();

    private Template blogTemplate = null;
    private File outputPath;

    public static void main(String[] args)
    {
        LJColligo ljcolligo = new LJColligo();
        List<Event> events = ljcolligo.downloadEvents(conf.getUserName(), conf.getPassword());

        StaticBlogBuilder staticBlog = new StaticBlogBuilder(new File(conf.getLJColligoTempDir()));
        staticBlog.build(events, 20);
    }

    public StaticBlogBuilder(File outputPath)
    {
        this.outputPath = outputPath;
        try {
            initVelocityEngine();
            blogTemplate = Velocity.getTemplate("/blog.vm");
        } catch (Exception e) {
            throw new LJColligoException(e);
        }
    }

    public void build(List<Event> events, int entriesPerPage)
    {
        int lastPage = lastPage(events.size(), entriesPerPage);

        VelocityContext context = createVelocityContext(events);

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
    }

    private void persist(List<Event> pageEvents, int pageNumber, int lastPage, VelocityContext ctx)
    {
        try {
            ctx.put("events", pageEvents);

            ctx.remove("previousPage");
            ctx.remove("nextPage");
            if (pageNumber > 0) {
                ctx.put("previousPage", pageLink(pageNumber - 1));
            }
            if (pageNumber != lastPage) {
                ctx.put("nextPage", pageLink(pageNumber + 1));
            }

            StringWriter html = new StringWriter();
            blogTemplate.merge(ctx, html);

            persist(html, pageNumber);

            logger.debug(html);
        } catch (Exception e) {
            throw new LJColligoException(e);
        }
    }

    private String pageLink(int page)
    {
        return "blog" + page + ".html";
    }

    private void persist(StringWriter sw, int page) throws IOException
    {
        FileWriter writer = new FileWriter(outputPath.getAbsolutePath() + File.separator + "blog" + page + ".html");
        writer.write(sw.toString());
        writer.flush();
        writer.close();
    }

    private VelocityContext createVelocityContext(List<Event> events)
    {
        try {
            VelocityContext context = new VelocityContext();

            String url = events.get(0).getUrl().toString();
            context.put("blogName", url.substring(url.indexOf('/') + 2, url.indexOf('.')));

            return context;
        } catch (Exception e) {
            throw new LJColligoException(e);
        }
    }

    private int lastPage(int total, int entriesPerPage)
    {
        int lastPage = 0;
        int mod = total % entriesPerPage;
        float div = (float) total / (float) entriesPerPage;
        if (mod == 0) {
            lastPage = (int) div;
        } else {
            lastPage = (int) Math.ceil(div) - 2; // -2 because it's zero based
        }
        return lastPage;
    }

    private void initVelocityEngine() throws Exception
    {
        Properties ps = new Properties();
        ps.setProperty("resource.loader", "class");
        ps.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        Velocity.init(ps);
    }
}
