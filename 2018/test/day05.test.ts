import fs from "fs";

import assert from "assert";
import { react, removeBestUnitType } from "../src/day05";

describe("Day 5", function () {
  it("Sample", function () {
    assert.equal(react("dabAcCaCBAcCcaDA"), 10);
  });

  it("Part 1", function () {
    assert.equal(react(readPolymer()), 10774);
  });

  it("Sample 2", function () {
    assert.equal(removeBestUnitType("dabAcCaCBAcCcaDA"), 4);
  });

  it("Part 2", function () {
    if (process.env.SKIP_SLOW_TESTS) {
      this.skip();
    }
    assert.equal(removeBestUnitType(readPolymer()), 5122);
  });
});

function readPolymer() {
  return fs.readFileSync("input/day05.txt").toString().trim();
}
