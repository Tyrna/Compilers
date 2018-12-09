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
	mov %edi, dword ptr [%ebp+8]
	mov %edi, [%edi]
	mov %esi, 1
	add %edi, %esi
	mov %eax, %edi
	leave
	ret

	.globl d2;
	.type d2, @function
d2:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 4
# Printing...
	mov %ebx, dword ptr [%ebp+12]
	push [%ebx]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
#---- Assignment expression...
	mov %edi, dword ptr [%ebp+8]
	mov %edi, [%edi]
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#---- Assignment expression...
	mov %edi, dword ptr [%ebp+12]
	mov %edi, [%edi]
#Assigned variable...
	mov %eax, dword ptr [%ebp+8]
	mov dword ptr [%eax], %edi
#---- Assignment expression...
	mov %edi, dword ptr [%ebp-4]
#Assigned variable...
	mov %eax, dword ptr [%ebp+12]
	mov dword ptr [%eax], %edi
	leave
	ret

	.globl d3;
	.type d3, @function
d3:
	push %ebp
	mov %ebp, %esp
#---- Assignment expression...
#Adding expression...
	mov %edi, dword ptr [%ebp+8]
	mov %edi, [%edi]
	mov %esi, 1
	add %edi, %esi
#Assigned variable...
	mov %eax, dword ptr [%ebp+8]
	mov dword ptr [%eax], %edi
#---- Assignment expression...
#Adding expression...
	mov %edi, dword ptr [%ebp+12]
	mov %edi, [%edi]
	mov %esi, 2
	add %edi, %esi
#Assigned variable...
	mov %eax, dword ptr [%ebp+12]
	mov dword ptr [%eax], %edi
#---- Assignment expression...
#Adding expression...
	mov %edi, dword ptr [%ebp+16]
	mov %edi, [%edi]
	mov %esi, 3
	add %edi, %esi
#Assigned variable...
	mov %eax, dword ptr [%ebp+16]
	mov dword ptr [%eax], %edi
#---- Assignment expression...
	mov %edi, dword ptr [%ebp+20]
	mov %edi, [%edi]
#Assigned variable...
	mov %eax, dword ptr [%ebp+20]
	mov dword ptr [%eax], %edi
	leave
	ret

	.globl d1;
	.type d1, @function
d1:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 4
#---- Assignment expression...
#Multiplication expression...
	mov %edi, dword ptr [%ebp+8]
	mov %edi, [%edi]
	mov %esi, 200
	imul %edi, %esi
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	push dword ptr [%ebp-4]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
#Calling a procedure...
	mov %edi, %ebp
	add %edi, 8
	push [%edi]
	mov %edi, %ebp
	sub %edi, 4
	push %edi
	call d2
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	push dword ptr [%ebp-4]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
#Calling a procedure...
	mov %edi, %ebp
	add %edi, 8
	push [%edi]
	mov %edi, %ebp
	add %edi, 8
	push [%edi]
	mov %edi, %ebp
	add %edi, 8
	push [%edi]
	mov %edi, %ebp
	sub %edi, 4
	push %edi
	call d3
	add %esp, 16
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	push dword ptr [%ebp-4]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
#Calling a procedure...
	mov %edi, %ebp
	sub %edi, 4
	push %edi
	mov %edi, %ebp
	sub %edi, 4
	push %edi
	mov %edi, %ebp
	sub %edi, 4
	push %edi
	mov %edi, %ebp
	sub %edi, 4
	push %edi
	call d3
	add %esp, 16
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	push dword ptr [%ebp-4]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
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
	sub %esp, 40
#---- Assignment expression...
	mov %edi, 1
#Assigned variable...
	mov dword ptr [%ebp-20], %edi
#---- Assignment expression...
	mov %edi, 2
#Assigned variable...
	mov dword ptr [%ebp-24], %edi
#---- Assignment expression...
	mov %edi, 3
#Assigned variable...
	mov dword ptr [%ebp-28], %edi
#---- Assignment expression...
	mov %edi, 4
#Assigned variable...
	mov dword ptr [%ebp-32], %edi
#---- Assignment expression...
	mov %edi, 1
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#---- Assignment expression...
	mov %edi, 2
#Assigned variable...
	mov dword ptr [%ebp-8], %edi
#---- Assignment expression...
	mov %edi, 3
#Assigned variable...
	mov dword ptr [%ebp-12], %edi
#---- Assignment expression...
	mov %edi, 4
#Assigned variable...
	mov dword ptr [%ebp-16], %edi
# Printing...
#Calling a function...
	mov %edi, %ebp
	sub %edi, 12
	push %edi
	call b4
	add %esp, 4
	mov %edi, %eax
	push %edi
	push [ offset flat:.io_format + 0]
	call printf
	add %esp, 8
#Calling a procedure...
	mov %edi, %ebp
	sub %edi, 20
	push %edi
	call d1
	add %esp, 4
# Printing...
	push dword ptr [%ebp-20]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
#Calling a function...
#Calling a function...
	mov %edi, 1
	push %edi
	mov %edi, dword ptr [%ebp]
	push %edi
	call b4
	add %esp, 8
	mov %edi, %eax
	push %edi
	call b4
	add %esp, 4
	mov %edi, %eax
	push %edi
	push [ offset flat:.io_format + 0]
	call printf
	add %esp, 8
	leave
	ret

.comm .__main_ebp,4,4
