	.intel_syntax
	.section .rodata

.io_format:
	.string "%d\12"
	.string "%f\12"
	.string "%c\12"
	.string "%s\12"

.io_format_in:
	.string "%d"
	.string "%f"
	.string "%c"

_constant:

	.globl b1;
	.type b1, @function
b1:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 4
#---- Assignment expression...
	mov %edi, 7
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
# Printing...
	push dword ptr [%ebp-4]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Returning...
	mov %edi, 1
	mov %eax, %edi
	leave
	ret

	.globl b2;
	.type b2, @function
b2:
	push %ebp
	mov %ebp, %esp
# Printing...
	push 2
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Returning...
	call b1
	mov %edi, %eax
	mov %eax, %edi
	leave
	ret

	.globl b3;
	.type b3, @function
b3:
	push %ebp
	mov %ebp, %esp
# Printing...
	push 3
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Returning...
#Adding expression...
	call b1
	mov %edi, %eax
	call b2
	mov %esi, %eax
	add %edi, %esi
	mov %eax, %edi
	leave
	ret

	.globl b4;
	.type b4, @function
b4:
	push %ebp
	mov %ebp, %esp
# Printing...
	push 4
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Returning...
#Adding expression...
	call b1
	mov %edi, %eax
#Adding expression...
	call b2
	mov %esi, %eax
	call b3
	mov %ebx, %eax
	add %esi, %ebx
	add %edi, %esi
	mov %eax, %edi
	leave
	ret

.text
	.globl main;
	.type main, @function

main:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 80
#---- Assignment expression...
	mov %edi, 99
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
# Printing...
	push dword ptr [%ebp-4]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
#Adding expression...
	call b1
	mov %edi, %eax
#Adding expression...
	call b2
	mov %esi, %eax
#Adding expression...
	call b3
	mov %ebx, %eax
	call b4
	mov %ecx, %eax
	add %ebx, %ecx
	add %esi, %ebx
	add %edi, %esi
	push %edi
	push [ offset flat:.io_format + 0]
	call printf
	add %esp, 8
	leave
	ret