import fs from "fs";

import assert from "assert";
import {
  BeginShift,
  FallAsleep,
  findGuardStrategyOne,
  findGuardStrategyTwo,
  parseRecord,
  WakeUp,
} from "../src/day04";

describe("Day 4", function () {
  it("Parse Guard Record", function () {
    const input = "[1518-05-12 00:02] Guard #439 begins shift";
    const expected = new BeginShift(new Date(1518, 4, 12, 0, 2), 439);
    assert.deepStrictEqual(parseRecord(input), expected);
  });

  it("Parse Falls Asleep Record", function () {
    const input = "[1518-07-16 00:10] falls asleep";
    const expected = new FallAsleep(new Date(1518, 6, 16, 0, 10));
    assert.deepStrictEqual(parseRecord(input), expected);
  });

  it("Parse Wake Up Record", function () {
    const input = "[1518-06-23 00:52] wakes up";
    const expected = new WakeUp(new Date(1518, 5, 23, 0, 52));
    assert.deepStrictEqual(parseRecord(input), expected);
  });

  it("Part 1", function () {
    assert.equal(findGuardStrategyOne(readRecords()), 21956);
  });

  it("Part 2", function () {
    assert.equal(findGuardStrategyTwo(readRecords()), 134511);
  });
});

function readRecords() {
  return fs
    .readFileSync("input/day04.txt")
    .toString()
    .trim()
    .split(/\r?\n/)
    .map((line) => parseRecord(line));
}
