/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
 * Created on 22/11/2007 19:15:36
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

package net.bpfurtado.ljcolligo.model;

import java.util.Collection;
import java.util.LinkedList;

import net.bpfurtado.ljcolligo.LJColligoListener;

public abstract class LJColligoObservable
{
	private Collection<LJColligoListener> ls = new LinkedList<LJColligoListener>();

	public LJColligoObservable()
	{
	}

	public LJColligoObservable(LJColligoObservable other)
	{
		addAllListenersFrom(other);
	}

	public void addAllListenersFrom(LJColligoObservable o)
	{
		ls.addAll(o.ls);
	}

	public void addListener(LJColligoListener l)
	{
		ls.add(l);
	}

	public void sendMessageToListeners(String msg)
	{
		if (!msg.endsWith("\n")) {
			msg += "\n";
		}

		for (LJColligoListener l : ls) {
			l.receiveFromLJColligo(msg);
		}
	}
}
