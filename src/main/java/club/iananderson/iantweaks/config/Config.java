package club.iananderson.iantweaks.config;

import club.iananderson.iantweaks.IanAndersonTweaks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;


@Mod.EventBusSubscriber(modid = IanAndersonTweaks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        SPEC = configBuilder.build();
    }

    public static  ForgeConfigSpec.ConfigValue<Integer> PLASMO_ICON_SIZE;
    public static  ForgeConfigSpec.ConfigValue<Integer> PLASMO_VERTICAL_OFFSET;
    public static  ForgeConfigSpec.ConfigValue<Integer> PLASMO_HORIZONTAL_OFFSET;
    public static ForgeConfigSpec.ConfigValue<Double> TARGET_SCALE;
    public static ForgeConfigSpec.ConfigValue<Double> MIN_SCALE;
    public static ForgeConfigSpec.ConfigValue<Double> MAX_SCALE;
    public static ForgeConfigSpec.ConfigValue<Boolean> DISPLAY_AS_BLOCKS;


    private static void setupConfig(ForgeConfigSpec.Builder BUILDER) {
        BUILDER.push("Plasmo Voice Tweaks");
            PLASMO_ICON_SIZE = BUILDER
                    .comment("Icon size of the Plasmo Voice HUD icons\n" +
                            "Original size is 16")
                    .defineInRange("plasmoIconSize", 16, 0, 16);

            PLASMO_VERTICAL_OFFSET = BUILDER
                    .comment("Vertical offset of the Plasmo Voice HUD icons\n" +
                            "Default is 0")
                    .define("plasmoVerticalOffset", 0);

            PLASMO_HORIZONTAL_OFFSET = BUILDER
                    .comment("Horizontal offset of the Plasmo Voice HUD icons\n" +
                            "Default is 0")
                    .define("plasmoHorizontalOffset", 0);
        BUILDER.pop();
        BUILDER.push("Pehkui Tweaks");
            TARGET_SCALE = BUILDER
                    .comment("Target scale to shrink/grow to using the Controller\n" +
                            "Default is x1 scale(2 blocks, 32 pixels tall)")
                    .define("targetScale", 1.0);
            MIN_SCALE = BUILDER
                    .comment("Min scale to grow to using the Controller\n" +
                            "Default is x0.05 scale")
                    .define("minScale", 0.05);
            MAX_SCALE = BUILDER
                    .comment("Max scale to grow to using the Controller\n" +
                            "Default is x2 scale")
                    .define("maxScale", 2.00);
            DISPLAY_AS_BLOCKS = BUILDER
                    .comment("Display the scale in the Controller as blocks instead of a multiplier\n"+
                            "Default is true")
                    .define("displayAsBlocks",true);
        BUILDER.pop();

    }
    public static int plasmoIconSize;
    public static int plasmoVerticalOffset;
    public static int plasmoHorizontalOffset;
    public static double targetScale;
    public static double minScale;
    public static double maxScale;
    public static boolean displayAsBlocks;

    public static void setTargetScale(double targetScale) {
        Config.TARGET_SCALE.set(targetScale);
    }
    public static void setDisplayAsBlocks(boolean displayAsBlocks) {
        Config.DISPLAY_AS_BLOCKS.set(displayAsBlocks);
    }


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        plasmoIconSize = PLASMO_ICON_SIZE.get();
        plasmoVerticalOffset = PLASMO_VERTICAL_OFFSET.get();
        plasmoHorizontalOffset = PLASMO_HORIZONTAL_OFFSET.get();
        targetScale = TARGET_SCALE.get();
        minScale = MIN_SCALE.get();
        maxScale = MAX_SCALE.get();
        displayAsBlocks = DISPLAY_AS_BLOCKS.get();
    }
}
