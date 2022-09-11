import fs from "fs";

import assert from "assert";
import { Light, parseLight, simulate } from "../src/day10";

describe("Day 10", function () {
  it("Parse Light", function () {
    const input = "position=<-6, 10> velocity=< 2, -2>";
    const expected = new Light(-6, 10, 2, -2);
    assert.deepStrictEqual(parseLight(input), expected);
  });

  it("Sample", function () {
    simulate(readLights("input/day10-sample.txt"), 10); // Displays "HI" at second 3
  });

  it("Part 1 & 2", function () {
    simulate(readLights("input/day10.txt"), 62); // Displays "KZHGRJGZ" at second 10932
  });
});

function readLights(path: string) {
  return fs
    .readFileSync(path)
    .toString()
    .trim()
    .split(/\r?\n/)
    .map((line) => parseLight(line));
}
