/*    */ package com.me.johnspace.util;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.OrthographicCamera;
/*    */ import com.badlogic.gdx.math.MathUtils;
/*    */ import com.badlogic.gdx.math.Vector2;
/*    */ import com.me.johnspace.game.objects.AbstractGameObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CameraHelper
/*    */ {
/* 28 */   private static final String TAG = CameraHelper.class.getName();
/*    */   
/* 30 */   private final float MAX_ZOOM_IN = 0.25F;
/* 31 */   private final float MAX_ZOOM_OUT = 10.0F;
/* 32 */   private final float FOLLOW_SPEED = 4.0F;
/*    */   
/*    */   private Vector2 position;
/*    */   private float zoom;
/*    */   private AbstractGameObject target;
/*    */   
/*    */   public CameraHelper()
/*    */   {
/* 40 */     this.position = new Vector2(9.0F, 7.0F);
/* 41 */     this.zoom = 1.0F;
/*    */   }
/*    */   
/*    */   public void update(float deltaTime) {
/* 45 */     if (!hasTarget()) return;
/* 46 */     if (Math.abs(this.position.x - this.target.position.x) > 0.25F)
/* 47 */       this.position.x = (this.target.position.x + this.target.origin.x);
/* 48 */     this.position.y = (this.target.position.y + this.target.origin.y);
/* 49 */     this.position.y = Math.max(7.0F, this.position.y);
/*    */   }
/*    */   
/*    */   public void setPosition(float x, float y) {
/* 53 */     this.position.set(x, y);
/*    */   }
/*    */   
/*    */   public Vector2 getPosition() {
/* 57 */     return this.position;
/*    */   }
/*    */   
/*    */   public void addZoom(float amount) {
/* 61 */     setZoom(this.zoom + amount);
/*    */   }
/*    */   
/*    */   public void setZoom(float zoom) {
/* 65 */     this.zoom = MathUtils.clamp(zoom, 0.25F, 10.0F);
/*    */   }
/*    */   
/*    */   public float getZoom() {
/* 69 */     return this.zoom;
/*    */   }
/*    */   
/*    */   public void setTarget(AbstractGameObject target) {
/* 73 */     this.target = target;
/*    */   }
/*    */   
/*    */   public AbstractGameObject getTarget() {
/* 77 */     return this.target;
/*    */   }
/*    */   
/*    */   public boolean hasTarget() {
/* 81 */     return this.target != null;
/*    */   }
/*    */   
/*    */   public boolean hasTarget(AbstractGameObject target) {
/* 85 */     return (hasTarget()) && (this.target.equals(target));
/*    */   }
/*    */   
/*    */   public void applyTo(OrthographicCamera camera) {
/* 89 */     camera.position.x = this.position.x;
/* 90 */     camera.position.y = this.position.y;
/* 91 */     camera.zoom = this.zoom;
/* 92 */     camera.update();
/*    */   }
/*    */ }


/* Location:              C:\\Users\\geoff\\Downloads\\johnspace.jar!\\com\\me\\trainingplatformer\\util\\CameraHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */