package club.iananderson.iantweaks.events;

import club.iananderson.iantweaks.impl.pehkui.PlayerResize;
import club.iananderson.iantweaks.networking.Channel;
import club.iananderson.iantweaks.networking.PacketToServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScaleEventHandler {
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickItem event) {
        Level level = event.getLevel();
        Player player = event.getEntity();

        if (level.isClientSide) {
            float targetScale = PlayerResize.INSTANCE.getTargetScale(player);
            Channel.sendToServer(new PacketToServer(targetScale));
        }
    }
}
