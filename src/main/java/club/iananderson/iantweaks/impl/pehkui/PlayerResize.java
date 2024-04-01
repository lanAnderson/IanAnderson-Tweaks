package club.iananderson.iantweaks.impl.pehkui;

import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.api.*;


public final class PlayerResize {
    private final ScaleType scaleType;
    public static PlayerResize INSTANCE = new PlayerResize(ScaleTypes.BASE);

    public PlayerResize(ScaleType scaleType){
        this.scaleType = scaleType;
    }

    public float currentScale(Player player){
        return scaleType.getScaleData(player).getScale();
    }
    public float getTargetScale(Player player){
        return scaleType.getScaleData(player).getTargetScale();
    }

    public void resize(Player player, float scale){
        scaleType.getScaleData(player).setScale(scale);
        scaleType.getScaleData(player).markForSync(true);
    }
    public void resetSize(Player player){
        scaleType.getScaleData(player).resetScale();
    }
}
