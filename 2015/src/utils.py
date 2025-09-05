from typing import TypeVar

T = TypeVar("T")


def raise_none(value: T | None) -> T:
    """Raise ValueError if value is None, otherwise return the value."""
    if value is None:
        raise ValueError("Expected non-None value, got None")
    return value
