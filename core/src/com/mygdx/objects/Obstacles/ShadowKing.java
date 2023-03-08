package com.mygdx.objects.Obstacles;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShadowKing {
    public enum State {FLOATING, DEATH}
    private State currState;

    private Texture kingImage; 
	private TextureRegion[] floatFrames;
	private TextureRegion[] deathFrames;
	private Animation<TextureRegion> animation;
	private TextureRegion[][] splitFrames;
	private float elapsedtime;
    private boolean LOOPING;

    
    public ShadowKing() {
        kingImage = new Texture("obstacles/shadowkingsheet.png");
		splitFrames = TextureRegion.split(kingImage, 40, 40);
        changeState(State.FLOATING);
    }

    public void changeState(State state) {
		switch(state) {
			case FLOATING:
                floatAnimation();
                currState = State.FLOATING;
                LOOPING = true;
				break;
			case DEATH:
                deathAnimation();
                currState = State.DEATH;
                LOOPING = false;
				elapsedtime = 0;
				break;
		}
	}

    public void floatAnimation() {
		floatFrames = new TextureRegion[6];
		for (int i = 0; i < 6; i++) {
			floatFrames[i] = splitFrames[0][i];
		} 
		animation = new Animation<TextureRegion>(1f/6f, floatFrames);
	}

    public void deathAnimation() {
		deathFrames = new TextureRegion[12];
		for (int i = 0; i < 12; i++) {
			deathFrames[i] = splitFrames[1][i];
		} 
		animation = new Animation<TextureRegion>(1f/12f, deathFrames);
	}

    public void render(SpriteBatch batch, OrthographicCamera camera) {
		elapsedtime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(elapsedtime, LOOPING), camera.position.x - 25, 100, 50, 50);
    }
}
