package club.iananderson.iantweaks.networking;


import net.minecraft.network.FriendlyByteBuf;

public class NetworkTools {
    public static void writeDouble(FriendlyByteBuf buf, Double d) {
        if (d != null) {
            buf.writeBoolean(true);
            buf.writeDouble(d);
        } else {
            buf.writeBoolean(false);
        }
    }

    public static Double readDouble(FriendlyByteBuf buf) {
        if (buf.readBoolean()) {
            return buf.readDouble();
        } else {
            return null;
        }
    }
}
