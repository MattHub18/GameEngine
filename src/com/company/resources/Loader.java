package com.company.resources;

public class Loader {
    public static void load() {
        Resources.TEXTURES.add(Resources.PLAYER, "res/entity/fake_player.png");
        Resources.TEXTURES.add(Resources.MAP, "res/map/fake_map.png");
        Resources.TEXTURES.add(Resources.BULLET, "res/entity/bullet.png");
        Resources.TEXTURES.add(Resources.MULTIPLAYER, "res/menu/multiplayer.png");
        Resources.TEXTURES.add(Resources.FPS, "res/font/fps.png");
        Resources.TEXTURES.add(Resources.CLIENT, "res/font/client.png");
    }
}
