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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import net.bpfurtado.ljcolligo.LJColligoException;

import org.apache.log4j.Logger;

/**
 * This class was basically created for me not to commit my user name and password on the source files.
 */
public class Conf
{
    private static final Logger logger = Logger.getLogger(Conf.class);

    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";

    private static final Conf inst = new Conf();

    public static Conf getInstance()
    {
        return inst;
    }

    private Properties properties;
    private File file;

    public static final String LAST_FOLDER = "lastFolder";

    private Conf()
    {
        try {
            properties = new Properties();
            file = new File(System.getProperty("user.home") + File.separator + "ljcolligo.properties");
            if (file.exists()) {
                properties.loadFromXML(new FileInputStream(file));
            } else {
                PrintWriter writer = new PrintWriter(file);
                writer.println("userName=<YOUR_USER_NAME>");
                writer.println("password=<YOUR_PASSWORD>");
                writer.flush();
                writer.close();
                logger.warn("Configuration file [" + file + "] not found, one was created for you, please fill in the user and password on it");
            }
        } catch (Exception e) {
            throw new LJColligoException(e);
        }
    }

    public String get(String key)
    {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue)
    {
        String r = properties.getProperty(key);
        if (r == null)
            return defaultValue;
        return r;
    }

    public String getPassword()
    {
        String password = properties.getProperty(PASSWORD);
        if (password == null) {
            logger.warn("No password saved on file [" + file + "], create the file and/or put a line: password=<YOUR_PASSWORD>");
            return "";
        }
        return EncryptionUtil.decrypt(password);
    }

    public void setPassword(String pw)
    {
        properties.put(PASSWORD, EncryptionUtil.encrypt(pw));
    }

    public String getUserName()
    {
        String userName = properties.getProperty(USER_NAME);
        if (userName == null) {
            logger.warn("No user name saved on file [" + file + "], create the file and/or put a line: userName=<YOUR_USER_NAME>");
            return "";
        }
        return userName;
    }

    public String getLJColligoTempDir()
    {
        return get("temp.dir", System.getProperty("java.io.tmpdir"));
    }

    public void setUserName(String userName)
    {
        properties.put(USER_NAME, userName);
    }

    public void set(String key, String s)
    {
        properties.setProperty(key, s);
    }

    public void save()
    {
        try {
            logger.debug("Saving to " + file);
            FileOutputStream output = new FileOutputStream(file);
            properties.storeToXML(output, "no comments", "UTF-8");
            logger.debug("Saved to " + file);
        } catch (Exception e) {
            throw new LJColligoException(e);
        }
    }
}
