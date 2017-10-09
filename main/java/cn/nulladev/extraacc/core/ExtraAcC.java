package cn.nulladev.extraacc.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ExtraAcC.MODID, name = ExtraAcC.MODNAME, version = ExtraAcC.VERSION, dependencies="required-after:academy-craft")
public class ExtraAcC {

	public static final String MODID = "extraacc";
	public static final String MODNAME = "ExtraAcC";
	public static final String VERSION = "10.17.10.1";
	
	static {
		VanillaAcCConfigHacker.try_to_register_firstly();
	}

	@Instance(MODID)
	public static ExtraAcC instance = new ExtraAcC();

	@SidedProxy(clientSide = "cn.nulladev.extraacc.client.MyClientProxy",
				serverSide = "cn.nulladev.extraacc.core.MyCommonProxy")
	public static MyCommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		MyRegistry.INSTANCE.register(instance);
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

}
