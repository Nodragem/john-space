/*    */ package com.me.johnspace.game.objects;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.badlogic.gdx.math.Vector2;
/*    */ import com.me.johnspace.game.Assets;
/*    */ 
/*    */ public class Mountains extends AbstractGameObject
/*    */ {
/* 10 */   private TextureRegion[] regHerb1 = new TextureRegion[3];
/* 11 */   private TextureRegion[] regHerb2 = new TextureRegion[3];
/* 12 */   private float[] depth_offset = new float[3];
/*    */   private int length;
/*    */   
/*    */   public Mountains(int length)
/*    */   {
/* 17 */     this.length = length;
/* 18 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 22 */     this.dimension.set(32.0F, 8.0F);
/* 23 */     this.depth_offset[0] = 0.0F;this.depth_offset[1] = 0.0F;this.depth_offset[2] = 0.2F;
/* 24 */     this.regHerb1[0] = Assets.instance.levelDecoration.herb1depth1;
/* 25 */     this.regHerb1[1] = Assets.instance.levelDecoration.herb1depth2;
/* 26 */     this.regHerb1[2] = Assets.instance.levelDecoration.herb1depth3;
/* 27 */     this.regHerb2[0] = Assets.instance.levelDecoration.herb2depth1;
/* 28 */     this.regHerb2[1] = Assets.instance.levelDecoration.herb2depth2;
/* 29 */     this.regHerb2[2] = Assets.instance.levelDecoration.herb2depth3;
/*    */     
/*    */ 
/* 32 */     this.origin.x = (-this.dimension.x * 2.0F);
/* 33 */     this.length = ((int)(this.length + this.dimension.x * 2.0F));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 38 */   public void updateScrollPosition(Vector2 camPosition) { this.position.set(camPosition.x, this.position.y); }
/*    */   
/*    */   private void drawMountain(SpriteBatch batch, float offsetX, int depth, float parallaxSpeedX) {
/* 41 */     TextureRegion reg = null;
/* 42 */     float xRel = this.dimension.x * offsetX;
/* 43 */     float yRel = this.dimension.y * this.depth_offset[depth];
/*    */     
/* 45 */     int mountainLength = 0;
/* 46 */     mountainLength += com.badlogic.gdx.math.MathUtils.ceil(this.length / (2.0F * this.dimension.x) * (1.0F - parallaxSpeedX));
/* 47 */     mountainLength += com.badlogic.gdx.math.MathUtils.ceil(0.5F + offsetX);
/*    */     
/* 49 */     for (int i = 0; i < mountainLength; i++)
/*    */     {
/* 51 */       reg = this.regHerb1[depth];
/* 52 */       batch.draw(reg.getTexture(), this.origin.x + xRel + this.position.x * parallaxSpeedX, this.position.y + this.origin.y + yRel, 
/* 53 */         this.origin.x, this.origin.y, 
/* 54 */         this.dimension.x, this.dimension.y, 
/* 55 */         this.scale.x, this.scale.y, 
/* 56 */         this.rotation, 
/* 57 */         reg.getRegionX(), reg.getRegionY(), 
/* 58 */         reg.getRegionWidth(), reg.getRegionHeight(), 
/* 59 */         false, false);
/* 60 */       xRel += this.dimension.x;
/*    */       
/*    */ 
/* 63 */       reg = this.regHerb2[depth];
/* 64 */       batch.draw(reg.getTexture(), this.origin.x + xRel + this.position.x * parallaxSpeedX, this.position.y + this.origin.y + yRel, 
/* 65 */         this.origin.x, this.origin.y, 
/* 66 */         this.dimension.x, this.dimension.y, 
/* 67 */         this.scale.x, this.scale.y, 
/* 68 */         this.rotation, 
/* 69 */         reg.getRegionX(), reg.getRegionY(), 
/* 70 */         reg.getRegionWidth(), reg.getRegionHeight(), 
/* 71 */         false, false);
/* 72 */       xRel += this.dimension.x;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void render(SpriteBatch batch)
/*    */   {
/* 79 */     drawMountain(batch, 0.5F, 2, 0.8F);
/* 80 */     drawMountain(batch, 0.25F, 1, 0.5F);
/* 81 */     drawMountain(batch, 0.0F, 0, 0.2F);
/*    */   }
/*    */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\objects\Mountains.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */