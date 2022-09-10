import fs from "fs";

import assert from "assert";
import { Coordinate, largestNonInfiniteArea, safeAreaSize } from "../src/day06";

describe("Day 6", function () {
  it("Sample", function () {
    assert.equal(largestNonInfiniteArea(sampleInput), 17);
  });

  it("Part 1", function () {
    assert.equal(largestNonInfiniteArea(readCoordinates()), 3238);
  });

  it("Sample 2", function () {
    assert.equal(safeAreaSize(sampleInput, 32), 16);
  });

  it("Part 2", function () {
    assert.equal(safeAreaSize(readCoordinates(), 10000), 45046);
  });
});

const sampleInput = [
  new Coordinate(1, 1),
  new Coordinate(1, 6),
  new Coordinate(8, 3),
  new Coordinate(3, 4),
  new Coordinate(5, 5),
  new Coordinate(8, 9),
];

function readCoordinates() {
  return fs
    .readFileSync("input/day06.txt")
    .toString()
    .trim()
    .split(/\r?\n/)
    .map((line) => parseCoordinate(line));
}

function parseCoordinate(line: string): Coordinate {
  const split = line.split(", ");
  return new Coordinate(
    Number.parseInt(split[0], 10),
    Number.parseInt(split[1], 10)
  );
}
