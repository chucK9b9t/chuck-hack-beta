package me.chuck.chuckhack.mixin.mixins.chuckhack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BooleanSupplier;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.chuck.chuckhack.mixin.mixins.chuckhack.events.bus.EventBus;
import me.chuck.chuckhack.mixin.mixins.chuckhack.events.bus.EventManager;
import me.chuck.chuckhack.mixin.mixins.chuckhack.gui.*;
import me.chuck.chuckhack.mixin.mixins.chuckhack.hud.HudEditor;
import me.chuck.chuckhack.mixin.mixins.chuckhack.hud.HudSettings;
import me.chuck.chuckhack.mixin.mixins.chuckhack.hud.components.ArrayListComponent;
import me.chuck.chuckhack.mixin.mixins.chuckhack.mods.bots.ObbyBuilderBot;
import me.chuck.chuckhack.mixin.mixins.chuckhack.mods.bots.elytrabot.ElytraBot;
import me.chuck.chuckhack.mixin.mixins.chuckhack.mods.combat.*;
import me.chuck.chuckhack.mixin.mixins.chuckhack.mods.movement.*;
import me.chuck.chuckhack.mixin.mixins.chuckhack.mods.world.*;
import me.chuck.chuckhack.mixin.mixins.chuckhack.rendering.Renderer;
import me.chuck.chuckhack.mixin.mixins.chuckhack.utils.EatingUtil;
import me.chuck.chuckhack.mixin.mixins.chuckhack.utils.InformationUtil;
import me.chuck.chuckhack.mods.exploits.*;
//import me.chuck.chuckhack.mods.games.Snake;
//import me.chuck.chuckhack.mods.games.tetris.Tetris;
import me.chuck.chuckhack.mods.misc.*;
import me.chuck.chuckhack.mods.render.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

@net.minecraftforge.fml.common.Mod(modid = Mod.MODID, name = Mod.NAME, version = Mod.VERSION)
public class Mod {
    public static final String MODID = "ChuckHack";
    public static final String NAME = "ChuckHack";
    public static final String VERSION = "1.04";
    public static final String DISCORD = "chucK#8635";
    
    public static Minecraft mc = Minecraft.getMinecraft();
    public static final EventBus EVENT_BUS = new EventManager();

    public String name = "";
    public String[] description;
    public Group group;
    private boolean toggled, hiddenOn, lastHiddenOn;
    public boolean defaultOn, defaultHidden;
    public boolean autoSubscribe = true;
    private GuiNode guiNode, hiddenNode;
    private int renderNumber = -1;
    public static ArrayList<Mod> modules = new ArrayList<Mod>();
    
    public Mod(Group group, String name, String... description) {
    	this.group = group;
    	this.name = name;
    	this.description = description;
    	modules.add(this);
    }
    
    public Mod(Group group) {
    	this.group = group;
    	modules.add(this);
    }
    
    public Mod() {
    	
    }
    
    @EventHandler
	public void init(FMLInitializationEvent event) {
		long ms = System.currentTimeMillis();
		
		//Register classes to event busses
    	MinecraftForge.EVENT_BUS.register(new HelpMessage());
    	MinecraftForge.EVENT_BUS.register(new Commands());
    	MinecraftForge.EVENT_BUS.register(new Keybind());
    	MinecraftForge.EVENT_BUS.register(new EatingUtil());
    	MinecraftForge.EVENT_BUS.register(new Renderer());
    	Mod.EVENT_BUS.subscribe(new Renderer());
    	InformationUtil informationUtil = new InformationUtil();
    	MinecraftForge.EVENT_BUS.register(informationUtil);
    	Mod.EVENT_BUS.subscribe(informationUtil);
    	
    	//Init mods
    	initMods();
		
    	//Initialize stuff
		new File(Settings.path).mkdir();
		Friends.loadFriends();
    	SetGuiNodes.setGuiNodes();
    	SetGuiNodes.setDefaults();
		Settings.loadSettings();
		Keybind.setKeybinds();
		HudSettings.loadSettings();
		
		for (Mod module : modules) {
			module.onPostInit();
		}
		
		System.out.println("ChuckHack - Initialization took " + Math.abs(System.currentTimeMillis() - ms) + "ms");
    }
    
