import { Bounds, getBounds } from "./bounds";

export function largestNonInfiniteArea(coordinates: Array<Coordinate>): number {
  const { left, right, top, bottom } = getBounds(coordinates);

  // Observation: any coordinate whose closest location hits the bounds will be infinite.
  // Why? Because the next adjacent spot outwards to infinity (in whatever direction) will
  // always be closest to that spot. Even if there are things next to it, they are +1 steps
  // away (versus the adjacency of the one that hits the fence).

  // Calculate how much space each coordinate takes up within the bounding box
  // as well as which coordinates are infinite
  const coordinateSizes = new Map<Coordinate, number>();
  const infiniteCoordinates = new Set<Coordinate>();
  for (let x = left; x <= right; x++) {
    for (let y = top; y <= bottom; y++) {
      const closestCoordinate = findClosestCoordinate(x, y, coordinates);
      if (closestCoordinate != null) {
        if (x === left || x === right || y === top || y === bottom) {
          infiniteCoordinates.add(closestCoordinate);
        }

        let currentSize = coordinateSizes.get(closestCoordinate);
        if (currentSize === undefined) {
          currentSize = 0;
        }
        coordinateSizes.set(closestCoordinate, currentSize + 1);
      }
    }
  }

  // Filter to the best coordinate
  const finiteCoordinates = coordinates.filter(
    (coordinate) => !infiniteCoordinates.has(coordinate)
  );
  let best = finiteCoordinates[0];
  for (let a = 1; a < finiteCoordinates.length; a++) {
    if (
      coordinateSizes.get(best)! < coordinateSizes.get(finiteCoordinates[a])!
    ) {
      best = finiteCoordinates[a];
    }
  }

  return coordinateSizes.get(best)!;
}

export function safeAreaSize(
  coordinates: Array<Coordinate>,
  maxTotalDistance: number
): number {
  const { left, right, top, bottom } = getBounds(coordinates);

  // You can create contrived setups where the region is *not* within the bounds,
  // but this is good enough for the problem at hand.
  let safeSize = 0;
  for (let x = left; x <= right; x++) {
    for (let y = top; y <= bottom; y++) {
      let distance = 0;
      for (const coordinate of coordinates) {
        distance += manhattanDistance(x, y, coordinate.x, coordinate.y);
      }

      if (distance < maxTotalDistance) {
        safeSize++;
      }
    }
  }

  return safeSize;
}

function findClosestCoordinate(
  x: number,
  y: number,
  coordinates: Array<Coordinate>
): Coordinate | null {
  let best = coordinates[0];
  let min = manhattanDistance(x, y, coordinates[0].x, coordinates[0].y);
  let twoOrMore = false;
  for (let a = 1; a < coordinates.length; a++) {
    const distance = manhattanDistance(
      x,
      y,
      coordinates[a].x,
      coordinates[a].y
    );
    if (distance < min) {
      twoOrMore = false;
      best = coordinates[a];
      min = distance;
    } else if (distance == min) {
      twoOrMore = true;
    }
  }

  if (twoOrMore) {
    return null;
  } else {
    return best;
  }
}

function manhattanDistance(
  x1: number,
  y1: number,
  x2: number,
  y2: number
): number {
  return Math.abs(x1 - x2) + Math.abs(y1 - y2);
}

export class Coordinate {
  constructor(readonly x: number, readonly y: number) {}
}
