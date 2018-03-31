/*    */ package com.me.johnspace.game.objects;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.badlogic.gdx.math.Vector2;
/*    */ 
/*    */ public class Jetpack extends AbstractGameObject
/*    */ {
/*    */   public boolean collected;
/*    */   private TextureRegion regJetpack;
/*    */   
/*    */   public Jetpack()
/*    */   {
/* 14 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 18 */     this.dimension.set(0.5F, 0.5F);
/* 19 */     this.regJetpack = com.me.johnspace.game.Assets.instance.box.box;
/* 20 */     this.bounds.set(0.0F, 0.0F, this.dimension.x, this.dimension.y);
/* 21 */     this.collected = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public void render(SpriteBatch batch)
/*    */   {
/* 27 */     if (this.collected) { return;
/*    */     }
/* 29 */     TextureRegion reg = null;
/* 30 */     reg = this.regJetpack;
/* 31 */     batch.draw(reg.getTexture(), 
/* 32 */       this.position.x, this.position.y, 
/* 33 */       this.origin.x, this.origin.y, 
/* 34 */       this.dimension.x, this.dimension.y, 
/* 35 */       this.scale.x, this.scale.y, 
/* 36 */       this.rotation, 
/* 37 */       reg.getRegionX(), reg.getRegionY(), 
/* 38 */       reg.getRegionWidth(), reg.getRegionHeight(), 
/* 39 */       false, false);
/*    */   }
/*    */   
/*    */   public int getScore()
/*    */   {
/* 44 */     return 500;
/*    */   }
/*    */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\objects\Jetpack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */