export function numberClaimOverlaps(claims: Claim[]): number {
  const fabric = countClaims(claims);

  // Reduce to find the number of spots with 2 or more claims
  return fabric.reduce(
    (acc, row) => acc + row.reduce((acc, curr) => acc + (curr >= 2 ? 1 : 0), 0),
    0
  );
}

export function findClaimWithoutOverlap(claims: Claim[]): number {
  const fabric = countClaims(claims);

  // Find a claim where there is only ever 1s in the count
  outer: for (const claim of claims) {
    for (let row = claim.left; row < claim.left + claim.width; row++) {
      for (let col = claim.top; col < claim.top + claim.height; col++) {
        if (fabric[row][col] > 1) {
          continue outer;
        }
      }
    }

    // If we got here, it means every spot in the claim was non-overlapping
    return claim.id;
  }

  return -1;
}

function countClaims(claims: Claim[]): Array<Array<number>> {
  const fabric = new Array<Array<number>>(1000);
  for (let a = 0; a < 1000; a++) {
    fabric[a] = new Array(1000).fill(0);
  }

  claims.forEach((claim) => {
    for (let row = claim.left; row < claim.left + claim.width; row++) {
      for (let col = claim.top; col < claim.top + claim.height; col++) {
        fabric[row][col]++;
      }
    }
  });

  return fabric;
}

const regex = /#(\d+) @ (\d+),(\d+): (\d+)x(\d+)/;

export function parseClaim(claim: string): Claim {
  const match = claim.match(regex);
  if (match === null) {
    throw new Error(`Invalid format: ${claim}`);
  }
  return new Claim(
    Number.parseInt(match[1], 10),
    Number.parseInt(match[2], 10),
    Number.parseInt(match[3], 10),
    Number.parseInt(match[4], 10),
    Number.parseInt(match[5], 10)
  );
}

export class Claim {
  constructor(
    readonly id: number,
    readonly left: number,
    readonly top: number,
    readonly width: number,
    readonly height: number
  ) {}
}
