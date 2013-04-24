/*
 * RemoteConfiguration.java
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

package com.wolvencraft.yasp.settings;

import com.wolvencraft.yasp.db.Query;
import com.wolvencraft.yasp.db.Query.QueryResult;
import com.wolvencraft.yasp.db.tables.Normal.SettingsTable;

/**
 * Represents the configuration pulled from the database.<br />
 * No data is stored locally; all information is pulled from the database during runtime.
 * @author bitWolfy
 *
 */
public enum RemoteConfiguration {
    DatabaseVersion("version"),
    
    Ping("ping"),
    LogDelay("log_delay"),
    ShowWelcomeMessages("show_welcome_messages"),
    WelcomeMessage("welcome_message"),
    ShowFirstJoinMessages("show_first_join_message"),
    FirstJoinMessage("first_join_message");
    
    String key;
    QueryResult entry;
    boolean refresh;
    
    /**
     * <b>Default constructor</b><br />
     * Creates a new RemoteConfiguration entry based on the specified key
     * @param key Entry key
     */
    RemoteConfiguration(String key) {
        try { this.entry = Query.table(SettingsTable.TableName).column("value").condition("key", key).select(); }
        catch (Throwable t) { this.entry = null; }
        refresh = false;
        this.key = key;
    }
    
    /**
     * Returns the configuration value as String
     * @deprecated <code>asString();</code> should be used instead
     * @return Configuration value
     */
    @Override
    public String toString() {
        return asString();
    }
    
    /**
     * Returns the configuration value as String
     * @return Configuration value
     */
    public String asString() {
        if(refresh) {
            try { entry = Query.table(SettingsTable.TableName).column("value").condition("key", key).select(); }
            catch (Throwable t) { entry = null; }
            refresh = false;
        }
        try { return entry.asString("value"); }
        catch (Throwable t) { return ""; }
    }
    
    /**
     * Returns the configuration value as an integer
     * @return Configuration value
     */
    public int asInteger() { 
        if(refresh) {
            try { entry = Query.table(SettingsTable.TableName).column("value").condition("key", key).select(); }
            catch (Throwable t) { entry = null; }
            refresh = false;
        }
        try { return entry.asInt("value"); }
        catch (Throwable t) { return 0; }
    }
    
    /**
     * Returns the configuration value as a boolean
     * @return Configuration value
     */
    public boolean asBoolean() {
        if(refresh) {
            try { entry = Query.table(SettingsTable.TableName).column("value").condition("key", key).select(); }
            catch (Throwable t) { entry = null; }
            refresh = false;
        }
        try { return entry.asBoolean("value"); }
        catch (Throwable t) { return false; }
    }
    
    /**
     * Updates the configuration with the specified value
     * @param value New configuration value
     * @return <b>true</b> if the update was successful, <b>false</b> otherwise
     */
    public boolean update(Object value) {
        return Query.table(SettingsTable.TableName).value("value", value).condition("key", key).update();
    }
    
    /**
     * Signals the plugin to pull the stored value from the database next time it is called
     */
    public static void clearCache() {
        for(RemoteConfiguration configEntry : RemoteConfiguration.values()) {
            configEntry.refresh = true;
        }
    }
}