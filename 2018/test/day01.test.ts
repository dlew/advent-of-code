import fs from "fs";
import { frequency, frequencyDuplicates } from "../src/day01";

import assert from "assert";

describe("Day 1", function () {
  it("Part 1", function () {
    assert.equal(frequency(readChanges()), 442);
  });

  it("Part 2", function () {
    assert.equal(frequencyDuplicates(readChanges()), 59908);
  });
});

function readChanges() {
  return fs
    .readFileSync("input/day01.txt")
    .toString()
    .trim()
    .split(/\r?\n/)
    .map((line) => Number.parseInt(line, 10));
}
