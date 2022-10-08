package com.thana.better119.common.item;

import com.thana.better119.core.Better1_19;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockItemBase extends BlockItem {

    public BlockItemBase(Block block) {
        super(block, new Item.Properties().tab(Better1_19.TAB));
    }
}
