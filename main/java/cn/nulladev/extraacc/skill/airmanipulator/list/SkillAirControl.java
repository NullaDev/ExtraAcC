package cn.nulladev.extraacc.skill.airmanipulator.list;

import cn.academy.ability.api.Skill;

public class SkillAirControl extends Skill {
	
	public static final SkillAirControl INSTANCE = new SkillAirControl();

	public SkillAirControl() {
		super("air_control", 1);
		this.canControl = false;
	}

}
