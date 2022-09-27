import assert from "assert";
import fs from "fs";
import { parseInitialState, parsePositiveRules, sumPots } from "../src/day12";

describe("Day 12", function () {
  it("Sample", function () {
    const input = readInput("input/day12-sample.txt");
    const initialState = parseInitialState(input);
    const positiveRules = parsePositiveRules(input);
    assert.equal(sumPots(initialState, positiveRules, 20), 325);
  });

  it("Part 1", function () {
    const input = readInput("input/day12.txt");
    const initialState = parseInitialState(input);
    const positiveRules = parsePositiveRules(input);
    assert.equal(sumPots(initialState, positiveRules, 20), 2840);
  });

  // This test will never finish (there's just way too many generations) but by reading the
  // output you can tell there's a pattern which you can use to deduce the final result
  it.skip("Part 2", function () {
    const input = readInput("input/day12.txt");
    const initialState = parseInitialState(input);
    const positiveRules = parsePositiveRules(input);
    assert.equal(sumPots(initialState, positiveRules, 50000000000), 2000000001684);
  });
});

function readInput(path: string) {
  return fs.readFileSync(path).toString().trim();
}
