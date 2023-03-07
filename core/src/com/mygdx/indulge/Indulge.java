package com.mygdx.indulge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.helpers.DemoScreenTypes;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.helpers.MenuScreenTypes;
import com.mygdx.screens.DemoScreens.DemoCoins;
import com.mygdx.screens.DemoScreens.DemoItem;
import com.mygdx.screens.DemoScreens.DemoJump;
import com.mygdx.screens.LevelScreens.LevelEnvy;
import com.mygdx.screens.LevelScreens.LevelGluttony;
import com.mygdx.screens.LevelScreens.LevelGreed;
import com.mygdx.screens.LevelScreens.LevelLust;
import com.mygdx.screens.LevelScreens.LevelSloth;
import com.mygdx.screens.MenuScreens.EndLevel;
import com.mygdx.screens.MenuScreens.Options;
import com.mygdx.screens.MenuScreens.TitleScreen;
import com.mygdx.screens.MenuScreens.WinScreen;
import com.mygdx.helpers.AudioManager;
 
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
        setScreen(new EndLevel());
        break;
      case WIN:
        setScreen(new WinScreen());
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
    ((AudioManager) AudioManager.getInstance()).disposeMusic();
    switch(new_demo) {
      case DEMO_JUMP:
        setScreen(new DemoJump());
        break;
      case DEMO_ITEM:
        setScreen(new DemoItem());
        break;
      case DEMO_COINS:
        setScreen(new DemoCoins());
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