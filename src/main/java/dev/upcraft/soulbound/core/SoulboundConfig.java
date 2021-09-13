package dev.upcraft.soulbound.core;

import dev.upcraft.soulbound.Soulbound;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.math.MathHelper;

@Config(name = Soulbound.MODID)
public class SoulboundConfig implements ConfigData {

    public double soulboundPreservationRate = 1.0F;
    public double soulboundDropChance = 0.0F;

    @Override
    public void validatePostLoad() throws ValidationException {
        soulboundPreservationRate = MathHelper.clamp(soulboundPreservationRate, 0.0F, 1.0F);
        soulboundDropChance = MathHelper.clamp(soulboundDropChance, 0.0F, 1.0F);
    }
}
