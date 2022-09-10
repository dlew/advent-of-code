import fs from "fs";

import assert from "assert";
import { orderSteps, orderStepsWithWorkers, parseGraph } from "../src/day07";

describe("Day 7", function () {
  it("Sample", function () {
    assert.equal(orderSteps(readGraph("input/day07-sample.txt")), "CABDFE");
  });

  it("Part 1", function () {
    assert.equal(
      orderSteps(readGraph("input/day07.txt")),
      "BDHNEGOLQASVWYPXUMZJIKRTFC"
    );
  });

  it("Sample 2", function () {
    assert.equal(
      orderStepsWithWorkers(readGraph("input/day07-sample.txt"), 2, 0),
      15
    );
  });

  it("Part 2", function () {
    assert.equal(
      orderStepsWithWorkers(readGraph("input/day07.txt"), 5, 60),
      1107
    );
  });
});

function readGraph(path: string) {
  const text = fs.readFileSync(path).toString().trim();

  return parseGraph(text);
}
