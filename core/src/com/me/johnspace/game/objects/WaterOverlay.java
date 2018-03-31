/*    */ package com.me.johnspace.game.objects;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.badlogic.gdx.math.Vector2;
/*    */ 
/*    */ public class WaterOverlay extends AbstractGameObject
/*    */ {
/*    */   private float length;
/*    */   private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion regWaterOverlay;
/*    */   
/*    */   public WaterOverlay(int length)
/*    */   {
/* 14 */     this.length = length;
/* 15 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 19 */     this.dimension.set(this.length * 10.0F, 4.0F);
/* 20 */     this.regWaterOverlay = 
/* 21 */       com.me.johnspace.game.Assets.instance.levelDecoration.water;
/* 22 */     this.origin.x = (-this.dimension.x / 2.0F);
/*    */   }
/*    */   
/*    */ 
/*    */   public void render(SpriteBatch batch)
/*    */   {
/* 28 */     TextureRegion reg = null;
/* 29 */     reg = this.regWaterOverlay;
/*    */     
/* 31 */     batch.draw(reg.getTexture(), 
/* 32 */       this.position.x + this.origin.x, this.position.y + this.origin.y, 
/* 33 */       this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, 
/* 34 */       this.scale.x, this.scale.y, 
/* 35 */       this.rotation, 
/* 36 */       reg.getRegionX(), reg.getRegionY(), 
/* 37 */       reg.getRegionWidth(), reg.getRegionHeight(), 
/* 38 */       false, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\objects\WaterOverlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */