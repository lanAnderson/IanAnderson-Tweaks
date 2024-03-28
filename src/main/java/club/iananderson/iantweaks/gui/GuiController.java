package club.iananderson.iantweaks.gui;

import club.iananderson.iantweaks.IanAndersonTweaks;
import club.iananderson.iantweaks.rendering.RenderHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.ChatFormatting.BOLD;

public class GuiController extends Screen {
    private static final int WIDTH = 256;
    private static final int HEIGHT = 183;

    private static final int BUTTON_WIDTH = 70;
    private static final int BUTTON_MARGIN = 80;
    public static final int BUTTON_HEIGHT = 16;

    private int guiLeft;
    private int guiTop;


    private static final ResourceLocation background = new ResourceLocation(IanAndersonTweaks.MODID, "textures/gui/guicontroller.png");

    protected GuiController() {
        super(Component.literal("Controller"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void init() {
        super.init();
        guiLeft = (this.width - WIDTH) / 2;
        guiTop = (this.height - HEIGHT) / 2;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        PoseStack poseStack = graphics.pose();
        RenderSystem.setShaderTexture(0, background);
        RenderHelper.drawTexturedModalRect(poseStack.last().pose(), guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);

        //graphics.blit(background, guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
        int x = guiLeft + 5;
        int y = guiTop + 8;

        RenderHelper.renderText(Minecraft.getInstance(), graphics, x, y, ChatFormatting.GOLD + "Resizer"); y += 10;
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new GuiController());
    }
}
