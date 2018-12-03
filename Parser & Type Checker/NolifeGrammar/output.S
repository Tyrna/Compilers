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

.text
	.globl main;
	.type main, @function

main:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 8
#---- Assignment expression...
	mov %edi, 1
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#---- Assignment expression...
	mov %edi, 5
#Assigned variable...
	mov dword ptr [%ebp-8], %edi
# Reading...
	mov %eax, %ebp
	sub %eax, 4
	push %eax
	push [ offset flat:.io_format_in + 0 ]
	call scanf
	add %esp, 8
# Printing...
	push dword ptr [%ebp-4]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	push dword ptr [%ebp-8]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
	leave
	ret