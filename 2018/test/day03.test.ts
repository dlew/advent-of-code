import fs from "fs";

import assert from "assert";
import {
  Claim,
  findClaimWithoutOverlap,
  numberClaimOverlaps,
  parseClaim,
} from "../src/day03";

describe("Day 3", function () {
  it("Parse Claim", function () {
    const input = "#25 @ 147,701: 11x16";
    const expectedClaim = new Claim(25, 147, 701, 11, 16);
    assert.deepStrictEqual(parseClaim(input), expectedClaim);
  });

  it("Part 1", function () {
    assert.equal(numberClaimOverlaps(readClaims()), 108961);
  });

  it("Part 2", function () {
    assert.equal(findClaimWithoutOverlap(readClaims()), 681);
  });
});

function readClaims() {
  return fs
    .readFileSync("input/day03.txt")
    .toString()
    .trim()
    .split(/\r?\n/)
    .map((line) => parseClaim(line));
}
