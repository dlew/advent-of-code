import fs from "fs";
import assert from "assert";
import { part1, part2 } from "../src/day16";

describe("Day 16", function () {
  it("Part 1 - Sample", function () {
    const input = readInput("input/day16-sample.txt");
    assert.equal(part1(input), 1);
  });

  it("Part 1", function () {
    const input = readInput("input/day16-part1.txt");
    assert.equal(part1(input), 529);
  });

  it("Part 2", function () {
    const sampleInput = readInput("input/day16-part1.txt");
    const program = readInput("input/day16-part2.txt");
    assert.equal(part2(sampleInput, program), 573);
  });
});

function readInput(path: string) {
  return fs.readFileSync(path).toString();
}
