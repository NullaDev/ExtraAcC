package cn.nulladev.extraacc.skill.airmanipulator.list;

import cn.academy.ability.api.Skill;
import cn.academy.ability.api.context.ClientRuntime;
import cn.nulladev.extraacc.skill.airmanipulator.context.WindBlade;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public class SkillWindBlade extends Skill {

	public static final SkillWindBlade INSTANCE = new SkillWindBlade();

	private SkillWindBlade() {
		super("wind_blade", 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void activate(ClientRuntime rt, int keyID) {
		activateSingleKey2(rt, keyID, (EntityPlayer p) -> new WindBlade(p));
	}

}
