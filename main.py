# DRX Calculator V1
# By: Team DRX
# Date: 11/20/2022
# Description: Parse a string equation and evaluate its content

operatorPrecedence = {
    "+": 1,
    "-": 1,
    "/": 2,
    "*": 2,
    "^": 3
}


def calcOp(x, y, operator):
    if operator == '+':
        return x + y
    if operator == '-':
        return x - y
    if operator == '*':
        return x * y
    if operator == '/':
        return x // y
    if operator == '^':
        return x ** y


def evaluate(tokens):
    numbers = []
    operators = []
    index = 0

    while index < len(tokens):

        if tokens[index] == ' ':
            index += 1
            continue

        elif tokens[index] == '(':
            operators.append(tokens[index])

        elif tokens[index].isdigit():
            number = 0
            decimal = False
            place = 1

            while index < len(tokens) and (tokens[index].isdigit() or tokens[index] == "."):
                if decimal:
                    number = number + (float(tokens[index]) / (10 ** place))
                    place = place + 1
                elif tokens[index].isdigit():
                    number = (number * 10) + float(tokens[index])
                if tokens[index] == '.':
                    decimal = True
                index += 1
            numbers.append(number)
            index -= 1

        elif tokens[index] == ')':

            while len(operators) != 0 and operators[-1] != '(':
                numbers2 = numbers.pop()
                numbers1 = numbers.pop()
                operator = operators.pop()

                numbers.append(calcOp(numbers1, numbers2, operator))

            operators.pop()

        else:

            while len(operators) != 0 and operatorPrecedence.get(operators[-1], 0) >= \
                    operatorPrecedence.get(tokens[index], 0):
                numbers2 = numbers.pop()
                numbers1 = numbers.pop()
                operator = operators.pop()

                numbers.append(calcOp(numbers1, numbers2, operator))

            operators.append(tokens[index])
        index += 1

    while len(operators) != 0:
        numbers2 = numbers.pop()
        numbers1 = numbers.pop()
        operator = operators.pop()
        numbers.append(calcOp(numbers1, numbers2, operator))

    return numbers[-1]


if __name__ == "__main__":
    print(evaluate("10.1 + 2 * 6"))
    print(evaluate("100 * 0.02 + 12"))
    print(evaluate("100 * ( 2 + 12 )"))
    print(evaluate("100 * ( 2 + 12 ) / 14"))
