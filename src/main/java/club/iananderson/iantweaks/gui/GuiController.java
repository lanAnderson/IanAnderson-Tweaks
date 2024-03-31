package club.iananderson.iantweaks.gui;

import club.iananderson.iantweaks.IanAndersonTweaks;
import club.iananderson.iantweaks.config.Config;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.DecimalFormat;

import static club.iananderson.iantweaks.config.Config.maxScale;
import static club.iananderson.iantweaks.config.Config.minScale;

public class GuiController extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(IanAndersonTweaks.MODID, "textures/gui/guicontroller.png");
    private static final ResourceLocation ARROWS = new ResourceLocation(IanAndersonTweaks.MODID, "textures/gui/buttons/arrows.png");
    private static final ResourceLocation CHECK = new ResourceLocation(IanAndersonTweaks.MODID, "textures/gui/buttons/check.png");
    private static final int WIDTH = 256;
    private static final int HEIGHT = 160;
    private static final int GUI_MARGIN = 16;
    private static final int BUTTON_WIDTH = 16;
    public static final int BUTTON_HEIGHT = 16;
    private int guiLeft;
    private int guiTop;
    private double targetScale = Config.TARGET_SCALE.get();
    private boolean unitBlocks = Config.DISPLAY_AS_BLOCKS.get();

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

    public void scaleUp(){
        if(targetScale < maxScale){
            if(hasShiftDown()){
                targetScale = maxScale;
            }
            else targetScale += 0.05;
        }
    }
    public void scaleDown(){
        if(targetScale > minScale){
            if(hasShiftDown()){
                targetScale = minScale;
            }
            else targetScale -= 0.05;
        }
    }

    public void renderBackground(GuiGraphics graphics){
        RenderSystem.setShaderTexture(0, BACKGROUND);
        graphics.blit(BACKGROUND, guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);

    }

    public void renderButtons(GuiGraphics graphics, Minecraft mc, int x, int y, int scale, float textScale){
        PoseStack poseStack = graphics.pose();

        Point dPadUp = new Point(x+(scale*10), y + (scale * 2));
        Point dPadDown = new Point(x+(scale*10), y + (scale * 4));
        Point dPadLeft = new Point(x+(scale*11), y + (scale * 3));
        Point dPadRight = new Point(x+(scale*9), y + (scale * 3));
        Point dPadMiddle = new Point(x+(scale*10), y + (scale * 3));

        Point redButton = new Point(x+(scale*9), y + (scale * 6));
        Point greenButton = new Point(x+(scale*11), y + (scale * 6));

        Point screenTopLeft = new Point(x+(scale*3), y + (scale * 3));
        Point screenBottomRight = new Point(x+(scale*7), y + (scale * 7));
        Point screenMiddle = new Point((screenTopLeft.x+screenBottomRight.x)/2,(screenTopLeft.y+screenBottomRight.y)/2);

        ImageButton unitButton = new ImageButton(screenTopLeft.x,screenTopLeft.y,BUTTON_WIDTH*4,BUTTON_HEIGHT,0,64,ARROWS,b -> {
            unitBlocks = !unitBlocks;
        });

        ImageButton upArrowButton = new ImageButton(dPadUp.x,dPadUp.y,BUTTON_WIDTH,BUTTON_HEIGHT,0,0,ARROWS,b ->
            scaleUp());
        ImageButton downArrowButton = new ImageButton(dPadDown.x,dPadDown.y,BUTTON_WIDTH,BUTTON_HEIGHT,16,0,ARROWS,b ->
            scaleDown());
        ImageButton middleButton = new ImageButton(dPadMiddle.x,dPadMiddle.y,BUTTON_WIDTH,BUTTON_HEIGHT,32,0,ARROWS,b ->
            targetScale = 1.0);

        ImageButton greenButtonCheck = new ImageButton(greenButton.x,greenButton.y,BUTTON_WIDTH,BUTTON_HEIGHT,0,0,CHECK,b -> {
            targetScale = Double.parseDouble(new DecimalFormat("0.00").format(targetScale));
            Config.setTargetScale(targetScale);
            Config.setDisplayAsBlocks(unitBlocks);
            mc.popGuiLayer();
        });
        ImageButton redButtonCheck = new ImageButton(redButton.x,redButton.y,BUTTON_WIDTH,BUTTON_HEIGHT,16,0,CHECK,b -> {
            mc.popGuiLayer();
        });

        addRenderableWidget(unitButton);
        addRenderableWidget(upArrowButton);
        addRenderableWidget(downArrowButton);
        addRenderableWidget(middleButton);
        addRenderableWidget(greenButtonCheck);
        addRenderableWidget(redButtonCheck);

        Component units = Component.translatable("units.iantweaks.multiplier").withStyle(ChatFormatting.UNDERLINE);
        String playerScaleString = new DecimalFormat("0.00").format(targetScale);

        if(unitBlocks){
            units = Component.translatable("units.iantweaks.blocks").withStyle(ChatFormatting.UNDERLINE);
            playerScaleString = (new DecimalFormat("0.0").format(targetScale * 2));
        }

        float lineHeight = mc.font.lineHeight-2;
        float lineWidth = mc.font.width(playerScaleString);
        float unitWidth = mc.font.width(units);

        poseStack.pushPose();
        graphics.drawString(mc.font, units, (int) ((screenMiddle.x)-(unitWidth/2)), (screenTopLeft.y)+BUTTON_HEIGHT/8,0x000000,false);
        poseStack.scale(textScale, textScale,1);
        graphics.drawString(mc.font, playerScaleString, (screenMiddle.x/textScale)-(lineWidth/2), (screenMiddle.y/textScale)-(lineHeight/2),0x838891,false);
        poseStack.popPose();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();

        int x = guiLeft + GUI_MARGIN;
        int y = guiTop;
        float textScale = 3;

        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderButtons(graphics,mc, x, y, BUTTON_HEIGHT, textScale);
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new GuiController());
    }


}
