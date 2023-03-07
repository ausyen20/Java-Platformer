package com.mygdx.screens.LevelScreens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.helpers.AudioManager;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.LevelScreenTypes;
import com.mygdx.objects.MovingObstacles.Boulder;
import com.mygdx.objects.MovingObstacles.Peppermint;

public class LevelEnvy extends GameScreen {
	

    public LevelEnvy() {
    	 constantScrollingSpeed = player.getSpeed() / (100 * (Constants.WORLD_WIDTH / Constants.ASSET_LAYOUT_WIDTH));
         cameraScrollingSpeed = constantScrollingSpeed;
         cameraUpdate();
         playerLeftOffset = (camera.position.x * 100) - player.getX();
         playerSpeed = player.getSpeed();
         mintLinearVel = new Vector2(0,0);
        // Add background assets
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture("backgrounds/envy00.png");
        backgrounds[1] = new Texture("backgrounds/envy01.png");
        backgrounds[2] = new Texture("backgrounds/envy02.png");
        ((AudioManager) AudioManager.getInstance()).setMusic("Music/Envy.mp3");
        ((AudioManager) AudioManager.getInstance()).playMusic();
    }
	
    public void updateBoulder() {
    	//Boulder for shadow king stage
    	for(Boulder b : tileMapHelper.getBoudler()) {
    		b.update(1f);
    		if(b.getX() < player.getPositionX() + 200f / Constants.PPM) {
    			b.getBBody().setActive(true);
    			b.setActivng(true);
    		}
    		
    		if(b.getID() == 1) {
    			if(player.getPositionX()> 117f && player.getPositionX() < 158f) {
    				if(player.RESPAWN) {
    					b.resetBoulder(player.getPositionX(), player.getRespawn(), b);
    				}
    			}
    		}
    		if(b.getID() == 2) {
    			if(player.getPositionX()> 117f && player.getPositionX() < 158f) {
    				if(player.RESPAWN) {
    					b.resetBoulder(player.getPositionX(), player.getRespawn(), b);
    				}
    			}
    		}
    		if(b.getID() == 3) {
    			if(player.getPositionX()> 117f && player.getPositionX() < 158f) {
    				if(player.RESPAWN) {
    					b.resetBoulder(player.getPositionX(), player.getRespawn(), b);
    				}
    			}
    		}
    		if(b.getID() == 4) {
    			if(player.getPositionX()> 117f && player.getPositionX() < 158f) {
    				if(player.RESPAWN) {
    					b.resetBoulder(player.getPositionX(), player.getRespawn(), b);
    				}
    			}
    		}
    		if(b.getID() == 5) {
    			if(player.getPositionX()> 117f && player.getPositionX() < 158f) {
    				if(player.RESPAWN) {
    					b.resetBoulder(player.getPositionX(), player.getRespawn(), b);
    					 player.setRespawn(false);
    				}
    			}
    		}
    		
    	}
    	
    	
    	//Boulder 1 for smaller boulders at earlier stage
    	for(Boulder b1 : tileMapHelper.getBoulder1()) {
    		b1.update(1f);	
    		if(b1.getX() < player.getPositionX() + 200f / Constants.PPM) {
    			
    			b1.getBBody().setActive(true);
    			b1.setActivng(true);
    		}
    		//System.out.println("X: " + player.getPositionX() + ", Y: " + player.getPositionY() + ", a: " + player.getRespawn() + ", bid: " + b1.getID());
    		if(b1.getID() == 1) {
    			if(player.getPositionX() > 0f && player.getPositionX() < 65.53f) {
                    if(player.RESPAWN) {            				
                    	b1.resetBoulder1(player.getPositionX(), player.getRespawn(), b1);
                    }
                }
    		}
    		if(b1.getID() == 2) {
    			if(player.getPositionX() > 0f && player.getPositionX() < 65.53f) {
                    if(player.RESPAWN) {            				
                    	b1.resetBoulder1(player.getPositionX(), player.getRespawn(), b1);
                    }
                }
    		}
    		if(b1.getID() == 3) {
    			if(player.getPositionX() > 0f && player.getPositionX() < 65.53f) {
                    if(player.RESPAWN) {            				
                    	b1.resetBoulder1(player.getPositionX(), player.getRespawn(), b1);
                    }
                }
    		}
    		if(b1.getID() == 4) {
    			if(player.getPositionX() > 0f && player.getPositionX() < 65.53f) {
                    if(player.RESPAWN) {            				
                    	b1.resetBoulder1(player.getPositionX(), player.getRespawn(), b1);
                        player.setRespawn(false);
                    }
                }
    		}
    		
    		
    	}
    	
    }	
    	/*
    	        for(Peppermint p: tileMapHelper.getPeppermint()) {
            p.update(1f);           
            //When player's x position is beyond a certain range, active the body
             // + (value)f , when value is smaller lands faster, larger then lands later
            if(p.getX() < player.getPositionX() + 250f / Constants.PPM) {
                //Set true to be active when in range
                p.getMintBody().setActive(true);
                p.setActivng(true);
            }
            // If id = 1, first pepper mint
            if(p.getID() == 1) {
                if(player.getPositionX() > 0f && player.getPositionX() < 65.53f) {
                    if(player.RESPAWN) {            				
                        p.resetPeppermint(player.getPositionX(), player.getRespawn(), p);
                        player.setRespawn(false);
                    }
                }
            }
            //If id =2, second pepper mint
            if(p.getID() == 2) {
                if(player.getPositionX() > 95f && player.getPositionX() < 131.8f) {
                    if(player.RESPAWN) {            				
                        p.resetPeppermint(player.getPositionX(), player.getRespawn(), p);
                        player.setRespawn(false);
                    }
                }
            }
        }
    	*/
    
    
    @Override
    public LevelScreenTypes getPreviousScreen() {
        return LevelScreenTypes.GREED;
    }

    @Override
    public LevelScreenTypes getCurrentScreen() {
        return LevelScreenTypes.ENVY;
    }

    @Override
    public LevelScreenTypes getNextScreen() {
        return LevelScreenTypes.ENVY;

    }
}