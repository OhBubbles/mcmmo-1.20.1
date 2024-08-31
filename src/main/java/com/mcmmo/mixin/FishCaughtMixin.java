package com.mcmmo.mixin;

import com.mcmmo.IPlayerProfessionData;
import com.mcmmo.PlayerProfessionData;
import com.mcmmo.Profession;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.TypedActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.lang.reflect.Field;

@Mixin(ServerPlayerEntity.class)
public abstract class FishCaughtMixin {

    @Unique
    private static final int MIN_XP = 1;
    @Unique
    private static final int MAX_XP = 6;

    static {
        // Register the callback for when a player uses an item
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (world.isClient) {
                return TypedActionResult.pass(ItemStack.EMPTY);
            }

            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.getItem() == Items.FISHING_ROD) {
                if (player instanceof ServerPlayerEntity serverPlayer) {
                    checkFishingSuccess(serverPlayer);
                }
            }

            return TypedActionResult.pass(itemStack);
        });
    }

    @Unique
    private static void checkFishingSuccess(ServerPlayerEntity player) {
        try {
            if (player.fishHook instanceof FishingBobberEntity && !player.isCreative()) {
                FishingBobberEntity bobber = player.fishHook;

                // Reflectively access the private 'caughtFish' field
                Field caughtFishField = FishingBobberEntity.class.getDeclaredField("caughtFish");
                caughtFishField.setAccessible(true);
                boolean caughtFish = caughtFishField.getBoolean(bobber);

                if (caughtFish) {
                    // Award profession XP
                    IPlayerProfessionData professionDataAccessor = (IPlayerProfessionData) player;
                    PlayerProfessionData data = professionDataAccessor.mcmmo$getProfessionData();

                    // Generate random XP between MIN_XP and MAX_XP
                    int fishingXP = MIN_XP + player.getEntityWorld().random.nextInt(MAX_XP - MIN_XP + 1);
                    data.addExperience(Profession.FISHING, fishingXP, fishingXP, player);

                    player.sendMessage(Text.literal("Gained " + fishingXP + " Fishing XP!"), true);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
