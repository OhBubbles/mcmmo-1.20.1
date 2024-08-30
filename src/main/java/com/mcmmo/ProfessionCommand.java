package com.mcmmo;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.server.network.ServerPlayerEntity;

public class ProfessionCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("profession")
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    IPlayerProfessionData professionDataAccessor = (IPlayerProfessionData) player;
                    assert professionDataAccessor != null;
                    PlayerProfessionData professionData = professionDataAccessor.mcmmo$getProfessionData();

                    for (Profession profession : Profession.values()) {
                        int level = professionData.getLevel(profession);
                        int xp = professionData.getExperience(profession);
                        int xpForNextLevel = professionData.getXPForNextLevel(level);
                        player.sendMessage(Text.literal("§6§l" + profession.name() + "§r§6" + " Level: " + level + " (XP: " + xp + "/" + xpForNextLevel + ")"), false);
                    }
                    return 1;
                })
        );
    }
}
