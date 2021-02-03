private List<Coordinate> backtrackPath(
  Coordinate cur) {
    List<Coordinate> path = new ArrayList<>();
    Coordinate iter = cur;

    while (iter != null) {
        path.add(iter);
        iter = iter.parent;
    }

    return path;
}

public List<Coordinate> solve(Maze maze) {
    LinkedList<Coordinate> nextToVisit
      = new LinkedList<>();
    Coordinate start = maze.getEntry();
    nextToVisit.add(start);

    while (!nextToVisit.isEmpty()) {
        Coordinate cur = nextToVisit.remove();

        if (!maze.isValidLocation(cur.getX(), cur.getY())
          || maze.isExplored(cur.getX(), cur.getY())
        ) {
            continue;
        }

        if (maze.isWall(cur.getX(), cur.getY())) {
            maze.setVisited(cur.getX(), cur.getY(), true);
            continue;
        }

        if (maze.isExit(cur.getX(), cur.getY())) {
            return backtrackPath(cur);
        }

        for (int[] direction : DIRECTIONS) {
            Coordinate coordinate
              = new Coordinate(
                cur.getX() + direction[0],
                cur.getY() + direction[1],
                cur
              );
            nextToVisit.add(coordinate);
            maze.setVisited(cur.getX(), cur.getY(), true);
        }
    }
    return Collections.emptyList();
}