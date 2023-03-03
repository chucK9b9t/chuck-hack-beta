package me.chuck.chuckhack.mods.combat;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.events.bus.EventHandler;
import me.chuck.chuckhack.events.bus.Listener;
import me.chuck.chuckhack.events.other.PacketEvent;
import me.chuck.chuckhack.gui.Group;
import me.chuck.chuckhack.gui.Mode;
import me.chuck.chuckhack.gui.Setting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class CevBreaker extends Mod {
    public static Setting distance = new Setting(Mode.DOUBLE, "Distance", 0.05, "How far it will teleport up");

    public CevBreaker() {
        super(Group.COMBAT, "CevBreaker", "Do critical damage to ur opponent", "Without jumping or falling");
    }

    @EventHandler
    private Listener<PacketEvent> packetEvent = new Listener<>(event -> {
        if (event.packet instanceof CPacketUseEntity) {
            CPacketUseEntity packet = (CPacketUseEntity)event.packet;

            if (packet.getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(mc.world) instanceof EntityLivingBase) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + distance.doubleValue(), mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            }
        }
    });
}