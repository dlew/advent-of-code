export function react(polymer: string): number {
  let index = 0;

  while (index < polymer.length - 1) {
    const curr = polymer[index];
    const next = polymer[index + 1];

    if (
      curr.toLowerCase() === next.toLowerCase() &&
      isLowerCase(curr) !== isLowerCase(next)
    ) {
      polymer = removePair(polymer, index);

      // If we can, back up one (since we need to re-check the previous char now)
      if (index >= 1) {
        index--;
      }
    } else {
      index++;
    }
  }

  return polymer.length;
}

export function removeBestUnitType(polymer: string): number {
  let min = Number.MAX_VALUE;
  for (let ascii = 97; ascii < 123; ascii++) {
    const char = String.fromCharCode(ascii);
    const re = new RegExp(char, "gi");
    const reducedPolymer = polymer.replace(re, "");
    const reactedSize = react(reducedPolymer);

    if (reactedSize < min) {
      min = reactedSize;
    }
  }

  return min;
}

function isLowerCase(character: string): boolean {
  return character.charCodeAt(0) >= 97; // No need to check upper bound, all input alphabetical
}

function removePair(polymer: string, index: number): string {
  return polymer.slice(0, index) + polymer.slice(index + 2);
}
