H, M = map(int, input().split())
cook_total = int(input())
cook_hour = cook_total // 60
cook_min = cook_total % 60
if M + cook_min > 60:
    if H + cook_hour + 1 >= 24:
        print(H + cook_hour -23, M + cook_min - 60)
    else:
        print(H + cook_hour + 1, M + cook_min - 60)
elif M + cook_min == 60:
    if H + cook_hour + 1 >= 24:
        print(H + cook_hour - 23, 0)
    else:
        print(H + cook_hour + 1, 0)
else:
    if H + cook_hour >= 24:
        print(H + cook_hour - 24, M + cook_min)
    else:
        print(H + cook_hour, M + cook_min)