/*
 * HookManager.java
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

package com.mctrakr.managers;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.mctrakr.Statistics;
import com.mctrakr.events.plugin.HookInitEvent;
import com.mctrakr.modules.hooks.PluginHook;
import com.mctrakr.modules.hooks.admincmd.AdminCmdHook;
import com.mctrakr.modules.hooks.banhammer.BanHammerHook;
import com.mctrakr.modules.hooks.commandbook.CommandBookHook;
import com.mctrakr.modules.hooks.factions.FactionsHook;
import com.mctrakr.modules.hooks.mcmmo.McMMOHook;
import com.mctrakr.modules.hooks.mobarena.MobArenaHook;
import com.mctrakr.modules.hooks.pvparena.PvpArenaHook;
import com.mctrakr.modules.hooks.vanish.VanishHook;
import com.mctrakr.modules.hooks.vault.VaultHook;
import com.mctrakr.modules.hooks.votifier.VotifierHook;
import com.mctrakr.modules.hooks.worldguard.WorldGuardHook;
import com.mctrakr.settings.ConfigLock.ModuleType;
import com.mctrakr.util.ExceptionHandler;
import com.mctrakr.util.Message;

public class HookManager {
    
    private static List<PluginHook> activeHooks = new ArrayList<PluginHook>();
    
    public HookManager() {
        PluginManager plManager = Statistics.getInstance().getServer().getPluginManager();
        Message.log(
                "| [+] Loading plugin hooks               |"
                );
        
        for(ApplicableHook hook : ApplicableHook.values()) {
            PluginHook hookObj;
            try { hookObj = hook.getHook().newInstance(); }
            catch (Throwable t) {
                ExceptionHandler.handle(t);
                continue;
            }
            
            if (plManager.getPlugin(hookObj.getPluginName()) == null) continue;
            else {
                HookInitEvent event = new HookInitEvent(hookObj.getLock().getType().getAlias());
                Bukkit.getServer().getPluginManager().callEvent(event);
                if(event.isCancelled()) continue;
                
                if(hookObj.enable()) {
                    Message.log(
                            "|  |- Hooked into " + Message.fillString(hookObj.getPluginName(), 23) + "|"
                            );
                    activeHooks.add(hookObj);
                } 
            }
        }
    }
    
    /**
     * Executes hook shutdown
     */
    public static void onDisable() {
        Message.log(
                "+-------- [ Hook Manager ] --------+",
                "|" + Message.centerString("Hook Manager shutting down", 34) + "|",
                "|" + Message.centerString("", 34) + "|"
                );
        
        for(PluginHook hook : activeHooks) {
            hook.disable();
            Message.log("|" + Message.centerString(hook.getPluginName() + " is shutting down", 34) + "|");
        }
        
        Message.log("+----------------------------------+");
    }
    
    /**
     * Returns a hook with the specified type
     * @param type Hook type
     * @return Hook, or <b>null</b> if it isn't active
     */
    public static PluginHook getHook(ModuleType type) {
        for(PluginHook hook : activeHooks) {
            if(hook.getLock().getType() == type) return hook;
        }
        return null;
    }
    
    @Getter(AccessLevel.PUBLIC)
    @AllArgsConstructor(access=AccessLevel.PUBLIC)
    private enum ApplicableHook {
        
        ADMIN_CMD       (AdminCmdHook.class),
        BAN_HAMMER      (BanHammerHook.class),
        COMMAND_BOOK    (CommandBookHook.class),
        FACTIONS        (FactionsHook.class),
        MCMMO           (McMMOHook.class),
        MOB_ARENA       (MobArenaHook.class),
        PVP_ARENA       (PvpArenaHook.class),
        VAULT           (VaultHook.class),
        VANISH          (VanishHook.class),
        VOTIFIER        (VotifierHook.class),
        WORLD_GUARD     (WorldGuardHook.class)
        ;
        
        @Getter(AccessLevel.PRIVATE)
        private Class<? extends PluginHook> hook;
        
    }
    
}