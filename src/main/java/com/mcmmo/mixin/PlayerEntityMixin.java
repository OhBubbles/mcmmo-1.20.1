package com.mcmmo.mixin;

import com.mcmmo.IPlayerProfessionData;
import com.mcmmo.PlayerProfessionData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class PlayerEntityMixin implements IPlayerProfessionData {

    @Unique
    private PlayerProfessionData professionData;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo info) {
        this.professionData = new PlayerProfessionData();
    }

    @Override
    public PlayerProfessionData mcmmo$getProfessionData() {
        return professionData;
    }
}
