package cn.nulladev.extraacc.skill.airmanipulator.list;

import cn.academy.ability.api.Skill;
import cn.academy.ability.api.context.ClientRuntime;
import cn.nulladev.extraacc.skill.airmanipulator.context.ExplosiveAirGun;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public class SkillExplosiveAirGun extends Skill {

	public static final SkillExplosiveAirGun INSTANCE = new SkillExplosiveAirGun();

	private SkillExplosiveAirGun() {
		super("explosive_air_gun", 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void activate(ClientRuntime rt, int keyID) {
		activateSingleKey2(rt, keyID, (EntityPlayer p) -> new ExplosiveAirGun(p));
	}

}

