package club.iananderson.iantweaks;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;


@Mod.EventBusSubscriber(modid = IanAndersonTweaks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        SPEC = configBuilder.build();
    }

    public static  ForgeConfigSpec.ConfigValue<Integer> PLASMO_ICON_SIZE;
    public static  ForgeConfigSpec.ConfigValue<Integer> PLASMO_VERTICAL_OFFSET;
    public static  ForgeConfigSpec.ConfigValue<Integer> PLASMO_HORIZONTAL_OFFSET;

    private static void setupConfig(ForgeConfigSpec.Builder BUILDER) {
        BUILDER.push("Plasmo Voice Tweaks");
            PLASMO_ICON_SIZE = BUILDER
                    .comment("Icon size of the Plasmo Voice HUD icons\n" +
                            "Original size is 16")
                    .defineInRange("plasmoIconSize", 12, 0, 16);

            PLASMO_VERTICAL_OFFSET = BUILDER
                    .comment("Vertical offset of the Plasmo Voice HUD icons\n" +
                            "Default is 0")
                    .define("plasmoVerticalOffset", 0);

            PLASMO_HORIZONTAL_OFFSET = BUILDER
                .comment("Horizontal offset of the Plasmo Voice HUD icons\n" +
                        "Default is 0")
                .define("plasmoHorizontalOffset", 0);
        BUILDER.pop();

    }
    public static int plasmoIconSize;
    public static int plasmoVerticalOffset;
    public static int plasmoHorizontalOffset;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        plasmoIconSize = PLASMO_ICON_SIZE.get();
        plasmoVerticalOffset = PLASMO_VERTICAL_OFFSET.get();
        plasmoHorizontalOffset = PLASMO_HORIZONTAL_OFFSET.get();

    }
}
