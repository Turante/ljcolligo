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
package net.bpfurtado.ljcolligo;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import net.bpfurtado.ljcolligo.comm.LJCommunicator;
import net.bpfurtado.ljcolligo.model.Event;
import net.bpfurtado.ljcolligo.model.LJColligoObservable;
import net.bpfurtado.ljcolligo.model.util.ModelFromRPCData;

import org.apache.log4j.Logger;

public class GetEvents extends LJColligoObservable
{
	public static final Logger logger = Logger.getLogger(GetEvents.class);

	public static SimpleDateFormat ljDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private LJCommunicator ljCommunicator;

	public GetEvents()
	{
		init();
	}

	public GetEvents(LJColligoObservable o)
	{
		super(o);
		init();
		ljCommunicator.addAllListenersFrom(o);
	}

	private void init()
	{
		ljCommunicator = new LJCommunicator();
	}
	
	public Collection<Event> download(String userName, String password)
	{
		try {
			Collection<Event> events = downloadEvents(userName, password);

			String msg = "All entries were downloaded";
			sendMessageToListeners(msg);
			logger.debug(msg);
			
			return events;
		} catch (Exception e) {
		    sendMessageToListeners(e.getMessage());
			throw new LJColligoException(e);
		}
	}

	private Collection<Event> downloadEvents(String userName, String password)
	{
		Map params = new HashMap();
		params.put("ver", 1);
		params.put("username", userName);
		params.put("auth_method", "challenge");
		params.put("selecttype", "lastn");
		params.put("howmany", "50");
		params.put("beforedate", ljDateFormatter.format(new Date()));
		params.put("lineendings", "\n");
		params.put("truncate", "0");
		
		Collection<Event> events = new LinkedList<Event>();

		int total = 0;
		Object[] eventsDataFromXMLRPC = null;
		do {
			ljCommunicator.updateChallenge(password, params);
			Map<String, Object> result = (Map<String, Object>) ljCommunicator.execute("getevents", params);
			eventsDataFromXMLRPC = (Object[]) result.get("events");

			ModelFromRPCData event = new ModelFromRPCData();
			String lastEventTime = null;
			for (int i = 0; i < eventsDataFromXMLRPC.length; i++) {
				HashMap xmlRPCData = (HashMap) eventsDataFromXMLRPC[i];
				events.add(event.from(xmlRPCData));
				lastEventTime = (String) xmlRPCData.get("eventtime");
			}
			params.put("beforedate", lastEventTime);
			total += eventsDataFromXMLRPC.length;

			String msg = "Partial=" + total;
			sendMessageToListeners(msg);
			logger.debug(msg);
		} while (eventsDataFromXMLRPC.length == 50);

		logger.debug("Total=" + events.size());
		
		return events;
	}


}
