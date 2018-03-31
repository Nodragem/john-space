/*     */ package com.me.johnspace.game;
/*     */ 
/*     */ import com.badlogic.gdx.Application;
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.assets.AssetDescriptor;
/*     */ import com.badlogic.gdx.assets.AssetErrorListener;
/*     */ import com.badlogic.gdx.assets.AssetManager;
/*     */ import com.badlogic.gdx.audio.Music;
/*     */ import com.badlogic.gdx.audio.Sound;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.graphics.Texture.TextureFilter;
/*     */ import com.badlogic.gdx.graphics.g2d.Animation;
/*     */ import com.badlogic.gdx.graphics.g2d.BitmapFont;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureAtlas;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.badlogic.gdx.utils.Array;
/*     */ 
/*     */ public class Assets implements com.badlogic.gdx.utils.Disposable, AssetErrorListener
/*     */ {
/*  21 */   public static final String TAG = Assets.class.getName();
/*  22 */   public static final Assets instance = new Assets();
/*     */   private AssetManager assetManager;
/*     */   public AssetHero hero;
/*     */   public AssetGround ground;
/*     */   public AssetStar star;
/*     */   public AssetBox box;
/*     */   public AssetLevelDecoration levelDecoration;
/*     */   public AssetFonts fonts;
/*     */   public AssetSounds sounds;
/*     */   public AssetMusic music;
/*     */   
/*     */   public class AssetSounds
/*     */   {
/*     */     public final Sound jump;
/*     */     public final Sound grounded;
/*     */     public final Sound jumpWithJetpack;
/*     */     public final Sound pickupCoin;
/*     */     public final Sound powerup;
/*     */     public final Sound liveLost;
/*     */     public final Sound gameover;
/*     */     public final Sound levelSuceeded;
/*  43 */     public final Array<Sound> atmosphere = null;
/*     */     
/*     */     public AssetSounds(AssetManager am) {
/*  46 */       this.jump = ((Sound)am.get("sounds/jump.ogg", Sound.class));
/*  47 */       this.grounded = ((Sound)am.get("sounds/grounded2.ogg", Sound.class));
/*  48 */       this.jumpWithJetpack = ((Sound)am.get("sounds/jetpack.ogg", Sound.class));
/*  49 */       this.pickupCoin = ((Sound)am.get("sounds/pickup_coin.ogg", Sound.class));
/*  50 */       this.powerup = ((Sound)am.get("sounds/powerup.ogg", Sound.class));
/*  51 */       this.liveLost = ((Sound)am.get("sounds/live_lost.ogg", Sound.class));
/*  52 */       this.gameover = ((Sound)am.get("sounds/game_over.ogg", Sound.class));
/*  53 */       this.levelSuceeded = ((Sound)am.get("sounds/level_suceeded.ogg", Sound.class));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public class AssetMusic
/*     */   {
/*     */     public final Music themeSong;
/*     */     
/*     */ 
/*     */ 
/*     */     public AssetMusic(AssetManager am)
/*     */     {
/*  68 */       this.themeSong = ((Music)am.get("music/iron_man.mp3", Music.class));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void init(AssetManager assetManager)
/*     */   {
/*  76 */     this.assetManager = assetManager;
/*  77 */     assetManager.setErrorListener(this);
/*  78 */     assetManager.load("ld/trainingplatformer.pack", TextureAtlas.class);
/*  79 */     assetManager.load("sounds/jump.ogg", Sound.class);
/*  80 */     assetManager.load("sounds/grounded2.ogg", Sound.class);
/*  81 */     assetManager.load("sounds/jetpack.ogg", Sound.class);
/*  82 */     assetManager.load("sounds/pickup_coin.ogg", Sound.class);
/*  83 */     assetManager.load("sounds/powerup.ogg", Sound.class);
/*  84 */     assetManager.load("sounds/live_lost.ogg", Sound.class);
/*  85 */     assetManager.load("sounds/game_over.ogg", Sound.class);
/*  86 */     assetManager.load("sounds/level_suceeded.ogg", Sound.class);
/*  87 */     assetManager.load("sounds/wind1.ogg", Sound.class);
/*  88 */     assetManager.load("sounds/wind2.ogg", Sound.class);
/*  89 */     assetManager.load("sounds/wind3.ogg", Sound.class);
/*  90 */     assetManager.load("sounds/wavesea1.ogg", Sound.class);
/*  91 */     assetManager.load("sounds/wavesea2.ogg", Sound.class);
/*  92 */     assetManager.load("sounds/bird.ogg", Sound.class);
/*     */     
/*  94 */     assetManager.load("music/iron_man.mp3", Music.class);
/*  95 */     assetManager.finishLoading();
/*     */     
/*  97 */     Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
/*  98 */     for (String a : assetManager.getAssetNames()) {
/*  99 */       Gdx.app.debug(TAG, "asset: " + a);
/*     */     }
/*     */     
/* 102 */     TextureAtlas atlas = (TextureAtlas)assetManager.get("ld/trainingplatformer.pack");
/*     */     
/* 104 */     for (Texture t : atlas.getTextures()) {
/* 105 */       t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
/*     */     }
/*     */     
/* 108 */     this.fonts = new AssetFonts();
/* 109 */     this.hero = new AssetHero(atlas);
/* 110 */     this.ground = new AssetGround(atlas);
/* 111 */     this.star = new AssetStar(atlas);
/* 112 */     this.box = new AssetBox(atlas);
/* 113 */     this.levelDecoration = new AssetLevelDecoration(atlas);
/* 114 */     this.sounds = new AssetSounds(assetManager);
/* 115 */     this.music = new AssetMusic(assetManager);
/*     */   }
/*     */   
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 121 */     this.assetManager.dispose();
/* 122 */     this.fonts.defaultSmall.dispose();
/* 123 */     this.fonts.defaultNormal.dispose();
/* 124 */     this.fonts.defaultBig.dispose();
/*     */   }
/*     */   
/*     */   public void error(AssetDescriptor asset, Throwable throwable)
/*     */   {
/* 129 */     Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
/*     */   }
/*     */   
/*     */   public class AssetHero
/*     */   {
/*     */     public final TextureAtlas.AtlasRegion john;
/*     */     public final Animation animWalk;
/*     */     public final Animation animNormal;
/*     */     public final Animation animJumpRising;
/*     */     public final Animation animFalling;
/*     */     
/*     */     public AssetHero(TextureAtlas atlas) {
/* 141 */       this.john = atlas.findRegion("john");
/* 142 */       Array<TextureAtlas.AtlasRegion> regions = null;
/* 143 */       TextureAtlas.AtlasRegion region = null;
/*     */       
/* 145 */       regions = atlas.findRegions("stand");
/* 146 */       this.animNormal = new Animation(0.5F, regions, Animation.PlayMode.LOOP);
/*     */       
/* 148 */       regions = atlas.findRegions("walk");
/* 149 */       this.animWalk = new Animation(0.083333336F, regions, Animation.PlayMode.LOOP);
/* 150 */       regions = atlas.findRegions("jump");
/* 151 */       this.animJumpRising = new Animation(0.083333336F, regions, Animation.PlayMode.LOOP);
/* 152 */       regions = atlas.findRegions("falling");
/* 153 */       this.animFalling = new Animation(0.083333336F, regions, Animation.PlayMode.LOOP);
/*     */       
/*     */ 
/* 156 */       Gdx.app.debug(Assets.TAG, "Frame duration: " + this.animWalk.getAnimationDuration());
/* 158 */       Gdx.app.debug(Assets.TAG, "Frame duration: " + this.animWalk.getKeyFrameIndex(0.1F));
/*     */     }
/*     */   }
/*     */   
/*     */   public class AssetGround {
/*     */     public final TextureAtlas.AtlasRegion border_up;
/*     */     public final TextureAtlas.AtlasRegion middle_up;
/*     */     public final TextureAtlas.AtlasRegion border_rep;
/*     */     public final TextureAtlas.AtlasRegion middle_rep;
/*     */     
/* 168 */     public AssetGround(TextureAtlas atlas) { this.border_up = atlas.findRegion("border_up");
/* 169 */       this.middle_up = atlas.findRegion("ground_up");
/* 170 */       this.border_rep = atlas.findRegion("border_rep");
/* 171 */       this.middle_rep = atlas.findRegion("ground_rep");
/*     */     }
/*     */   }
/*     */   
/*     */   public class AssetStar {
/*     */     public final Animation animStar;
/*     */     public final TextureAtlas.AtlasRegion star1;
/*     */     
/* 179 */     public AssetStar(TextureAtlas atlas) { Array<TextureAtlas.AtlasRegion> regions = null;
/* 180 */       TextureAtlas.AtlasRegion region = null;
/*     */       
/* 182 */       regions = atlas.findRegions("etoile");
/* 183 */       this.animStar = new Animation(0.2F, regions, Animation.PlayMode.LOOP_RANDOM);
/* 184 */       this.star1 = atlas.findRegion("etoile");
/*     */     }
/*     */   }
/*     */   
/*     */   public class AssetBox {
/*     */     public final TextureAtlas.AtlasRegion box;
/*     */     
/* 191 */     public AssetBox(TextureAtlas atlas) { this.box = atlas.findRegion("boite"); }
/*     */   }
/*     */   
/*     */ 
/*     */   public class AssetLevelDecoration
/*     */   {
/*     */     public final TextureAtlas.AtlasRegion cloud1;
/*     */     public final TextureAtlas.AtlasRegion cloud2;
/*     */     public final TextureAtlas.AtlasRegion cloud3;
/*     */     public final TextureAtlas.AtlasRegion herb1depth1;
/*     */     public final TextureAtlas.AtlasRegion herb1depth2;
/*     */     public final TextureAtlas.AtlasRegion herb1depth3;
/*     */     public final TextureAtlas.AtlasRegion herb2depth1;
/*     */     public final TextureAtlas.AtlasRegion herb2depth2;
/*     */     public final TextureAtlas.AtlasRegion herb2depth3;
/*     */     public final TextureAtlas.AtlasRegion water;
/*     */     public final TextureAtlas.AtlasRegion bottle;
/*     */     public final TextureAtlas.AtlasRegion totem;
/*     */     
/*     */     public AssetLevelDecoration(TextureAtlas atlas)
/*     */     {
/* 212 */       this.cloud1 = atlas.findRegion("cloud1");
/* 213 */       this.cloud2 = atlas.findRegion("cloud2");
/* 214 */       this.cloud3 = atlas.findRegion("cloud3");
/* 215 */       this.herb1depth1 = atlas.findRegion("herb1_depth1");
/* 216 */       this.herb1depth2 = atlas.findRegion("herb1_depth2");
/* 217 */       this.herb1depth3 = atlas.findRegion("herb1_depth3");
/* 218 */       this.herb2depth1 = atlas.findRegion("herb2_depth1");
/* 219 */       this.herb2depth2 = atlas.findRegion("herb2_depth2");
/* 220 */       this.herb2depth3 = atlas.findRegion("herb2_depth3");
/* 221 */       this.water = atlas.findRegion("eau_simple");
/* 222 */       this.bottle = atlas.findRegion("bottle");
/* 223 */       this.totem = atlas.findRegion("totem");
/*     */     }
/*     */   }
/*     */   
/*     */   public class AssetFonts
/*     */   {
/*     */     public final BitmapFont defaultSmall;
/*     */     public final BitmapFont defaultNormal;
/*     */     public final BitmapFont defaultBig;
/*     */     
/*     */     public AssetFonts()
/*     */     {
/* 235 */       this.defaultSmall = new BitmapFont(true);
/* 236 */       this.defaultNormal = new BitmapFont(true);
/* 237 */       this.defaultBig = new BitmapFont(true);
/*     */       
/* 239 */       this.defaultSmall.setScale(0.75F);
/* 240 */       this.defaultNormal.setScale(1.0F);
/* 241 */       this.defaultBig.setScale(2.0F);
/*     */       
/* 243 */       this.defaultSmall.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
/* 244 */       this.defaultNormal.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
/* 245 */       this.defaultBig.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\geoff\Downloads\johnspace.jar!\com\me\trainingplatformer\game\Assets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */