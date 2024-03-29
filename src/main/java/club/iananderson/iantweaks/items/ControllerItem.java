package club.iananderson.iantweaks.items;

import club.iananderson.iantweaks.gui.GuiController;
import club.iananderson.iantweaks.impl.pehkui.PlayerResize;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import virtuoel.pehkui.api.ScaleTypes;

public class ControllerItem extends Item {

    public ControllerItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack heldItem = player.getItemInHand(usedHand);
        PlayerResize INSTANCE = new PlayerResize(ScaleTypes.BASE);
        if(ModList.get().isLoaded("pehkui")) {
            if (player.isCrouching()) {
                GuiController.open();
                return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide());
            }
            else if (INSTANCE.currentScale(player) == ScaleTypes.BASE.getDefaultBaseScale()){
                INSTANCE.resize(player, 0.1F);
                return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide());
            }
            else
                INSTANCE.resetSize(player);
                return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide());
        }
        else return InteractionResultHolder.fail(heldItem);
    }


}

