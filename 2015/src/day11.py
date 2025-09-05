def day11(password: str) -> str:
    code = _password_to_code(password)
    code = _remove_banned_letters(code)
    while True:
        code = _iterate_password(code)
        if _valid_password(code):
            return _code_to_password(code)


def _password_to_code(password: str) -> list[int]:
    return [ord(c) for c in password]


def _code_to_password(code: list[int]) -> str:
    return "".join([chr(d) for d in code])


_BANNED_LETTERS = set([ord(c) for c in ["i", "o", "l"]])


def _remove_banned_letters(code: list[int]) -> list[int]:
    for index in range(len(code)):
        if code[index] in _BANNED_LETTERS:
            first = code[:index]
            last = [97] * (len(code) - index - 1)
            return first + [code[index] + 1] + last
    return code


def _iterate_password(code: list[int]) -> list[int]:
    new_code = code.copy()
    for index in reversed(range(len(code))):
        if new_code[index] == 122:
            new_code[index] = 97
        elif new_code[index] + 1 in _BANNED_LETTERS:
            new_code[index] += 2
            return new_code
        else:
            new_code[index] += 1
            return new_code
    raise RuntimeError("How did you get here?!?")


def _valid_password(code: list[int]) -> bool:
    doubles = 0
    straight = 0
    longest_straight = 0

    double_last = None
    straight_last = None
    for c in code:
        # Detect non-overlapping pairs
        if double_last is None:
            double_last = c
        elif double_last == c:
            doubles += 1
            double_last = None
        else:
            double_last = c

        # Detect straights
        if straight_last is None or straight_last + 1 == c:
            straight += 1
        else:
            longest_straight = max(straight, longest_straight)
            straight = 1

        straight_last = c

    return longest_straight >= 3 and doubles >= 2
