package club.iananderson.iantweaks.items;

import club.iananderson.iantweaks.gui.GuiController;
import club.iananderson.iantweaks.impl.pehkui.PlayerResize;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import virtuoel.pehkui.api.ScaleData;

import static virtuoel.pehkui.api.ScaleTypes.BASE;

public class ControllerItem extends Item {
    public ControllerItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack heldItem = player.getItemInHand(usedHand);

        if (level.isClientSide()) {
            PlayerResize playerResize = new PlayerResize(BASE);
            ScaleData playerScaleData = BASE.getScaleData(player);
            float currentScale = playerScaleData.getScale();

            if (player.isCrouching()) {
                GuiController.open();
            } else if (currentScale == 1F){
                playerResize.resize(player, currentScale / 2);
                player.sendSystemMessage(Component.literal("Resized to: " + currentScale / 2));
            } else if (currentScale < 1F)
                playerResize.resize(player, 1F);
                player.sendSystemMessage(Component.literal("Resized to: " + 1));

        }
        return InteractionResultHolder.pass(heldItem);
    }
}

