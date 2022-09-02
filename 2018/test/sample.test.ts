import assert from "assert";
import { add } from "../src/sample";

describe("Sample", function () {
  describe("#add()", function () {
    it("should add two numbers", function () {
      assert.equal(add(1, 2), 3);
      assert.equal(add(-3, 2), -1);
      assert.equal(add(0, 0), 0);
    });
  });
});
