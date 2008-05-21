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

package net.bpfurtado.ljcolligo.model;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

public class Event implements Serializable
{
    private static final long serialVersionUID = -147754089034715853L;

    private Integer id;

    private String subject;
    private Date date;
    private String body;

    private Integer aNum;

    private URL url;
    private EventMetadata metadata;

    private Collection<Comment> comments = new LinkedList<Comment>();

    @Override
    public String toString()
    {
        String bodyExcerpt = "";
        if (body != null)
            bodyExcerpt = body.substring(0, 50);

        return "[Event: id=" + id + ", subject=" + subject + ", body=" + bodyExcerpt + "..." +
                ", url=" + url + ", metadata=" + metadata +
                "comments=" + comments + "]";
    }

    public Integer getId()
    {
        return id;
    }

    public Date getDate()
    {
        return date;
    }

    public String getBody()
    {
        return body;
    }

    public Integer getANum()
    {
        return aNum;
    }

    public URL getUrl()
    {
        return url;
    }

    public EventMetadata getMetadata()
    {
        return metadata;
    }

    public Iterable<Comment> getComments()
    {
        return comments;
    }

    public int getCommentsSize()
    {
        return comments.size();
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public void setANum(Integer num)
    {
        aNum = num;
    }

    public void setUrl(URL url)
    {
        this.url = url;
    }

    public void setMetadata(EventMetadata metadata)
    {
        this.metadata = metadata;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public void add(Comment c)
    {
        comments.add(c);
    }
}
