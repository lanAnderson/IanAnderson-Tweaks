package club.iananderson.iantweaks.networking;

import club.iananderson.iantweaks.IanAndersonTweaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Channel {
    public static SimpleChannel INSTANCE;
    private static final String PROTOCOL_VERSION = "1";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void register() {
        SimpleChannel channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(IanAndersonTweaks.MODID, "iantweaks"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals);

        INSTANCE = channel;

        channel.messageBuilder(PacketToServer.class, nextID(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketToServer::new)
                .encoder(PacketToServer::toBytes)
                .consumerMainThread(PacketToServer::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
