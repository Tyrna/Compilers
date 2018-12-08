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
	.float 3.7

	.globl b1;
	.type b1, @function
b1:
	push %ebp
	mov %ebp, %esp
#---- Assignment expression...
	mov %edi, dword ptr [%ebp+8]
	mov %edi, [%edi]
#Assigned variable...
	mov %eax, offset flat:.__main_ebp
	mov %eax, dword ptr [%eax]
	mov dword ptr [%eax-12], %edi
# Printing...
	sub %esp, 8
	mov %ebx, dword ptr [%ebp+8]
	fld dword ptr [%ebx]
	fstp qword ptr [%esp]
	push [ offset flat:.io_format + 4 ]
	call printf
	add %esp, 12
# Returning...
	mov %edi, dword ptr [%ebp+8]
	mov %edi, [%edi]
	mov %eax, %edi
	leave
	ret

.text
	.globl main;
	.type main, @function

main:
	push %ebp
	mov %ebp, %esp
	mov %eax, offset flat:.__main_ebp
	mov dword ptr [%eax], %ebp
#Declaring variables. Amount of declarations * 4
	sub %esp, 24
#---- Assignment expression...
	push 2
	fild dword ptr [%esp]
	fstp dword ptr [%esp]
	pop %edi
#Assigned variable...
	mov dword ptr [%ebp-20], %edi
#---- Assignment expression...
	mov %edi, [ _constant + 0 ]
#Assigned variable...
	mov dword ptr [%ebp-16], %edi
# Printing...
	mov %esi, %ebp
	sub %esi, 20
	mov %edi, %esi
	sub %edi, 1
	imul %edi, 4
	add %edi, -20
	add %edi, %ebp
	mov %ebx, %edi
	mov %edi, %ebp
	sub %edi, %ebx
	push %edi
	call b1
	add %esp, 4
	mov %edi, %eax
	push %edi
	push [ offset flat:.io_format + 0]
	call printf
	add %esp, 8
	leave
	ret

.comm .__main_ebp,4,4
