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

import java.io.File;
import java.util.Collection;

import net.bpfurtado.ljcolligo.model.Event;
import net.bpfurtado.ljcolligo.persistence.Events2XMLWriter;
import net.bpfurtado.ljcolligo.util.Conf;
import net.bpfurtado.ljcolligo.util.Util;

import org.apache.log4j.Logger;

public class LJColligoMain
{
	private static final Logger logger = Logger.getLogger(LJColligoMain.class);

	public static void main(String[] args) throws Exception
	{
		logger.debug("Start");

		String userName = Conf.getInstance().getUserName();
		String password = Conf.getInstance().getPassword();

		String outputPath = System.getProperty("user.home") + File.separator + "Desktop/LJColligo/";

		logger.debug("User name = [" + userName + "]");
		logger.debug("Password = [" + password + "]");
		logger.debug("outputPath = [" + outputPath + "]");

		Collection<Event> events = new LJColligo().downloadEvents(userName, password);
		logger.debug("downloaded " + events.size() + " events");

		File outputFile = Util.genFile(userName, outputPath, ".xml");
		
		new Events2XMLWriter().write(events, outputFile);

		logger.debug("End");
	}
}
