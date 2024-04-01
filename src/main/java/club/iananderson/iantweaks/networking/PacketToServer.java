package club.iananderson.iantweaks.networking;

import club.iananderson.iantweaks.items.ControllerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketToServer {
    private final float targetScale;

    public PacketToServer(float targetScale) {
        this.targetScale = targetScale;
    }

    public PacketToServer(FriendlyByteBuf buf) {
        targetScale = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(targetScale);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        //Server side
        ServerPlayer player = ctx.getSender();
        if (player.getUseItem().getItem() instanceof ControllerItem controllerItem) {
            controllerItem.getTargetValue();
        }
        return true;
    }
}
