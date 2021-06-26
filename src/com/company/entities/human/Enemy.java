package com.company.entities.human;

import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.resources.Resources;

import java.io.Serializable;
import java.util.*;

public class Enemy extends Entity implements Serializable {

    public static String filename = "saves/enemy.data";
    private final int maxFrames;
    private final com.company.worlds.Map map;
    private Entity target;

    public Enemy(com.company.worlds.Map map, int maxFrames) {
        super(Resources.ENEMY, 3, 1, 16);
        this.maxFrames = maxFrames;
        this.map = map;
    }

    public Enemy(Enemy copy) {
        super(copy);
        this.maxFrames = copy.maxFrames;
        this.map = copy.map;
        this.target = copy.target;
    }

    @Override
    public void move() {
        ArrayList<Node> path = AStar();
        if (path != null) {
            Node move = null;
            try {
                move = path.get(0);
            } catch (IndexOutOfBoundsException ignored) {
            }
            if (move != null) {
                if (Math.abs(posX - target.posX) < TILE_WIDTH && Math.abs(posY - target.posY) < TILE_HEIGHT)
                    return;

                if (move.x != posX / TILE_WIDTH) {
                    if (move.x > posX / TILE_WIDTH)
                        right = true;
                    else
                        left = true;
                }
                if (move.y != posY / TILE_HEIGHT) {
                    if (move.y > posY / TILE_HEIGHT)
                        down = true;
                    else
                        up = true;
                }
                super.move();
            }
        }
        switch (super.facing) {
            case NORTH:
                super.entityID = Resources.PLAYER_BACK;
                break;
            case SOUTH:
                super.entityID = Resources.PLAYER_FRONT;
                break;
            case WEST:
                super.entityID = Resources.PLAYER_LEFT;
                break;
            case EAST:
                super.entityID = Resources.PLAYER_RIGHT;
                break;
        }
        right = false;
        left = false;
        down = false;
        up = false;
    }

    private ArrayList<Node> makePath(Map<Node, Node> map, Node current) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(current);
        while (map.containsKey(current)) {
            current = map.remove(current);
            if (current != null)
                path.add(current);
        }
        Collections.reverse(path);
        path.remove(0);
        return path;
    }

    private ArrayList<Node> AStar() {

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(o -> o.fScore));

        Node start = new Node(posX / TILE_WIDTH, posY / TILE_HEIGHT);
        openSet.add(start);

        Map<Node, Integer> gScore = new HashMap<>();
        for (int y = 0; y < map.getHeightInPixel() / TILE_HEIGHT; y++) {
            for (int x = 0; x < map.getWidthInPixel() / TILE_WIDTH; x++) {
                if (x == start.x && y == start.y)
                    gScore.put(start, 0);
                else
                    gScore.put(new Node(x, y), Node.INFINITY);
            }
        }

        Map<Node, Node> cameFrom = new HashMap<>();
        for (Node n : gScore.keySet())
            cameFrom.put(n, null);

        start.fScore = Node.manhattanDistance(start, start);

        while (!openSet.isEmpty()) {
            Node current = openSet.peek();
            if (current.equals(new Node(target.posX / TILE_WIDTH, target.posY / TILE_HEIGHT)))
                return makePath(cameFrom, current);
            openSet.remove(current);
            List<Node> neighborhood = new ArrayList<>();

            if (current.x != 0)
                neighborhood.add(Node.getNode(gScore, current.x - 1, current.y));
            if (current.y != 0)
                neighborhood.add(Node.getNode(gScore, current.x, current.y - 1));
            if (current.y != map.getWidthInPixel() / TILE_WIDTH - 1)
                neighborhood.add(Node.getNode(gScore, current.x + 1, current.y));
            if (current.y != map.getHeightInPixel() / TILE_HEIGHT - 1)
                neighborhood.add(Node.getNode(gScore, current.x, current.y + 1));

            for (Node neighbor : neighborhood) {
                if (neighbor != null) {
                    int tentative_gScore = gScore.get(current) + Node.typeOf(map, neighbor);
                    if (tentative_gScore < gScore.get(neighbor)) {
                        cameFrom.replace(neighbor, current);
                        gScore.replace(neighbor, tentative_gScore);
                        neighbor.fScore = gScore.get(neighbor) + Node.manhattanDistance(current, neighbor);
                        if (!openSet.contains(neighbor))
                            openSet.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (up || down || left || right) {
            animationFrame += dt * animationDelay;
            if (animationFrame > maxFrames)
                animationFrame = 1;
        }
    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage player = new TileImage(Resources.TEXTURES.get(Resources.ENEMY), GameLoop.TILE_WIDTH, GameLoop.TILE_HEIGHT);
        if (up || down || left || right) {
            r.addImage(player.getTile((int) animationFrame, entityID), super.posX, super.posY);
        } else {
            r.addImage(player.getTile(0, entityID), super.posX, super.posY);
        }
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public int manhattanDistance(Entity player) {
        return Node.manhattanDistance(new Node(posX, posY), new Node(player.posX, player.posY));
    }

    static class Node {
        protected static final int INFINITY = 1000000000;
        protected final int x;
        protected final int y;
        protected int fScore = INFINITY;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        protected static int manhattanDistance(Node start, Node end) {
            return Math.abs(end.x - start.x + end.y - start.y);
        }

        protected static Node getNode(Map<Node, Integer> gScore, int x, int y) {
            for (Node n : gScore.keySet()) {
                if (n.x == x && n.y == y)
                    return n;
            }
            return null;
        }

        protected static int typeOf(com.company.worlds.Map map, Node neighbor) {
            int x = neighbor.x;
            int y = neighbor.y;
            if (map.getTile(x, y).isFloor())
                return 1;
            return INFINITY;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
