/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on Nov 2007
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
package net.bpfurtado.ljcolligo.persistence;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.bpfurtado.ljcolligo.util.Util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Map2XMLWriter
{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(Map2XMLWriter.class);

	public static final String EVENT_TAG_NAME = "event";
	private static final String EVENT_BODY_TAG_NAME = "body";

	private Element root;

	public Map2XMLWriter()
	{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("events");
		this.root = root;
	}

	public void addEvent(Map<String, Object> event)
	{
		mapToXML(root, event);
	}

	private void mapToXML(Element thisRoot, Map<String, Object> event)
	{
		Element node = null;
		if (thisRoot.equals(root)) {
			node = thisRoot.addElement(EVENT_TAG_NAME);
		} else {
			node = thisRoot;
		}

		for (Entry<String, Object> e : event.entrySet()) {
			if (e.getValue() instanceof HashMap) {
				Element newRoot = node.addElement(e.getKey());
				mapToXML(newRoot, (HashMap<String, Object>) e.getValue());
			} else {
				String value = null;
				if (!(e.getValue() instanceof String) && !(e.getValue() instanceof Integer)) {
					value = new String((byte[]) e.getValue(), Charset.forName("UTF-8"));
				} else {
					value = e.getValue().toString();
				}

				if (e.getKey().equals(EVENT_TAG_NAME)) {
					node.addElement(EVENT_BODY_TAG_NAME).addCDATA(value);
				} else {
					node.addElement(e.getKey()).setText(value);
				}
			}
		}
	}

	public void save(File file)
	{
		Util.save(root, file);
	}
}
