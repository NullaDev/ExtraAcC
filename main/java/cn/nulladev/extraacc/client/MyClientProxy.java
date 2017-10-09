package cn.nulladev.extraacc.client;

import org.lwjgl.opengl.Display;

import cn.nulladev.extraacc.core.MyCommonProxy;
import cn.nulladev.extraacc.core.MyRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class MyClientProxy extends MyCommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		Display.setTitle("Minceraft 1.7.10");
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
    	MyRegistry.INSTANCE.registerRenderers();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}

}