export function findGuardStrategyOne(records: Record[]): number {
  const data = calculateSleep(records);

  // Figure out which guard sleeps the most
  let maxGuard = -1;
  let maxSleep = -1;
  for (const [guard, sleep] of data.entries()) {
    const totalSleep = sleep.reduce((acc, n) => acc + n);
    if (totalSleep > maxSleep) {
      maxGuard = guard;
      maxSleep = totalSleep;
    }
  }

  // Figure out which minute they sleep the most
  const worstGuard = data.get(maxGuard)!;
  const maxSleepInAMinute = Math.max(...worstGuard);
  const maxSleepMinute = worstGuard.indexOf(maxSleepInAMinute);

  return maxGuard * maxSleepMinute;
}

export function findGuardStrategyTwo(records: Record[]): number {
  const data = calculateSleep(records);

  let value = 0;
  let maxSleepInAMinute = -1;
  for (const [guard, sleep] of data.entries()) {
    const guardMaxSleep = Math.max(...sleep);
    if (guardMaxSleep > maxSleepInAMinute) {
      const maxSleepMinute = sleep.indexOf(guardMaxSleep);
      value = maxSleepMinute * guard;
      maxSleepInAMinute = guardMaxSleep;
    }
  }

  return value;
}

// Record how much each guard sleeps at each minute
function calculateSleep(records: Record[]): Map<number, Array<number>> {
  const data = new Map<number, Array<number>>();
  let guard = -1;
  let start = -1;
  records.forEach((record) => {
    if (record instanceof BeginShift) {
      guard = record.id;
    } else if (record instanceof FallAsleep) {
      start = record.timestamp.getMinutes();
    } else if (record instanceof WakeUp) {
      const end = record.timestamp.getMinutes();
      let guardData = data.get(guard);
      if (guardData === undefined) {
        guardData = new Array(60).fill(0);
        data.set(guard, guardData);
      }

      for (let a = start; a < end; a++) {
        guardData[a]++;
      }
    }
  });

  return data;
}

export type Record = BeginShift | FallAsleep | WakeUp;

export class BeginShift {
  constructor(readonly timestamp: Date, readonly id: number) {}
}

export class FallAsleep {
  constructor(readonly timestamp: Date) {}
}

export class WakeUp {
  constructor(readonly timestamp: Date) {}
}

const regex =
  /\[(\d+)-(\d+)-(\d+) (\d+):(\d+)\] (wakes up|falls asleep|Guard #(\d+) begins shift)/;

export function parseRecord(record: string): Record {
  const match = record.match(regex);
  if (match === null) {
    throw new Error(`Invalid format: ${record}`);
  }

  const timestamp = new Date(
    Number.parseInt(match[1], 10),
    Number.parseInt(match[2], 10) - 1,
    Number.parseInt(match[3], 10),
    Number.parseInt(match[4], 10),
    Number.parseInt(match[5], 10)
  );

  switch (match[6]) {
    case "wakes up":
      return new WakeUp(timestamp);
    case "falls asleep":
      return new FallAsleep(timestamp);
    default:
      return new BeginShift(timestamp, Number.parseInt(match[7], 10));
  }
}
