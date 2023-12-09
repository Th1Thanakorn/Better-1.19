package com.thana.better119.common.item;

import com.thana.better119.core.Better1_19;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;

public class HoneyMilkBucketItem extends MilkBucketItem {

	public HoneyMilkBucketItem() {
		super(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(Better1_19.TAB));
	}
}
