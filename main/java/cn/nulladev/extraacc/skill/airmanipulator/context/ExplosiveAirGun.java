package cn.nulladev.extraacc.skill.airmanipulator.context;

import cn.academy.ability.api.context.Context;
import cn.lambdalib.s11n.network.NetworkMessage.Listener;
import cn.lambdalib.util.generic.MathUtils;
import cn.nulladev.extraacc.entity.EntityBasicAirGun;
import cn.nulladev.extraacc.entity.EntityExplosiveAirGun;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillExplosiveAirGun;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ExplosiveAirGun extends Context {
	
	static final String MSG_PERFORM = "perform";
	static final String MSG_PERFORM2 = "perform2";
	
	public EntityExplosiveAirGun gun;
	private boolean initialized = false;
	
	private float cp;

	public ExplosiveAirGun(EntityPlayer _player) {
		super(_player, SkillExplosiveAirGun.INSTANCE);
		
		cp = MathUtils.lerpf(200, 300, ctx.getSkillExp());
	}
	
	private boolean consume() {
		float overload = MathUtils.lerpf(25, 15, ctx.getSkillExp());
		return ctx.consume(overload, cp);
	}
	
	@Listener(channel=MSG_KEYDOWN, side=Side.CLIENT)
	public void l_keydown()  {
		sendToServer(MSG_PERFORM);
	}
	
	@Listener(channel=MSG_KEYUP, side=Side.CLIENT)
	public void l_keyup()  {
		sendToServer(MSG_PERFORM2);
	}
	
	@Listener(channel=MSG_PERFORM, side=Side.SERVER)
	public void s_perform()  {
		if(consume()) {
			World world = player.worldObj;
			gun = new EntityExplosiveAirGun(world, player, ctx.getSkillExp(), player.getLookVec());
			world.spawnEntityInWorld(gun);
		}
		initialized = true;
	}
	
	@Listener(channel=MSG_PERFORM2, side=Side.SERVER)
	public void s_perform2()  {
		if (this.getStatus() == Status.TERMINATED) {
			return;
		}
		explode();
		end();
	}
	
	@Listener(channel=MSG_TICK, side=Side.SERVER)
	public void s_tick()  {
		if (!initialized) {
			return;
		}
		if (this.getStatus() == Status.TERMINATED) {
			return;
		}
		if (gun == null || !gun.canControl()) {
			end();
			return;
		}
		if(ctx.consume(0, 5)) {
			;
		} else {
			explode();
			end();
		}
	}

	private float getExpIncr()  {
		return MathUtils.lerpf(0.004f, 0.006f, ctx.getSkillExp());
	}
	
	private void end() {
		ctx.addSkillExp(getExpIncr());
		ctx.setCooldown((int)MathUtils.lerpf(50, 30, ctx.getSkillExp()));
		terminate();
	}
	
	private void explode() {
		if (gun != null) {
			if (gun.canControl()) {
				gun.explode();
			}
		}
	}
		
}
