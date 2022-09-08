export function checksum(ids: string[]): number {
  let two = 0;
  let three = 0;

  ids.forEach((id) => {
    const count = countCharacters(id);
    const values = Array.from(count.values());
    if (values.some((value) => value == 2)) {
      two++;
    }
    if (values.some((value) => value == 3)) {
      three++;
    }
  });

  return two * three;
}

function countCharacters(id: string): Map<string, number> {
  const map = new Map<string, number>();
  for (const character of id) {
    const curr = map.get(character);
    map.set(character, curr === undefined ? 1 : curr + 1);
  }
  return map;
}

export function findBox(ids: string[]): string {
  for (let a = 0; a < ids.length; a++) {
    const first = ids[a];
    for (let b = a + 1; b < ids.length; b++) {
      const second = ids[b];
      for (let index = 0; index < first.length; index++) {
        if (splice(first, index) == splice(second, index)) {
          return splice(first, index)
        }
      }
    }
  }

  return "Failed to find ID";
}

function splice(id: string, index: number): string {
  return id.slice(0, index) + id.slice(index + 1)
}

