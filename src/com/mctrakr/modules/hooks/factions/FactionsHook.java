/*
 * FactionsHook.java
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

package com.mctrakr.modules.hooks.factions;

import org.bukkit.entity.Player;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.mctrakr.modules.hooks.PluginHook;

public class FactionsHook extends PluginHook {
    
    private static final String PLUGIN_NAME = "Factions";
    
    public FactionsHook() {
        super(FactionsDataStore.lock, PLUGIN_NAME);
    }
    
    /**
     * Returns the name of the player's faction
     * @param player Player object
     * @return Name of the faction
     */
    public String getCurrentFaction(Player player) {
        FPlayer fplayer = FPlayers.i.get(player);
        return fplayer.getFaction().getId();
    }
    
    /**
     * Returns the faction that owns the land the player is currently standing.
     * @param player Player object
     * @return Name of the faction
     */
    public String getCurrentLocation(Player player) {
        FPlayer fplayer = FPlayers.i.get(player);
        Faction factionAt = Board.getFactionAt(fplayer.getLastStoodAt());
        if(factionAt == null) return "none";
        return factionAt.getId();
    }
    
    /**
     * Returns the player's current power
     * @param player Player object
     * @return Player's power
     */
    public double getPower(Player player) {
        FPlayer fplayer = FPlayers.i.get(player);
        return fplayer.getPower();
    }
    
    /**
     * Returns the player's maximum power
     * @param player Player object
     * @return Player's maximum power
     */
    public double getMaxPower(Player player) {
        FPlayer fplayer = FPlayers.i.get(player);
        return fplayer.getPowerMax();
    }
    
    /**
     * Returns the player's role
     * @param player Player object
     * @return Player's role
     */
    public String getRole(Player player) {
        FPlayer fplayer = FPlayers.i.get(player);
        return fplayer.getRole().name();
    }
    
    /**
     * Returns the player's title
     * @param player Player object
     * @return Player's title
     */
    public String getTitle(Player player) {
        FPlayer fplayer = FPlayers.i.get(player);
        return fplayer.getTitle();
    }
    
}