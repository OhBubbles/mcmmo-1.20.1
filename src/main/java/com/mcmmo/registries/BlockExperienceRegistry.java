package com.mcmmo.registries;

import com.mcmmo.Profession;
import com.mcmmo.data.BlockExperienceData;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class BlockExperienceRegistry {
    private static final Map<Block, BlockExperienceData> BLOCK_DATA = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger("BlockExperienceRegistry");

    public static void register(Block block, Profession profession, int minExp, int maxExp) {
        BLOCK_DATA.put(block, new BlockExperienceData(profession, minExp, maxExp));
        LOGGER.info("Registered block: {} for profession: {} with exp range: {}-{}", block, profession, minExp, maxExp);
    }

    private static void registerBlocksFromPartial(String partialId, List<String> blacklist, Profession profession, int minExp, int maxExp) {
        // Iterate over all registered blocks
        Registries.BLOCK.stream().forEach(block -> {
            Identifier blockId = Registries.BLOCK.getId(block);
            String blockIdPath = blockId.getPath().toLowerCase();

            // Check if the block ID contains the partial ID and does not contain any blacklisted words
            if (blockIdPath.contains(partialId.toLowerCase()) && blacklist.stream().noneMatch(blockIdPath::contains)) {
                register(block, profession, minExp, maxExp);
                LOGGER.info("Registered block matching partial ID: {} - Block: {} for profession: {}", partialId, block.getTranslationKey(), profession);
            }
        });
    }

    public static BlockExperienceData getBlockData(Block block) {
        return BLOCK_DATA.get(block);
    }

    public static void init() {
        LOGGER.info("Initializing BlockExperienceRegistry");

        // Example for singular register(Blocks.COPPER_ORE, Profession.MINING, 3, 5);

        // Blacklists
        List<String> logBlacklist = List.of("stripped");
        List<String> nuggetBlacklist = List.of("nether");

        // Mining
        register(Blocks.NETHER_GOLD_ORE, Profession.MINING, 1, 1);
        registerBlocksFromPartial("coal_ore", List.of(), Profession.MINING, 1, 2);
        registerBlocksFromPartial("copper_ore", List.of(), Profession.MINING, 1, 3);
        registerBlocksFromPartial("iron_ore", List.of(), Profession.MINING, 1, 3);
        registerBlocksFromPartial("gold_ore", nuggetBlacklist, Profession.MINING, 2, 4);
        registerBlocksFromPartial("redstone_ore", List.of(), Profession.MINING, 1, 5);
        registerBlocksFromPartial("lapis_ore", List.of(), Profession.MINING, 2, 5);
        registerBlocksFromPartial("quartz_ore", List.of(), Profession.MINING, 2, 5);
        registerBlocksFromPartial("diamond_ore", List.of(), Profession.MINING, 3, 7);
        registerBlocksFromPartial("emerald_ore", List.of(), Profession.MINING, 3, 7);

        // Foraging
        registerBlocksFromPartial("log", logBlacklist, Profession.WOODCUTTING, 1, 2);
    }
}
