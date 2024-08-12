package com.mcmmo;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.EnumMap;
import java.util.Random;

import static net.minecraft.util.math.MathHelper.floor;

public class PlayerProfessionData {
    private EnumMap<Profession, Integer> experience;
    private EnumMap<Profession, Integer> levels;

    private static int AMOUNT = 0;
    private static int REQ_EXP = 100;
    private static final int MAX_LEVEL = 50;

    public PlayerProfessionData() {
        experience = new EnumMap<>(Profession.class);
        levels = new EnumMap<>(Profession.class);

        for (Profession profession : Profession.values()) {
            experience.put(profession, 0);
            levels.put(profession, 1);
        }
    }

    public int getAmount(){
        return AMOUNT;
    }

    public int getExperience(Profession profession) {
        return experience.getOrDefault(profession, 0);
    }

    public int getLevel(Profession profession) {
        return levels.getOrDefault(profession, 1);
    }

    public void addExperience(Profession profession, int minEXP, int maxEXP, ServerPlayerEntity player) {
        int currentXP = getExperience(profession);
        int currentLevel = getLevel(profession);

        Random r = new Random();
        int amount = r.nextInt(maxEXP-minEXP) + minEXP;
        AMOUNT = amount;

        if (currentLevel >= MAX_LEVEL) {
            return; // Don't add XP if already at max level
        }

        experience.put(profession, currentXP + amount);

        // Check if level up is needed
        if (currentXP + amount >= getXPForNextLevel(currentLevel)) {
            experience.put(profession, 0); // Reset XP after leveling up
            if (currentLevel < MAX_LEVEL) {
                levels.put(profession, currentLevel + 1);
                // Notify the player of level up
                player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1, 1);
                player.sendMessage(Text.literal("§6§l" + "Congratulations! §r§6You have reached level " + (currentLevel + 1) + " in " + profession.name().substring(0, 1) + profession.name().substring(1).toLowerCase() + "!"), false);
            }
        }
    }

    public int getXPForNextLevel(int level) {
        // Example XP curve: increases by 100 XP per level
        // return 100 + (level - 1) * 50;
        if (level <= 15) {
            REQ_EXP = 2 * level + 7;
        }
        else if (level >= 16) {
            REQ_EXP = 5 * level - 38;
        }
        else if (level >= 31) {
            REQ_EXP = 9 * level - 158;
        }
        else {
            REQ_EXP = 100;
        }
        return REQ_EXP;
    }
}
