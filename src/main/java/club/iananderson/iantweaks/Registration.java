package club.iananderson.iantweaks;

import club.iananderson.iantweaks.items.ControllerItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registration {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IanAndersonTweaks.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IanAndersonTweaks.MODID);

    public static final RegistryObject<Item> CONTROLLER = ITEMS.register("controller", () -> new ControllerItem(new Item.Properties()));

    public static RegistryObject<CreativeModeTab> TAB = TABS.register(IanAndersonTweaks.MODID, () -> CreativeModeTab.builder()
        .title(Component.translatable("tab.iantweaks")).icon(() -> new ItemStack(CONTROLLER.get()))
        .icon(() -> new ItemStack(CONTROLLER.get()))
        .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
        .displayItems((featureFlags, output) -> {
            output.accept(CONTROLLER.get());
        })
        .build());

    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
    }
}