package com.mygdx.indulge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Indulge extends Game {

  Screen gameScreen;

  @Override
  public void create() {
    //gameScreen = new GameScreen();
    gameScreen = new LevelSloth();
    setScreen(gameScreen);
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
  
}
