/*    */ package com.me.johnspace.util;
/*    */ 
/*    */ import com.badlogic.gdx.audio.Sound;
/*    */ 
/*    */ public class AudioManager
/*    */ {
/*  7 */   public static final AudioManager instance = new AudioManager();
/*    */   
/*    */ 
/*    */   private com.badlogic.gdx.audio.Music playingMusic;
/*    */   
/*    */ 
/*    */   public void play(Sound sound)
/*    */   {
/* 15 */     play(sound, 1.0F);
/*    */   }
/*    */   
/*    */   public void play(Sound sound, float volume) {
/* 19 */     play(sound, volume, 1.0F);
/*    */   }
/*    */   
/*    */   public void play(Sound sound, float volume, float pitch) {
/* 23 */     play(sound, volume, pitch, 0.0F);
/*    */   }
/*    */   
/*    */   public void play(Sound sound, float volume, float pitch, float pan)
/*    */   {
/* 28 */     sound.play(1.0F * volume, pitch, pan);
/*    */   }
/*    */   
/*    */   public long loop(Sound sound) {
/* 32 */     return sound.loop();
/*    */   }
/*    */   
/*    */   public void stop(Sound sound, long ID) {
/* 36 */     sound.stop(ID);
/*    */   }
/*    */   
/*    */   public void play(com.badlogic.gdx.audio.Music music) {
/* 40 */     stopMusic();
/* 41 */     this.playingMusic = music;
/*    */     
/* 43 */     music.setLooping(true);
/* 44 */     music.setVolume(0.5F);
/* 45 */     music.play();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void stopMusic()
/*    */   {
/* 52 */     if (this.playingMusic != null) this.playingMusic.stop();
/*    */   }
/*    */   
/*    */   public void onSettingUpdated() {
/* 56 */     if (this.playingMusic == null) return;
/* 57 */     this.playingMusic.setVolume(1.0F);
/*    */   }
/*    */ }


/* Location:              C:\\Users\\geoff\\Downloads\\johnspace.jar!\\com\\me\\trainingplatformer\\util\\AudioManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */