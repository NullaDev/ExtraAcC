package cn.nulladev.extraacc.skill.airmanipulator.list;

import cn.academy.ability.api.Skill;
import cn.academy.ability.api.context.ClientRuntime;
import cn.nulladev.extraacc.skill.airmanipulator.context.AirCooling;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public class SkillAirCooling extends Skill {

	public static final SkillAirCooling INSTANCE = new SkillAirCooling();

	private SkillAirCooling() {
		super("air_cooling", 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void activate(ClientRuntime rt, int keyID) {
		activateSingleKey2(rt, keyID, (EntityPlayer p) -> new AirCooling(p));
	}

}
