package club.iananderson.iantweaks.impl.pehkui;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;

public class PlayerResize {
    private final ScaleType scaleType;

    public PlayerResize(ScaleType scaleType){
        this.scaleType = scaleType;
    }

    public void resize(Player player, Float scale){
        if(ModList.get().isLoaded("pehkui")){
            ScaleData playerScaleData = scaleType.getScaleData(player);

            playerScaleData.setScale(scale);
        }
    }
}
