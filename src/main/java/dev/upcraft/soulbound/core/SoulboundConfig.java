package dev.upcraft.soulbound.core;

import dev.upcraft.soulbound.Soulbound;
import me.shedaniel.autoconfig.ConfigData;
import net.minecraft.util.math.MathHelper;

@me.shedaniel.autoconfig.annotation.Config(name = Soulbound.MODID)
public class SoulboundConfig implements ConfigData {

    public float soulboundRemovalChance = 0.0F;

    @Override
    public void validatePostLoad() throws ValidationException {
        soulboundRemovalChance = MathHelper.clamp(soulboundRemovalChance, 0.0F, 1.0F);
    }
}
