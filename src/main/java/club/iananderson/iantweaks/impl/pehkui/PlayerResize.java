package club.iananderson.iantweaks.impl.pehkui;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import virtuoel.pehkui.api.*;


public final class PlayerResize {
    private final ScaleType scaleType;

    public PlayerResize(ScaleType scaleType){
        this.scaleType = scaleType;
    }

    public float currentScale(Player player){
        return scaleType.getScaleData(player).getScale();
    }

    public void resize(Player player, float scale){
            scaleType.getScaleData(player).setScale(scale);
    }
    public void resetSize(Player player){
            scaleType.getScaleData(player).resetScale();
    }
}
