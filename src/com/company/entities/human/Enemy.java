package com.company.entities.human;

import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.world.World;

import java.io.Serializable;
import java.util.*;

public abstract class Enemy extends Entity implements Serializable {

    private World world;
    private Entity target;

    public Enemy(int tileWidth, int tileHeight, World world, byte tfn, int posX, int posY, int maxFrames, int delay) {
        super(tfn, tileWidth, tileHeight, posX, posY, maxFrames, delay);
        this.world = world;
    }

    public Enemy copy(Enemy copy) {
        Enemy e = (Enemy) super.copy(copy);
        e.world = copy.world;
        e.target = copy.target;
        return e;
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
                if (Math.abs(posX - target.posX) < GameLoop.TILE_WIDTH && Math.abs(posY - target.posY) < GameLoop.TILE_HEIGHT)
                    return;

                if (move.x != posX / GameLoop.TILE_WIDTH) {
                    if (move.x > posX / GameLoop.TILE_WIDTH)
                        right = true;
                    else
                        left = true;
                }
                if (move.y != posY / GameLoop.TILE_HEIGHT) {
                    if (move.y > posY / GameLoop.TILE_HEIGHT)
                        down = true;
                    else
                        up = true;
                }
                super.move();
            }
        }
    }

    @Override
    public void render(GameLoop gl, Render r) {
        super.render(gl, r);
        clearMove();
    }

    private void clearMove() {
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

        Node start = new Node(posX / GameLoop.TILE_WIDTH, posY / GameLoop.TILE_HEIGHT);
        openSet.add(start);

        Map<Node, Integer> gScore = new HashMap<>();
        for (int y = 0; y < world.getHeightInPixel() / GameLoop.TILE_HEIGHT; y++) {
            for (int x = 0; x < world.getWidthInPixel() / GameLoop.TILE_WIDTH; x++) {
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
            if (current.equals(new Node(target.posX / GameLoop.TILE_WIDTH, target.posY / GameLoop.TILE_HEIGHT)))
                return makePath(cameFrom, current);
            openSet.remove(current);
            List<Node> neighborhood = new ArrayList<>();

            if (current.x != 0)
                neighborhood.add(Node.getNode(gScore, current.x - 1, current.y));
            if (current.y != 0)
                neighborhood.add(Node.getNode(gScore, current.x, current.y - 1));
            if (current.y != world.getWidthInPixel() / GameLoop.TILE_WIDTH - 1)
                neighborhood.add(Node.getNode(gScore, current.x + 1, current.y));
            if (current.y != world.getHeightInPixel() / GameLoop.TILE_HEIGHT - 1)
                neighborhood.add(Node.getNode(gScore, current.x, current.y + 1));

            for (Node neighbor : neighborhood) {
                if (neighbor != null) {
                    int tentative_gScore = gScore.get(current) + Node.typeOf(world, neighbor);
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

        protected static int typeOf(World world, Node neighbor) {
            int x = neighbor.x;
            int y = neighbor.y;
            if (world.getTile(x, y).isFloor())
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
