import { getBounds } from "./bounds";

export function simulate(lights: Array<Light>, bound: number) {
  let state: "before" | "inside" | "after" = "before";
  let seconds = 0;
  while (state !== "after") {
    const bounds = getBounds(lights);
    if (bounds.width() < bound && bounds.height() < bound) {
      state = "inside";

      console.log(`After ${seconds} second(s)`);
      printLights(lights);
      console.log("");
    } else if (state === "inside") {
      state = "after";
    }

    lights.forEach((light) => light.move());
    seconds++;
  }
}

function printLights(lights: Array<Light>) {
  // Setup matrix
  const bounds = getBounds(lights);
  const matrix = new Array<Array<string>>(bounds.height() + 1);
  const width = bounds.width() + 1;
  for (let row = 0; row < matrix.length; row++) {
    matrix[row] = new Array<string>(width).fill(" ");
  }

  // Mark all the lights
  for (const { x, y } of lights) {
    matrix[y - bounds.top][x - bounds.left] = "#";
  }

  // Print matrix
  for (let row = 0; row < matrix.length; row++) {
    console.log(matrix[row].join(""));
  }
}

const re = /position=<\s*(-?\d+),\s*(-?\d+)> velocity=<\s*(-?\d+),\s*(-?\d+)>/;

export function parseLight(text: string): Light {
  const match = text.match(re)!;
  return new Light(
    Number.parseInt(match[1], 10),
    Number.parseInt(match[2], 10),
    Number.parseInt(match[3], 10),
    Number.parseInt(match[4], 10)
  );
}

export class Light {
  constructor(
    public x: number,
    public y: number,
    readonly dx: number,
    readonly dy: number
  ) {}

  move() {
    this.x += this.dx;
    this.y += this.dy;
  }
}
