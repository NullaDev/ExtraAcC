package cn.nulladev.extraacc.skill.airmanipulator.context;

import cn.academy.ability.api.context.Context;
import cn.lambdalib.s11n.network.NetworkMessage.Listener;
import cn.lambdalib.util.generic.MathUtils;
import cn.nulladev.extraacc.entity.EntityBasicAirGun;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillBasicAirGun;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BasicAirGun extends Context {
	
	static final String MSG_PERFORM = "perform";
	
	private float cp;

	public BasicAirGun(EntityPlayer _player) {
		super(_player, SkillBasicAirGun.INSTANCE);
		
		cp = MathUtils.lerpf(150, 250, ctx.getSkillExp());
	}
	
	private boolean consume() {
		float overload = MathUtils.lerpf(15, 9, ctx.getSkillExp());
		return ctx.consume(overload, cp);
	}
	
	@Listener(channel=MSG_KEYDOWN, side=Side.CLIENT)
	public void l_keydown()  {
		//System.out.println("caonima");
		sendToServer(MSG_PERFORM);
	}
	
	@Listener(channel=MSG_PERFORM, side=Side.SERVER)
	public void s_perform()  {
		if(consume()) {
			//System.out.println("caonima2");
			World world = player.worldObj;
			
			EntityBasicAirGun gun = new EntityBasicAirGun(world, player, ctx.getSkillExp(), player.getLookVec());
			world.spawnEntityInWorld(gun);
			
			ctx.addSkillExp(getExpIncr());
			ctx.setCooldown((int)MathUtils.lerpf(45, 15, ctx.getSkillExp()));
		}
	    terminate();
	}

	private float getExpIncr()  {
		return MathUtils.lerpf(0.004f, 0.002f, ctx.getSkillExp());
	}
		
}
