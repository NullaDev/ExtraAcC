package cn.nulladev.extraacc.item;

import cn.academy.core.AcademyCraft;
import cn.nulladev.extraacc.core.ExtraAcC;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.*;

public class ItemImagSiliconArmor extends ItemArmor {
	
	private static final int[] ArmorVars = {2, 6, 5, 2};
	public static final ArmorMaterial SILICON = EnumHelper.addArmorMaterial("SILICON", 10, ArmorVars, 0);

	int pos;

	/** position代表护甲位置，0为头盔，1为胸甲，2为护腿，3为靴子。 */
	public ItemImagSiliconArmor(int position) {
		super(SILICON, 0, position);
		this.pos = position;
		this.setCreativeTab(AcademyCraft.cct);
		
		switch (position) {
		case 0:
			this.setUnlocalizedName("imag_silicon_helmet")
			.setTextureName("extraacc:imag_silicon_helmet");
			GameRegistry.registerItem(this, "imag_silicon_helmet");
			break;
		case 1:
			this.setUnlocalizedName("imag_silicon_chestplate")
			.setTextureName("extraacc:imag_silicon_chestplate");
			GameRegistry.registerItem(this, "imag_silicon_chestplate");
			break;
		case 2:
			this.setUnlocalizedName("imag_silicon_leggings")
			.setTextureName("extraacc:imag_silicon_leggings");
			GameRegistry.registerItem(this, "imag_silicon_leggings");
			break;
		case 3:
			this.setUnlocalizedName("imag_silicon_boots")
			.setTextureName("extraacc:imag_silicon_boots");
			GameRegistry.registerItem(this, "imag_silicon_boots");
			break;
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (pos == 2)
			return ExtraAcC.MODID + ":textures/models/armor/silicon_layer_2.png";	
		return ExtraAcC.MODID + ":textures/models/armor/silicon_layer_1.png";	
	}

}
