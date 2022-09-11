import assert from "assert";
import { highestScore } from "../src/day09";

describe("Day 9", function () {
  it("Samples", function () {
    assert.equal(highestScore(9, 25), 32);
    assert.equal(highestScore(10, 1618), 8317);
    assert.equal(highestScore(13, 7999), 146373);
    assert.equal(highestScore(17, 1104), 2764);
    assert.equal(highestScore(21, 6111), 54718);
    assert.equal(highestScore(30, 5807), 37305);
  });

  it("Part 1", function () {
    assert.equal(highestScore(446, 71522), 390592);
  });

  it("Part 2", function () {
    if (process.env.SKIP_SLOW_TESTS) {
      this.skip();
    }
    assert.equal(highestScore(446, 7152200), 3277920293);
  });
});