    public void initMods() {
    	//Combat
    	new AutoArmor();
    	new AutoCrystal();
    	new AutoLog();
    	new AutoTotem();
    	new AutoTrap();
    	new CevBreaker();
    	new HoleFiller();
    	new KillAura();
    	new NoKnockback();
    	new SelfWeb();
    	new Surround();
    	new Offhand();
    	new PistonAura();
    	
    	//Exploits
    	new Burrow();
    	new MiningSpoof();
    	new NewChunks();
    	new PacketFly();
    	new Reach();
    	new PortalGodMode();
    	new LiquidInteract();
		new FreecamWithPackets();
		
    	//Misc
    	new AntiAFK();
    	new AutoEat();
    	new AutoFirework();
    	new AutoInventoryManager();
    	new AutoMend();
    	new AutoMessager();
    	new AutoReconnect();
    	new ChestSwap();
    	//try {new DiscordRPC();} catch(UnsatisfiedLinkError e) {}
    	new FakePlayer();
    	new Friends();
    	new MiddleClickFriends();
    	new PacketCanceller();
    	new UpdateChecker();
    	new VisualRange();
    	new XCarry();
    	new NoSound();
    	new AutoHotbar();
		new Debug();
		new ArmorDropper();
		
    	//Movement
    	new AntiHunger();
    	new AntiLevitation();
    	new AutoSprint();
    	new AutoWalk();
    	new Blink();
    	new ElytraFly();
    	new EntityControl();
    	new EntitySpeed();
    	new Flight();
    	new HighJump();
    	new IceSpeed();
    	new InventoryMove();
    	new Jesus();
    	new NoFall();
    	new NoRotate();
    	new NoSlowDown();
    	new SafeWalk();
    	new Speed();
    	new Step();
    	new Strafe();
    	new LiquidSpeed();
		new Tooltips();
		
    	//Render
    	new AutoTrapIndicator();
    	new BlockVision();
    	new EntityESP();
    	new Freecam();
    	new FullBright();
    	new HoleESP();
    	new LiquidVision();
    	new NameTags();
    	new NoRender();
    	new Search();
    	new ShulkerPreview();
    	new Tracers();
    	new Trajectories();
    	new VoidESP();
    	new Waypoints();
    	new XRay();
    	new Zoom();
		
    	//World
    	new AutoBuilder();
    	new AutoEnderChestMiner();
    	new AutoFish();
    	new CrystalBlock();
    	new FastUse();
    	new NoEntityTrace();
    	new NoGlitchBlocks();
    	new PacketMine();
    	new Scaffold();
    	new SpeedMine();
    	new Timer();
    	new AutoTool();
    	new AutoRespawn();
    	new StashLogger();
    	new AutoEnderpearl();
    	new AutoSign();
		
    	/* Games
		new Snake();
		new Tetris();
		*/
    	//Bots
    	new ElytraBot();
    	new ObbyBuilderBot();
		
		//Sort the modules list from A to Z
		List<String> names = new ArrayList<String>();
		for (Mod module : modules) {
			names.add(module.name);
		}
		
		String[] sortedNames = new String[names.size()];
		sortedNames = names.toArray(sortedNames);
		Arrays.sort(sortedNames);
		
		ArrayList<Mod> temp = new ArrayList<Mod>();
		for (String name : sortedNames) {
			for (Mod module : modules) {
				if (module.name.equals(name)) {
					temp.add(module);
					break;
				}
			}
		}
		
		modules = temp;
		
    	//Gui
    	new HudEditor();
		new GuiSettings();
    }
    
