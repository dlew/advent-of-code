import re
from dataclasses import dataclass
from enum import auto, StrEnum


class _Direction(StrEnum):
    L = auto()
    R = auto()


class _Operation(StrEnum):
    AND = auto()
    OR = auto()


@dataclass(frozen=True)
class _ValueSignal:
    value: int
    out_wire: str


@dataclass(frozen=True)
class _WireSignal:
    in_wire: int
    out_wire: str


@dataclass(frozen=True)
class _NotGate:
    in_wire: str
    out_wire: str


@dataclass(frozen=True)
class _LogicGate:
    in_left_wire: str
    operation: _Operation
    in_right_wire: str
    out_wire: str


@dataclass(frozen=True)
class _Shift:
    in_wire: str
    direction: _Direction
    shift: int
    out_wire: str


def day7_part1(data: str) -> dict[str, int]:
    commands = _parse(data)
    return _execute(commands)


def day7_part2(data: str) -> dict[str, int]:
    commands = _parse(data)
    first_run = _execute(commands)

    override_commands = [
        c for c in commands if not isinstance(c, _ValueSignal) or c.out_wire != "b"
    ]
    override_commands.append(_ValueSignal(first_run["a"], "b"))
    return _execute(override_commands)


def _execute(commands: list) -> dict[str, int]:
    # Loop through all commands until each one has been executed.
    #
    # (I assume that no command is executed more than once, since there's no way
    # to conditionally *break* a loop in this simple language).
    wires = {}
    while len(commands) != 0:
        used_commands = set()

        for command in commands:
            if isinstance(command, _ValueSignal):
                wires[command.out_wire] = command.value
                used_commands.add(command)

            elif isinstance(command, _WireSignal) and command.in_wire in wires:
                wires[command.out_wire] = wires[command.in_wire]
                used_commands.add(command)

            elif isinstance(command, _NotGate) and command.in_wire in wires:
                wires[command.out_wire] = ~wires[command.in_wire] & 65535
                used_commands.add(command)

            elif (
                isinstance(command, _LogicGate)
                and command.in_left_wire in wires
                and command.in_right_wire in wires
            ):
                if command.operation == _Operation.AND:
                    wires[command.out_wire] = (
                        wires[command.in_left_wire] & wires[command.in_right_wire]
                    )
                else:
                    wires[command.out_wire] = (
                        wires[command.in_left_wire] | wires[command.in_right_wire]
                    )
                used_commands.add(command)

            elif isinstance(command, _Shift) and command.in_wire in wires:
                if command.direction == _Direction.L:
                    wires[command.out_wire] = wires[command.in_wire] << command.shift
                else:
                    wires[command.out_wire] = wires[command.in_wire] >> command.shift
                used_commands.add(command)

        commands = [command for command in commands if command not in used_commands]

    return wires


def _parse(data: str) -> list:
    value_signal_pattern = re.compile(r"(\d+) -> (\w+)")
    wire_signal_pattern = re.compile(r"(\w+) -> (\w+)")
    not_pattern = re.compile(r"NOT (\w+) -> (\w+)")
    and_or_pattern = re.compile(r"(\w+) (AND|OR) (\w+) -> (\w+)")
    shift_pattern = re.compile(r"(\w+) ([LR])SHIFT (\d+) -> (\w+)")
    commands = []
    for line in data.splitlines():
        signal_match = value_signal_pattern.match(line)
        if signal_match is not None:
            value, out_wire = signal_match.groups()
            commands.append(_ValueSignal(int(value), out_wire))
            continue

        wire_signal_match = wire_signal_pattern.match(line)
        if wire_signal_match is not None:
            in_wire, out_wire = wire_signal_match.groups()
            commands.append(_WireSignal(in_wire, out_wire))
            continue

        not_match = not_pattern.match(line)
        if not_match is not None:
            in_wire, out_wire = not_match.groups()
            commands.append(_NotGate(in_wire, out_wire))
            continue

        and_or_match = and_or_pattern.match(line)
        if and_or_match is not None:
            in_left_wire, operation, in_right_wire, out_wire = and_or_match.groups()
            commands.append(
                _LogicGate(in_left_wire, _Operation[operation], in_right_wire, out_wire)
            )

            # Tricky: sometimes the left "in" is a number; simulate using a value signal
            if in_left_wire.isdigit():
                commands.append(_ValueSignal(int(in_left_wire), in_left_wire))

            continue

        shift_match = shift_pattern.match(line)
        in_wire, direction, shift, out_wire = shift_match.groups()
        commands.append(_Shift(in_wire, _Direction[direction], int(shift), out_wire))

    return commands
