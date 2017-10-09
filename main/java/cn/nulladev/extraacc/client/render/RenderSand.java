package cn.nulladev.extraacc.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cn.nulladev.extraacc.entity.EntitySand;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class RenderSand extends Render {
	
	public Block[] blocks = {Blocks.stone, Blocks.sand, Blocks.sandstone, Blocks.bedrock, Blocks.cobblestone, Blocks.dirt, Blocks.grass};
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		if (!(entity instanceof EntitySand)) {
			return;
		}
		
		int index=(int)(Math.random()*blocks.length);
		Block rand = blocks[index];
		IIcon iicon = rand.getBlockTextureFromSide(0);

		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        
        Tessellator tessellator = Tessellator.instance;
        
        double r = entity.width / 2;
                        
        for (int i = 0; i < 4; ++i) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, 1.0F);
            double f = iicon.getMinU();
            double f1 = iicon.getMaxU();
            double f2 = iicon.getMinV();
            double f3 = iicon.getMaxV();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            tessellator.addVertexWithUV(-r, -r, r, f, f3);
            tessellator.addVertexWithUV(r, -r, r, f1, f3);
            tessellator.addVertexWithUV(r, r, r, f1, f2);
            tessellator.addVertexWithUV(-r, r, r, f, f2);
            tessellator.draw();
        }
        
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        for (int i = 0; i < 2; ++i) {
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, 1.0F);
            double f = iicon.getMinU();
            double f1 = iicon.getMaxU();
            double f2 = iicon.getMinV();
            double f3 = iicon.getMaxV();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            tessellator.addVertexWithUV(-r, -r, r, f, f3);
            tessellator.addVertexWithUV(r, -r, r, f1, f3);
            tessellator.addVertexWithUV(r, r, r, f1, f2);
            tessellator.addVertexWithUV(-r, r, r, f, f2);
            tessellator.draw();
        }

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }

	@Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return TextureMap.locationBlocksTexture;
    }

}
