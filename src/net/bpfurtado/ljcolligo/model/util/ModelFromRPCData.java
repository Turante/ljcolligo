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
package net.bpfurtado.ljcolligo.model.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import net.bpfurtado.ljcolligo.GetEvents;
import net.bpfurtado.ljcolligo.LJColligoException;
import net.bpfurtado.ljcolligo.model.Event;
import net.bpfurtado.ljcolligo.model.EventMetadata;
import net.bpfurtado.ljcolligo.persistence.Map2XMLWriter;

import org.apache.log4j.Logger;

public class ModelFromRPCData
{
	private static final Logger logger = Logger.getLogger(ModelFromRPCData.class);

	private HashMap eventData;

	public Event from(HashMap eventData)
	{
		this.eventData = eventData;

		Event event = new Event();

		event.setId(xInteger("itemid"));
		event.setSubject(xString("subject"));
		event.setBody(xString(Map2XMLWriter.EVENT_TAG_NAME));
		event.setANum(xInteger("anum"));
		event.setDate(xDate("eventtime"));

		readURL(event);

		this.eventData = (HashMap) eventData.get("props");

		EventMetadata m = new EventMetadata();
		m.setMusic(xString("current_music"));
		m.setLocation(xString("current_location"));
		m.setMoodId(xInteger("current_moodid"));
		m.setTags(xTags(xString("taglist")));

		event.setMetadata(m);

		return event;
	}

	private Date xDate(String name)
	{
		String dateStr = xString(name);
		try {
			return GetEvents.ljDateFormatter.parse(dateStr);
		} catch (ParseException pe) {
			throw new LJColligoException("Could not parse date [" + dateStr + "]", pe);
		}
	}

	private void readURL(Event e)
	{
		String urlStr = xString("url");
		try {
			e.setUrl(new URL(urlStr));
		} catch (MalformedURLException mue) {
			logger.warn("Could not read URL [" + urlStr + "]", mue);
		}
	}

	private Collection<String> xTags(String s)
	{
		Collection<String> tags = new LinkedList<String>();
		if (s == null)
			return tags;

		StringTokenizer stk = new StringTokenizer(s, ",");
		while (stk.hasMoreElements()) {
			tags.add(stk.nextToken().trim());
		}
		return tags;
	}

	private Integer xInteger(String name)
	{
		return (Integer) eventData.get(name);
	}

	private String xString(String name)
	{
		Object o = eventData.get(name);
		if (o == null)
			return null;

		try {
			return (String) o;
		} catch (ClassCastException e) {
			return new String((byte[]) o, Charset.forName("UTF-8"));
		}
	}
}
