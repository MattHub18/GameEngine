package com.company.ai.movement;

import com.company.entities.human.entity.GameEntity;
import com.company.physics.basics.Point;
import com.company.world.Room;

import java.util.*;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class AStarMove implements MovementComponent {

    public Point move(GameEntity aiEntity, int targetX, int targetY) {

        if (Math.abs(aiEntity.getPosX() - targetX) <= TILE_WIDTH && Math.abs(aiEntity.getPosY() - targetY) <= TILE_HEIGHT)
            return null;

        ArrayList<Node> path = AStar(aiEntity, aiEntity.getRoom(), targetX, targetY);

        if (path.size() > 0) {
            Node move = path.get(0);
            return new Point(move.x, move.y);
        }
        return null;
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

    private ArrayList<Node> AStar(GameEntity aiEntity, Room room, int targetX, int targetY) {

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(o -> o.fScore));

        Node start = new Node(aiEntity.getPosX() / TILE_WIDTH, aiEntity.getPosY() / TILE_HEIGHT);
        openSet.add(start);

        Map<Node, Integer> gScore = new HashMap<>();
        for (int y = 0; y < room.getHeightInPixel() / TILE_HEIGHT; y++) {
            for (int x = 0; x < room.getWidthInPixel() / TILE_WIDTH; x++) {
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
            if (current.equals(new Node(targetX / TILE_WIDTH, targetY / TILE_HEIGHT)))
                return makePath(cameFrom, current);
            openSet.remove(current);
            List<Node> neighborhood = new ArrayList<>();

            if (current.x != 0)
                neighborhood.add(Node.getNode(gScore, current.x - 1, current.y));
            if (current.y != 0)
                neighborhood.add(Node.getNode(gScore, current.x, current.y - 1));
            if (current.y != room.getWidthInPixel() / TILE_WIDTH - 1)
                neighborhood.add(Node.getNode(gScore, current.x + 1, current.y));
            if (current.y != room.getHeightInPixel() / TILE_HEIGHT - 1)
                neighborhood.add(Node.getNode(gScore, current.x, current.y + 1));

            for (Node neighbor : neighborhood) {
                if (neighbor != null) {
                    int tentative_gScore = gScore.get(current) + Node.typeOf(room, neighbor);
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
        return new ArrayList<>();
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

        protected static int typeOf(Room room, Node neighbor) {
            int x = neighbor.x;
            int y = neighbor.y;
            if (room.getTile(x, y).isFloor())
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
