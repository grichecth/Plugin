package com.wolvencraft.yasp.Database.tables.normal;

import com.wolvencraft.yasp.Database.tables.DBTable;

public enum TotalItemsTable implements DBTable {
	
	TotalBlocksId("totalitemsID"),
	MaterialId("material_id"),
	PlayerId("player_id"),
	Dropped("drop"),
	PickedUp("pickup");
	
	TotalItemsTable(String columnName) {
		this.columnName = columnName;
	}
	
	private String columnName;
	
	@Override
	public String getColumnName() { return columnName; }
}
