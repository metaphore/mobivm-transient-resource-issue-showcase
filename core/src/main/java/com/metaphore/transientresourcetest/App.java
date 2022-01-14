package com.metaphore.transientresourcetest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;

public class App extends ApplicationAdapter {
	private static final Color CLEAR_COLOR = new Color(0.25f, 0.25f, 0.35f, 1f);

	private SpriteBatch batch;
	private Texture image;
	private Viewport viewport;
	private VfxManager vfxManager;
	private GaussianBlurEffect vfxEffect;

	@Override
	public void create() {
		batch = new SpriteBatch();
		image = new Texture("libgdx.png");

		viewport = new ExtendViewport(640, 480);

		vfxManager = new VfxManager(Pixmap.Format.RGBA8888);
		vfxEffect = new GaussianBlurEffect();
		vfxEffect.setPasses(4);
		vfxEffect.setAmount(4);
		vfxManager.addEffect(vfxEffect);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		batch.setProjectionMatrix(viewport.getCamera().projection);

		vfxManager.resize(
				Math.round(viewport.getWorldWidth()),
				Math.round(viewport.getWorldHeight()));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		vfxManager.cleanUpBuffers(CLEAR_COLOR);

		vfxManager.beginInputCapture();

		batch.begin();
		batch.draw(image,
				-image.getWidth() * 0.5f,
				-image.getHeight() * 0.5f);
		batch.end();

		vfxManager.endInputCapture();

		vfxManager.applyEffects();

		vfxManager.renderToScreen();
	}

	@Override
	public void dispose() {
		batch.dispose();
		image.dispose();
		vfxManager.dispose();
		vfxEffect.dispose();
	}
}