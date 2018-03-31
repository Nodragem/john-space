/*    */ package com.me.johnspace.game.objects;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.badlogic.gdx.math.Vector2;
/*    */ 
/*    */ public class Bottle extends AbstractGameObject
/*    */ {
/*    */   private TextureRegion regBottle;
/*    */   
/*    */   public Bottle()
/*    */   {
/* 12 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 16 */     this.dimension.set(0.2F, 0.6F);
/* 17 */     this.regBottle = com.me.johnspace.game.Assets.instance.levelDecoration.bottle;
/*    */     
/* 19 */     this.bounds.set(0.0F, 0.0F, this.dimension.x, this.dimension.y);
/* 20 */     this.origin.set(this.dimension.x / 2.0F, this.dimension.y / 2.0F);
/*    */   }
/*    */   
/*    */   public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch)
/*    */   {
/* 25 */     TextureRegion reg = null;
/* 26 */     reg = this.regBottle;
/* 27 */     batch.draw(reg.getTexture(), 
/* 28 */       this.position.x - this.origin.x, this.position.y - this.origin.y, 
/* 29 */       this.origin.x, this.origin.y, 
/* 30 */       this.dimension.x, this.dimension.y, 
/* 31 */       this.scale.x, this.scale.y, 
/* 32 */       this.rotation, 
/* 33 */       reg.getRegionX(), reg.getRegionY(), 
/* 34 */       reg.getRegionWidth(), reg.getRegionHeight(), 
/* 35 */       false, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\objects\Bottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */