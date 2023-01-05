package com.mygdx.indulge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Indulge extends Game {

  private static Indulge INSTANCE = null;

  public Indulge() {
    INSTANCE = this;
  }

  Screen gameScreen;

  @Override
  public void create() {
    //gameScreen = new GameScreen();
    gameScreen = new LevelLust();
    //gameScreen = new LevelGluttony();
    //gameScreen = new LevelSloth();
    //gameScreen = new LevelGreed();

    setScreen(gameScreen);
  }

  public void change_screen(Levels new_screen) {
    switch(new_screen) {
      case LUST:
        setScreen(new LevelLust());
        break;
      case GLUTTONY:
        setScreen(new LevelGluttony());
        break;
      case SLOTH:
        setScreen(new LevelSloth());
        break;
      case GREED:
        setScreen(new LevelGreed());
        break;
      case ENVY:
        setScreen(new LevelLust());
        break;
      case PRIDE:
        setScreen(new LevelLust());
        break;
      case WRATH:
        setScreen(new LevelLust());
        break;
    }
  }

  @Override
  public void dispose() {
    gameScreen.dispose();
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void resize(int width, int height) {
    gameScreen.resize(width, height);
  }

  public static Object getInstance() {
    if(INSTANCE == null)
    INSTANCE = new Indulge();

return INSTANCE;
  }
  
}
