package club.iananderson.iantweaks.items;

import club.iananderson.iantweaks.impl.pehkui.PlayerResize;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleData;

import static virtuoel.pehkui.api.ScaleTypes.BASE;

public class ControllerItem extends Item {
    public ControllerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide()){
            PlayerResize playerResize = new PlayerResize(BASE);
            ScaleData playerScaleData = BASE.getScaleData(pPlayer);
            float currentScale = playerScaleData.getScale();

            if(pPlayer.isCrouching()) {
            playerResize.resize(pPlayer,1F);

                pPlayer.sendSystemMessage(Component.literal("Resized to: " + 1.0));

            }

            else{
                playerResize.resize(pPlayer,currentScale/2);
                pPlayer.sendSystemMessage(Component.literal("Resized to: " + currentScale/2));
            }
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}

