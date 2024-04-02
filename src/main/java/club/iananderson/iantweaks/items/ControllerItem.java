package club.iananderson.iantweaks.items;

import club.iananderson.iantweaks.gui.GuiController;
import club.iananderson.iantweaks.impl.pehkui.PlayerResize;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.List;

import static club.iananderson.iantweaks.config.Config.DISPLAY_AS_BLOCKS;
import static club.iananderson.iantweaks.config.Config.TARGET_SCALE;
import static club.iananderson.iantweaks.impl.pehkui.PlayerResize.INSTANCE;


public class ControllerItem extends Item {

    public ControllerItem() {
        super(new Properties()
                .stacksTo(1));
    }

    public float getTargetValue(){
        return (float) TARGET_SCALE.get().floatValue();
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack heldItem = player.getItemInHand(usedHand);
            if (ModList.get().isLoaded("pehkui")) {
                if (level.isClientSide() && player.isCrouching()){
                    GuiController.open();
                }

                else if (!player.isCrouching()){
                    if (PlayerResize.INSTANCE.currentScale(player) != getTargetValue()) {
                        INSTANCE.resize(player, getTargetValue());
                    }

                    else {
                        INSTANCE.resetSize(player);
                    }
                }
                return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide());

            }
        return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        Component units = Component.translatable("units.iantweaks.multiplier");
        String playerScaleString = new DecimalFormat("0.00").format(TARGET_SCALE.get());

        if(DISPLAY_AS_BLOCKS.get()){
            units = Component.translatable("units.iantweaks.blocks");
            playerScaleString = (new DecimalFormat("0.0").format(TARGET_SCALE.get() * 2));
        }

        tooltip.add(Component.translatable("item.iantweaks.controller.tooltip1").withStyle(ChatFormatting.RED).append(Component.literal(playerScaleString +" "+ units.getString()).withStyle(ChatFormatting.GRAY)));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("item.iantweaks.controller.tooltip2").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.iantweaks.controller.tooltip3").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack,level,tooltip, flag);
    }
}

