export function getTenRecipesAfter(input: number): string {
  const recipes = [3, 7];
  const elves = [0, 1];

  while (recipes.length < input + 10) {
    tick(recipes, elves);
  }

  return recipes.slice(input, input + 10).join("");
}

function arrayEquals(arr1: number[], arr2: number[]): boolean {
  return arr1.every((val, index) => val === arr2[index]);
}

export function recipeFirstAppears(input: string): number {
  const recipes = [3, 7];
  const elves = [0, 1];

  const inputArr = input.split("").map((s) => parseInt(s, 10));
  const inputLength = input.length;

  // eslint-disable-next-line no-constant-condition
  while (true) {
    tick(recipes, elves);

    // Since two digits could be added, check the last input.length + 1 digits for the answer
    const length = recipes.length;
    if (
      length > inputLength &&
      arrayEquals(recipes.slice(length - inputLength), inputArr)
    ) {
      return recipes.length - inputLength;
    } else if (
      length > inputLength + 1 &&
      arrayEquals(recipes.slice(length - inputLength - 1, length - 1), inputArr)
    ) {
      return recipes.length - inputLength - 1;
    }
  }
}

function tick(recipes: number[], elves: number[]) {
  // Combine current recipes
  const sum = elves.reduce((curr, index) => curr + recipes[index], 0);

  // Append new recipe
  if (sum >= 10) {
    recipes.push(1);
  }
  recipes.push(sum % 10);

  // Pick new recipes
  for (let a = 0; a < elves.length; a++) {
    const elfPosition = elves[a];
    elves[a] = (elfPosition + recipes[elfPosition] + 1) % recipes.length;
  }
}
