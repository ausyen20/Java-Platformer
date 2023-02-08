package com.mygdx.indulge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.Levels;
import com.mygdx.screens.LevelEnvy;
import com.mygdx.screens.LevelGluttony;
import com.mygdx.screens.LevelGreed;
import com.mygdx.screens.LevelLust;
import com.mygdx.screens.LevelSloth;

public class Indulge extends Game {
  private static Indulge INSTANCE = null;

  public Indulge() {
    INSTANCE = this;
  }

  Screen gameScreen;

  @Override
  public void create() {
    gameScreen = new LevelLust();
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
        setScreen(new LevelEnvy());
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