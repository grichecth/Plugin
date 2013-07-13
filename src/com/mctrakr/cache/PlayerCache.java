/* 
 * PlayerCache.java
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

package com.mctrakr.cache;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.mctrakr.Statistics;
import com.mctrakr.database.Query;
import com.mctrakr.database.Query.QueryResult;
import com.mctrakr.modules.stats.player.Tables.PlayersTable;
import com.mctrakr.util.Message;

/**
 * Caches player names and IDs server-side
 * @author bitWolfy
 *
 */
public class PlayerCache {
    
    private PlayerCache() { }
    
    /**
     * Returns the ID of the player
     * @param player Player to look up
     * @return Player ID
     */
    public static int get(Player player) {
        if(player.hasMetadata("stats_id")) {
            return player.getMetadata("stats_id").get(0).asInt();
        } else {
            int playerId = get(player.getName());
            player.setMetadata("stats_id", new FixedMetadataValue(Statistics.getInstance(), playerId));
            return playerId;
        }
    }
    
    /**
     * Returns the player ID based on his name.<br />
     * Very resource-heavy; if possible, use <code>get(Player player);</code>
     * @param username Player name to look up
     * @return Player ID
     */
    public static int get(String username) {
        Message.debug("Retrieving a player ID for " + username);
        
        int playerId = -1;
        do {
            QueryResult playerRow = Query.table(PlayersTable.TableName)
                    .column(PlayersTable.PlayerId)
                    .condition(PlayersTable.Name, username)
                    .select();
            
            if(playerRow == null) {
                Query.table(PlayersTable.TableName)
                    .value(PlayersTable.Name, username)
                    .insert();
                continue;
            }
            playerId = playerRow.asInt(PlayersTable.PlayerId);
        } while (playerId == -1);
        Message.debug("User ID found: " + playerId);
        return playerId;
    }
}