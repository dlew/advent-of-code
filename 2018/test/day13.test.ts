import assert from "assert";
import fs from "fs";
import { findFirstCrash, findLastSurvivingCart } from "../src/day13";

describe("Day 13", function () {
  it("Part 1 - Sample", function () {
    const input = readInput("input/day13-sample.txt");
    assert.equal(findFirstCrash(input), "7,3");
  });

  it("Part 1", function () {
    const input = readInput("input/day13.txt");
    assert.equal(findFirstCrash(input), "28,107");
  });

  it("Part 2 - Sample", function () {
    const input = readInput("input/day13-sample2.txt");
    assert.equal(findLastSurvivingCart(input), "6,4");
  });

  it("Part 2", function () {
    const input = readInput("input/day13.txt");
    assert.equal(findLastSurvivingCart(input), "36,123");
  });
});

function readInput(path: string) {
  return fs.readFileSync(path).toString();
}
