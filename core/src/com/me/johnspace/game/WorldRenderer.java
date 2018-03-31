/*     */ package com.me.johnspace.game;
/*     */ 
/*     */ import com.badlogic.gdx.graphics.OrthographicCamera;
/*     */ import com.badlogic.gdx.graphics.g2d.BitmapFont;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.badlogic.gdx.math.MathUtils;
/*     */ import com.badlogic.gdx.math.Vector3;
/*     */ import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
/*     */ import com.me.johnspace.game.objects.Level;
/*     */ 
/*     */ public class WorldRenderer implements com.badlogic.gdx.utils.Disposable
/*     */ {
/*     */   private OrthographicCamera camera;
/*     */   private OrthographicCamera cameraGUI;
/*     */   private SpriteBatch batch;
/*     */   private WorldController worldController;
/*     */   private static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
/*     */   private Box2DDebugRenderer b2debugRenderer;
/*     */   
/*     */   public WorldRenderer(WorldController worldController)
/*     */   {
/*  22 */     this.worldController = worldController;
/*  23 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  27 */     this.batch = new SpriteBatch();
/*  28 */     this.camera = new OrthographicCamera(18.0F, 14.0F);
/*     */     
/*  30 */     this.camera.position.set(9.0F, 7.0F, 0.0F);
/*  31 */     this.camera.update();
/*     */     
/*  33 */     this.cameraGUI = new OrthographicCamera(800.0F, 600.0F);
/*     */     
/*  35 */     this.cameraGUI.setToOrtho(true);
/*  36 */     this.cameraGUI.update();
/*  37 */     this.b2debugRenderer = new Box2DDebugRenderer();
/*     */   }
/*     */   
/*     */   public void render() {
/*  41 */     renderWorld(this.batch);
/*  42 */     renderGUI(this.batch);
/*     */   }
/*     */   
/*     */   private void renderWorld(SpriteBatch batch) {
/*  46 */     this.worldController.cameraHelper.applyTo(this.camera);
/*  47 */     batch.setProjectionMatrix(this.camera.combined);
/*  48 */     batch.begin();
/*  49 */     this.worldController.level.render(batch);
/*  50 */     batch.end();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void renderGUI(SpriteBatch batch)
/*     */   {
/*  57 */     batch.setProjectionMatrix(this.cameraGUI.combined);
/*  58 */     batch.begin();
/*  59 */     renderGuiScore(batch);
/*  60 */     renderGuiJetpack(batch);
/*  61 */     renderGuiExtraLive(batch);
/*  62 */     renderGuiFpsCounter(batch);
/*  63 */     renderGuiGameOverMessage(batch);
/*  64 */     batch.end();
/*     */   }
/*     */   
/*     */   private void renderGuiScore(SpriteBatch batch) {
/*  68 */     float x = -15.0F;
/*  69 */     float y = -15.0F;
/*  70 */     float offsetX = 50.0F;
/*  71 */     float offsetY = 50.0F;
/*  72 */     if (this.worldController.scoreVisual < this.worldController.score) {
/*  73 */       long shakeAlpha = System.currentTimeMillis() % 360L;
/*  74 */       float shakeDist = 1.5F;
/*  75 */       offsetX += MathUtils.sinDeg((float)shakeAlpha * 2.2F) * shakeDist;
/*  76 */       offsetY += MathUtils.sinDeg((float)shakeAlpha * 2.9F) * shakeDist;
/*     */     }
/*     */     
/*  79 */     batch.draw(Assets.instance.star.star1, 
/*  80 */       x, y, offsetX, offsetY, 100.0F, 100.0F, 0.35F, -0.35F, 0.0F);
/*  81 */     Assets.instance.fonts.defaultBig.draw(batch, String.valueOf((int) this.worldController.scoreVisual),
                                                    x + 75.0F, y + 37.0F);
/*     */   }
/*     */   
/*     */   private void renderGuiExtraLive(SpriteBatch batch) {
/*  85 */     float x = this.cameraGUI.viewportWidth - 50.0F - 150.0F;
/*  86 */     float y = -15.0F;
/*  87 */     for (int i = 0; i < 3; i++) {
/*  88 */       if (this.worldController.lives <= i) batch.setColor(0.5F, 0.5F, 0.5F, 0.5F);
/*  89 */       batch.draw(Assets.instance.hero.john, x + i * 50, y, 50.0F, 50.0F, 120.0F, 100.0F, 0.35F, -0.35F, 0.0F);
/*  90 */       batch.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*  92 */     if ((this.worldController.lives >= 0) && (this.worldController.livesVisual > this.worldController.lives)) {
/*  93 */       int i = this.worldController.lives;
/*  94 */       float alphaColor = Math.max(0.0F, this.worldController.livesVisual - this.worldController.lives - 0.5F);
/*  95 */       float alphaScale = 0.35F * (2 + this.worldController.lives - this.worldController.livesVisual) * 2.0F;
/*  96 */       float alphaRotate = -45.0F * alphaColor;
/*  97 */       batch.setColor(1.0F, 0.7F, 0.7F, alphaColor);
/*  98 */       batch.draw(Assets.instance.hero.john, x + i * 50, y, 50.0F, 50.0F, 120.0F, 100.0F, alphaScale, -alphaScale, alphaRotate);
/*  99 */       batch.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderGuiFpsCounter(SpriteBatch batch)
/*     */   {
/* 105 */     float x = this.cameraGUI.viewportWidth - 55.0F;
/* 106 */     float y = this.cameraGUI.viewportHeight - 15.0F;
/*     */     
/* 108 */     int fps = com.badlogic.gdx.Gdx.graphics.getFramesPerSecond();
/* 109 */     BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
/* 110 */     if (fps >= 45) {
/* 111 */       fpsFont.setColor(0.0F, 1.0F, 0.0F, 1.0F);
/* 112 */     } else if (fps >= 30) {
/* 113 */       fpsFont.setColor(1.0F, 1.0F, 0.0F, 1.0F);
/*     */     } else {
/* 115 */       fpsFont.setColor(1.0F, 0.0F, 0.0F, 1.0F);
/*     */     }
/* 117 */     fpsFont.draw(batch, "FPS: " + fps, x, y);
/* 118 */     fpsFont.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   private void renderGuiGameOverMessage(SpriteBatch batch) {
/* 122 */     float x = this.cameraGUI.viewportWidth / 2.0F;
/* 123 */     float y = this.cameraGUI.viewportHeight / 2.0F;
/* 124 */     if (this.worldController.isGameOver()) {
/* 125 */       BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
/* 126 */       fontGameOver.setColor(1.0F, 0.25F, 0.25F, 1.0F);
/* 127 */       fontGameOver.drawMultiLine(batch, "GAME OVER \n You missed your chance for a galactic beer!", x, y, 0.0F, com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment.CENTER);
/* 128 */       fontGameOver.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderGuiJetpack(SpriteBatch batch) {
/* 133 */     float x = -15.0F;
/* 134 */     float y = 30.0F;
/* 135 */     float fuelLeft = this.worldController.level.hero.fuelLeftJetpack;
/* 136 */     if (fuelLeft > 0.0F) {
/* 137 */       if ((fuelLeft < 6.0D) && 
/* 138 */         ((int)(fuelLeft * 5.0F) % 2 != 0)) {
/* 139 */         batch.setColor(1.0F, 1.0F, 1.0F, 0.5F);
/*     */       }
/*     */       
/* 142 */       batch.draw(Assets.instance.box.box, x, y, 50.0F, 50.0F,
                                    100.0F, 100.0F, 0.35F, -0.35F, 0.0F);
/* 143 */       batch.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 144 */       Assets.instance.fonts.defaultSmall.draw(batch, String.valueOf((int)fuelLeft),
                                                        x + 60.0F, y + 57.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resize(int width, int height)
/*     */   {
/* 150 */     this.camera.viewportWidth = (14.0F / height * width);
/* 151 */     this.camera.update();
/* 152 */     this.cameraGUI.viewportHeight = 600.0F;
/* 153 */     this.cameraGUI.viewportWidth = (600.0F / height * width);
/* 154 */     this.cameraGUI.position.set(this.cameraGUI.viewportWidth / 2.0F, this.cameraGUI.viewportHeight / 2.0F, 0.0F);
/* 155 */     this.cameraGUI.update();
/*     */   }
/*     */   
/*     */   public void dispose()
/*     */   {
/* 160 */     this.batch.dispose();
/*     */   }
/*     */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\WorldRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */