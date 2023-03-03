package me.chuck.chuckhack.mods.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.render.RenderTooltipEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.utils.ItemUtil;
import net.minecraft.client.renderer.GlStateManager;

import java.util.ArrayList;
import java.util.List;

public class Tooltips extends Mod {
    public static Setting durability = new Setting(Mode.BOOLEAN, "Durability", true, "Renders the durability of the item");

    public Tooltips() {
        super(Group.RENDER, "Tooltips", "Renders some selected info about the item your hovering over with mouse in gui");
    }

    @EventHandler
    private Listener<RenderTooltipEvent> renderTooltip = new Listener<>(event -> {
        if (event.itemStack == null) {
            return;
        }

        GlStateManager.disableDepth();

        List<String> renders = new ArrayList<>();
        if (durability.booleanValue()) {
            renders.add(ChatFormatting.GRAY + "Durability: " + ItemUtil.getDurabilityColor(event.itemStack) + ItemUtil.getDurability(event.itemStack));
        }

        for (int i = 0; i < renders.size(); i++) {
            mc.fontRenderer.drawStringWithShadow(renders.get(i), event.x + mc.fontRenderer.FONT_HEIGHT, event.y -mc.fontRenderer.FONT_HEIGHT - 1 - ((mc.fontRenderer.FONT_HEIGHT - 1) * (-i + 2)), 0xFFFFFFFF);
        }
    });
}
