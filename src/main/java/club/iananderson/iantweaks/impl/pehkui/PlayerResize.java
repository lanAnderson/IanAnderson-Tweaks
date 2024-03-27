package club.iananderson.iantweaks.impl.pehkui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

public class PlayerResize {

    public PlayerResize(){
    }

    public static void test(){
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        ScaleType scaleType = ScaleTypes.BASE;

        if(ModList.get().isLoaded("pehkui") && Minecraft.getInstance().options.renderDebug){
            ScaleData playerScaleData = scaleType.getScaleData(player);
            playerScaleData.setTargetScale(0.5F);
        }
    }
}
