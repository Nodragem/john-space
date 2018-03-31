/*    */ package com.johnspace.game.desktop;
/*    */
/*    */ import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
/*    */ import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
/*    */ import com.badlogic.gdx.tools.texturepacker.TexturePacker;
/*    */ import com.me.johnspace.JohnSpaceGame;
/*    */
/*    */
/*    */
/*    */ public class DesktopLauncher
/*    */ {
	/* 11 */   private static boolean rebuildAtlas = false;
	/* 12 */   private static boolean drawDebugOutline = false;
	/*    */
/*    */   public static void main(String[] args) {
/* 15 */     if (rebuildAtlas) {
/* 16 */       TexturePacker.Settings settings = new TexturePacker.Settings();
/* 17 */       settings.maxWidth = 1024;
/* 18 */       settings.maxHeight = 1024;
/* 19 */       settings.duplicatePadding = true;
/* 20 */       settings.debug = drawDebugOutline;
/* 21 */       TexturePacker.process(settings, "raw-asset/ld", "../TrainingPlatformer-android/assets/ld", "trainingplatformer.pack");
/*    */     }
/*    */
/* 24 */     LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
/* 25 */     cfg.title = "TrainingPlatformer";
/* 26 */     cfg.useGL30 = false;
/* 27 */     cfg.width = 800;
/* 28 */     cfg.height = 600;
/*    */
/*    */
/* 31 */     cfg.fullscreen = false;
/*    */
/* 33 */     cfg.vSyncEnabled = false;
/*    */
/*    */
/* 36 */     new LwjglApplication(new JohnSpaceGame(), cfg);
/*    */   }
/*    */ }
