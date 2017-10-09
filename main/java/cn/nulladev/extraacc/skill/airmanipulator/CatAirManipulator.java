package cn.nulladev.extraacc.skill.airmanipulator;

import cn.academy.ability.api.Category;
import cn.academy.ability.api.Skill;
import cn.academy.vanilla.ModuleVanilla;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillAirControl;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillAirCooling;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillBasicAirGun;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillExplosiveAirGun;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillSandStorm;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillTurbulentStorm;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillUpdraft;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillWindAssault;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillWindBlade;

public class CatAirManipulator extends Category {
	
	public static Skill basic_air_gun = SkillBasicAirGun.INSTANCE;
	public static Skill air_control = SkillAirControl.INSTANCE;
	
	public static Skill wind_blade = SkillWindBlade.INSTANCE;
	public static Skill wind_assault = SkillWindAssault.INSTANCE;

	public static Skill explosive_air_gun = SkillExplosiveAirGun.INSTANCE;
	public static Skill air_cooling = SkillAirCooling.INSTANCE;
	
	public static Skill updraft = SkillUpdraft.INSTANCE;
	public static Skill sand_storm = SkillSandStorm.INSTANCE;
	
	public static Skill turbulent_storm = SkillTurbulentStorm.INSTANCE;

	public CatAirManipulator() {
		super("airmanipulator");
		this.colorStyle.setColor4i(127, 127, 255, 80);
		
		addSkill(basic_air_gun);
		addSkill(air_control);
		
		addSkill(wind_blade);
		addSkill(wind_assault);
		
		addSkill(explosive_air_gun);
		addSkill(air_cooling);
		
		addSkill(updraft);
		addSkill(sand_storm);
		
		addSkill(turbulent_storm);
		
        ModuleVanilla.addGenericSkills(this);
		
		basic_air_gun.setPosition(20, 25);
		air_control.setPosition(35, 60);
		
		wind_blade.setPosition(70, 15);
		wind_assault.setPosition(75, 75);
		
		explosive_air_gun.setPosition(90, 45);
		air_cooling.setPosition(115, 80);
		
		updraft.setPosition(150, 20);
		sand_storm.setPosition(160, 75);
		
		turbulent_storm.setPosition(200, 35);
		
		wind_blade.setParent(basic_air_gun, 0.8F);
		wind_assault.setParent(air_control);
		explosive_air_gun.setParent(basic_air_gun, 1F);
		air_cooling.setParent(wind_assault, 0.75F);
		updraft.setParent(wind_blade, 0.9F);
		sand_storm.setParent(air_cooling, 0.6F);
		turbulent_storm.setParent(updraft, 0.7F);
	}

}