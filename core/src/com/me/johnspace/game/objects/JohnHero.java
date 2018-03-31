/*     */ package com.me.johnspace.game.objects;
/*     */ 
/*     */ import com.badlogic.gdx.graphics.g2d.ParticleEffect;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.badlogic.gdx.math.Rectangle;
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.me.johnspace.game.Assets;
/*     */ import com.me.johnspace.util.AudioManager;
/*     */ 
/*     */ public class JohnHero extends AbstractGameObject
/*     */ {
/*  12 */   public static final String TAG = JohnHero.class.getName();
/*     */   
/*  14 */   private final float JUMP_TIME_MAX = 0.3F;
/*  15 */   private final float JUMP_TIME_MIN = 0.1F;
/*  16 */   private final float JUMP_TIME_OFFSET_FLYING = -1.0F;
/*     */   
/*  18 */   public static enum VIEW_DIRECTION { LEFT,  RIGHT; }
/*  19 */   public static enum JUMP_STATE { GROUNDED,  FALLING,  JUMP_RISING,  JUMP_FALLING;
/*     */   }
/*     */   
/*     */   public Grounds myground;
/*     */   public VIEW_DIRECTION viewDirection;
/*     */   public float timeJumping;
/*     */   public JUMP_STATE jumpState;
/*     */   public boolean hasJetpack;
/*     */   public float fuelLeftJetpack;
/*  28 */   public ParticleEffect dustParticles = new ParticleEffect();
/*  29 */   public ParticleEffect jetPackParticles = new ParticleEffect();
/*     */   
/*     */   private com.badlogic.gdx.graphics.g2d.Animation animWalk;
/*     */   private com.badlogic.gdx.graphics.g2d.Animation animNormal;
/*     */   private com.badlogic.gdx.graphics.g2d.Animation animJump;
/*     */   private com.badlogic.gdx.graphics.g2d.Animation animFalling;
/*     */   private long IDloopJetpack;
/*     */   private boolean loopJetpack;
/*     */   
/*     */   public JohnHero()
/*     */   {
/*  40 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  44 */     this.dimension.set(1.0F, 1.0F);
/*     */     
/*  46 */     this.origin.set(this.dimension.x / 2.0F, this.dimension.y / 2.0F);
/*  47 */     this.terminalVelocity.set(6.0F, 16.0F);
/*  48 */     this.friction.set(12.0F, 0.0F);
/*  49 */     this.acceleration.set(0.0F, -25.0F);
/*  50 */     this.viewDirection = VIEW_DIRECTION.RIGHT;
/*  51 */     this.jumpState = JUMP_STATE.FALLING;
/*  52 */     this.timeJumping = 0.0F;
/*     */     
/*  54 */     this.hasJetpack = false;
/*  55 */     this.fuelLeftJetpack = 0.0F;
/*     */     
/*  57 */     this.dustParticles.load(com.badlogic.gdx.Gdx.files.internal("particles/dust_stop.p"), com.badlogic.gdx.Gdx.files.internal("particles"));
/*  58 */     this.jetPackParticles.load(com.badlogic.gdx.Gdx.files.internal("particles/firebigbig.p"), com.badlogic.gdx.Gdx.files.internal("particles"));
/*     */     
/*  60 */     this.animWalk = Assets.instance.hero.animWalk;
/*  61 */     this.animNormal = Assets.instance.hero.animNormal;
/*  62 */     this.animJump = Assets.instance.hero.animJumpRising;
/*  63 */     this.animFalling = Assets.instance.hero.animFalling;
/*  64 */     setAnimation(this.animNormal);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setJumping(boolean jumpKeyPressed)
/*     */   {
/*  70 */     switch (this.jumpState) {
/*     */     case GROUNDED:
/*  72 */       if (jumpKeyPressed) {
/*  73 */         AudioManager.instance.play(Assets.instance.sounds.jump);
/*  74 */         this.timeJumping = 0.0F;
/*  75 */         this.jumpState = JUMP_STATE.JUMP_RISING;
/*     */       }
/*     */       
/*  78 */       break;
/*     */     case JUMP_RISING:
/*  80 */       if (!jumpKeyPressed) {
/*  81 */         this.jumpState = JUMP_STATE.JUMP_FALLING;
/*     */       }
/*     */       
/*  84 */       break;
/*     */     case JUMP_FALLING:
/*     */     case FALLING:
/*  87 */       if ((jumpKeyPressed) && (this.hasJetpack)) {
/*  88 */         this.timeJumping = -1.0F;
/*  89 */         this.jumpState = JUMP_STATE.JUMP_RISING;
/*     */       }
/*     */       break;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setJetpack(boolean pickedUp)
/*     */   {
/*  97 */     this.hasJetpack = pickedUp;
/*  98 */     if (pickedUp) {
/*  99 */       this.fuelLeftJetpack = 60.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasJetpack() {
/* 104 */     return (this.hasJetpack) && (this.fuelLeftJetpack > 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void update(float deltaTime)
/*     */   {
/* 111 */     if (this.velocity.x != 0.0F) {
/* 112 */       this.viewDirection = (this.velocity.x < 0.0F ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT);
/*     */     }
/*     */     
/* 115 */     super.update(deltaTime);
/* 116 */     float jet_pos = this.viewDirection == VIEW_DIRECTION.LEFT ? this.dimension.x - 0.2F : 0.2F;
/* 117 */     this.jetPackParticles.setPosition(this.position.x + jet_pos, this.position.y + this.dimension.y / 3.0F);
/* 118 */     this.jetPackParticles.update(deltaTime);
/* 119 */     this.dustParticles.update(deltaTime);
/* 120 */     if (this.fuelLeftJetpack > 0.0F) {
/* 121 */       this.fuelLeftJetpack -= deltaTime;
/* 122 */       if (this.fuelLeftJetpack < 0.0F)
/*     */       {
/* 124 */         this.fuelLeftJetpack = 0.0F;
/* 125 */         setJetpack(false);
/*     */       }
/*     */     } else {
/* 128 */       this.jetPackParticles.allowCompletion();
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateMotionY(float deltaTime)
/*     */   {
/* 134 */     switch (this.jumpState) {
/*     */     case GROUNDED:
/* 136 */       this.jetPackParticles.allowCompletion();
/* 137 */       this.dustParticles.setPosition(this.position.x + this.dimension.x / 2.0F, this.position.y);
/* 138 */       if (this.velocity.y < 0.0F) {
/* 139 */         this.dustParticles.start();
/* 140 */         com.badlogic.gdx.Gdx.app.debug(TAG, "dust and step sound");
/* 141 */         AudioManager.instance.play(Assets.instance.sounds.grounded);
/*     */       }
/* 143 */       this.velocity.y = 0.0F;
/* 144 */       if (this.velocity.x != 0.0F) {
/* 145 */         if (this.animation != this.animWalk) { setAnimation(this.animWalk);
/*     */         }
/*     */       }
/* 148 */       else if (this.animation != this.animNormal) { setAnimation(this.animNormal);
/*     */       }
/* 151 */       if ((this.bounds.x > this.myground.bounds.x + this.myground.bounds.width) || (this.bounds.x + this.bounds.width < this.myground.bounds.x))
/*     */       {
/* 153 */         this.jumpState = JUMP_STATE.FALLING;
/*     */       }
/* 155 */       this.position.y = (this.myground.position.y + this.myground.dimension.y);
/* 156 */       break;
/*     */     case JUMP_RISING:
/* 158 */       if (this.animation != this.animJump) setAnimation(this.animJump);
/* 159 */       this.jetPackParticles.findEmitter("fire").getEmission().setHigh(250.0F);
/* 160 */       if ((!this.loopJetpack) && (this.hasJetpack)) {
/* 161 */         this.IDloopJetpack = AudioManager.instance.loop(Assets.instance.sounds.jumpWithJetpack);
/* 162 */         this.loopJetpack = true;
/*     */       }
/* 164 */       this.timeJumping += deltaTime;
/* 165 */       if (this.timeJumping <= 0.3F)
/*     */       {
/* 167 */         this.velocity.y = (this.terminalVelocity.y / 2.0F);
/*     */       }
/*     */       else
/* 170 */         this.jumpState = JUMP_STATE.JUMP_FALLING;
/* 171 */       break;
/*     */     case FALLING:
/* 173 */       if (this.animation != this.animFalling) setAnimation(this.animFalling);
/* 174 */       this.jetPackParticles.findEmitter("fire").getEmission().setHigh(50.0F);
/* 175 */       if (this.loopJetpack) {
/* 176 */         AudioManager.instance.stop(Assets.instance.sounds.jumpWithJetpack, this.IDloopJetpack);
/* 177 */         this.loopJetpack = false;
/*     */       }
/* 179 */       break;
/*     */     case JUMP_FALLING:
/* 181 */       if (this.animation != this.animFalling) setAnimation(this.animFalling);
/* 182 */       this.jetPackParticles.findEmitter("fire").getEmission().setHigh(50.0F);
/* 183 */       if (this.loopJetpack) {
/* 184 */         AudioManager.instance.stop(Assets.instance.sounds.jumpWithJetpack, this.IDloopJetpack);
/* 185 */         this.loopJetpack = false;
/*     */       }
/* 187 */       this.timeJumping += deltaTime;
/* 188 */       if ((this.timeJumping > 0.0F) && (this.timeJumping <= 0.1F) && 
/* 189 */         (!this.hasJetpack)) { this.velocity.y = (this.terminalVelocity.y / 2.0F);
/*     */       }
/*     */       break;
/*     */     }
/* 193 */     if (this.jumpState != JUMP_STATE.GROUNDED) {
/* 194 */       if (this.hasJetpack) {
/* 195 */         this.jetPackParticles.start();
/*     */       }
/*     */       
/*     */ 
/* 199 */       super.updateMotionY(deltaTime);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch)
/*     */   {
/* 207 */     TextureRegion reg = null;
/* 208 */     if (this.hasJetpack) {
/* 209 */       batch.setColor(1.0F, 0.8F, 0.0F, 1.0F);
/*     */     }
/*     */     
/* 212 */     reg = this.animation.getKeyFrame(this.stateTime, true);
/* 213 */     batch.draw(reg.getTexture(), 
/* 214 */       this.position.x, this.position.y, 
/* 215 */       this.origin.x, this.origin.y, 
/* 216 */       this.dimension.x, this.dimension.y, 
/* 217 */       this.scale.x, this.scale.y, 
/* 218 */       this.rotation, 
/* 219 */       reg.getRegionX(), reg.getRegionY(), 
/* 220 */       reg.getRegionWidth(), reg.getRegionHeight(), 
/* 221 */       this.viewDirection == VIEW_DIRECTION.LEFT, false);
/* 222 */     batch.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 223 */     this.dustParticles.draw(batch);
/* 224 */     this.jetPackParticles.draw(batch);
/*     */   }
/*     */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\objects\JohnHero.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */