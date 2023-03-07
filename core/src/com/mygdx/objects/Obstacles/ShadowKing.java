package com.mygdx.objects.Obstacles;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.helpers.Constants;
import com.mygdx.objects.player.Player;

public class ShadowKing {
    private enum State {FLOATING, ATTACKING, DYING}
    private State currState;

    private Texture kingImage; 
	private TextureRegion[] floatFrames;
	private TextureRegion[] attackFrames;
	private TextureRegion[] dieFrames;
	private Animation<TextureRegion> animation;
	private TextureRegion[][] splitFrames;
	private float elapsedtime;

    
    public ShadowKing() {
        kingImage = new Texture("obstacles/shadowking-sheet.png");
		splitFrames = TextureRegion.split(kingImage, 40, 40);
        changeState(State.FLOATING);
    }

    public void changeState(State state) {
		switch(state) {
			case FLOATING:
                floatAnimation();
				currState = State.FLOATING;
				break;
			case ATTACKING:
				currState = State.ATTACKING;
				break;
			case DYING:
				currState = State.DYING;
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

    public void render(SpriteBatch batch, OrthographicCamera camera) {
		elapsedtime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(elapsedtime, true), camera.position.x - 25, 100, 50, 50);
	}
}
