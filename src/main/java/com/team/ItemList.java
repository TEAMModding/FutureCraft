package com.team;

import com.team.item.ItemLaser;
import com.team.item.ItemMultimeter;
import com.team.item.ItemSpaceSuit;
import com.team.item.SimpleItem;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

public class ItemList 
{
	//ingots
	public static SimpleItem copper_ingot;
	public static SimpleItem tin_ingot;
	public static SimpleItem steel_ingot;
	public static SimpleItem bronze_ingot;
	
	//bronze tools
	public static ItemSword bronze_sword;
	public static ItemSpade bronze_shovel;
	public static ItemAxe bronze_axe;
	public static ItemPickaxe bronze_pickaxe;
	public static ItemHoe bronze_hoe;
	
	//steel tools
	public static ItemSword steel_sword;
	public static ItemSpade steel_shovel;
	public static ItemAxe steel_axe;
	public static ItemPickaxe steel_pickaxe;
	public static ItemHoe steel_hoe;
	
	//space suit
	public static ItemSpaceSuit space_suit;
	
	//misc
	public static SimpleItem stone_channel;
	public static SimpleItem stone_cast;
	public static ItemMultimeter multimeter;
	public static ItemLaser itemLaser;
}
