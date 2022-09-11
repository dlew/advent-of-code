import assert from "assert";
import {
  findMaxPowerGrid3x3,
  findMaxPowerGridAny,
  powerLevel,
} from "../src/day11";

describe("Day 11", function () {
  it("Power Level", function () {
    assert.equal(powerLevel(3, 5, 8), 4);
    assert.equal(powerLevel(122, 79, 57), -5);
    assert.equal(powerLevel(217, 196, 39), 0);
    assert.equal(powerLevel(101, 153, 71), 4);
  });

  it("Sample", function () {
    assert.equal(findMaxPowerGrid3x3(18), "33,45");
    assert.equal(findMaxPowerGrid3x3(42), "21,61");
  });

  it("Part 1", function () {
    assert.equal(findMaxPowerGrid3x3(9798), "44,37");
  });

  it("Sample 2", function () {
    assert.equal(findMaxPowerGridAny(18), "90,269,16");
    assert.equal(findMaxPowerGridAny(42), "232,251,12");
  });

  it("Part 2", function () {
    assert.equal(findMaxPowerGridAny(9798), "235,87,13");
  });
});
