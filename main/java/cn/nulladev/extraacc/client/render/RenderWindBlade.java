package cn.nulladev.extraacc.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cn.nulladev.extraacc.core.ExtraAcC;
import cn.nulladev.extraacc.entity.EntityWindBlade;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderWindBlade extends Render {
	
	private static final ResourceLocation texture = new ResourceLocation(ExtraAcC.MODID, "textures/entity/wind_blade.png");
	
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		if (!(entity instanceof EntityWindBlade)) {
			return;
		}
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        //原点移到实体中心
        GL11.glRotatef(entity.rotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
        
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        GL11.glScalef(1, 0.1f, 1);
        for (int i = 0; i < 180; ++i)
        {
            GL11.glRotatef(1F, 1.0F, 0.0F, 0.0F);
            //GL11.glNormal3f(0.0F, 0.0F, f10);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-1D, -1D, 0.0D, 0, 1); // 左下
            tessellator.addVertexWithUV( 1D, -1D, 0.0D, 0, 0); // 左上
            tessellator.addVertexWithUV( 1D,  1D, 0.0D, 1, 0); // 右上
            tessellator.addVertexWithUV(-1D,  1D, 0.0D, 1, 1); // 右下
            tessellator.draw();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
	
	protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
	
}