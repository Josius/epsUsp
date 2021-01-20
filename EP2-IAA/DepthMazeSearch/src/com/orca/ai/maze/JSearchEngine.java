package com.orca.ai.maze;

/**
 *
 * @author Orca
 */
public class JSearchEngine {
    public JSearchEngine(int width, int height) {
        maze = new Maze(width, height);
        initSearch();
    }
    public Maze getMaze() { return maze; }
    protected Maze maze;
    /**
     * We will use the Java type Position (fields width and height will
 encode the coordinates in x and y directions) for the search path:
     */
    protected Position [] searchPath = null;
    protected int pathCount;
    protected int maxDepth;
    protected Position startLoc, goalLoc, currentLoc;
    protected boolean isSearching = true;

    protected void initSearch() {
        if (searchPath == null) {
            searchPath = new Position[1000];
            for (int i=0; i<1000; i++) {
                searchPath[i] = new Position();
            }
        }
        pathCount = 0;
        startLoc = maze.startLoc;
        currentLoc = startLoc;
        goalLoc = maze.goalLoc;
        searchPath[pathCount++] = currentLoc;
    }

    protected boolean equals(Position d1, Position d2) {
        return d1.x == d2.x && d1.y == d2.y;
    }

    public Position [] getPath() {
      Position [] ret = new Position[maxDepth];
      for (int i=0; i<maxDepth; i++) {
        ret[i] = searchPath[i];
      }
      return ret;
    }
    protected Position [] getPossibleMoves(Position loc) {
        Position tempMoves [] = new Position[4];
        tempMoves[0] = tempMoves[1] = tempMoves[2] = tempMoves[3] = null;
        int x = loc.x;
        int y = loc.y;
        int num = 0;
        if (maze.getValue(x - 1, y) == 0 || maze.getValue(x - 1, y) == Maze.GOAL_LOC_VALUE) {
            tempMoves[num++] = new Position(x - 1, y);
        }
        if (maze.getValue(x + 1, y) == 0 || maze.getValue(x + 1, y) == Maze.GOAL_LOC_VALUE) {
            tempMoves[num++] = new Position(x + 1, y);
        }
        if (maze.getValue(x, y - 1) == 0 || maze.getValue(x, y - 1) == Maze.GOAL_LOC_VALUE) {
            tempMoves[num++] = new Position(x, y - 1);
        }
        if (maze.getValue(x, y + 1) == 0 || maze.getValue(x, y + 1) == Maze.GOAL_LOC_VALUE) {
            tempMoves[num++] = new Position(x, y + 1);
        }
        return tempMoves;
    }
}
