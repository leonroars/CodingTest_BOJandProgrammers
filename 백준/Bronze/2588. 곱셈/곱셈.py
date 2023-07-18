a = input()
b = input()
b_lst = []
for i in range(0, 3):
    b_lst.append(b[i])

a = int(a)
b = int(b)
print(a * int(b_lst[-1]))
print(a * int(b_lst[-2]))
print(a * int(b_lst[0]))
print(a * b)