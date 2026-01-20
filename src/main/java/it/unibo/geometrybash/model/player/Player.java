package it.unibo.geometrybash.model.player;

import it.unibo.geometrybash.model.geometry.Vector2;

public interface Player {

    void jump();

    void kill();

    void respawn(Vector2 position);

    void addCoin();

    int getCoins();

    void applySpeedModifier(float multiplier, float duration);

    Skin getSkin();

    void setSkin(Skin skin);
}
