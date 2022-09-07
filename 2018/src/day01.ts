export function frequency(changes: number[]): number {
  return changes.reduce((prev, curr) => prev + curr, 0);
}

export function frequencyDuplicates(changes: number[]): number {
  const seenFrequencies = new Set<number>();
  let frequency = 0;

  // eslint-disable-next-line no-constant-condition
  while(true) {
    for (const value of changes) {
      seenFrequencies.add(frequency);
      frequency += value;

      if (seenFrequencies.has(frequency)) {
        return frequency;
      }
    }
  }
}