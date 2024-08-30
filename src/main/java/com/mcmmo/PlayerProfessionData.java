package com.mcmmo;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.EnumMap;
import java.util.Random;

public class PlayerProfessionData {
    private final EnumMap<Profession, Integer> experience;
    private final EnumMap<Profession, Integer> levels;

    private static int AMOUNT = 0;
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

    public static int getRandomExp(int minEXP, int maxEXP) {
        Random r = new Random();

        if (minEXP > maxEXP) {
            throw new IllegalArgumentException("minExp must be less than or equal to maxExp");
        }
        return r.nextInt(maxEXP - minEXP + 1) + minEXP;
    }

    public void addExperience(Profession profession, int minEXP, int maxEXP, ServerPlayerEntity player) {
        int currentXP = getExperience(profession);
        int currentLevel = getLevel(profession);

        int amount = getRandomExp(minEXP, maxEXP);
        AMOUNT = amount;

        if (currentLevel >= MAX_LEVEL) {
            return; // Don't add XP if already at max level
        }

        int newXP = currentXP + amount;
        int xpForNextLevel = getXPForNextLevel(currentLevel);

        experience.put(profession, newXP);

        // Check if level up is needed
        if (newXP >= xpForNextLevel) {
            levels.put(profession, currentLevel + 1);
            // Carry over the excess XP to the next level
            experience.put(profession, newXP - xpForNextLevel);
            // Notify the player of level up
            player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1, 1);
            player.sendMessage(Text.literal("§6§l" + "Congratulations! §r§6You have reached level " + (currentLevel + 1) + " in " + profession.name().charAt(0) + profession.name().substring(1).toLowerCase() + "!"), false);
        }
    }

    public int getXPForNextLevel(int level) {
        // Example XP curve: increases by 100 XP per level
        // return 100 + (level - 1) * 50;
        int REQ_EXP;

        if (level <= 15) {
            REQ_EXP = 2 * level + 7;
        } else if (level <= 30) {
            REQ_EXP = 5 * level - 38;
        } else {
            REQ_EXP = 9 * level - 158;
        }

        return REQ_EXP;
    }
}
