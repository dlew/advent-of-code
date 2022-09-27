export function parseInitialState(input: string): Array<boolean> {
  return parseBooleanArray(input.split(/\r?\n/)[0].substring(15));
}

export function parsePositiveRules(input: string): Set<number> {
  const rules = input.split(/\r?\n/);
  rules.splice(0, 2);

  const re = /(.+) => (.)/;

  const positiveRules = new Set<number>();
  for (const rule of rules) {
    const match = rule.match(re)!;
    if (match[2] === "#") {
      positiveRules.add(sliceToInt(parseBooleanArray(match[1])));
    }
  }

  return positiveRules;
}

function parseBooleanArray(input: string): Array<boolean> {
  return input.split("").map((c) => c === "#");
}

export function sumPots(
  initialState: Array<boolean>,
  positiveRules: Set<number>,
  numGenerations: number
): number {
  const state = initialState;

  // Pad with four empty pots on either end (input assumed to have plants on each side)
  let numNegativePlants = 4;
  state.unshift(false, false, false, false);
  state.push(false, false, false, false);

  const start = new Date().getTime()

  for (let generation = 0; generation < numGenerations; generation++) {
    if (generation % 10000 === 0) {
      const end = new Date().getTime()
      console.log(`gen=${generation} pots=${state.length} sum=${totalValue(state, numNegativePlants)} time=${end - start}ms`);
    }

    // Remember, each generation starts with four empty pots on either end
    let num = 0;

    for (let index = 2; index < state.length - 2; index++) {
      num = (num >>> 1) + (state[index + 2] ? 16 : 0)
      state[index] = positiveRules.has(num);
    }

    // Pad with more planters if needed
    const firstPlant = state.findIndex((b) => b);
    if (firstPlant < 4) {
      state.unshift(false, false, false, false);
      numNegativePlants += 4;
    }

    const lastPlant = findLastTrueBoolean(state);
    if (state.length - lastPlant < 4) {
      state.push(false, false, false, false);
    }
  }

  return totalValue(state, numNegativePlants);
}

function totalValue(state: Array<boolean>, numNegativePlants: number): number {
  return state.reduce((prev, curr, index) => {
    if (!curr) {
      return prev;
    } else {
      const value = index - numNegativePlants;
      return prev + value;
    }
  }, 0);
}

function sliceToInt(slice: Array<boolean>): number {
  let n = 0;
  for (const b of slice) {
    n = (n >>> 1) + (b ? 16 : 0);
  }
  return n;
}

function findLastTrueBoolean(state: Array<boolean>): number {
  let index = state.length - 1;
  while (!state[index]) {
    index--;
  }
  return index;
}
