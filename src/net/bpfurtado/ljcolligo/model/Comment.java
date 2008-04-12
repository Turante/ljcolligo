/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on 2007-11-15 21:00
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment
{
	private String id;
	private Integer eventId;
	private String subject;
	private String body;

	private Date date;
	private String user;

	private String posterId;
	private String state;
	private String parentId;

	public Integer getEventId()
	{
		return eventId;
	}

	public void setEventId(Integer eventId)
	{
		this.eventId = eventId;
	}

	public String getBody()
	{
		return body;
	}

	public String getPosterId()
	{
		return posterId;
	}

	public String getState()
	{
		return state;
	}

	public String getParentId()
	{
		return parentId;
	}

	public String getId()
	{
		return id;
	}

	public String getUser()
	{
		return user;
	}

	public String getSubject()
	{
		return subject;
	}

	public Date getDate()
	{
		return date;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public void setPosterId(String posterId)
	{
		this.posterId = posterId;
	}

	public void setState(String state)
	{
		this.state = state;
	}


	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	@Override
	public String toString()
	{
		return "[Comment: id=" + id + ", jItemId=" + eventId + ", body=" + body + "]";
	}

	public String toString(SimpleDateFormat sdf)
	{
		String s = "[Comment: id=" + id + ", jItemId=" + eventId;
		if (date != null) {
			s += ", date=" + sdf.format(date) + "]";
		} else {
			s += "]";
		}
		return s;
	}
}
