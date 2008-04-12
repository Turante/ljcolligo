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
package net.bpfurtado.ljcolligo.util;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import net.bpfurtado.ljcolligo.GetEvents;
import net.bpfurtado.ljcolligo.LJColligoException;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Util
{
	private static final Logger logger = Logger.getLogger(Util.class);

	private static SimpleDateFormat outputFileDateFormatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");

	public static File genFile(String userName, String outputPath, String extension)
	{
		File outputDir = new File(outputPath);
		if (!outputDir.exists()) {
			logger.info("Creating dir [" + outputPath + "]");
			outputDir.mkdirs();
		} else if (outputDir.isFile()) {
			throw new LJColligoException("the output path [" + outputPath + "] is a file and should be a dir");
		}

		return new File(outputPath + "LJColligo_" + userName + "_" + outputFileDateFormatter.format(new Date()) + extension);
	}

	public static void print(Map<String, Object> map)
	{
		for (Entry<String, Object> e : map.entrySet()) {
			GetEvents.logger.debug("\t" + e.getKey() + "=" + e.getValue());
		}
	}

	public static void save(Element root, File file)
	{
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setIndentSize(4);
		format.setEncoding("UTF-8");
		format.setNewlines(true);
		format.setLineSeparator(System.getProperty("line.separator"));
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileWriter(file), format);
			writer.startDocument();
			writer.write(root);
			writer.close();
			logger.debug("All entries saved to file [" + file.getAbsolutePath() + "]");
		} catch (Exception e) {
			throw new LJColligoException("File [" + file.getAbsolutePath() + "]", e);
		}
	}
}
