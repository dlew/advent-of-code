export function findFirstCrash(input: string): string {
  const grid = input.split(/\r?\n/);
  const carts = findCarts(grid);

  // eslint-disable-next-line no-constant-condition
  while (true) {
    // Tick
    const result = tick(grid, carts);

    // Check for collision
    if (result !== null) {
      return result;
    }
  }
}

export function findLastSurvivingCart(input: string): string {
  const grid = input.split(/\r?\n/);
  const carts = findCarts(grid);

  // eslint-disable-next-line no-constant-condition
  while (true) {
    // Tick
    tick(grid, carts);

    if (carts.length === 1) {
      const lastCart = carts[0]
      return `${lastCart.x},${lastCart.y}`
    }
  }
}

function sortCartsByTurnOrder(carts: Cart[]) {
  carts.sort((a, b) => {
    return a.y !== b.y ? a.y - b.y : a.x - b.x;
  });
}

function tick(grid: string[], carts: Cart[]): string | null {
  sortCartsByTurnOrder(carts);
  let firstCollision = null;
  for (let a = 0; a < carts.length; a++ ) {
    const cart = carts[a];
    let newX = cart.x;
    let newY = cart.y;

    switch (cart.facing) {
      case Direction.NORTH:
        newY--;
        break;
      case Direction.EAST:
        newX++;
        break;
      case Direction.SOUTH:
        newY++;
        break;
      case Direction.WEST:
        newX--;
        break;
    }

    // Check for collision on the new position
    for (const otherCart of carts) {
      if (cart !== otherCart && otherCart.x === newX && otherCart.y === newY) {
        // Determine first collision (for part 1)
        if (firstCollision === null) {
          firstCollision = `${newX},${newY}`
        }

        // Remove the crashed carts (for part 2) and account for index changes
        const index = carts.indexOf(cart)
        carts.splice(index, 1)
        a--;

        const otherIndex = carts.indexOf(otherCart);
        carts.splice(otherIndex, 1)
        if (otherIndex <= a) {
          a--;
        }

        break;
      }
    }

    // Update position
    cart.x = newX;
    cart.y = newY;

    // Modify where the cart is facing
    switch (grid[newY][newX]) {
      case "|":
      case "-":
      case "^":
      case ">":
      case "v":
      case "<":
        // Do nothing
        break;
      case "/": {
        let newDirection: Direction;
        switch (cart.facing) {
          case Direction.NORTH:
            newDirection = Direction.EAST;
            break;
          case Direction.EAST:
            newDirection = Direction.NORTH;
            break;
          case Direction.SOUTH:
            newDirection = Direction.WEST;
            break;
          case Direction.WEST:
            newDirection = Direction.SOUTH;
            break;
        }
        cart.facing = newDirection;
        break;
      }
      case "\\": {
        let newDirection: Direction;
        switch (cart.facing) {
          case Direction.NORTH:
            newDirection = Direction.WEST;
            break;
          case Direction.EAST:
            newDirection = Direction.SOUTH;
            break;
          case Direction.SOUTH:
            newDirection = Direction.EAST;
            break;
          case Direction.WEST:
            newDirection = Direction.NORTH;
            break;
        }
        cart.facing = newDirection;
        break;
      }
      case "+": {
        const mod = cart.turn % 3;
        let newDirection: Direction;
        switch (mod) {
          case 0:
            newDirection = turnLeft(cart.facing);
            break;
          case 1:
            newDirection = cart.facing;
            break;
          case 2:
            newDirection = turnRight(cart.facing);
            break;
          default:
            throw new Error("How did you get here?!");
        }
        cart.facing = newDirection;
        cart.turn = mod + 1;
        break;
      }
      default:
        throw new Error(`Can't go ${cart.facing} to ${grid[newY][newX]}`);
    }
  }

  return firstCollision;
}

function findCarts(grid: string[]): Cart[] {
  const carts: Cart[] = [];
  for (let y = 0; y < grid.length; y++) {
    for (let x = 0; x < grid[y].length; x++) {
      let direction: Direction | null;
      switch (grid[y][x]) {
        case "^":
          direction = Direction.NORTH;
          break;
        case ">":
          direction = Direction.EAST;
          break;
        case "v":
          direction = Direction.SOUTH;
          break;
        case "<":
          direction = Direction.WEST;
          break;
        default:
          direction = null;
          break;
      }

      if (direction !== null) {
        carts.push({
          x: x,
          y: y,
          facing: direction,
          turn: 0,
        });
      }
    }
  }
  return carts;
}

enum Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST,
}

function turnLeft(direction: Direction): Direction {
  switch (direction) {
    case Direction.NORTH:
      return Direction.WEST;
    case Direction.EAST:
      return Direction.NORTH;
    case Direction.SOUTH:
      return Direction.EAST;
    case Direction.WEST:
      return Direction.SOUTH;
  }
}

function turnRight(direction: Direction): Direction {
  switch (direction) {
    case Direction.NORTH:
      return Direction.EAST;
    case Direction.EAST:
      return Direction.SOUTH;
    case Direction.SOUTH:
      return Direction.WEST;
    case Direction.WEST:
      return Direction.NORTH;
  }
}

type Cart = {
  x: number;
  y: number;
  facing: Direction;
  turn: number;
};
