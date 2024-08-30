package com.mcmmo.data;

import com.mcmmo.Profession;

public record BlockExperienceData(Profession profession, int minExp, int maxExp) {
}
