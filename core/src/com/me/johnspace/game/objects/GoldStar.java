/*    */ package com.me.johnspace.game.objects;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.Animation;
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.badlogic.gdx.math.Vector2;
/*    */ import com.me.johnspace.game.Assets;
/*    */ 
/*    */ public class GoldStar extends AbstractGameObject
/*    */ {
/*    */   public boolean collected;
/*    */   
/*    */   public GoldStar()
/*    */   {
/* 15 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 19 */     this.dimension.set(0.5F, 0.5F);
/* 20 */     setAnimation(Assets.instance.star.animStar);
/*    */     
/* 22 */     this.collected = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public void render(SpriteBatch batch)
/*    */   {
/* 28 */     if (this.collected) { return;
/*    */     }
/* 30 */     TextureRegion reg = null;
/* 31 */     reg = this.animation.getKeyFrame(this.stateTime, true);
/* 32 */     batch.draw(reg.getTexture(), 
/* 33 */       this.position.x, this.position.y, 
/* 34 */       this.origin.x, this.origin.y, 
/* 35 */       this.dimension.x, this.dimension.y, 
/* 36 */       this.scale.x, this.scale.y, 
/* 37 */       this.rotation, 
/* 38 */       reg.getRegionX(), reg.getRegionY(), 
/* 39 */       reg.getRegionWidth(), reg.getRegionHeight(), 
/* 40 */       false, false);
/*    */   }
/*    */   
/*    */   public int getScore()
/*    */   {
/* 45 */     return 100;
/*    */   }
/*    */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\objects\GoldStar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */