lst = []
mx_num = 0
indx = 0
for i in range(9):
    N = int(input())
    lst.append(N)
    if N > mx_num:
        mx_num = N
        indx = i
print(mx_num)
print(indx+1)