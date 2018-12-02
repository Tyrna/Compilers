	.intel_syntax
	.section .rodata

.io_format:

	.string "%d\12"
	.string "%f\12"
	.string "%s\12"
	.string "%c\12"

_constant:

	.float 3.3
	.float 2.5

.text
	.globl main;
	.type main, @function

main:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 12
#---- Assignment expression...
	mov %edi, [ offset flat:_constant + 0 ]
#Assigned variable...
	fld dword ptr [%edi]
	fstp dword ptr [%ebp-4]
# Printing...
#Adding expression...
	mov %edi, dword ptr [%ebp-4]
	mov %esi, [ offset flat:_constant + 4 ]
#Float addition...
	fld dword ptr [%ebp-4]
	fadd dword ptr [%esi]
	sub %esp, 4
	fstp dword ptr [%esp]
	pop %edi
	sub %esp, 4
	push %edi
	fld dword ptr [%esp]
	fstp qword ptr [%esp]
	push [ offset flat:.io_format + 4 ]
	call printf
	add %esp, 12
	leave
	ret