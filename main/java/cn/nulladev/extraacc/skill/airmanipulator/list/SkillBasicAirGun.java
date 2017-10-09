package cn.nulladev.extraacc.skill.airmanipulator.list;

import cn.academy.ability.api.Skill;
import cn.academy.ability.api.context.ClientRuntime;
import cn.nulladev.extraacc.skill.airmanipulator.context.BasicAirGun;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public class SkillBasicAirGun extends Skill {

	public static final SkillBasicAirGun INSTANCE = new SkillBasicAirGun();

	private SkillBasicAirGun() {
		super("basic_air_gun", 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void activate(ClientRuntime rt, int keyID) {
		activateSingleKey2(rt, keyID, (EntityPlayer p) -> new BasicAirGun(p));
	}

}
