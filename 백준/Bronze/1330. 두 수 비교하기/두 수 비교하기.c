#include <stdio.h>

static int compare(int p, int q) {
    if(p > q){return 1;}
    if(p == q){return 0;}
    return -1;
}


main()
{   
    // 0. 필요 변수 선언
    int A, B, result;
    char C;
    
    // 1. 입력 받아 공백 기준 구분 후 A, B 메모리 주소에 값 저장.
    scanf("%d %d", &A, &B);
    
    result = compare(A, B);
    
    // 2. Control Flow 작성
    if(result == 1){printf(">");}
    if(result == 0){printf("==");}
    if(result == -1){printf("<");}
}