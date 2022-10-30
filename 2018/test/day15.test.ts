import fs from "fs";
import assert from "assert";
import { keepElvesAlive, simulateCombat } from "../src/day15";

describe("Day 15", function () {
  it("Part 1 - Sample 1", function () {
    const input = readInput("input/day15-sample1.txt");
    assert.equal(simulateCombat(input), 27730);
  });

  it("Part 1 - Sample 2", function () {
    const input = readInput("input/day15-sample2.txt");
    assert.equal(simulateCombat(input), 36334);
  });

  it("Part 1 - Sample 3", function () {
    const input = readInput("input/day15-sample3.txt");
    assert.equal(simulateCombat(input), 39514);
  });

  it("Part 1 - Sample 4", function () {
    const input = readInput("input/day15-sample4.txt");
    assert.equal(simulateCombat(input), 27755);
  });

  it("Part 1 - Sample 5", function () {
    const input = readInput("input/day15-sample5.txt");
    assert.equal(simulateCombat(input), 28944);
  });

  it("Part 1 - Sample 6", function () {
    const input = readInput("input/day15-sample6.txt");
    assert.equal(simulateCombat(input), 18740);
  });

  it("Part 1", function () {
    if (process.env.SKIP_SLOW_TESTS) {
      this.skip();
    }
    const input = readInput("input/day15.txt");
    assert.equal(simulateCombat(input), 188576);
  });

  it("Part 2 - Sample 1", function () {
    const input = readInput("input/day15-sample1.txt");
    assert.equal(keepElvesAlive(input), 4988);
  });

  it("Part 2 - Sample 2", function () {
    const input = readInput("input/day15-sample3.txt");
    assert.equal(keepElvesAlive(input), 31284);
  });

  it("Part 2 - Sample 3", function () {
    const input = readInput("input/day15-sample4.txt");
    assert.equal(keepElvesAlive(input), 3478);
  });

  it("Part 2 - Sample 4", function () {
    const input = readInput("input/day15-sample5.txt");
    assert.equal(keepElvesAlive(input), 6474);
  });

  it("Part 2 - Sample 5", function () {
    const input = readInput("input/day15-sample6.txt");
    assert.equal(keepElvesAlive(input), 1140);
  });

  it("Part 2", function () {
    if (process.env.SKIP_SLOW_TESTS) {
      this.skip();
    }
    const input = readInput("input/day15.txt");
    assert.equal(keepElvesAlive(input), 57112);
  });
});

function readInput(path: string) {
  return fs.readFileSync(path).toString();
}
