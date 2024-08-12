package com.mcmmo.mixin;

import com.mcmmo.IPlayerProfessionData;
import com.mcmmo.PlayerProfessionData;
import com.mcmmo.Profession;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayerEntity.class)
public abstract class BlockBreakMixin {

    @Shadow public abstract void addExperience(int experience);

    static {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!world.isClient && player instanceof ServerPlayerEntity) {
                handleBlockBreak((ServerPlayerEntity) player, world, pos, state);
            }
        });
    }

    private static void handleBlockBreak(ServerPlayerEntity player, World world, BlockPos pos, BlockState state) {
        IPlayerProfessionData professionDataAccessor = (IPlayerProfessionData) player;
        PlayerProfessionData data = professionDataAccessor.getProfessionData();

        if (state.isOf(Blocks.COAL_ORE) || state.isOf(Blocks.DEEPSLATE_COAL_ORE)) {
            data.addExperience(Profession.MINING, 1, 3, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Mining XP!"), true);
        }
        else if (state.isOf(Blocks.COPPER_ORE) || state.isOf(Blocks.DEEPSLATE_COPPER_ORE)) {
            data.addExperience(Profession.MINING, 3, 5, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Mining XP!"), true);
        }
        else if (state.isOf(Blocks.IRON_ORE) || state.isOf(Blocks.DEEPSLATE_IRON_ORE)) {
            data.addExperience(Profession.MINING, 3, 5, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Mining XP!"), true);
        }
        else if (state.isOf(Blocks.GOLD_ORE) || state.isOf(Blocks.DEEPSLATE_GOLD_ORE) || state.isOf(Blocks.NETHER_GOLD_ORE)) {
            data.addExperience(Profession.MINING, 4, 6, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Mining XP!"), true);
        }
        else if (state.isOf(Blocks.LAPIS_ORE) || state.isOf(Blocks.DEEPSLATE_LAPIS_ORE)) {
            data.addExperience(Profession.MINING, 4, 6, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Mining XP!"), true);
        }
        else if (state.isOf(Blocks.REDSTONE_ORE) || state.isOf(Blocks.DEEPSLATE_REDSTONE_ORE)) {
            data.addExperience(Profession.MINING, 4, 6, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Mining XP!"), true);
        }
        else if (state.isOf(Blocks.NETHER_QUARTZ_ORE)) {
            data.addExperience(Profession.MINING, 6, 8, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Mining XP!"), true);
        }
        else if (state.isOf(Blocks.DIAMOND_ORE) || state.isOf(Blocks.DEEPSLATE_DIAMOND_ORE)) {
            data.addExperience(Profession.MINING, 8, 10, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Mining XP!"), true);
        }
        else if (state.isOf(Blocks.EMERALD_ORE) || state.isOf(Blocks.DEEPSLATE_EMERALD_ORE)) {
            data.addExperience(Profession.MINING, 10, 12, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Mining XP!"), true);
        }
        else if (state.isOf(Blocks.ANCIENT_DEBRIS)) {
            data.addExperience(Profession.MINING, 12, 16, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Mining XP!"), true);
        }

        if (state.isOf(Blocks.OAK_LOG)) {
            data.addExperience(Profession.FORAGING, 1, 3, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Foraging XP!"), true);
        }
        else if (state.isOf(Blocks.DARK_OAK_LOG)) {
            data.addExperience(Profession.FORAGING, 1, 3, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Foraging XP!"), true);
        }
        else if (state.isOf(Blocks.BIRCH_LOG)) {
            data.addExperience(Profession.FORAGING, 1, 3, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Foraging XP!"), true);
        }
        else if (state.isOf(Blocks.SPRUCE_LOG)) {
            data.addExperience(Profession.FORAGING, 1, 3, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Foraging XP!"), true);
        }
        else if (state.isOf(Blocks.ACACIA_LOG)) {
            data.addExperience(Profession.FORAGING, 1, 3, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Foraging XP!"), true);
        }
        else if (state.isOf(Blocks.CHERRY_LOG)) {
            data.addExperience(Profession.FORAGING, 1, 3, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Foraging XP!"), true);
        }
        else if (state.isOf(Blocks.MANGROVE_LOG)) {
            data.addExperience(Profession.FORAGING, 1, 3, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Foraging XP!"), true);
        }
        else if (state.isOf(Blocks.JUNGLE_LOG)) {
            data.addExperience(Profession.FORAGING, 1, 3, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Foraging XP!"), true);
        }
        else if (state.isOf(Blocks.BAMBOO)) {
            data.addExperience(Profession.FORAGING, 1, 3, player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " Foraging XP!"), true);
        }
    }
}
