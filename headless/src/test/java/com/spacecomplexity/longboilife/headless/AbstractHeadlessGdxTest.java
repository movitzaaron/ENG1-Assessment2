package com.spacecomplexity.longboilife.headless;

import static org.mockito.Mockito.mock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.spacecomplexiy.longboilife.headless.HeadlessLauncher;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractHeadlessGdxTest {
  @BeforeEach
  public void setup() {
    Gdx.gl = Gdx.gl20 = mock(GL20.class);
    HeadlessLauncher.main(new String[0]);
  }
}
