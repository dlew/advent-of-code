import fs from "fs";
import { checksum, findBox } from "../src/day02";

import assert from "assert";

describe("Day 2", function () {
  it("Part 1", function () {
    assert.equal(checksum(readIds()), 5976);
  });

  it("Part 2", function () {
    assert.equal(findBox(readIds()), "xretqmmonskvzupalfiwhcfdb");
  });
});

function readIds() {
  return fs.readFileSync("input/day02.txt").toString().trim().split(/\r?\n/);
}
