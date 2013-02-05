package com.wolvencraft.yasp.Database.tables.normal;

import com.wolvencraft.yasp.Database.tables.DBTable;

public enum TotalPVETable implements DBTable {
	
	TotalBlocksId("total_pve_id"),
	PlayerId("player_id"),
	CreatureId("creature_id"),
	PlayerKilled("player_killed"),
	CreatureKilled("creature_killed");
	
	TotalPVETable(String columnName) {
		this.columnName = columnName;
	}
	
	private String columnName;
	
	@Override
	public String getColumnName() { return columnName; }
}
