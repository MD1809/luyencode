# luyencode
luyện tập

# ảnh
![Ảnh chụp màn hình 2024-09-20 100759](https://github.com/user-attachments/assets/a08af1a9-9087-4bae-a3ce-d0440b375b93)

# ảnh 2
![Ảnh chụp màn hình 2024-09-20 123358](https://github.com/user-attachments/assets/9dd9246f-23c2-48ab-aaac-a0c36af2638b)

# ảnh thử
![đợi](https://github.com/MD1809/luyencode/blob/7cb12d10723425e87282b1bc71c3fdf0b54b79ea/A%CC%89nh.png)

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

#define MAX 100

// Hàm kiểm tra xem một ký tự có phải là toán tử không
int isOperator(char c) {
    return (c == '+' || c == '-' || c == '*' || c == '/');
}

// Hàm trả về độ ưu tiên của toán tử
int precedence(char op) {
    if (op == '+' || op == '-') {
        return 1;
    }
    if (op == '*' || op == '/') {
        return 2;
    }
    return 0;
}

// Hàm thực hiện phép tính với hai toán hạng
int applyOperation(int a, int b, char op) {
    switch (op) {
        case '+': return a + b;
        case '-': return a - b;
        case '*': return a * b;
        case '/': return a / b;
    }
    return 0;
}

// Hàm chuyển biểu thức trung tố thành hậu tố
int infixToPostfix(char* expression, char* output) {
    char stack[MAX];
    int top = -1;
    int k = 0;

    for (int i = 0; expression[i]; i++) {
        // Nếu ký tự là số, thêm vào output
        if (isdigit(expression[i])) {
            output[k++] = expression[i];
        }
        // Nếu ký tự là dấu mở ngoặc, đẩy nó vào ngăn xếp
        else if (expression[i] == '(') {
            stack[++top] = expression[i];
        }
        // Nếu ký tự là dấu đóng ngoặc
        else if (expression[i] == ')') {
            while (top != -1 && stack[top] != '(') {
                output[k++] = stack[top--];
            }
            top--; // Bỏ dấu mở ngoặc '('
        }
        // Nếu ký tự là toán tử
        else if (isOperator(expression[i])) {
            while (top != -1 && precedence(stack[top]) >= precedence(expression[i])) {
                output[k++] = stack[top--];
            }
            stack[++top] = expression[i];
        }
    }

    // Đẩy các toán tử còn lại trong ngăn xếp vào output
    while (top != -1) {
        output[k++] = stack[top--];
    }
    output[k] = '\0';
    return 1;
}

// Hàm tính giá trị của biểu thức hậu tố
int evaluatePostfix(char* expression) {
    int stack[MAX];
    int top = -1;

    for (int i = 0; expression[i]; i++) {
        // Nếu là số, đẩy vào ngăn xếp
        if (isdigit(expression[i])) {
            stack[++top] = expression[i] - '0';
        }
        // Nếu là toán tử, lấy 2 số từ ngăn xếp và tính toán
        else {
            int val2 = stack[top--];
            int val1 = stack[top--];
            stack[++top] = applyOperation(val1, val2, expression[i]);
        }
    }
    return stack[top];
}

int main() {
    char expression[MAX], postfix[MAX];

    // Ví dụ: biểu thức nhập vào
    printf("Nhập biểu thức toán học: ");
    scanf("%s", expression);

    // Chuyển biểu thức từ trung tố sang hậu tố
    infixToPostfix(expression, postfix);

    // Tính giá trị của biểu thức hậu tố
    int result = evaluatePostfix(postfix);

    printf("Giá trị của biểu thức: %d\n", result);

    return 0;
}
