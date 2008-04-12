/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on 30/11/2007 15:40:21
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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import net.bpfurtado.ljcolligo.LJColligoException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Jeff Genender
 * @URL http://www.genender.net/blog/2004/08/06/howto-simple-java-symmetric-cipher-for-strings/
 */
public class EncryptionUtil
{
	private static final byte[] _3DESData = {
			(byte) 0x76, (byte) 0xAF, (byte) 0xBA, (byte) 0x39, (byte) 0x31, (byte) 0x2F,
			(byte) 0x01, (byte) 0x4A, (byte) 0xF3, (byte) 0x90, (byte) 0x11, (byte) 0xDE,
			(byte) 0x15, (byte) 0xA5, (byte) 0xFF, (byte) 0x13, (byte) 0xF9, (byte) 0xCA,
			(byte) 0x22, (byte) 0x17, (byte) 0x33, (byte) 0x77, (byte) 0x39, (byte) 0x19 };

	private static SecretKeySpec KEY = new SecretKeySpec(_3DESData, "DESede");

	public static String encrypt(String text)
	{
		byte[] plaintext = text.getBytes();

		try {
			Cipher cipher = Cipher.getInstance("DESede"); // Triple-DES encryption
			cipher.init(Cipher.ENCRYPT_MODE, KEY);

			byte[] cipherText = cipher.doFinal(plaintext);

			BASE64Encoder b64 = new BASE64Encoder();
			return b64.encode(cipherText);
		} catch (Exception e) {
			throw new LJColligoException(e);
		}
	}

	public static String decrypt(String text)
	{
		try {
			BASE64Decoder b64 = new BASE64Decoder();
			byte[] cipherText = b64.decodeBuffer(text);

			Cipher cipher = Cipher.getInstance("DESede"); // Triple-DES encryption
			cipher.init(Cipher.DECRYPT_MODE, KEY);

			String plainText = new String(cipher.doFinal(cipherText));

			return plainText;
		} catch (Exception e) {
			throw new LJColligoException(e);
		}
	}
}