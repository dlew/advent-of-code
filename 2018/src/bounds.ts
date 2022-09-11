export function getBounds(lights: Array<{ x: number; y: number }>): Bounds {
  let left = Number.MAX_VALUE;
  let right = Number.MIN_VALUE;
  let top = Number.MAX_VALUE;
  let bottom = Number.MIN_VALUE;

  lights.forEach(({ x, y }) => {
    left = Math.min(left, x);
    right = Math.max(right, x);
    top = Math.min(top, y);
    bottom = Math.max(bottom, y);
  });

  return new Bounds(left, right, top, bottom);
}

export class Bounds {
  readonly left: number;
  readonly right: number;
  readonly top: number;
  readonly bottom: number;

  constructor(left: number, right: number, top: number, bottom: number) {
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
  }

  width(): number {
    return Math.abs(this.left - this.right);
  }

  height(): number {
    return Math.abs(this.top - this.bottom);
  }
}
