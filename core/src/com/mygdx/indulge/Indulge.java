package com.mygdx.indulge;

import javax.swing.event.SwingPropertyChangeSupport;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.helpers.DemoScreenTypes;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.screens.DemoScreens.DemoLust;
import com.mygdx.screens.LevelScreens.LevelEnvy;
import com.mygdx.screens.LevelScreens.LevelGluttony;
import com.mygdx.screens.LevelScreens.LevelGreed;
import com.mygdx.screens.LevelScreens.LevelLust;
import com.mygdx.screens.LevelScreens.LevelSloth;
import com.mygdx.screens.MenuScreens.Options;
import com.mygdx.screens.MenuScreens.TitleScreen;

public class Indulge extends Game {
  private static Indulge INSTANCE = null;

  public Indulge() {
    INSTANCE = this;
  }

  Screen gameScreen;

  @Override
  public void create() {
    gameScreen = new TitleScreen();
    setScreen(gameScreen);
  }

  public void change_menu(MenuScreenTypes new_menu) {
    getScreen().dispose();
    switch(new_menu) {
      case TITLE:
        setScreen(new TitleScreen());
        break;
      case OPTIONS:
        setScreen(new Options());
        break;
      case END:
        break;
      default:
        break;
    }
  }

  public void change_levels(LevelScreenTypes new_level) {
    getScreen().dispose();
    switch(new_level) {
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

  public void change_demo(DemoScreenTypes new_demo) {
    getScreen().dispose();
    switch(new_demo) {
      case DEMO_LUST:
        setScreen(new DemoLust());
        break;
      case DEMO_ENVY:
        break;
      case DEMO_GLUTTONY:
        break;
      case DEMO_GREED:
        break;
      case DEMO_PRIDE:
        break;
      case DEMO_SLOTH:
        break;
      case DEMO_WRATH:
        break;
      default:
        break;
    }
  }

  @Override
  public void dispose() {
    //gameScreen.dispose();
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