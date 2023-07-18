king = 1
queen = 1
rook = 2
knight = 2
bishop = 2
pawn = 8
k, q, r, b, kn, p = map(int, input().split())
print(king-k, queen-q, rook-r, bishop-b, knight-kn, pawn-p)
