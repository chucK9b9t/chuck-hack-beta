package me.chuck.chuckhack.mixin.mixins.chuckhack.mods.misc;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import me.chuck.chuckhack.Mod;
import me.chuck.chuckhack.gui.Group;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FakePlayer extends Mod {
	public static EntityOtherPlayerMP fakePlayer;
	
	public FakePlayer() {
		super(Group.MISC, "FakePlayer", "Creates a fakeplayer at ur location");
	}
	
	@Override
	public void onEnabled() {
		if (mc.player == null) {
			disable();
			return;
		}
		
		fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("2dd10580-f5fc-4e70-989c-d0bf95c6e17f"), "syta"));
		fakePlayer.copyLocationAndAnglesFrom(mc.player);
		fakePlayer.rotationYawHead = mc.player.rotationYawHead;
		mc.world.addEntityToWorld(-100, fakePlayer);
	}
	
	@Override
	public void onDisabled() {
		if (mc.world != null && fakePlayer != null) {
			mc.world.removeEntity(fakePlayer);
		}
	}
}
