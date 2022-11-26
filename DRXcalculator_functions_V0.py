import math

# we use math library for now until we can solve how to parse funcs.

function = {
    "sin": 1,
    "cos": 1,
    "tan": 1,
    "log": 1,
    "log": 1,
    "sum": 1,
    "prod": 1,
    "pi": 2,
    "e": 2
}


def sin(solve):
    print("hi")


def tan(solve):
    print("hi")


def cos(solve):
    print("hi")


def log(solve):
    print("hi")


def calcOp(solve, operator):
    if operator == 'sin':
        return sin(solve)
    elif operator == 'cos':
        return cos(solve)
    elif operator == 'tan':
        return tan(solve)
    elif operator == 'log':
        return log(solve)


def applyConstants(tokens):
    tokens = tokens.replace("pi", "3.14")
    tokens = tokens.replace("e", "2.71")

    return tokens


if __name__ == "__main__":
    print(applyConstants("sin(pi + 2 * e) + 1"))
