/*     */ package com.me.johnspace.game.objects;
/*     */ 
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.me.johnspace.game.Assets;
/*     */ import com.me.johnspace.game.Assets.AssetGround;
/*     */ 
/*     */ public class Grounds extends AbstractGameObject
/*     */ {
/*     */   private TextureRegion regBorder;
/*     */   private TextureRegion regMiddle;
/*     */   private TextureRegion repBorder;
/*     */   private TextureRegion repMiddle;
/*     */   public boolean touched;
/*  16 */   private final float FLOAT_CYCLE_TIME = 2.0F;
/*  17 */   private final float FLOAT_AMPLITUDE = 0.25F;
/*     */   
/*     */   private float floatCycleTimeLeft;
/*     */   private boolean floatingDownwards;
/*     */   private Vector2 floatTargetPosition;
/*     */   
/*     */   public Grounds()
/*     */   {
/*  25 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  29 */     this.touched = false;
/*  30 */     this.dimension.set(1.0F, 4.0F);
/*  31 */     this.regBorder = Assets.instance.ground.border_up;
/*  32 */     this.regMiddle = Assets.instance.ground.middle_up;
/*  33 */     this.repBorder = Assets.instance.ground.border_rep;
/*  34 */     this.repMiddle = Assets.instance.ground.middle_rep;
/*     */     
/*  36 */     this.floatingDownwards = false;
/*  37 */     this.floatCycleTimeLeft = com.badlogic.gdx.math.MathUtils.random(0.0F, 1.0F);
/*  38 */     this.floatTargetPosition = null;
/*     */   }
/*     */   
/*     */   public void setLength(int length)
/*     */   {
/*  43 */     this.repeat_x = length;
/*  44 */     this.bounds.set(this.position.x, this.position.y, this.dimension.x * this.repeat_x, this.dimension.y * this.repeat_y);
/*     */   }
/*     */   
/*     */   public void increaseLength(int amount) {
/*  48 */     setLength(this.repeat_x + amount);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void render(SpriteBatch batch)
/*     */   {
/*  68 */     TextureRegion reg = null;
/*  69 */     float relX = 0.0F;
/*  70 */     float relY = 0.0F;
/*     */     
/*  72 */     reg = this.regBorder;
/*  73 */     relX -= this.dimension.x / 4.0F;
/*  74 */     batch.draw(reg.getTexture(), 
/*  75 */       this.position.x + relX, this.position.y + relY, 
/*  76 */       this.origin.x, this.origin.y, this.dimension.x / 4.0F, this.dimension.y, 
/*  77 */       this.scale.x, this.scale.y, 
/*  78 */       this.rotation, 
/*  79 */       reg.getRegionX(), reg.getRegionY(), 
/*  80 */       reg.getRegionWidth(), reg.getRegionHeight(), 
/*  81 */       false, false);
/*     */     
/*  83 */     reg = this.regMiddle;
/*  84 */     relX = 0.0F;
/*  85 */     if (this.touched) batch.setColor(0.8F, 0.2F, 0.2F, 1.0F);
/*  86 */     for (int i = 0; i < this.repeat_x; i++) {
/*  87 */       batch.draw(reg.getTexture(), 
/*  88 */         this.position.x + relX, this.position.y + relY, 
/*  89 */         this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, 
/*  90 */         this.scale.x, this.scale.y, 
/*  91 */         this.rotation, 
/*  92 */         reg.getRegionX(), reg.getRegionY(), 
/*  93 */         reg.getRegionWidth(), reg.getRegionHeight(), 
/*  94 */         false, false);
/*  95 */       relX += this.dimension.x;
/*     */     }
/*     */     
/*     */ 
/*  99 */     batch.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*     */ 
/* 102 */     reg = this.regBorder;
/* 103 */     batch.draw(reg.getTexture(), 
/* 104 */       this.position.x + relX, this.position.y + relY, 
/* 105 */       this.origin.x, this.origin.y, 
/* 106 */       this.dimension.x / 4.0F, this.dimension.y, 
/* 107 */       this.scale.x, this.scale.y, 
/* 108 */       this.rotation, 
/* 109 */       reg.getRegionX(), reg.getRegionY(), 
/* 110 */       reg.getRegionWidth(), reg.getRegionHeight(), 
/* 111 */       true, false);
/*     */     
/* 113 */     float altitude = this.position.y;
/* 114 */     while (altitude > 0.0F) {
/* 115 */       altitude -= 1.0F * this.dimension.y;
/* 116 */       reg = null;
/* 117 */       relX = 0.0F;
/* 118 */       relY = 0.0F;
/*     */       
/* 120 */       reg = this.repBorder;
/* 121 */       relX -= this.dimension.x / 4.0F;
/* 122 */       batch.draw(reg.getTexture(), 
/* 123 */         this.position.x + relX, altitude + relY, 
/* 124 */         this.origin.x, this.origin.y, this.dimension.x / 4.0F, this.dimension.y, 
/* 125 */         this.scale.x, this.scale.y, 
/* 126 */         this.rotation, 
/* 127 */         reg.getRegionX(), reg.getRegionY(), 
/* 128 */         reg.getRegionWidth(), reg.getRegionHeight(), 
/* 129 */         false, false);
/*     */       
/* 131 */       reg = this.repMiddle;
/* 132 */       relX = 0.0F;
/* 133 */       for (int i = 0; i < this.repeat_x; i++) {
/* 134 */         batch.draw(reg.getTexture(), 
/* 135 */           this.position.x + relX, altitude + relY, 
/* 136 */           this.origin.x, this.origin.y, this.dimension.x, this.dimension.y, 
/* 137 */           this.scale.x, this.scale.y, 
/* 138 */           this.rotation, 
/* 139 */           reg.getRegionX(), reg.getRegionY(), 
/* 140 */           reg.getRegionWidth(), reg.getRegionHeight(), 
/* 141 */           false, false);
/* 142 */         relX += this.dimension.x;
/*     */       }
/*     */       
/*     */ 
/* 146 */       reg = this.repBorder;
/* 147 */       batch.draw(reg.getTexture(), 
/* 148 */         this.position.x + relX, altitude + relY, 
/* 149 */         this.origin.x, this.origin.y, 
/* 150 */         this.dimension.x / 4.0F, this.dimension.y, 
/* 151 */         this.scale.x, this.scale.y, 
/* 152 */         this.rotation, 
/* 153 */         reg.getRegionX(), reg.getRegionY(), 
/* 154 */         reg.getRegionWidth(), reg.getRegionHeight(), 
/* 155 */         true, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\objects\Grounds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */