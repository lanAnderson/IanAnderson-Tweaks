package club.iananderson.iantweaks.mixin.plasmo;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import su.plo.lib.mod.client.render.RenderUtil;
import su.plo.voice.api.client.config.IconPosition;
import su.plo.voice.client.config.VoiceClientConfig;
import su.plo.voice.client.render.voice.HudIconRenderer;
import su.plo.voice.universal.UMatrixStack;

@Mixin(HudIconRenderer.class)
public abstract class HudIconRendererMixin {
    @Shadow
    protected abstract int calcIconX(Integer x);
    @Shadow
    protected abstract int calcIconY(Integer y);

    @Final
    @Shadow
    private VoiceClientConfig config;


    @Redirect(
            method = "renderIcon",
            at = @At(value = "INVOKE", target = "Lsu/plo/lib/mod/client/render/RenderUtil;blit(Lsu/plo/voice/universal/UMatrixStack;IIFFIIII)V"),
            remap = false)


    private void scaledBlit(UMatrixStack stack, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight){
        IconPosition iconPosition = (IconPosition)this.config.getOverlay().getActivationIconPosition().value();
        int scaledWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int scaledHeight= Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int iconDim = 12;

        int iconX = (scaledWidth/2)-((iconDim/2));
        int iconY = (scaledHeight - 30 - (iconDim));

        RenderUtil.blit(stack, iconX, iconY, 0.0F, 0.0F, iconDim, iconDim, iconDim, iconDim);
    }
}