    public void onEnabled(){}
    public void onDisabled(){}
    public void onPostInit(){}
    public void onGuiDrawScreen(int mouseX, int mouseY, float partialTicks){}
    public boolean onGuiClick(int x, int y, int button){return false;}
    public void onGuiKeyPress(GuiScreenEvent.KeyboardInputEvent.Post e){}
    public void onRenderWorld(float partialTicks) {}
    
    /**
     * Sends a clientSided message
     * @param red if true then message will be red if false then it will be some other color
     * @param text of the module it will add in the message
     * @param red renders text as red
     */
    public void sendMessage(String text, boolean red) {
		sendMessage(text, red, name);
    }

	/**
	 * Sends a clientSided message
	 * @param text of the module it will add in the message
	 * @param red renders text as red
	 * @param moduleName module name to send in message
	 */
	public static void sendMessage(String text, boolean red, String moduleName) {
		if (mc.player == null) {
			return;
		}

		//Send message
		String module = "";
		ChatFormatting color = ChatFormatting.WHITE;
		if (red) {
			color = ChatFormatting.RED;
		}
		if (!moduleName.isEmpty()) {
			module = "-" + moduleName;
		}

		mc.player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + ChatFormatting.LIGHT_PURPLE + NAME + module + ChatFormatting.GREEN + "] " + color + text));
	}

    public int getRenderNumber() {
    	return this.renderNumber;
    }
    
    public void setRenderNumber(int number) {
    	if (this.renderNumber == -1) {
    		ArrayListComponent.lastArraylistSize = -1;
    	}
    	
    	this.renderNumber = number;
    }
    
    public void enable() {
    	if (autoSubscribe) {
    		MinecraftForge.EVENT_BUS.register(this);
    		Mod.EVENT_BUS.subscribe(this);
    	}
    	getGuiNode().toggled = true;
    	ArrayListComponent.arraylist.add(this);
		this.toggled = true;
		this.onEnabled();
    }
    
    public void disable() {
    	if (autoSubscribe) {
    		MinecraftForge.EVENT_BUS.unregister(this);
    		Mod.EVENT_BUS.unsubscribe(this);
    	}
    	getGuiNode().toggled = false;
		ArrayListComponent.arraylist.remove(this);
		this.toggled = false;
		this.onDisabled();
    }
    
    public void toggle() {
    	if (toggled) {
    		disable();
    	} else {
    		enable();
    	}
    }
    
    /**
     * Sets the module on but doesnt show it in gui or arraylist or anything
     * This can be used by other modules to turn this module on
     */
    public void setHiddenOn(boolean value) {
    	hiddenOn = value;
    	
    	if (hiddenOn != lastHiddenOn) {	
    		if (hiddenOn) {
            	if (autoSubscribe) {
            		MinecraftForge.EVENT_BUS.register(this);
            		Mod.EVENT_BUS.subscribe(this);
            	}	
        		onEnabled();
    		} else {
            	if (autoSubscribe) {
            		MinecraftForge.EVENT_BUS.unregister(this);
            		Mod.EVENT_BUS.unsubscribe(this);
            	}
        		onDisabled();
    		}
    	}
    	
    	lastHiddenOn = hiddenOn;
    }
    
    public GuiNode getGuiNode() {
    	if (guiNode == null) {
    		guiNode = Settings.getGuiNodeFromId(name);
    		return guiNode;
    	} else {
    		return guiNode;
    	}
    }
    
    public boolean isHidden() {
    	if (hiddenOn) {
    		return true;
    	}
    	
    	if (hiddenNode == null) {
        	hiddenNode = Settings.getGuiNodeFromId(name + "Hidden");
    	}
    	
    	return hiddenNode.toggled;
    }
    
    public boolean isOn() {
    	return toggled;
    }
    
    public static void toggleMod(String name, boolean on) {
    	GuiNode node = Settings.getGuiNodeFromId(name);
    	
    	node.toggled = on;
    	node.setSetting();
    	
    	for (Mod module : modules) {
    		if (module.name.equals(name)) {
    			if (on) {
    				module.enable();
    			} else {
    				module.disable();
    			}
    			
    			module.toggled = on;
    			break;
    		}
    	}
    }
    
    /**
     * 1 = 50% change and so on
     */
    public static boolean random(int i) {
    	return new Random().nextInt(i + 1) == 0;
    }
    
    /**
     * Generates random number between min and max
     */
    public static int random(int min, int max) {
    	return new Random().nextInt(min + max) - min;
    }
    
	public void setStatus(String status) {
		setStatus(status, name);
	}
	
	public static void setStatus(String status, String module) {
    	if (!module.isEmpty()) {
    		module = "-" + module;
    	}
    	
    	Renderer.status = new String[10];
    	Renderer.status[0] = ChatFormatting.GREEN + "[" + ChatFormatting.LIGHT_PURPLE + NAME + module + ChatFormatting.GREEN + "] " + ChatFormatting.WHITE + status;
	}
	
	public void addToStatus(String status, int index) {
		addToStatus(status, index, name);
	}
	
	public static void addToStatus(String status, int index, String module) {
    	if (!module.isEmpty()) {
    		module = "-" + module;
    	}
    	
    	Renderer.status[index] = ChatFormatting.WHITE + status;
	}
	
	public static void clearStatus() {
		Renderer.status = null;
	}
	
	public static void suspend(Thread thread) {
		if (thread != null) thread.suspend();
	}
	
	public static Block getBlock(BlockPos pos) {
		try {
			return mc.world.getBlockState(pos).getBlock();
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public static boolean isSolid(BlockPos pos) {
		try {
			return mc.world.getBlockState(pos).getMaterial().isSolid();
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public static BlockPos getPlayerPos() {
		return new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
	}
	
	/**
	 * Checks if the player has the given potion effect like "regeneration"
	 */
	public static boolean isPotionActive(String name, EntityPlayer player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			if (effect.getEffectName().contains(name.toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static String[] addToArray(String[] myArray, String newItem) {
		int currentSize = myArray.length;
		int newSize = currentSize + 1;
		String[] tempArray = new String[ newSize ];
		for (int i = 0; i < currentSize; i++) {
		    tempArray[i] = myArray [i];
		}
		tempArray[newSize- 1] = newItem;
		
		return tempArray;
	}
	
    public static void sleep(int ms) {
    	try {
    		Thread.sleep(ms);
    	} catch (Exception ignored) {
    		
    	}
    }
    
	public static void sleepUntil(BooleanSupplier condition, int timeout) {
		long startTime = System.currentTimeMillis();
		while(true) {
			if (condition.getAsBoolean()) {
				break;
			} else if (timeout != -1 && System.currentTimeMillis() - startTime >= timeout) {
				break;
			}
			
			sleep(10);
		}
	}
	
	public static void sleepUntil(BooleanSupplier condition, int timeout, int amountToSleep) {
		long startTime = System.currentTimeMillis();
		while(true) {
			if (condition.getAsBoolean()) {
				break;
			} else if (timeout != -1 && System.currentTimeMillis() - startTime >= timeout) {
				break;
			}
			
			sleep(amountToSleep);
		}
	}
	
	//Send a help message telling the prefix and stuff if the settings file doesnt exist which would mean the person is using the mod for the first time
	public static class HelpMessage {
		boolean check = false;
		
		@SubscribeEvent
		public void onTick(ClientTickEvent e) {
	    	if (!check && mc.player != null) {
	    		if (!Settings.settings.exists()) {
	    			new Mod().sendMessage("Welcome to " + ChatFormatting.GREEN + NAME + ChatFormatting.WHITE + " version " + ChatFormatting.GREEN + VERSION, false);
	    			new Mod().sendMessage("You can open the GUI by typing " + ChatFormatting.GREEN + GuiSettings.prefix.stringValue() + "gui" + ChatFormatting.WHITE + " on chat", false);
	    			Settings.saveSettings();
	    		}
	    		
	    		check = true;
	    		MinecraftForge.EVENT_BUS.unregister(this);
	    	}
		}
	}
}
