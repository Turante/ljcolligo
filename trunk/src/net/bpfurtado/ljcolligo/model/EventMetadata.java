/**
 * Created by Bruno Patini Furtado [http://bpfurtado.livejournal.com]
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
import java.util.Date;

public class EventMetadata
{
	private Collection<String> tags;

	private Date revisionTime;
	private Integer revisionNumber;

	private String music;
	private Boolean preformattedOption;
	private String location;
	private Integer moodId;
	
	@Override
	public String toString()
	{
		return "[Event: tags=" + tags + "]";
	}

	public Collection<String> getTags()
	{
		return tags;
	}

	public Integer getRevisionNumber()
	{
		return revisionNumber;
	}

	public String getMusic()
	{
		return music;
	}

	public Date getRevisionTime()
	{
		return revisionTime;
	}

	public Boolean getPreformattedOption()
	{
		return preformattedOption;
	}

	public String getLocation()
	{
		return location;
	}

	public Integer getMoodId()
	{
		return moodId;
	}

	public void setTags(Collection<String> tags)
	{
		this.tags = tags;
	}

	public void setRevisionNumber(Integer revisionNumber)
	{
		this.revisionNumber = revisionNumber;
	}

	public void setMusic(String music)
	{
		this.music = music;
	}

	public void setRevisionTime(Date revisionTime)
	{
		this.revisionTime = revisionTime;
	}

	public void setPreformattedOption(Boolean preformattedOption)
	{
		this.preformattedOption = preformattedOption;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public void setMoodId(Integer integer)
	{
		this.moodId = integer;
	}
}
