package club.iananderson.iantweaks.networking;

import club.iananderson.iantweaks.IanAndersonTweaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PacketHandler {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID(){
        return ID++;
    }


    public static void register(){
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(IanAndersonTweaks.MODID,"main"),
                () -> "1.0",
                version -> true,
                version -> true);

        //Server
        INSTANCE.registerMessage(nextID(), PlayerTargetSizePacket.class,
                PlayerTargetSizePacket::encode,
                PlayerTargetSizePacket::new,
                PlayerTargetSizePacket::handle);
    }

    public static void sendToServer(Object msg){
        INSTANCE.send(PacketDistributor.SERVER.noArg(),msg);
    }

    public static void sendToPlayer(Object msg, Supplier<ServerPlayer> player){
        INSTANCE.send(PacketDistributor.PLAYER.with(player), msg);
    }

    public static void sendToAllClients(Object msg){
        INSTANCE.send(PacketDistributor.ALL.noArg(),msg);
    }
}
