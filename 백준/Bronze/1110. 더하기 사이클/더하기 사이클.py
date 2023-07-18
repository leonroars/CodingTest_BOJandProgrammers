import sys

N = sys.stdin.readline().rstrip()
cnt = 0
turn_num = N
while True:
    if int(turn_num) < 10:
        turn_num = '0' + turn_num
        hap = str(int(turn_num[0]) + int(turn_num[-1]))
        turn_num = turn_num[-1] + hap[-1]
        cnt += 1
    else:
        hap = str(int(turn_num[0]) + int(turn_num[-1]))
        turn_num = turn_num[-1] + hap[-1]
        cnt += 1
    if int(turn_num) == int(N):
        break
print(cnt)