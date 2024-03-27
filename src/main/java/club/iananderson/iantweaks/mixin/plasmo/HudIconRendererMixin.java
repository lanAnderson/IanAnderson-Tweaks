package club.iananderson.iantweaks.mixin.plasmo;

import dev.yurisuika.raised.client.option.RaisedConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import su.plo.lib.mod.client.render.RenderUtil;
import su.plo.voice.api.client.config.IconPosition;
import su.plo.voice.client.config.VoiceClientConfig;
import su.plo.voice.client.render.voice.HudIconRenderer;
import su.plo.voice.universal.UMatrixStack;

import static club.iananderson.iantweaks.config.Config.*;
import static su.plo.voice.api.client.config.IconPosition.BOTTOM_CENTER;

@Mixin(HudIconRenderer.class)
public abstract class HudIconRendererMixin {

    @Unique
    private int newCalcIconX(Integer x){
        Minecraft mc = Minecraft.getInstance();
        if (x == null) {
            return mc.getWindow().getGuiScaledWidth() / 2 - (plasmoIconSize/2);
        } else {
            return x < 0 ? mc.getWindow().getGuiScaledWidth() + x - plasmoIconSize : x;
        }
    }

    @Unique
    private int newCalcIconY(Integer y){
        Minecraft mc = Minecraft.getInstance();
        if (y == null) {
            return mc.getWindow().getGuiScaledHeight() - (plasmoIconSize * 2);
        } else {
            return y < 0 ? mc.getWindow().getGuiScaledHeight() + y - plasmoIconSize : y;
        }
    }

    @Final
    @Shadow
    private VoiceClientConfig config;

    @Redirect(method = "renderIcon", at = @At(value = "INVOKE", target = "Lsu/plo/lib/mod/client/render/RenderUtil;blit(Lsu/plo/voice/universal/UMatrixStack;IIFFIIII)V"),remap = false)
    private void scaledBlit(UMatrixStack stack, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight){
        IconPosition iconPosition = (IconPosition)this.config.getOverlay().getActivationIconPosition().value();
        int verticalOffset = plasmoVerticalOffset;
        int horizontalOffset = plasmoHorizontalOffset;

        if (ModList.get().isLoaded("raised") && iconPosition == BOTTOM_CENTER) {
            verticalOffset = verticalOffset - RaisedConfig.getHud();
        }

        int iconX = newCalcIconX(iconPosition.getX())+horizontalOffset;
        int iconY = newCalcIconY(iconPosition.getY())+verticalOffset;

        RenderUtil.blit(stack, iconX, iconY, 0.0F, 0.0F, plasmoIconSize, plasmoIconSize, plasmoIconSize, plasmoIconSize);
    }
}
