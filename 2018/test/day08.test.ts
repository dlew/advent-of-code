import fs from "fs";

import assert from "assert";
import { sumMetadata, sumMetadataComplex } from "../src/day08";

describe("Day 8", function () {
  it("Sample", function () {
    const input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";
    const numbers = input.split(" ").map((num) => Number.parseInt(num, 10));
    assert.equal(sumMetadata(numbers), 138);
  });

  it("Part 1", function () {
    assert.equal(sumMetadata(readLicense()), 40701);
  });

  it("Sample 2", function () {
    const input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";
    const numbers = input.split(" ").map((num) => Number.parseInt(num, 10));
    assert.equal(sumMetadataComplex(numbers), 66);
  });

  it("Part 2", function () {
    assert.equal(sumMetadataComplex(readLicense()), 21399);
  });
});

function readLicense() {
  return fs
    .readFileSync("input/day08.txt")
    .toString()
    .trim()
    .split(" ")
    .map((num) => Number.parseInt(num, 10));
}
