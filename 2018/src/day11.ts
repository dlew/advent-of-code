const SIZE = 300;

export function findMaxPowerGrid3x3(gridSerialNumber: number): string {
  // Fill in the grid
  const grid = generateGrid(gridSerialNumber);

  // Search for max value 3x3 grid
  const max = { x: -1, y: -1, value: -1 };
  for (let x = 1; x < SIZE - 1; x++) {
    for (let y = 1; y < SIZE - 1; y++) {
      // Compute value of 3x3 grid
      let value = 0;
      for (let dx = -1; dx <= 1; dx++) {
        for (let dy = -1; dy <= 1; dy++) {
          value += grid[x + dx][y + dy];
        }
      }

      // Compare to current max
      if (max.value < value) {
        max.x = x;
        max.y = y;
        max.value = value;
      }
    }
  }

  return `${max.x},${max.y}`;
}

export function findMaxPowerGridAny(gridSerialNumber: number): string {
  // Generate summed area table
  const sumTable = new Array<Array<number>>(SIZE);
  for (let x = 0; x < SIZE; x++) {
    sumTable[x] = new Array<number>(SIZE);
    for (let y = 0; y < SIZE; y++) {
      sumTable[x][y] = powerLevel(x + 1, y + 1, gridSerialNumber);
      if (x !== 0) {
        sumTable[x][y] += sumTable[x - 1][y];
      }
      if (y !== 0) {
        sumTable[x][y] += sumTable[x][y - 1];
      }
      if (x !== 0 && y !== 0) {
        sumTable[x][y] -= sumTable[x - 1][y - 1];
      }
    }
  }

  // Search for max value grid (for any given size)
  // We do this semi-quickly by checking each top-left corner, then expanding
  // outwards from it (until it hits a wall)
  const max = { x: -1, y: -1, size: -1, value: -1 };
  for (let x = 0; x < SIZE; x++) {
    for (let y = 0; y < SIZE; y++) {
      const maxSize = SIZE - Math.max(x, y);
      for (let size = 0; size < maxSize; size++) {
        const value =
          sumTable[x + size][y + size] +
          sumTable[x][y] -
          sumTable[x + size][y] -
          sumTable[x][y + size];

        // Check if this is the highest-value grid now
        if (max.value < value) {
          max.x = x;
          max.y = y;
          max.size = size;
          max.value = value;
        }
      }
    }
  }

  return `${max.x + 2},${max.y + 2},${max.size}`;
}

function generateGrid(gridSerialNumber: number): Array<Array<number>> {
  const grid = new Array<Array<number>>(SIZE);
  for (let x = 0; x < SIZE; x++) {
    grid[x] = new Array<number>(SIZE);
    for (let y = 0; y < SIZE; y++) {
      grid[x][y] = powerLevel(x + 1, y + 1, gridSerialNumber);
    }
  }
  return grid;
}

export function powerLevel(
  x: number,
  y: number,
  gridSerialNumber: number
): number {
  const rackId = x + 10;
  let powerLevel = rackId * y;
  powerLevel += gridSerialNumber;
  powerLevel *= rackId;
  powerLevel = Math.floor(powerLevel / 100) % 10;
  powerLevel -= 5;
  return powerLevel;
}
