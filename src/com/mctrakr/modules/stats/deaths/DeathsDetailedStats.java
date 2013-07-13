/*
 * DeathsDetailedStats.java
 * 
 * Statistics
 * Copyright (C) 2013 bitWolfy <http://www.wolvencraft.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.mctrakr.modules.stats.deaths;

import lombok.AccessLevel;
import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.mctrakr.database.Query;
import com.mctrakr.modules.DataStore.DetailedData;
import com.mctrakr.modules.stats.deaths.Tables.DetailedDeathsTable;
import com.mctrakr.session.OnlineSession;
import com.mctrakr.util.Util;

public class DeathsDetailedStats {
    
    /**
     * An immutable natural death entry
     * @author bitWolfy
     *
     */
    @Getter(AccessLevel.PUBLIC) 
    public static class NaturalDeathEntry extends DetailedData {
        
        private DamageCause cause;
        private Location location;
        private long timestamp;
        
        public NaturalDeathEntry(OnlineSession session, Location location, DamageCause cause) {
            super(session);
            this.cause = cause;
            this.location = location.clone();
            timestamp = Util.getTimestamp();
        }

        @Override
        public boolean pushData() {
            return Query.table(DetailedDeathsTable.TableName)
                    .value(DetailedDeathsTable.PlayerId, session.getId())
                    .value(DetailedDeathsTable.Cause, cause.name())
                    .value(DetailedDeathsTable.World, location.getWorld().getName())
                    .value(DetailedDeathsTable.XCoord, location.getBlockX())
                    .value(DetailedDeathsTable.YCoord, location.getBlockY())
                    .value(DetailedDeathsTable.ZCoord, location.getBlockZ())
                    .value(DetailedDeathsTable.Timestamp, timestamp)
                    .insert();
        }
    }
}