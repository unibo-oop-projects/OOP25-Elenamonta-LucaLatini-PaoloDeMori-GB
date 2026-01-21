package it.unibo.geometrybash.model.player;

import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.powerup.PowerUpManager;

public interface Player {

    void jump();

    void die();

    void respawn(Vector2 position);

    void addCoin(int value);

    int getCoins();

    void applySpeedModifier(float multiplier);

    Skin getSkin();

    void setSkin(Skin skin);

    PowerUpManager getPowerUpManager();
}
