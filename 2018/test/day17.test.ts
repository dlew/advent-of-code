import fs from "fs";
import assert from "assert";
import { part1, part2 } from "../src/day17";

describe("Day 17", function () {
  it("Part 1 - Sample", function () {
    const input = readInput("input/day17-sample.txt");
    assert.equal(part1(input), 57);
  });

  it("Part 1", function () {
    const input = readInput("input/day17.txt");
    assert.equal(part1(input), 37649);
  });

  it("Part 2 - Sample", function () {
    const input = readInput("input/day17-sample.txt");
    assert.equal(part2(input), 29);
  });

  it("Part 2", function () {
    const input = readInput("input/day17.txt");
    assert.equal(part2(input), 30112);
  });
});

function readInput(path: string) {
  return fs.readFileSync(path).toString();
}
