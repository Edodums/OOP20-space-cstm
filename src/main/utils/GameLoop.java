package main.utils;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Taken Here:
 * https://github.com/edencoding/javafx-game-dev/blob/5f7cabd832db5e43865e611732d33db2e34228d2/SpaceShooter/src/main/java/com/edencoding/animation/GameLoopTimer.java
 */
public abstract class GameLoop extends AnimationTimer {
  long pauseStart;
  long animationStart;
  DoubleProperty animationDuration = new SimpleDoubleProperty(0L);
  
  long lastFrameTimeNanos;
  
  boolean isPaused;
  boolean isActive;
  
  boolean pauseScheduled;
  boolean playScheduled;
  boolean restartScheduled;
  
  public boolean isPaused() {
    return isPaused;
  }
  
  public boolean isActive() {
    return isActive;
  }
  
  public DoubleProperty animationDurationProperty() {
    return animationDuration;
  }
  
  public void pause() {
    if (!isPaused) {
      setPauseScheduled(true);
    }
  }
  
  public void play() {
    if (isPaused) {
      setPlayScheduled(true);
    }
  }
  
  @Override
  public void start() {
    super.start();
    setActive(true);
    setRestartScheduled(true);
  }
  
  @Override
  public void stop() {
    super.stop();
    
    setPauseStart(0);
    setPaused(false);
    setActive(false);
    setPlayScheduled(false);
    setPauseScheduled(false);
    
    animationDuration.set(0);
  }
  
  @Override
  public void handle(long now) {
    if (pauseScheduled) {
      setPauseStart(now);
      setPaused(true);
      setPauseScheduled(false);
    }
    
    if (playScheduled) {
      setAnimationStart(animationStart + (now - pauseStart));
      setPaused(false);
      setPlayScheduled(false);
    }
    
    if (restartScheduled) {
      setPaused(false);
      setAnimationStart(now);
      setRestartScheduled(false);
    }
    
    if (!isPaused) {
      long animDuration = now - animationStart;
      
      animationDuration.set(animDuration / 1e9);
      
      float secondsSinceLastFrame = (float) ((now - lastFrameTimeNanos) / 1e9);
      
      setLastFrameTimeNanos(now);
      tick(secondsSinceLastFrame);
    }
  }
  
  public void setPauseStart(long pauseStart) {
    this.pauseStart = pauseStart;
  }
  
  public void setAnimationStart(long animationStart) {
    this.animationStart = animationStart;
  }
  
  public void setAnimationDuration(double animationDuration) {
    this.animationDuration.set(animationDuration);
  }
  
  public void setLastFrameTimeNanos(long lastFrameTimeNanos) {
    this.lastFrameTimeNanos = lastFrameTimeNanos;
  }
  
  public void setPaused(boolean paused) {
    isPaused = paused;
  }
  
  public void setActive(boolean active) {
    isActive = active;
  }
  
  public void setPauseScheduled(boolean pauseScheduled) {
    this.pauseScheduled = pauseScheduled;
  }
  
  public void setPlayScheduled(boolean playScheduled) {
    this.playScheduled = playScheduled;
  }
  
  public void setRestartScheduled(boolean restartScheduled) {
    this.restartScheduled = restartScheduled;
  }
  
  public abstract void tick(float secondsSinceLastFrame);
}
