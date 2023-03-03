package me.chuck.chuckhack.mods.misc;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import me.chuck.chuckhack.utils.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;

public class ArmorDropper extends Mod {
    public Thread thread;
    public static Setting delay = new Setting(Mode.INTEGER, "Delay", 100, "Delay in milliseconds before checking and dropping again");
    public static Setting delayBetweenDrops = new Setting(Mode.INTEGER, "DelayBetweenDrops", 15, "Delay in milliseconds to wait before dropping another armor piece");

    public ArmorDropper() {
        super(Group.MISC, "ArmorDropper", "Drops all your currently equipped armor");
    }

    @Override
    public void onEnabled() {
        thread = new Thread() {
            public void run() {
                while(thread != null && thread.equals(this)) {
                    loop();

                    Mod.sleep(delay.intValue());
                }
            }
        };

        thread.start();
    }

    @Override
    public void onDisabled() {
        suspend(thread);
        thread = null;
    }

    public void loop() {
        InventoryUtil.ItemStackUtil[] armor = new InventoryUtil.ItemStackUtil[]{
                new InventoryUtil.ItemStackUtil(InventoryUtil.getItemStack(39), InventoryUtil.getClickSlot(39)),
                new InventoryUtil.ItemStackUtil(InventoryUtil.getItemStack(38), InventoryUtil.getClickSlot(38)),
                new InventoryUtil.ItemStackUtil(InventoryUtil.getItemStack(37), InventoryUtil.getClickSlot(37)),
                new InventoryUtil.ItemStackUtil(InventoryUtil.getItemStack(36), InventoryUtil.getClickSlot(36))};

        for (InventoryUtil.ItemStackUtil stack : armor) {
            if (stack.itemStack.getItem() != Items.AIR) {
                mc.playerController.windowClick(mc.player.openContainer.windowId, stack.slotId, 0, ClickType.THROW, mc.player);
                Mod.sleep(delayBetweenDrops.intValue());
            }
        }
    }
}
