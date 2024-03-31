package club.iananderson.iantweaks.gui;

import club.iananderson.iantweaks.IanAndersonTweaks;
import club.iananderson.iantweaks.impl.pehkui.PlayerResize;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.api.ScaleTypes;

import java.awt.*;
import java.text.DecimalFormat;

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
    private float playerScale = new PlayerResize(ScaleTypes.BASE).currentScale(Minecraft.getInstance().player);

    protected GuiController(Player player) {
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
        playerScale += 0.05F;
    }
    public void scaleDown(){
        playerScale -= 0.05F;
    }

    public void renderBackground(GuiGraphics graphics){
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        graphics.blit(BACKGROUND, guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        PlayerResize INSTANCE = new PlayerResize(ScaleTypes.BASE);
        Font font = Minecraft.getInstance().font;
        PoseStack poseStack = graphics.pose();

        this.renderBackground(graphics);

        super.render(graphics, mouseX, mouseY, partialTicks);

        int x = guiLeft + GUI_MARGIN;
        int y = guiTop;
        int scale = BUTTON_HEIGHT;
        float textScale = 3;

        Point dPadUp = new Point(x+(scale*10), y + (scale * 2));
        Point dPadDown = new Point(x+(scale*10), y + (scale * 4));
        Point dPadLeft = new Point(x+(scale*11), y + (scale * 3));
        Point dPadRight = new Point(x+(scale*9), y + (scale * 3));

        Point redButton = new Point(x+(scale*9), y + (scale * 6));
        Point greenButton = new Point(x+(scale*11), y + (scale * 6));

        Point screenTopLeft = new Point(x+(scale*3), y + (scale * 3));
        Point screenBottomRight = new Point(x+(scale*7), y + (scale * 7));
        Point screenMiddle = new Point((screenTopLeft.x+screenBottomRight.x)/2,(screenTopLeft.y+screenBottomRight.y)/2);

        ImageButton upArrowButton = new ImageButton(dPadUp.x,dPadUp.y,BUTTON_WIDTH,BUTTON_HEIGHT,0,0,ARROWS,b -> scaleUp());
        addRenderableWidget(upArrowButton);

        ImageButton downArrowButton = new ImageButton(dPadDown.x,dPadDown.y,BUTTON_WIDTH,BUTTON_HEIGHT,16,0,ARROWS,b -> scaleDown());
        addRenderableWidget(downArrowButton);

        ImageButton greenButtonCheck = new ImageButton(greenButton.x,greenButton.y,BUTTON_WIDTH,BUTTON_HEIGHT,0,0,CHECK,b -> {
                INSTANCE.resize(mc.player,playerScale);
                mc.popGuiLayer();
        });
        addRenderableWidget(greenButtonCheck);

        ImageButton redButtonCheck = new ImageButton(redButton.x,redButton.y,BUTTON_WIDTH,BUTTON_HEIGHT,16,0,CHECK,b -> {
                mc.popGuiLayer();
        });
        addRenderableWidget(redButtonCheck);

        String playerScaleString = String.valueOf(new DecimalFormat("0.0").format(playerScale*2));
        float lineHeight = Minecraft.getInstance().font.lineHeight-2;
        float lineWidth = Minecraft.getInstance().font.width(playerScaleString);

        poseStack.pushPose();
        poseStack.scale(textScale, textScale,1);
        graphics.drawString(font, playerScaleString, (screenMiddle.x/textScale)-(lineWidth/2), (screenMiddle.y/textScale)-(lineHeight/2),0x838891,false);
        poseStack.popPose();
    }

    public static void open(Player player) {
        Minecraft.getInstance().setScreen(new GuiController(player));
    }


}
