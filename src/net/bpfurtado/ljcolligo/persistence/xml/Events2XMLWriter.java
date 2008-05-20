/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on 19/11/2007 20:53:56
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

package net.bpfurtado.ljcolligo.persistence.xml;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import net.bpfurtado.ljcolligo.model.Comment;
import net.bpfurtado.ljcolligo.model.Event;
import net.bpfurtado.ljcolligo.model.EventMetadata;
import net.bpfurtado.ljcolligo.model.LJColligoObservable;
import net.bpfurtado.ljcolligo.util.Util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Events2XMLWriter extends LJColligoObservable
{
	public static final Logger logger = Logger.getLogger(Events2XMLWriter.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Element root;

	public Events2XMLWriter()
	{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("events");
		this.root = root;
	}

	public void write(Collection<Event> events, File outputPath)
	{
		for (Event e : events) {
			Element event = root.addElement("event");
			event.addAttribute("id", getString(e.getId()));
			event.addAttribute("subject", e.getSubject());
			event.addAttribute("date", getString(e.getDate()));
			event.addElement("body").addCDATA(new String(e.getBody().getBytes(), Charset.forName("UTF-8")));
			event.addAttribute("aNum", getString(e.getANum()));
			event.addAttribute("url", e.getUrl().toString());

			Element metadata = event.addElement("metadata");
			EventMetadata meta = e.getMetadata();
			metadata.addAttribute("revisionTime", getString(meta.getRevisionTime()));
			metadata.addAttribute("revisionNumber", getString(meta.getRevisionNumber()));
			metadata.addAttribute("music", meta.getMusic());
			metadata.addAttribute("preformattedOption", getString(meta.getPreformattedOption()));
			metadata.addAttribute("location", meta.getLocation());
			metadata.addAttribute("moodId", getString(meta.getMoodId()));
			String tags = meta.getTags().toString();
			if (tags.length() > 2) { // TODO review this
				metadata.addAttribute("tags", tags.substring(1, tags.length() - 1));
			}

			Element commentsNode = event.addElement("comments");
			for (Comment c : e.getComments()) {
				Element comments = commentsNode.addElement("comment");
				comments.addAttribute("id", c.getId());
				comments.addAttribute("eventId", getString(c.getEventId()));
				comments.addAttribute("subject", getString(c.getSubject()));
				comments.addAttribute("date", getString(c.getDate()));
				comments.addAttribute("user", getString(c.getUser()));
				comments.addAttribute("posterId", getString(c.getPosterId()));
				comments.addAttribute("state", getString(c.getState()));
				comments.addAttribute("parentId", getString(c.getParentId()));
				if (c.getBody() != null) {
					comments.addElement("body").addCDATA(new String(c.getBody().getBytes(), Charset.forName("UTF-8")));
				}
			}
		}

		Util.save(root, outputPath);
		
		sendMessageToListeners("All entries saved to file [" + outputPath.getAbsolutePath() + "]\n");
	}

	private String getString(String s)
	{
		if (s == null)
			return "";

		return new String(s.getBytes(), Charset.forName("UTF-8"));
	}

	private String getString(Integer i)
	{
		if (i == null)
			return "";
		return i.toString();
	}

	private String getString(Boolean b)
	{
		if (b == null)
			return "";
		return b.toString();
	}

	private String getString(Date date)
	{
		if (date == null)
			return "";
		return sdf.format(date);
	}
}
