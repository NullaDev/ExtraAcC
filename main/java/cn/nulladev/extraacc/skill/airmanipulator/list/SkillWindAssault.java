package cn.nulladev.extraacc.skill.airmanipulator.list;

import cn.academy.ability.api.Skill;
import cn.academy.ability.api.context.ClientRuntime;
import cn.nulladev.extraacc.skill.airmanipulator.context.WindAssault;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public class SkillWindAssault extends Skill {

	public static final SkillWindAssault INSTANCE = new SkillWindAssault();

	private SkillWindAssault() {
		super("wind_assault", 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void activate(ClientRuntime rt, int keyID) {
		activateSingleKey2(rt, keyID, (EntityPlayer p) -> new WindAssault(p));
	}

}