package com.mcmmo.mixin;

import com.mcmmo.IPlayerProfessionData;
import com.mcmmo.PlayerProfessionData;
import com.mcmmo.data.BlockExperienceData;
import com.mcmmo.registries.BlockExperienceRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayerEntity.class)
public abstract class BlockBreakMixin {

    static {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!world.isClient && player instanceof ServerPlayerEntity) {
                handleBlockBreak((ServerPlayerEntity) player, state);
            }
        });
    }

    @Unique
    private static void handleBlockBreak(ServerPlayerEntity player, BlockState state) {
        Block block = state.getBlock();
        BlockExperienceData blockData = BlockExperienceRegistry.getBlockData(block);

        if (blockData != null) {
            IPlayerProfessionData professionDataAccessor = (IPlayerProfessionData) player;
            PlayerProfessionData data = professionDataAccessor.mcmmo$getProfessionData();
            data.addExperience(blockData.profession(), blockData.minExp(), blockData.maxExp(), player);
            player.sendMessage(Text.literal("Gained " + data.getAmount() + " " + blockData.profession().name() + " XP!"), true);
        }
    }
}