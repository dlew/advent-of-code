import _ from "lodash";
import * as util from "util";

const STATE_REGEX = /.*\[(\d+), (\d+), (\d+), (\d+)]/;
const INSTRUCTION_REGEX = /(\d+) (\d+) (\d+) (\d+)/;

export function part1(input: string) {
  return parseSamples(input).filter(
    (sample) => possibleMatches(sample, allFunctions).length >= 3
  ).length;
}

export function part2(sampleInput: string, program: string) {
  const samples = parseSamples(sampleInput);

  // Build up all the possibilities for each opcode
  const opCodePossibilities = new Map<number, Set<number>>();
  samples.forEach((sample) => {
    const opcode = sample.instruction.opcode;
    const matches = possibleMatches(sample, allFunctions);
    const possibilities = opCodePossibilities.get(opcode) ?? new Set<number>();
    matches.forEach((match) => possibilities.add(match));
    opCodePossibilities.set(opcode, possibilities);
  });

  // Use deduction to narrow down opcodes to functions
  const functionMap = new Map<number, number>();
  while (opCodePossibilities.size !== 0) {
    const match = [...opCodePossibilities.entries()].find(
      ([_, possibilities]) => possibilities.size === 1
    );

    if (match === undefined) {
      console.log(
        `Cannot solve; possibilities=${util.inspect(opCodePossibilities)}`
      );
      break;
    }

    const opcode = match[0];
    const functionIndex = [...match[1].values()][0];

    functionMap.set(opcode, functionIndex);
    opCodePossibilities.delete(opcode);
    opCodePossibilities.forEach((possibility) => {
      possibility.delete(functionIndex);
    });
  }

  // Run the program
  const registers = [0, 0, 0, 0];
  parseProgram(program).forEach((instruction) => {
    const functionIndex = functionMap.get(instruction.opcode)!;
    allFunctions[functionIndex](registers, instruction);
  });

  return registers[0];
}

// Given a sample, which
function possibleMatches(
  sample: Sample,
  operations: Array<(registers: Array<number>, ins: Instruction) => void>
): Array<number> {
  const matchingOps = [];
  for (let a = 0; a < operations.length; a++) {
    const registers = [...sample.before];
    operations[a](registers, sample.instruction);
    if (_.isEqual(registers, sample.after)) {
      matchingOps.push(a);
    }
  }
  return matchingOps;
}

// Models

class Sample {
  constructor(
    readonly before: Array<number>,
    readonly instruction: Instruction,
    readonly after: Array<number>
  ) {}
}

class Instruction {
  constructor(
    readonly opcode: number,
    readonly a: number,
    readonly b: number,
    readonly c: number
  ) {}
}

// Functions

function addr(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] + registers[ins.b];
}

function addi(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] + ins.b;
}

function mulr(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] * registers[ins.b];
}

function muli(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] * ins.b;
}

function banr(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] & registers[ins.b];
}

function bani(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] & ins.b;
}

function borr(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] | registers[ins.b];
}

function bori(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] | ins.b;
}

function setr(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a];
}

function seti(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = ins.a;
}

function gtir(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = ins.a > registers[ins.b] ? 1 : 0;
}

function gtri(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] > ins.b ? 1 : 0;
}

function gtrr(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] > registers[ins.b] ? 1 : 0;
}

function eqir(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = ins.a === registers[ins.b] ? 1 : 0;
}

function eqri(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] === ins.b ? 1 : 0;
}

function eqrr(registers: Array<number>, ins: Instruction) {
  registers[ins.c] = registers[ins.a] === registers[ins.b] ? 1 : 0;
}

const allFunctions = [
  addr,
  addi,
  mulr,
  muli,
  banr,
  bani,
  borr,
  bori,
  setr,
  seti,
  gtir,
  gtri,
  gtrr,
  eqir,
  eqri,
  eqrr,
];

// Parsing

function parseSamples(input: string): Array<Sample> {
  return _.chunk(input.split(/\r?\n/), 4).map((sample) => {
    return new Sample(
      regexpMatchToNumberArray(sample[0].match(STATE_REGEX)!),
      parseInstruction(sample[1]),
      regexpMatchToNumberArray(sample[2].match(STATE_REGEX)!)
    );
  });
}

function parseProgram(input: string): Array<Instruction> {
  return input
    .split(/\r?\n/)
    .map((instruction) => parseInstruction(instruction));
}

function parseInstruction(instruction: string): Instruction {
  const match = regexpMatchToNumberArray(instruction.match(INSTRUCTION_REGEX)!);
  return new Instruction(match[0], match[1], match[2], match[3]);
}

function regexpMatchToNumberArray(regexMatch: RegExpMatchArray): Array<number> {
  return regexMatch.slice(1, 5).map((n) => parseInt(n));
}
