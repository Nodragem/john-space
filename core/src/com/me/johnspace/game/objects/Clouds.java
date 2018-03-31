/*    */ package com.me.johnspace.game.objects;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.badlogic.gdx.math.MathUtils;
/*    */ import com.badlogic.gdx.math.Vector2;
/*    */ import com.badlogic.gdx.utils.Array;
/*    */ import com.me.johnspace.game.Assets;
/*    */ 
/*    */ public class Clouds extends AbstractGameObject
/*    */ {
/*    */   private float length;
/*    */   private Array<TextureRegion> regClouds;
/*    */   private Array<Cloud> clouds;
/*    */   
/*    */   private class Cloud extends AbstractGameObject
/*    */   {
/*    */     private TextureRegion regCloud;
/*    */     
/*    */     public Cloud() {}
/*    */     
/*    */     public void setRegion(TextureRegion region)
/*    */     {
/* 24 */       this.regCloud = region;
/*    */     }
/*    */     
/*    */     public void render(SpriteBatch batch)
/*    */     {
/* 29 */       TextureRegion reg = this.regCloud;
/* 30 */       batch.draw(reg.getTexture(), this.position.x + this.origin.x, this.position.y + this.origin.y, this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, 
/* 31 */         this.scale.x, this.scale.y, this.rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, 
/* 32 */         false);
/*    */     }
/*    */   }
/*    */   
/*    */   public Clouds(float length, float posY) {
/* 37 */     this.length = length;
/* 38 */     this.position.y = posY;
/* 39 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 43 */     this.dimension.set(16.0F, 4.0F);
/*    */     
/* 45 */     this.regClouds = new Array();
/* 46 */     this.regClouds.add(Assets.instance.levelDecoration.cloud1);
/* 47 */     this.regClouds.add(Assets.instance.levelDecoration.cloud2);
/*    */     
/*    */ 
/* 50 */     int distFac = 10;
/* 51 */     int numClouds = (int)(this.length / distFac);
/* 52 */     this.clouds = new Array(2 * numClouds);
/* 53 */     for (int i = 0; i < numClouds; i++) {
/* 54 */       Cloud cloud = spawnCloud();
/* 55 */       cloud.position.x = (i * distFac);
/* 56 */       this.clouds.add(cloud);
/*    */     }
/*    */   }
/*    */   
/*    */   private Cloud spawnCloud() {
/* 61 */     Cloud cloud = new Cloud();
/* 62 */     cloud.dimension.set(this.dimension);
/*    */     
/* 64 */     cloud.setRegion((TextureRegion)this.regClouds.random());
/*    */     
/* 66 */     Vector2 pos = new Vector2();
/* 67 */     pos.x = (this.length + 10.0F);
/* 68 */     pos.y = this.position.y;
/* 69 */     pos.y += MathUtils.random(0.0F, 12.0F);
/* 70 */     cloud.position.set(pos);
/* 71 */     Vector2 speed = new Vector2();
/* 72 */     speed.x += 0.5F;
/* 73 */     speed.x += MathUtils.random(0.0F, 0.25F);
/* 74 */     cloud.terminalVelocity.set(speed);
/* 75 */     speed.x *= 1.0F;
/* 76 */     cloud.velocity.set(speed);
/* 77 */     return cloud;
/*    */   }
/*    */   
/*    */   public void update(float deltaTime)
/*    */   {
/* 82 */     for (int i = this.clouds.size - 1; i >= 0; i--) {
/* 83 */       Cloud cloud = (Cloud)this.clouds.get(i);
/* 84 */       cloud.update(deltaTime);
/* 85 */       if (cloud.position.x < -10.0F) {
/* 86 */         this.clouds.removeIndex(i);
/* 87 */         this.clouds.add(spawnCloud());
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void render(SpriteBatch batch)
/*    */   {
/* 94 */     for (Cloud cloud : this.clouds) {
/* 95 */       cloud.render(batch);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\objects\Clouds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */