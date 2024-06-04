import fs from "fs";
import assert from "assert";
import { part1, part2 } from "../src/day18";

describe("Day 18", function () {
  it("Part 1 - Sample", function () {
    const input = readInput("input/day18-sample.txt");
    assert.equal(part1(input), 1147);
  });

  it("Part 1", function () {
    const input = readInput("input/day18.txt");
    assert.equal(part1(input), 514944);
  });

  it("Part 2", function () {
    const input = readInput("input/day18.txt");
    assert.equal(part2(input), 193050);
  });
});

function readInput(path: string) {
  return fs.readFileSync(path).toString();
}
