package com.mcmmo.registries;

import com.mcmmo.Profession;
import com.mcmmo.data.BlockExperienceData;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class BlockExperienceRegistry {
    private static final Map<Block, BlockExperienceData> BLOCK_DATA = new HashMap<>();

    public static void register(Block block, Profession profession, int minExp, int maxExp) {
        BLOCK_DATA.put(block, new BlockExperienceData(profession, minExp, maxExp));
    }

    public static BlockExperienceData getBlockData(Block block) {
        return BLOCK_DATA.get(block);
    }

    public static void init() {
        // Mining
        register(Blocks.COAL_ORE, Profession.MINING, 1, 3);
        register(Blocks.DEEPSLATE_COAL_ORE, Profession.MINING, 1, 3);
        register(Blocks.COPPER_ORE, Profession.MINING, 3, 5);

        // Foraging
        register(Blocks.OAK_LOG, Profession.FORAGING, 1, 3);
        register(Blocks.BIRCH_LOG, Profession.FORAGING, 1, 3);
        register(Blocks.SPRUCE_LOG, Profession.FORAGING, 1, 3);
    }
}
