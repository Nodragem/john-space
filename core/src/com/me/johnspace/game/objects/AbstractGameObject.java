/*    */ package com.me.johnspace.game.objects;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.Animation;
/*    */ import com.badlogic.gdx.math.MathUtils;
/*    */ import com.badlogic.gdx.math.Rectangle;
/*    */ import com.badlogic.gdx.math.Vector2;
/*    */ import com.badlogic.gdx.physics.box2d.Body;
/*    */ 
/*    */ public abstract class AbstractGameObject
/*    */ {
/*    */   public Vector2 position;
/*    */   public Vector2 dimension;
/*    */   public Vector2 origin;
/*    */   public Vector2 scale;
/*    */   public float rotation;
/*    */   public int repeat_x;
/*    */   public int repeat_y;
/*    */   public Vector2 velocity;
/*    */   public Vector2 terminalVelocity;
/*    */   public Vector2 friction;
/*    */   public Vector2 acceleration;
/*    */   public Rectangle bounds;
/*    */   public float stateTime;
/*    */   public Body body;
/*    */   public Animation animation;
/*    */   
/*    */   public AbstractGameObject()
/*    */   {
/* 29 */     this.position = new Vector2(0.0F, 0.0F);
/* 30 */     this.dimension = new Vector2(1.0F, 1.0F);
/* 31 */     this.origin = new Vector2(0.0F, 0.0F);
/* 32 */     this.scale = new Vector2(1.0F, 1.0F);
/* 33 */     this.rotation = 0.0F;
/* 34 */     this.velocity = new Vector2();
/* 35 */     this.terminalVelocity = new Vector2(1.0F, 1.0F);
/* 36 */     this.friction = new Vector2();
/* 37 */     this.acceleration = new Vector2();
/* 38 */     this.bounds = new Rectangle();
/* 39 */     this.repeat_x = 1;
/* 40 */     this.repeat_y = 1;
/*    */   }
/*    */   
/*    */   public void setAnimation(Animation animation) {
/* 44 */     this.animation = animation;
/* 45 */     this.stateTime = 0.0F;
/*    */   }
/*    */   
/*    */   public void update(float deltaTime) {
/* 49 */     if (this.body == null) {
/* 50 */       this.stateTime += deltaTime;
/* 51 */       updateMotionX(deltaTime);
/* 52 */       updateMotionY(deltaTime);
/* 53 */       this.position.x += this.velocity.x * deltaTime;
/* 54 */       this.position.y += this.velocity.y * deltaTime;
/*    */     }
/*    */     else
/*    */     {
/* 58 */       this.position.set(this.body.getPosition());
/* 59 */       this.rotation = (this.body.getAngle() * 57.295776F);
/*    */     }
/* 61 */     this.bounds.set(this.position.x, this.position.y, this.dimension.x * this.repeat_x, this.dimension.y * this.repeat_y);
/*    */   }
/*    */   
/*    */   public abstract void render(com.badlogic.gdx.graphics.g2d.SpriteBatch paramSpriteBatch);
/*    */   
/*    */   protected void updateMotionX(float deltaTime)
/*    */   {
/* 68 */     if (this.velocity.x != 0.0F) {
/* 69 */       if (this.velocity.x > 0.0F) {
/* 70 */         this.velocity.x = Math.max(this.velocity.x - this.friction.x * deltaTime, 0.0F);
/*    */       } else {
/* 72 */         this.velocity.x = Math.min(this.velocity.x + this.friction.x * deltaTime, 0.0F);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 79 */     this.velocity.x += this.acceleration.x * deltaTime;
/* 80 */     this.velocity.x = MathUtils.clamp(this.velocity.x, -this.terminalVelocity.x, this.terminalVelocity.x);
/*    */   }
/*    */   
/*    */   protected void updateMotionY(float deltaTime) {
/* 84 */     if (this.velocity.y != 0.0F) {
/* 85 */       if (this.velocity.y > 0.0F) {
/* 86 */         this.velocity.y = Math.max(this.velocity.y - this.friction.y * deltaTime, 0.0F);
/*    */       } else {
/* 88 */         this.velocity.y = Math.min(this.velocity.y + this.friction.y * deltaTime, 0.0F);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 95 */     this.velocity.y += this.acceleration.y * deltaTime;
/*    */     
/* 97 */     this.velocity.y = MathUtils.clamp(this.velocity.y, -this.terminalVelocity.y, this.terminalVelocity.y);
/*    */   }
/*    */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\objects\AbstractGameObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */