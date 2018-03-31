/*     */ package com.me.johnspace.game.objects;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.graphics.Pixmap;
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.badlogic.gdx.utils.Array;
/*     */ 
/*     */ public class Level
/*     */ {
/*  10 */   public static final String TAG = Level.class.getName();
/*     */   public Array<Grounds> grounds;
/*     */   
/*  13 */   public static enum BLOCK_TYPE { EMPTY(0, 0, 0), 
/*  14 */     GROUND(0, 255, 0), 
/*  15 */     PLAYER_SPAWNPOINT(255, 255, 255), 
/*  16 */     ITEM_JETPACK(255, 0, 255), 
/*  17 */     ITEM_GOLD_STAR(255, 255, 0), 
/*  18 */     GOAL(255, 0, 0);
/*     */     
/*     */     private int color;
/*     */     
/*     */     private BLOCK_TYPE(int r, int g, int b) {
/*  23 */       this.color = (r << 24 | g << 16 | b << 8 | 0xFF);
/*     */     }
/*     */     
/*     */     public boolean sameColor(int color) {
/*  27 */       return this.color == color;
/*     */     }
/*     */     
/*     */     public int getColor() {
/*  31 */       return this.color;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public JohnHero hero;
/*     */   
/*     */   public Array<GoldStar> goldStars;
/*     */   
/*     */   public Array<Jetpack> jetpacks;
/*     */   
/*     */   public Array<Bottle> bottles;
/*     */   
/*     */   public Totem totem;
/*     */   
/*     */   public Clouds clouds;
/*     */   public Mountains mountains;
/*     */   public WaterOverlay waterOverlay;
/*     */   public Level(String filename)
/*     */   {
/*  51 */     init(filename);
/*     */   }
/*     */   
/*     */   private void init(String filename)
/*     */   {
/*  56 */     this.grounds = new Array();
/*  57 */     this.goldStars = new Array();
/*  58 */     this.jetpacks = new Array();
/*  59 */     this.bottles = new Array();
/*  60 */     this.hero = null;
/*     */     
/*     */ 
/*  63 */     Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
/*     */     
/*  65 */     int lastPixel = -1;
/*  66 */     for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
/*  67 */       for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
/*  68 */         AbstractGameObject obj = null;
/*  69 */         float offsetHeight = 0.0F;
/*  70 */         float offsetWidth = 0.0F;
/*     */         
/*  72 */         float baseHeight = pixmap.getHeight() - pixelY;
/*     */         
/*  74 */         int currentPixel = pixmap.getPixel(pixelX, pixelY);
/*     */         
/*  76 */         if (!BLOCK_TYPE.EMPTY.sameColor(currentPixel))
/*     */         {
/*     */ 
/*     */ 
/*  80 */           if (BLOCK_TYPE.GROUND.sameColor(currentPixel)) {
/*  81 */             if (lastPixel != currentPixel) {
/*  82 */               obj = new Grounds();
/*  83 */               float heightIncreaseFactor = 1.0F;
/*  84 */               offsetHeight = 0.0F;
/*  85 */               obj.position.set(pixelX, baseHeight * heightIncreaseFactor + offsetHeight - obj.dimension.y);
/*  86 */               this.grounds.add((Grounds)obj);
/*     */             } else {
/*  88 */               ((Grounds)this.grounds.get(this.grounds.size - 1)).increaseLength(1);
/*     */             }
/*     */             
/*     */           }
/*  92 */           else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {
/*  93 */             obj = new JohnHero();
/*  94 */             offsetHeight = 0.0F;
/*  95 */             float heightIncreaseFactor = 1.0F;
/*  96 */             obj.position.set(pixelX, baseHeight * heightIncreaseFactor + offsetHeight);
/*  97 */             this.hero = ((JohnHero)obj);
/*     */ 
/*     */           }
/* 100 */           else if (BLOCK_TYPE.ITEM_JETPACK.sameColor(currentPixel)) {
/* 101 */             obj = new Jetpack();
/* 102 */             offsetHeight = -1.0F;
/* 103 */             offsetWidth = (1.0F - obj.dimension.x) / 2.0F;
/* 104 */             float heightIncreaseFactor = 1.0F;
/* 105 */             obj.position.set(pixelX, baseHeight * heightIncreaseFactor + offsetHeight);
/* 106 */             this.jetpacks.add((Jetpack)obj);
/*     */ 
/*     */           }
/* 109 */           else if (BLOCK_TYPE.ITEM_GOLD_STAR.sameColor(currentPixel)) {
/* 110 */             obj = new GoldStar();
/* 111 */             offsetHeight = -0.5F;
/* 112 */             offsetWidth = (1.0F - obj.dimension.x) / 2.0F;
/* 113 */             float heightIncreaseFactor = 1.0F;
/* 114 */             obj.position.set(pixelX, baseHeight * heightIncreaseFactor + offsetHeight);
/* 115 */             this.goldStars.add((GoldStar)obj);
/*     */ 
/*     */           }
/* 118 */           else if (BLOCK_TYPE.GOAL.sameColor(currentPixel)) {
/* 119 */             obj = new Totem();
/* 120 */             offsetHeight = -1.2F;
/*     */             
/* 122 */             obj.position.set(pixelX, baseHeight + offsetHeight);
/* 123 */             this.totem = ((Totem)obj);
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 128 */             int r = 0xFF & currentPixel >>> 24;
/*     */             
/* 130 */             int g = 0xFF & currentPixel >>> 16;
/*     */             
/* 132 */             int b = 0xFF & currentPixel >>> 8;
/*     */             
/* 134 */             int a = 0xFF & currentPixel;
/* 135 */             Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" + g + "> b<" + b + 
/* 136 */               "> a<" + a + ">");
/*     */           } }
/* 138 */         lastPixel = currentPixel;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 143 */     this.clouds = new Clouds(pixmap.getWidth(), 10.0F);
/* 144 */     this.mountains = new Mountains(pixmap.getWidth());
/* 145 */     this.mountains.position.set(-1.0F, 3.5F);
/* 146 */     this.waterOverlay = new WaterOverlay(pixmap.getWidth());
/* 147 */     this.waterOverlay.position.set(0.0F, -0.5F);
/*     */     
/*     */ 
/* 150 */     pixmap.dispose();
/* 151 */     Gdx.app.debug(TAG, "level '" + filename + "' loaded");
/*     */   }
/*     */   
/*     */   public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch)
/*     */   {
/* 156 */     this.mountains.render(batch);
/*     */     
/* 158 */     this.clouds.render(batch);
/*     */     
/* 160 */     this.totem.render(batch);
/* 161 */     for (Grounds ground : this.grounds)
/* 162 */       ground.render(batch);
/* 163 */     for (GoldStar goldStar : this.goldStars)
/* 164 */       goldStar.render(batch);
/* 165 */     for (Jetpack jetpack : this.jetpacks)
/* 166 */       jetpack.render(batch);
/* 167 */     for (Bottle bottle : this.bottles)
/* 168 */       bottle.render(batch);
/* 169 */     this.hero.render(batch);
/*     */     
/* 171 */     this.waterOverlay.render(batch);
/*     */   }
/*     */   
/*     */ 
/*     */   public void update(float deltaTime)
/*     */   {
/* 177 */     this.hero.update(deltaTime);
/* 178 */     for (Grounds ground : this.grounds)
/* 179 */       ground.update(deltaTime);
/* 180 */     for (GoldStar goldStar : this.goldStars)
/* 181 */       goldStar.update(deltaTime);
/* 182 */     for (Jetpack jetpack : this.jetpacks)
/* 183 */       jetpack.update(deltaTime);
/* 184 */     for (Bottle bottle : this.bottles)
/* 185 */       bottle.update(deltaTime);
/* 186 */     this.clouds.update(deltaTime);
/*     */   }
/*     */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\objects\Level.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */