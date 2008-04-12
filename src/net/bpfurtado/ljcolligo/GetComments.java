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
import java.util.LinkedList;
import java.util.List;

import net.bpfurtado.ljcolligo.model.Comment;
import net.bpfurtado.ljcolligo.model.LJColligoObservable;
import net.bpfurtado.ljcolligo.util.Conf;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class GetComments extends LJColligoObservable
{
	private static final Logger logger = Logger.getLogger(GetComments.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");

	public static void main(String[] args)
	{
		Conf conf = Conf.getInstance();
		Collection<Comment> comments = new GetComments().download(conf.getUserName(), conf.getPassword());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Comment c : comments) {
			logger.debug(c.toString(sdf));
		}
	}

	public GetComments()
	{
		super();
	}

	public GetComments(LJColligoObservable other)
	{
		super(other);
	}

	public Collection<Comment> download(String userName, String password)
	{
		HttpClient http = new HttpClient();
		http.getHostConfiguration().setHost("www.livejournal.com", 80, "http");

		try {
			PostMethod login = new PostMethod("/login.bml");
			NameValuePair[] data = { new NameValuePair("user", userName), new NameValuePair("password", password) };

			login.setRequestBody(data);
			
			sendMessageToListeners("Login to get comments...");
			http.executeMethod(login);

			sendMessageToListeners("Invoking export_comments.bml...");
			HttpMethod exportComments = new GetMethod("/export_comments.bml?get=comment_body&startid=0");
			http.executeMethod(exportComments);

			Collection<Comment> comments = new LinkedList<Comment>();

			SAXReader reader = new SAXReader();
			Document doc = reader.read(exportComments.getResponseBodyAsStream());
			
			sendMessageToListeners("Comments response received, processing...");

			List commentNodes = doc.selectNodes("//comment");
			sendMessageToListeners(commentNodes.size() + " comments found");
			int c = 0;

			for (Object o : commentNodes) {
				Node node = (Node) o;
				Comment comment = from(node);
				comments.add(comment);
				sendMessageToListeners((++c) + " comments processed");
			}
			return comments;
		} catch (Exception e) {
			throw new LJColligoException(e);
		}
	}

	private Comment from(Node node)
	{
		Comment c = new Comment();

		c.setId(node.valueOf("@id"));
		c.setEventId(Integer.parseInt(node.valueOf("@jitemid")));

		c.setDate(dateFrom(node, "date"));
		c.setBody(textFrom(node, "body"));

		// TODO URGENT add rest of fields

		return c;
	}

	private Date dateFrom(Node n, String nodeName)
	{
		try {
			String txt = textFrom(n, nodeName);
			if (txt != null) {
				return sdf.parse(txt);
			}
		} catch (Exception e) {
			logger.info(e);
		}
		return null;
	}

	private String textFrom(Node n, String nodeName)
	{
		List nodes = n.selectNodes(nodeName);
		if (!nodes.isEmpty()) {
			return ((Node) nodes.get(0)).getText().trim();
		}
		return null;
	}
}
