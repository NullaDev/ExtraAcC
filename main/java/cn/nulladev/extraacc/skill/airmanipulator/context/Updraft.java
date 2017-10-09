package cn.nulladev.extraacc.skill.airmanipulator.context;

import cn.academy.ability.api.context.Context;
import cn.lambdalib.s11n.network.NetworkMessage.Listener;
import cn.lambdalib.util.generic.MathUtils;
import cn.nulladev.extraacc.entity.EntityUpdraft;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillUpdraft;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Updraft extends Context {
	
static final String MSG_PERFORM = "perform";
	
	private float cp;

	public Updraft(EntityPlayer _player) {
		super(_player, SkillUpdraft.INSTANCE);
		
		cp = MathUtils.lerpf(1280, 960, ctx.getSkillExp());
	}
	
	private boolean consume() {
		float overload = MathUtils.lerpf(64, 48, ctx.getSkillExp());
		return ctx.consume(overload, cp);
	}
	
	@Listener(channel=MSG_KEYDOWN, side=Side.CLIENT)
	public void l_keydown()  {
		sendToServer(MSG_PERFORM);
	}
	
	@Listener(channel=MSG_PERFORM, side=Side.SERVER)
	public void s_perform()  {
		if(consume()) {
			World world = player.worldObj;
			
			Vec3 vec = Vec3.createVectorHelper(player.getLookVec().xCoord, 0, player.getLookVec().zCoord).normalize();
			
			EntityUpdraft gun = new EntityUpdraft(world, player, ctx.getSkillExp(), vec);
			world.spawnEntityInWorld(gun);
			
			ctx.addSkillExp(getExpIncr());
			ctx.setCooldown((int)MathUtils.lerpf(120, 80, ctx.getSkillExp()));
		}
	    terminate();
	}

	private float getExpIncr()  {
		return MathUtils.lerpf(0.008f, 0.010f, ctx.getSkillExp());
	}

}
