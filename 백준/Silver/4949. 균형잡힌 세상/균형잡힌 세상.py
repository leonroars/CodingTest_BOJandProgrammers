def change(str):
    if str == "]":
        t = "["
        return t
    elif str == ")":
        return "("


while True:
    tmp = 0
    str = input()
    stack = []
    if str == ".":
        break
    lit = list(str)
    for i in lit:
        if i == "[" or i =="(":
            stack.append(i)
        elif i =="]" or i == ")":
            if len(stack)==0:
                print("no")
                tmp =1

                break
            check = stack.pop(-1)
            if check == change(i):
                continue
            else:
                tmp=1
                print("no")
                break
    if len(stack) ==0 and tmp ==0:
        print("yes")
    elif len(stack) != 0 and tmp ==0:
        print("no")