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

public class LJColligoException extends RuntimeException
{
	private static final long serialVersionUID = -1695057699040745850L;

	public LJColligoException()
	{
		super();
	}

	public LJColligoException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public LJColligoException(String message)
	{
		super(message);
	}

	public LJColligoException(Throwable cause)
	{
		super(cause);
	}
}
