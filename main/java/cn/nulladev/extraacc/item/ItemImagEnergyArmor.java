package cn.nulladev.extraacc.item;

import java.util.List;

import cn.academy.core.AcademyCraft;
import cn.academy.energy.api.IFItemManager;
import cn.academy.energy.api.item.ImagEnergyItem;
import cn.nulladev.extraacc.core.ExtraAcC;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ItemImagEnergyArmor extends ItemArmor implements ImagEnergyItem, ISpecialArmor {
	
	protected static IFItemManager itemManager = IFItemManager.instance;
	
	private static final int[] ArmorVars = {3, 8, 6, 3};
	public static final ArmorMaterial ENERGY = EnumHelper.addArmorMaterial("ENERGY", 1024, ArmorVars, 0);

	int pos;

	/** position代表护甲位置，0为头盔，1为胸甲，2为护腿，3为靴子。 */
	public ItemImagEnergyArmor(int position) {
		super(ENERGY, 0, position);
		this.pos = position;
		this.setMaxDamage(13);
		this.setCreativeTab(AcademyCraft.cct);
		
		switch (position) {
		case 0:
			this.setUnlocalizedName("imag_energy_helmet")
			.setTextureName("extraacc:imag_energy_helmet");
			GameRegistry.registerItem(this, "imag_energy_helmet");
			break;
		case 1:
			this.setUnlocalizedName("imag_energy_chestplate")
			.setTextureName("extraacc:imag_energy_chestplate");
			GameRegistry.registerItem(this, "imag_energy_chestplate");
			break;
		case 2:
			this.setUnlocalizedName("imag_energy_leggings")
			.setTextureName("extraacc:imag_energy_leggings");
			GameRegistry.registerItem(this, "imag_energy_leggings");
			break;
		case 3:
			this.setUnlocalizedName("imag_energy_boots")
			.setTextureName("extraacc:imag_energy_boots");
			GameRegistry.registerItem(this, "imag_energy_boots");
			break;
		}
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if (source.isUnblockable()) {
			return new ISpecialArmor.ArmorProperties(0, 0.0D, 0);
		}
		double absorptionRatio = getBaseAbsorptionRatio() * getDamageAbsorptionRatio();
		int damageLimit = (int) (25 * itemManager.getEnergy(armor) / getEnergyPerDamage());
		return new ISpecialArmor.ArmorProperties(0, absorptionRatio, damageLimit);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (itemManager.getEnergy(armor) >= getEnergyPerDamage()) {
			return (int)Math.round(20.0D * getBaseAbsorptionRatio() * getDamageAbsorptionRatio());
		}
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		double newEnergy = Math.max(itemManager.getEnergy(stack) - damage * getEnergyPerDamage(), 0);
		itemManager.setEnergy(stack, newEnergy);
	}

	@Override
	public double getBandwidth() {
		return 100;
	}

	@Override
	public double getMaxEnergy() {
		return 50000;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs cct, List list) {
        ItemStack is = new ItemStack(this);
        list.add(is);
        itemManager.charge(is, 0, true);
        
        is = new ItemStack(this);
        itemManager.charge(is, Double.MAX_VALUE, true);
        list.add(is);
    }
	
	private double getBaseAbsorptionRatio() {
		switch (this.armorType) {
		case 0: 
			return 0.15D;
		case 1: 
			return 0.4D;
		case 2: 
			return 0.3D;
		case 3: 
			return 0.15D;
		}
		return 0.0D;
	}
	
	public double getDamageAbsorptionRatio() {
		return 0.75D;
	}
	  
	public int getEnergyPerDamage() {
		return 500;
	}
	  
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (itemManager.getEnergy(stack) > 0) {
			if (pos == 2)
				return ExtraAcC.MODID + ":textures/models/armor/energy_layer_2.png";	
			return ExtraAcC.MODID + ":textures/models/armor/energy_layer_1.png";	
		} else {
			if (pos == 2)
				return ExtraAcC.MODID + ":textures/models/armor/noenergy_layer_2.png";	
			return ExtraAcC.MODID + ":textures/models/armor/noenergy_layer_1.png";	
		}
		
	}

}
