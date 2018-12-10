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
	.float 0.01

	.globl init;
	.type init, @function
init:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 8
#---- Assignment expression...
	mov %edi, 1
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#---- Assignment expression...
	mov %edi, 10
#Assigned variable...
	mov dword ptr [%ebp-8], %edi
#While Statement...
.L1:
#Less Equal Expression...
	mov %edi, dword ptr [%ebp-4]
	mov %esi, 10
	cmp %edi, %esi
	jg .L2
	mov %edi, 1
	jmp .L3
.L2:
	mov %edi, 0
.L3:
	cmp %edi, 0
	je .L4
#---- Assignment expression...
#Adding expression...
#Multiplication expression...
	mov %esi, dword ptr [%ebp-4]
	push %esi
	fild dword ptr [%esp]
	fstp dword ptr [%esp]
	pop %esi
	mov %ebx, [ _constant + 0 ]
#Float Multiplication...
	push %esi
	fld dword ptr [%esp]
	add %esp, 4
	push %ebx
	fmul dword ptr [%esp]
	add %esp, 4
	sub %esp, 4
	fstp dword ptr [%esp]
	pop %esi
	mov %ebx, dword ptr [%ebp-8]
	push %ebx
	fild dword ptr [%esp]
	fstp dword ptr [%esp]
	pop %ebx
#Float addition...
	push %esi
	fld dword ptr [%esp]
	add %esp, 4
	push %ebx
	fadd dword ptr [%esp]
	add %esp, 4
	sub %esp, 4
	fstp dword ptr [%esp]
	pop %esi
#Assigned variable...
	push %edi
	fld dword ptr [%esp]
	fisttp dword ptr [%esp]
	pop %edi
	mov %ebx, dword ptr [%ebp-4]
	mov %ebx, %ebx
	sub %ebx, 1
	imul %ebx, 4
	mov %eax, dword ptr [%ebp+8]
	add %eax, %ebx
	mov dword ptr [%eax], %edi
#---- Assignment expression...
#Adding expression...
	mov %edi, dword ptr [%ebp-4]
	mov %ebx, 1
	add %edi, %ebx
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#---- Assignment expression...
#Adding expression...
	mov %edi, dword ptr [%ebp-8]
	mov %ebx, 1
	add %edi, %ebx
#Assigned variable...
	mov dword ptr [%ebp-8], %edi
	jmp .L1
.L4:
	leave
	ret

	.globl writearray;
	.type writearray, @function
writearray:
	push %ebp
	mov %ebp, %esp
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx+4]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx+8]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx+12]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx+16]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx+20]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx+24]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx+28]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx+32]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Printing...
	mov %ebx, dword ptr [%ebp+8]
	push [%ebx+36]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
	leave
	ret

	.globl inc;
	.type inc, @function
inc:
	push %ebp
	mov %ebp, %esp
#---- Assignment expression...
#Adding expression...
	mov %edi, dword ptr [%ebp+8]
	mov %edi, [%edi]
	mov %ebx, 1
	add %edi, %ebx
#Assigned variable...
	mov %eax, dword ptr [%ebp+8]
	mov dword ptr [%eax], %edi
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
	sub %esp, 72
#Calling a procedure...
	push %esi
#Save the base before the call...
	mov %edi, %esp
#Checking for constants...
#Checking for variables...
	mov %ebx, %ebp
	sub %ebx, 72
	push %ebx
	call init
	add %esp, 4
	pop %esi
#Calling a procedure...
	push %esi
#Save the base before the call...
	mov %edi, %esp
#Checking for constants...
#Checking for variables...
	mov %ebx, %ebp
	sub %ebx, 72
	push %ebx
	call writearray
	add %esp, 4
	pop %esi
#---- Assignment expression...
	mov %edi, 1
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#While Statement...
.L5:
#Less Equal Expression...
	mov %edi, dword ptr [%ebp-4]
	mov %ebx, 10
	cmp %edi, %ebx
	jg .L6
	mov %edi, 1
	jmp .L7
.L6:
	mov %edi, 0
.L7:
	cmp %edi, 0
	je .L8
#---- Assignment expression...
#Multiplication expression...
	mov %ecx, dword ptr [%ebp-4]
	mov %ebx, %ecx
	sub %ebx, 1
	imul %ebx, 4
	add %ebx, -72
	add %ebx, %ebp
	mov %ebx, [%ebx]
	mov %ecx, dword ptr [%ebp-4]
	imul %ebx, %ecx
#Assigned variable...
	mov %ecx, dword ptr [%ebp-4]
	mov %ebx, %ecx
	sub %ebx, 1
	imul %ebx, 4
	add %ebx, -72
	add %ebx, %ebp
	mov dword ptr [%ebx], %edi
#Calling a procedure...
	push %ebx
	push %esi
#Save the base before the call...
	mov %edi, %esp
#Checking for constants...
#Checking for variables...
	mov %edx, %ebp
	sub %edx, 4
	mov %edx, [%edx]
	mov %ecx, %edx
	sub %ecx, 1
	imul %ecx, 4
	add %ecx, -72
	add %ecx, %ebp
	push %ecx
	call inc
	add %esp, 4
	pop %ebx
	pop %esi
#---- Assignment expression...
#Adding expression...
	mov %edi, dword ptr [%ebp-4]
	mov %ecx, 1
	add %edi, %ecx
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
	jmp .L5
.L8:
#Calling a procedure...
	push %ebx
	push %esi
#Save the base before the call...
	mov %edi, %esp
#Checking for constants...
#Checking for variables...
	mov %ecx, %ebp
	sub %ecx, 72
	push %ecx
	call writearray
	add %esp, 4
	pop %ebx
	pop %esi
	leave
	ret

.comm .__main_ebp,4,4
