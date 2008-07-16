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
package net.bpfurtado.ljcolligo.comm;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import net.bpfurtado.ljcolligo.LJColligoException;
import net.bpfurtado.ljcolligo.model.LJColligoObservable;
import net.bpfurtado.ljcolligo.util.MD5Hex;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class LJCommunicator extends LJColligoObservable
{
	private static final Logger logger = Logger.getLogger(LJCommunicator.class);

	private XmlRpcClient xmlRPCClient = null;

	public LJCommunicator()
	{
		try {
			xmlRPCClient = buildXMLRPCClient();
		} catch (MalformedURLException e) {
			throw new LJColligoException(e);
		}
	}

	public Map<String, Object> execute(String xmlRPCCommand, Map params)
	{
		try {
			logger.debug("Sending XML RPC command [" + xmlRPCCommand + "]...");
			sendMessageToListeners("Sending XML RPC command [" + xmlRPCCommand + "]...");
			
			Map<String, Object> result = (Map<String, Object>) xmlRPCClient.execute("LJ.XMLRPC." + xmlRPCCommand, new Object[] { params });

			logger.debug("Response came.");
			sendMessageToListeners("Response came.");
			
			return (Map<String, Object>) result;
		} catch (XmlRpcException e) {
			throw new LJColligoException(e);
		}
	}

	public void updateChallenge(String password, Map<String, String> params)
	{
		String challenge = requestChallenge();
		params.put("auth_challenge", challenge);
		params.put("auth_response", MD5Hex.md5hex(challenge + MD5Hex.md5hex(password)));
	}

	private String requestChallenge()
	{
		try {

			Map<String, Object> challengeResponse = (Map<String, Object>) xmlRPCClient.execute("LJ.XMLRPC.getchallenge", new Object[] {});
			return (String) challengeResponse.get("challenge");
		} catch (XmlRpcException e) {
			throw new LJColligoException(e);
		}
	}

	private XmlRpcClient buildXMLRPCClient() throws MalformedURLException
	{
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://www.livejournal.com/interface/xmlrpc"));

		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		return client;
	}
}
