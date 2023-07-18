def notselfnum(num):
    num = num + sum(map(int, str(num)))
    return num
Notselfnum = []
for p in range(1, 10001):
    Notselfnum.append(notselfnum(p))

for q in range(1, 10001):
    if q not in Notselfnum:
        print(q)

