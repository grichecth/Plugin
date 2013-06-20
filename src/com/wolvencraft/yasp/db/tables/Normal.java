/*
 * Normal.java
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

package com.wolvencraft.yasp.db.tables;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents any non-detailed tables in the database.<br >
 * Stores table and column names, so that they can be safely used from the plugin
 * @author bitWolfy
 *
 */
public class Normal {
    
    /**
     * Represents the <i>settings</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum SettingsTable implements DBTable {
        TableName       ("settings"),
        Key             ("key"),
        Value           ("value");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>server_statistics</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum ServerStatsTable implements DBTable {
        TableName       ("server_statistics"),
        Key             ("key"),
        Value           ("value");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>distances</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum DistancePlayersTable implements DBTable {
        TableName       ("distances"),
        PlayerId        ("player_id"),
        Foot            ("foot"),
        Swim            ("swim"),
        Flight          ("flight"),
        Boat            ("boat"),
        Minecart        ("minecart"),
        Pig             ("pig");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>players</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum PlayersTable implements DBTable {
        TableName       ("players"),
        PlayerId        ("player_id"),
        Name            ("name"),
        Online          ("online"),
        LoginTime       ("login_time"),
        Playtime        ("playtime"),
        FirstLogin      ("first_login"),
        LongestSession  ("longest_session"),
        Logins          ("logins");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>misc_info_players</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum MiscInfoPlayersTable implements DBTable {
        TableName       ("misc_info_players"),
        PlayerId        ("player_id"),
        IsOp            ("is_op"),
        IsBanned        ("is_banned"),
        PlayerIp        ("player_ip"),
        Gamemode        ("gamemode"),
        ExpPercent      ("exp_perc"),
        ExpTotal        ("exp_total"),
        ExpLevel        ("exp_level"),
        FoodLevel       ("food_level"),
        HealthLevel     ("health"),
        ArmorLevel      ("armor_rating"),
        FishCaught      ("fish_caught"),
        TimesKicked     ("times_kicked"),
        EggsThrown      ("eggs_thrown"),
        FoodEaten       ("food_eaten"),
        ArrowsShot      ("arrows_shot"),
        DamageTaken     ("damage_taken"),
        TimesJumped     ("times_jumped"),
        BedsEntered     ("beds_entered"),
        PortalsEntered  ("portals_entered"),
        WordsSaid       ("words_said"),
        CommandsSent    ("commands_sent"),
        CurKillStreak   ("kill_streak"),
        MaxKillStreak   ("max_kill_streak");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>player_inventories</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum PlayersInv implements DBTable {
        TableName       ("player_inventories"),
        PlayerId        ("player_id"),
        SelectedItem    ("selected_item"),
        Hotbar          ("hotbar"),
        RowOne          ("row_one"),
        RowTwo          ("row_two"),
        RowThree        ("row_three"),
        Armor           ("armor"),
        PotionEffects   ("potion_effects");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>total_blocks</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum TotalBlocksTable implements DBTable {
        TableName       ("total_blocks"),
        MaterialId      ("material_id"),
        PlayerId        ("player_id"),
        Destroyed       ("destroyed"),
        Placed          ("placed");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>total_items</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum TotalItemsTable implements DBTable {
        TableName       ("total_items"),
        MaterialId      ("material_id"),
        PlayerId        ("player_id"),
        Dropped         ("dropped"),
        PickedUp        ("picked_up"),
        Used            ("used"),
        Crafted         ("crafted"),
        Smelted         ("smelted"),
        Broken          ("broken"),
        Enchanted       ("enchanted"),
        Repaired        ("repaired");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>total_deaths</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum TotalDeathPlayersTable implements DBTable {
        TableName       ("total_deaths"),
        PlayerId        ("player_id"),
        Cause           ("cause"),
        Times           ("times");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>total_pve_kills</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum TotalPVEKillsTable implements DBTable {
        TableName       ("total_pve_kills"),
        MaterialId      ("material_id"),
        CreatureId      ("entity_id"),
        PlayerId        ("player_id"),
        PlayerKilled    ("player_killed"),
        CreatureKilled  ("creature_killed");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>total_pvp_kills</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum TotalPVPKillsTable implements DBTable {
        TableName       ("total_pvp_kills"),
        MaterialId      ("material_id"),
        PlayerId        ("player_id"),
        VictimId        ("victim_id"),
        Times           ("times");
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
    /**
     * Represents the <i>player_locations</i> table.
     * @author bitWolfy
     *
     */
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public enum PlayersLocations implements DBTable {
        TableName       ("player_locations"),
        PlayerId        ("player_id"),
        World           ("world"),
        XCoord          ("x"),
        YCoord          ("y"),
        ZCoord          ("z"),
        Biome           ("biome"),
        Humidity        ("humidity")
        ;
        
        private String columnName;
        
        @Override
        public String toString() { return columnName; }
    }
    
}
