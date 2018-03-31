/*    */ package com.me.johnspace;
/*    */ 
/*    */ import com.badlogic.gdx.Application;
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.assets.AssetManager;
/*    */ import com.me.johnspace.game.Assets;
/*    */ import com.me.johnspace.game.Assets.AssetMusic;
/*    */ import com.me.johnspace.game.WorldController;
/*    */ import com.me.johnspace.game.WorldRenderer;
/*    */ import com.me.johnspace.util.AudioManager;
/*    */ 
/*    */ public class JohnSpaceGame implements com.badlogic.gdx.ApplicationListener
/*    */ {
/* 15 */   private static final String TAG = JohnSpaceGame.class.getName();
/*    */   
/*    */   private WorldController worldController;
/*    */   private WorldRenderer worldRenderer;
/*    */   private boolean paused;
/*    */   
/*    */   public void create()
/*    */   {
/* 23 */     Gdx.app.setLogLevel(3);
/* 24 */     Assets.instance.init(new AssetManager());
/*    */     
/* 26 */     AudioManager.instance.play(Assets.instance.music.themeSong);
/* 27 */     this.worldController = new WorldController();
/* 28 */     this.worldRenderer = new WorldRenderer(this.worldController);
/* 29 */     this.paused = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public void resize(int width, int height)
/*    */   {
/* 35 */     this.worldRenderer.resize(width, height);
/*    */   }
/*    */   
/*    */ 
/*    */   public void render()
/*    */   {
/* 41 */     if (!this.paused) this.worldController.update(Gdx.graphics.getDeltaTime());
/* 42 */     Gdx.gl.glClearColor(0.39215687F, 0.58431375F, 0.92941177F, 1.0F);
/* 43 */     Gdx.gl.glClear(16384);
/* 44 */     this.worldRenderer.render();
/*    */   }
/*    */   
/*    */   public void pause()
/*    */   {
/* 49 */     this.paused = true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void resume()
/*    */   {
/* 55 */     Assets.instance.init(new AssetManager());
/* 56 */     this.paused = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public void dispose()
/*    */   {
/* 62 */     this.worldRenderer.dispose();
/* 63 */     this.worldController.dispose();
/* 64 */     Assets.instance.dispose();
/*    */   }
/*    */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\JohnSpaceGame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */