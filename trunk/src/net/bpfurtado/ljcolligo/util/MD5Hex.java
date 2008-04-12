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

import java.security.MessageDigest;

public class MD5Hex
{
	public static String md5hex(String str)
	{
		return makeMD5(str);
	}

	private static String makeMD5(String str)
	{
		byte[] bytes = new byte[32];
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("iso-8859-1"), 0, str.length());
			bytes = md.digest();
		} catch (Exception e) {
			return null;
		}
		return convertToHex(bytes);
	}

	private static String convertToHex(byte[] bytes)
	{
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int halfbyte = (bytes[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buffer.append((char) ('0' + halfbyte));
				else
					buffer.append((char) ('a' + (halfbyte - 10)));
				halfbyte = bytes[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buffer.toString();
	}
}