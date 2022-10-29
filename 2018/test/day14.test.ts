import assert from "assert";
import { getTenRecipesAfter, recipeFirstAppears } from "../src/day14";

describe("Day 14", function () {
  it("Part 1 - Samples", function () {
    assert.equal(getTenRecipesAfter(9), "5158916779");
    assert.equal(getTenRecipesAfter(5), "0124515891");
    assert.equal(getTenRecipesAfter(18), "9251071085");
    assert.equal(getTenRecipesAfter(2018), "5941429882");
  });

  it("Part 1", function () {
    assert.equal(getTenRecipesAfter(170641), "2103141159");
  });

  it("Part 2 - Samples", function () {
    assert.equal(recipeFirstAppears("51589"), 9);
    assert.equal(recipeFirstAppears("01245"), 5);
    assert.equal(recipeFirstAppears("92510"), 18);
    assert.equal(recipeFirstAppears("59414"), 2018);
  });

  it("Part 2", function () {
    if (process.env.SKIP_SLOW_TESTS) {
      this.skip();
    }
    assert.equal(recipeFirstAppears("170641"), 20165733);
  });
});
