package club.iananderson.iantweaks.rendering;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;

public class RenderHelper {

    public static int renderText(Minecraft mc, GuiGraphics graphics, int x, int y, String txt) {
        PoseStack poseStack = graphics.pose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);

        poseStack.pushPose();
        poseStack.translate(0.0F, 0.0F, 32.0F);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        Lighting.setupFor3DItems();

        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        int width = mc.font.width(txt);
        graphics.drawString(mc.font, txt, x, y, 16777215);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();

        poseStack.popPose();

        return width;
    }

    public static int renderText(Minecraft mc, GuiGraphics graphics, int x, int y, Component text) {
        PoseStack poseStack = graphics.pose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);

        poseStack.pushPose();
        poseStack.translate(0.0F, 0.0F, 32.0F);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        Lighting.setupFor3DItems();

        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        int width = mc.font.width(text.getVisualOrderText());//Otherwise it breaks
        graphics.drawString(mc.font, text, x, y, 16777215);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();

        poseStack.popPose();

        return width;
    }

    public static void drawTexturedModalRect(Matrix4f matrix, int x, int y, int u, int v, int width, int height) {
        float zLevel = 0.01f;
        float f = (1 / 256.0f);
        float f1 = (1 / 256.0f);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        buffer.vertex(matrix, (x), (y + height), zLevel).uv(((u) * f), ((v + height) * f1)).endVertex();
        buffer.vertex(matrix, (x + width), (y + height), zLevel).uv(((u + width) * f), ((v + height) * f1)).endVertex();
        buffer.vertex(matrix, (x + width), (y), zLevel).uv(((u + width) * f), ((v) * f1)).endVertex();
        buffer.vertex(matrix, (x), (y), zLevel).uv(((u) * f), ((v) * f1)).endVertex();
        tessellator.end();
    }

    public static void drawTexturedModalRect(Matrix4f matrix, int x, int y, TextureAtlasSprite sprite, int width, int height) {
        float zLevel = 0.01f;
        float f = (1 / 256.0f);
        float f1 = (1 / 256.0f);

        float u1 = sprite.getU0();
        float v1 = sprite.getV0();
        float u2 = sprite.getU1();
        float v2 = sprite.getV1();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(matrix, (x), (y + height), zLevel).uv(u1, v1).endVertex();
        buffer.vertex(matrix, (x + width), (y + height), zLevel).uv(u1, v2).endVertex();
        buffer.vertex(matrix, (x + width), (y), zLevel).uv(u2, v2).endVertex();
        buffer.vertex(matrix, (x), (y), zLevel).uv(u2, v1).endVertex();
        tessellator.end();
    }
}
