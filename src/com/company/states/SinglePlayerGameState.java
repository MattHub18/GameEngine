package com.company.states;

import com.company.commands.InputHandler;
import com.company.entities.human.GameEntity;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.graphic.primitives.RenderObject;
import com.company.hud.HUD;
import com.company.observer.Subject;
import com.company.world.World;

public class SinglePlayerGameState implements State {

    private final World world;
    private final GameEntity player;
    private final HUD hud;
    private final InputHandler gameHandler;

    public SinglePlayerGameState(World world, GameEntity player, HUD hud, InputHandler gameHandler) {
        this.world = world;
        this.player = player;
        this.hud = hud;

        this.gameHandler = gameHandler;
        this.gameHandler.insertCommands();
    }

    @Override
    public void update(GameLoop gl, float dt) {
        world.update(gl, dt);
        hud.update(gl, dt);
        gameHandler.handleInput(gl);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        world.render(gl, r);
        hud.render(gl, r);
    }

    @Override
    public RenderObject getRenderObject() {
        return world;
    }

    @Override
    public Subject getActor() {
        return (Subject) player;
    }

    @Override
    public void stopSound() {
        world.stopSound();
    }
}
