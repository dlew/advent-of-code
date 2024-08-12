def day10(data: str, repeat: int) -> int:
    code = data
    for _ in range(repeat):
        code = _look_and_say(code)
    return len(code)


def _look_and_say(code: str) -> str:
    count = 1
    curr_digit = code[0]
    out = []
    for digit in code[1:]:
        if digit == curr_digit:
            count += 1
        else:
            out.append(str(count))
            out.append(curr_digit)
            curr_digit = digit
            count = 1

    out.append(str(count))
    out.append(curr_digit)

    return "".join(out)
