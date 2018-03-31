/*     */ package com.me.johnspace.game;
/*     */ 
/*     */ import com.badlogic.gdx.Application;
/*     */ import com.badlogic.gdx.Application.ApplicationType;
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Input;
/*     */ import com.badlogic.gdx.InputAdapter;
/*     */ import com.badlogic.gdx.controllers.Controller;
/*     */ import com.badlogic.gdx.controllers.Controllers;
/*     */ import com.badlogic.gdx.graphics.g2d.Sprite;
/*     */ import com.badlogic.gdx.math.MathUtils;
/*     */ import com.badlogic.gdx.math.Rectangle;
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.badlogic.gdx.physics.box2d.Body;
/*     */ import com.badlogic.gdx.physics.box2d.BodyDef;
/*     */ import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
/*     */ import com.badlogic.gdx.physics.box2d.FixtureDef;
/*     */ import com.badlogic.gdx.physics.box2d.PolygonShape;
/*     */ import com.badlogic.gdx.physics.box2d.World;
/*     */ import com.badlogic.gdx.utils.Array;
/*     */ import com.me.johnspace.game.objects.Bottle;
/*     */ import com.me.johnspace.game.objects.GoldStar;
/*     */ import com.me.johnspace.game.objects.Grounds;
/*     */ import com.me.johnspace.game.objects.Jetpack;
/*     */ import com.me.johnspace.game.objects.JohnHero;
/*     */ import com.me.johnspace.game.objects.JohnHero.JUMP_STATE;
/*     */ import com.me.johnspace.game.objects.Level;
/*     */ import com.me.johnspace.game.objects.Totem;
/*     */ import com.me.johnspace.util.AudioManager;
/*     */ import com.me.johnspace.util.CameraHelper;
/*     */
/*     */ public class WorldController extends InputAdapter implements com.badlogic.gdx.utils.Disposable
/*     */ {
/*  34 */   private static final String TAG = WorldController.class.getName();
/*     */   public Level level;
/*     */   public int lives;
/*     */   public int score;
/*     */   public Sprite[] testSprites;
/*     */   public int selectedSprite;
/*     */   public Controller cont;
/*     */   public CameraHelper cameraHelper;
/*  42 */   private Rectangle r1 = new Rectangle();
/*  43 */   private Rectangle r2 = new Rectangle();
/*     */   private float timeLeftGameOverDelay;
/*     */   public float livesVisual;
/*     */   public float scoreVisual;
/*     */   private boolean goalReached;
/*     */   public World b2world;
/*     */   
/*     */   public WorldController() {
/*  51 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  55 */     Gdx.input.setInputProcessor(this);
/*  56 */     if (Controllers.getControllers().size > 0) {
/*  57 */       this.cont = ((Controller)Controllers.getControllers().get(0));
/*     */     }
/*  59 */     this.cameraHelper = new CameraHelper();
/*  60 */     this.lives = 3;
/*  61 */     this.livesVisual = this.lives;
/*  62 */     this.timeLeftGameOverDelay = 0.0F;
/*  63 */     initLevel();
/*     */   }
/*     */   
/*     */   public void dispose()
/*     */   {
/*  68 */     if (this.b2world != null) this.b2world.dispose();
/*     */   }
/*     */   
/*     */   private void initLevel() {
/*  72 */     this.score = 0;
/*  73 */     this.scoreVisual = this.score;
/*  74 */     this.goalReached = false;
/*  75 */     this.level = new Level("levels/level-01.png");
/*  76 */     this.cameraHelper.setTarget(this.level.hero);
/*  77 */     initPhysics();
/*     */   }
/*     */   
/*     */   private void initPhysics() {
/*  81 */     if (this.b2world != null) this.b2world.dispose();
/*  82 */     this.b2world = new World(new Vector2(0.0F, -9.81F), true);
/*     */     
/*  84 */     Vector2 origin = new Vector2();
/*  85 */     for (Grounds g : this.level.grounds) {
/*  86 */       BodyDef bodyDef = new BodyDef();
/*  87 */       origin.x = (g.bounds.width / 2.0F);
/*  88 */       origin.y = (g.bounds.height / 2.0F);
/*  89 */       Gdx.app.debug(TAG, "origin.x =" + origin.x + "origin.y =" + origin.y);
/*  90 */       bodyDef.type = BodyDef.BodyType.KinematicBody;
/*  91 */       bodyDef.position.set(g.position);
/*  92 */       Body body = this.b2world.createBody(bodyDef);
/*  93 */       g.body = body;
/*  94 */       PolygonShape polygonShape = new PolygonShape();
/*     */       
/*  96 */       polygonShape.setAsBox(origin.x, origin.y, origin, 0.0F);
/*  97 */       FixtureDef fixtureDef = new FixtureDef();
/*  98 */       fixtureDef.shape = polygonShape;
/*  99 */       body.createFixture(fixtureDef);
/* 100 */       polygonShape.dispose();
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(float deltaTime) {
/* 105 */     handleDebugInput(deltaTime);
/* 106 */     if ((isGameOver()) || (this.goalReached)) {
/* 107 */       this.timeLeftGameOverDelay -= deltaTime;
/* 108 */       if (this.timeLeftGameOverDelay < 0.0F) init();
/*     */     } else {
/* 110 */       handleGameInput(deltaTime);
/*     */     }
/*     */     
/* 113 */     this.level.update(deltaTime);
/* 114 */     testCollisions(deltaTime);
/* 115 */     this.b2world.step(deltaTime, 8, 3);
/*     */     
/* 117 */     this.cameraHelper.update(deltaTime);
/* 118 */     if ((!isGameOver()) && (isPlayeInWater())) {
/* 119 */       this.lives -= 1;
/* 120 */       if (isGameOver()) {
/* 121 */         AudioManager.instance.play(Assets.instance.sounds.gameover);
/* 122 */         this.timeLeftGameOverDelay = 3.0F;
/*     */       }
/*     */       else {
/* 125 */         initLevel();
/* 126 */         AudioManager.instance.play(Assets.instance.sounds.liveLost, 1.0F);
/*     */       }
/*     */     }
/* 129 */     this.level.mountains.updateScrollPosition(this.cameraHelper.getPosition());
/* 130 */     if (this.livesVisual > this.lives)
/* 131 */       this.livesVisual = Math.max(this.lives, this.livesVisual - 1.0F * deltaTime);
/* 132 */     if (this.scoreVisual < this.score)
/* 133 */       this.scoreVisual = Math.min(this.score, this.scoreVisual + 250.0F * deltaTime);
/*     */   }
/*     */   
/*     */   private void onCollisionHeroWithGround(Grounds ground, float deltaTime) {
/* 137 */     JohnHero hero = this.level.hero;
/* 138 */     float heightDifference = hero.position.y - hero.velocity.y * deltaTime - (ground.position.y + ground.bounds.height);
/* 139 */     if (heightDifference <= 0.0F) return;
/* 140 */     switch (hero.jumpState)
/*     */     {
    /*     */     case JUMP_RISING:
    /*     */       break;
    /*     */     case FALLING:
    /*     */     case JUMP_FALLING:
    /* 146 */       hero.position.y = (ground.position.y + ground.bounds.height);
    /* 147 */       hero.jumpState = JohnHero.JUMP_STATE.GROUNDED;
    /* 148 */       hero.myground = ground;
    /* 149 */       Gdx.app.debug(TAG, "GROUNDED! on: " + hero.myground);
    /* 150 */       break;
    /*     */     case GROUNDED:
    /* 152 */       hero.position.y = (ground.position.y + ground.bounds.height);
/*     */     }
/*     */   }
/*     */   
/*     */   private void onCollisionHeroWithGoldStar(GoldStar goldStar)
/*     */   {
/* 158 */     goldStar.collected = true;
/* 159 */     AudioManager.instance.play(Assets.instance.sounds.pickupCoin, 1.0F);
/* 160 */     this.score += goldStar.getScore();
/* 161 */     Gdx.app.log(TAG, "GoldStar collected!");
/*     */   }
/*     */   
/*     */   private void onCollisionHeroWithJetpack(Jetpack jetpack) {
/* 165 */     jetpack.collected = true;
/* 166 */     AudioManager.instance.play(Assets.instance.sounds.powerup);
/* 167 */     this.score += jetpack.getScore();
/* 168 */     this.level.hero.setJetpack(true);
/* 169 */     Gdx.app.log(TAG, "Jetpack collected!");
/*     */   }
/*     */   
/*     */   private void onCollisionHeroWithTotem() {
/* 173 */     this.goalReached = true;
/* 174 */     this.timeLeftGameOverDelay = 9.0F;
/* 175 */     Vector2 centerPosHero = new Vector2(this.level.hero.position);
/* 176 */     centerPosHero.x += this.level.hero.bounds.width / 2.0F;
/* 177 */     AudioManager.instance.play(Assets.instance.sounds.levelSuceeded);
/* 178 */     spawnBottles(centerPosHero, 100, 3.5F);
/*     */   }
/*     */   
/*     */   private void testCollisions(float deltaTime)
/*     */   {
/* 183 */     this.r1 = this.level.hero.bounds;
/*     */     
/*     */ 
/* 186 */     for (Grounds ground : this.level.grounds) {
/* 187 */       this.r2 = ground.bounds;
/* 188 */       if (!this.r1.overlaps(this.r2)) {
/* 189 */         ground.touched = false;
/*     */       }
/*     */       else {
/* 192 */         ground.touched = true;
/* 193 */         onCollisionHeroWithGround(ground, deltaTime);
/*     */       }
/*     */     }
/*     */     
/* 197 */     for (GoldStar goldStar : this.level.goldStars) {
/* 198 */       if (!goldStar.collected) {
/* 199 */         this.r2 = goldStar.bounds;
/* 200 */         if (this.r1.overlaps(this.r2)) {
/* 201 */           onCollisionHeroWithGoldStar(goldStar);
/*     */         }
/*     */       }
/*     */     }
/* 205 */     for (Jetpack jetpack : this.level.jetpacks) {
/* 206 */       if (!jetpack.collected) {
/* 207 */         this.r2 = jetpack.bounds;
/* 208 */         if (this.r1.overlaps(this.r2))
/* 209 */           onCollisionHeroWithJetpack(jetpack);
/*     */       }
/*     */     }
/* 212 */     if (!this.goalReached) {
/* 213 */       this.r2.set(this.level.totem.bounds);
/* 214 */       this.r2.x = this.level.totem.position.x;
/* 215 */       this.r2.y = this.level.totem.position.y;
/* 216 */       if (this.r1.overlaps(this.r2)) { onCollisionHeroWithTotem();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void handleGameInput(float deltaTime)
/*     */   {
/* 224 */     if (this.cont != null) {
/* 225 */       float x_factor = this.cont.getAxis(1);
/*     */       
/* 227 */       float speedMultiplier = this.cont.getButton(5) ? 1.0F : 2.0F;
/*     */       
/*     */ 
/* 230 */       if (Math.abs(x_factor) > 0.2D)
/*     */       {
/* 232 */         this.level.hero.velocity.x = (this.level.hero.terminalVelocity.x * x_factor / speedMultiplier);
/*     */       }
/* 234 */       if (this.cont.getButton(0))
/*     */       {
/* 236 */         this.level.hero.setJumping(true); } else {
/* 237 */         this.level.hero.setJumping(false);
/*     */       }
/*     */     }
              else {
                float x_factor = 0;
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                    x_factor = -1;
                else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                    x_factor = 1;
/*     */
/* 227 */       float speedMultiplier = Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) ? 1.0F : 2.0F;
/*     */
/*     */       this.level.hero.velocity.x = (this.level.hero.terminalVelocity.x * x_factor / speedMultiplier);
/* 234 */       if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
/*     */       {
/* 236 */         this.level.hero.setJumping(true); } else {
/* 237 */         this.level.hero.setJumping(false);
/*     */       }
              }
/*     */   }
/*     */   
/*     */   private void handleDebugInput(float deltaTime) {
/* 243 */     if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;
/* 244 */     float sprMoveSpeed = 5.0F * deltaTime;
/*     */     
/* 246 */     if (!this.cameraHelper.hasTarget(this.level.hero)) {
/* 247 */       float camMoveSpeed = 5.0F * deltaTime;
/* 248 */       float camMoveSpeedAcceleratorFactor = 5.0F;
/* 249 */       if (Gdx.input.isKeyPressed(59)) camMoveSpeed *= camMoveSpeedAcceleratorFactor;
/* 250 */       if (Gdx.input.isKeyPressed(21)) moveCamera(-camMoveSpeed, 0.0F);
/* 251 */       if (Gdx.input.isKeyPressed(22)) moveCamera(camMoveSpeed, 0.0F);
/* 252 */       if (Gdx.input.isKeyPressed(19)) moveCamera(0.0F, camMoveSpeed);
/* 253 */       if (Gdx.input.isKeyPressed(20)) moveCamera(0.0F, -camMoveSpeed);
/* 254 */       if (Gdx.input.isKeyPressed(67)) { this.cameraHelper.setPosition(0.0F, 0.0F);
/*     */       }
/*     */       
/* 257 */       float camZoomSpeed = 1.0F * deltaTime;
/* 258 */       float camZoomSpeedAcceleratorFactor = 5.0F;
/* 259 */       if (Gdx.input.isKeyPressed(60)) camZoomSpeed *= camZoomSpeedAcceleratorFactor;
/* 260 */       if (Gdx.input.isKeyPressed(44)) this.cameraHelper.addZoom(camZoomSpeed);
/* 261 */       if (Gdx.input.isKeyPressed(43)) this.cameraHelper.addZoom(-camZoomSpeed);
/* 262 */       if (Gdx.input.isKeyPressed(37)) { this.cameraHelper.setZoom(1.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void spawnBottles(Vector2 pos, int numBottles, float radius)
/*     */   {
/* 270 */     float bottleShapeScale = 0.5F;
/*     */     
/* 272 */     for (int i = 0; i < numBottles; i++) {
/* 273 */       Bottle bottle = new Bottle();
/* 274 */       float x = MathUtils.random(-radius, radius);
/* 275 */       float y = MathUtils.random(15.0F, 30.0F);
/* 276 */       float rotation = MathUtils.random(0.0F, 360.0F);
/* 277 */       float bottleScale = MathUtils.random(0.5F, 1.5F);
/* 278 */       bottle.scale.set(bottleScale, bottleScale);
/*     */       
/* 280 */       BodyDef bd = new BodyDef();
/* 281 */       bd.position.set(pos);
/* 282 */       bd.position.add(x, y);
/* 283 */       bd.angle = rotation;
/* 284 */       Body b = this.b2world.createBody(bd);
/* 285 */       b.setType(BodyDef.BodyType.DynamicBody);
/* 286 */       bottle.body = b;
/* 287 */       PolygonShape polygonShape = new PolygonShape();
/* 288 */       float halfWidth = bottle.bounds.width / 2.0F * bottleScale;
/* 289 */       float halfHeight = bottle.bounds.height / 2.0F * bottleScale;
/* 290 */       polygonShape.setAsBox(halfWidth * bottleShapeScale, halfHeight * bottleShapeScale);
/* 291 */       FixtureDef fixtureDef = new FixtureDef();
/* 292 */       fixtureDef.shape = polygonShape;
/* 293 */       fixtureDef.density = 50.0F;
/* 294 */       fixtureDef.restitution = 0.5F;
/* 295 */       fixtureDef.friction = 0.5F;
/* 296 */       b.createFixture(fixtureDef);
/* 297 */       polygonShape.dispose();
/* 298 */       this.level.bottles.add(bottle);
/*     */     }
/*     */   }
/*     */   
/*     */   private void moveCamera(float x, float y)
/*     */   {
/* 304 */     x += this.cameraHelper.getPosition().x;
/* 305 */     y += this.cameraHelper.getPosition().y;
/* 306 */     this.cameraHelper.setPosition(x, y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean keyUp(int keycode)
/*     */   {
/* 313 */     if (keycode == Input.Keys.R) {
/* 314 */       init();
/* 315 */       Gdx.app.debug(TAG, "Game world resetted");
/*     */ 
/*     */     }
/* 318 */     else if (keycode == Input.Keys.B) {
/* 319 */       onCollisionHeroWithTotem();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/* 326 */     else if (keycode == Input.Keys.C) {
/* 327 */       this.cameraHelper.setTarget(this.cameraHelper.hasTarget() ? null : this.level.hero);
/* 328 */       Gdx.app.debug(TAG, "Camera follow enabled: " + this.cameraHelper.hasTarget());
/*     */     }
/* 330 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isGameOver() {
/* 334 */     return this.lives < 0;
/*     */   }
/*     */   
/*     */   public boolean isPlayeInWater() {
/* 338 */     return this.level.hero.position.y < -1.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\WorldController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */