package cn.nulladev.extraacc.core;

import cn.academy.ability.api.Category;
import cn.academy.ability.api.CategoryManager;
import cn.academy.crafting.ModuleCrafting;
import cn.nulladev.extraacc.client.render.RenderAirGun;
import cn.nulladev.extraacc.client.render.RenderNull;
import cn.nulladev.extraacc.client.render.RenderSand;
import cn.nulladev.extraacc.client.render.RenderUpdraft;
import cn.nulladev.extraacc.client.render.RenderWindBlade;
import cn.nulladev.extraacc.entity.EntityBasicAirGun;
import cn.nulladev.extraacc.entity.EntityExplosiveAirGun;
import cn.nulladev.extraacc.entity.EntitySand;
import cn.nulladev.extraacc.entity.EntityTurbulentStorm;
import cn.nulladev.extraacc.entity.EntityUpdraft;
import cn.nulladev.extraacc.entity.EntityWindBlade;
import cn.nulladev.extraacc.event.DamageRecalc;
import cn.nulladev.extraacc.item.ItemImagEnergyArmor;
import cn.nulladev.extraacc.item.ItemImagSiliconArmor;
import cn.nulladev.extraacc.skill.airmanipulator.CatAirManipulator;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class MyRegistry {
	
	public static final MyRegistry INSTANCE = new MyRegistry();
	
	public Category air_manipulator;
	
	public Item imag_silicon_helmet;
    public Item imag_silicon_chestplate;
    public Item imag_silicon_leggings;
    public Item imag_silicon_boots;
    
    public Item imag_energy_helmet;
    public Item imag_energy_chestplate;
    public Item imag_energy_leggings;
    public Item imag_energy_boots;
	
	private MyRegistry() {}
		
	public void register(Object ModObject) {
		registerItems();
		registerRecipes();
		registerEntities(ModObject);
		registerCats();
		registerEvents();
	}
	
	public void registerItems() {
		imag_silicon_helmet = new ItemImagSiliconArmor(0);
		imag_silicon_chestplate = new ItemImagSiliconArmor(1);
		imag_silicon_leggings = new ItemImagSiliconArmor(2);
		imag_silicon_boots = new ItemImagSiliconArmor(3);
		
		imag_energy_helmet = new ItemImagEnergyArmor(0);
		imag_energy_chestplate = new ItemImagEnergyArmor(1);
		imag_energy_leggings = new ItemImagEnergyArmor(2);
		imag_energy_boots = new ItemImagEnergyArmor(3);
	}
	
	private void registerCats() {
		//System.out.println("Register my cat");
		air_manipulator = new CatAirManipulator();
		CategoryManager.INSTANCE.register(air_manipulator);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerRenderers() {
		//System.out.println("Register my render");
		RenderingRegistry.registerEntityRenderingHandler(EntityBasicAirGun.class, new RenderAirGun());
		RenderingRegistry.registerEntityRenderingHandler(EntityWindBlade.class, new RenderWindBlade());
		RenderingRegistry.registerEntityRenderingHandler(EntityExplosiveAirGun.class, new RenderAirGun());
		RenderingRegistry.registerEntityRenderingHandler(EntityUpdraft.class, new RenderUpdraft());	
		RenderingRegistry.registerEntityRenderingHandler(EntitySand.class, new RenderSand());
		RenderingRegistry.registerEntityRenderingHandler(EntityTurbulentStorm.class, new RenderNull());
		
	}
	
	private void registerEntities(Object ModObject) {
		//System.out.println("Register my entity");
		int modID = 1;
    	EntityRegistry.registerModEntity(EntityBasicAirGun.class, "basic_air_gun", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityWindBlade.class, "wind_blade", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityExplosiveAirGun.class, "explosive_air_gun", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityUpdraft.class, "updraft", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntitySand.class, "sand", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityTurbulentStorm.class, "turbulent_storm", modID++, ModObject, 128, 1, true);

	}
	
	private void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new DamageRecalc());
		FMLCommonHandler.instance().bus().register(new DamageRecalc());
	}
	
	private void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(imag_silicon_helmet), new Object[] { "ABA", "A A", 'A', ModuleCrafting.ingotImagSil, 'B', ModuleCrafting.resoCrystal });
		GameRegistry.addShapedRecipe(new ItemStack(imag_silicon_chestplate), new Object[] { "A A", "ABA", "AAA", 'A', ModuleCrafting.ingotImagSil, 'B', ModuleCrafting.resoCrystal });
		GameRegistry.addShapedRecipe(new ItemStack(imag_silicon_leggings), new Object[] { "ABA", "A A", "A A", 'A', ModuleCrafting.ingotImagSil, 'B', ModuleCrafting.resoCrystal });
		GameRegistry.addShapedRecipe(new ItemStack(imag_silicon_boots), new Object[] { "A A", "ABA", 'A', ModuleCrafting.ingotImagSil, 'B', ModuleCrafting.resoCrystal });
		
		GameRegistry.addShapedRecipe(new ItemStack(imag_energy_helmet), new Object[] { "ABA", "ACA", 'A', ModuleCrafting.constPlate, 'B', imag_silicon_helmet, 'C', ModuleCrafting.crystalNormal });
		GameRegistry.addShapedRecipe(new ItemStack(imag_energy_chestplate), new Object[] { "A A", "ABA", "ACA", 'A', ModuleCrafting.constPlate, 'B', imag_silicon_chestplate, 'C', ModuleCrafting.crystalPure });
		GameRegistry.addShapedRecipe(new ItemStack(imag_energy_leggings), new Object[] { "ABA", "ACA", "A A", 'A', ModuleCrafting.constPlate, 'B', imag_silicon_leggings, 'C', ModuleCrafting.crystalPure });
		GameRegistry.addShapedRecipe(new ItemStack(imag_energy_boots), new Object[] { "ACA", "ABA", 'A', ModuleCrafting.constPlate, 'B', imag_silicon_boots, 'C', ModuleCrafting.crystalNormal });

	}

}
