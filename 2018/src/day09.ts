export function highestScore(players: number, max: number): number {
  const scores = new Array<number>(players).fill(0);
  let curr = new Marble(0);
  for (let marble = 1; marble <= max; marble++) {
    if (marble % 23 === 0) {
      const player = (marble - 1) % players;

      // Remove marble 7 spaces counter-clockwise and add it to score
      curr = move(curr, -7);
      const next = curr.next;
      scores[player] += curr.value + marble;
      removeMarble(curr);
      curr = next;
    } else {
      curr = move(curr, 1);
      curr = insertAfter(curr, marble);
    }
  }

  let maxScore = 0;
  for (const score of scores) {
    maxScore = Math.max(maxScore, score);
  }

  return maxScore;
}

function move(marble: Marble, steps: number): Marble {
  while (steps != 0) {
    if (steps > 0) {
      marble = marble.next;
      steps--;
    } else if (steps < 0) {
      marble = marble.prev;
      steps++;
    }
  }
  return marble;
}

// Inserts a new marble after the given marble, returning the marble just inserted
function insertAfter(marble: Marble, value: number): Marble {
  const newMarble = new Marble(value);
  const nextMarble = marble.next;

  // Connect marble <-> newMarble
  marble.next = newMarble;
  newMarble.prev = marble;

  // Connect newMarble <-> nextMarble
  newMarble.next = nextMarble;
  nextMarble.prev = newMarble;

  return newMarble;
}

function removeMarble(marble: Marble) {
  const prevMarble = marble.prev;
  const nextMarble = marble.next;

  prevMarble.next = nextMarble;
  nextMarble.prev = prevMarble;
}

class Marble {
  readonly value: number;
  prev: Marble = this;
  next: Marble = this;

  constructor(value: number) {
    this.value = value;
  }
}
