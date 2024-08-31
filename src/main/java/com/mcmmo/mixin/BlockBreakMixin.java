package com.mcmmo.mixin;

import com.mcmmo.IPlayerProfessionData;
import com.mcmmo.PlayerProfessionData;
import com.mcmmo.data.BlockExperienceData;
import com.mcmmo.registries.BlockExperienceRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
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

        if (blockData != null && !player.isCreative()) {
            ItemStack mainHandItem = player.getMainHandStack();
            String professionName = blockData.profession().name().toLowerCase();

            // Determine if the tool is relevant to the profession and does not have Silk Touch
            if ((isMiningTool(mainHandItem, professionName) || isWoodcuttingTool(mainHandItem, professionName)) &&
                    !hasSilkTouch(mainHandItem)) {

                IPlayerProfessionData professionDataAccessor = (IPlayerProfessionData) player;
                PlayerProfessionData data = professionDataAccessor.mcmmo$getProfessionData();
                data.addExperience(blockData.profession(), blockData.minExp(), blockData.maxExp(), player);
                player.sendMessage(Text.literal("Gained " + data.getAmount() + " " + blockData.profession().name() + " XP!"), true);
            }
        }
    }

    @Unique
    private static boolean isMiningTool(ItemStack item, String professionName) {
        return item.isIn(ItemTags.PICKAXES) && professionName.contains("mining");
    }

    @Unique
    private static boolean isWoodcuttingTool(ItemStack item, String professionName) {
        return item.isIn(ItemTags.AXES) && professionName.contains("woodcutting");
    }

    @Unique
    private static boolean hasSilkTouch(ItemStack item) {
        return EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, item) > 0;
    }
}
