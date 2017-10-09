package cn.nulladev.extraacc.event;

import cn.academy.ability.api.data.AbilityData;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillAirControl;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class DamageRecalc {
	
	@SubscribeEvent
    public void recalc(LivingHurtEvent event) {
		if (!(event.entity instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) event.entity;
		if (!AbilityData.get(player).isSkillLearned(SkillAirControl.INSTANCE)) {
			return;
		}
		if (event.source.getDamageType().equals("inWall") || event.source.getDamageType().equals("drown")) {
			event.ammount *= 0.1;
		} else if (event.source.getDamageType().equals("fall")) {
			event.ammount *= 0.25;
		} else if (event.source.isProjectile() || event.source.getDamageType().equals("anvil")) {
			event.ammount *= 0.75;
		}
    }
	
	@SubscribeEvent
    public void air(PlayerTickEvent event) {
		if (event.player.getAir() < 300 && event.player.worldObj.getTotalWorldTime() % 10 == 0) {
			if (AbilityData.get(event.player).isSkillLearned(SkillAirControl.INSTANCE)) {
				event.player.setAir(300);
			}
		}
	}

}
