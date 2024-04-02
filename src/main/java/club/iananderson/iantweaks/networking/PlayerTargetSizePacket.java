package club.iananderson.iantweaks.networking;

import club.iananderson.iantweaks.config.Config;
import club.iananderson.iantweaks.impl.pehkui.PlayerResize;
import club.iananderson.iantweaks.items.ControllerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerTargetSizePacket {
    private final double targetScale;

    public PlayerTargetSizePacket(double targetScale){
        this.targetScale = targetScale;
    }

    public PlayerTargetSizePacket(FriendlyByteBuf buffer){
        this(buffer.readDouble());
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeDouble(this.targetScale);
    }

    public void handle(Supplier<NetworkEvent.Context> context){
        ServerPlayer player = context.get().getSender();
        if(player == null){
            return;
        }
        PlayerResize.INSTANCE.resize(player, (float) targetScale);
    }
}
