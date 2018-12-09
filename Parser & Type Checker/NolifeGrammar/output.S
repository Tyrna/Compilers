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
	.string "A?"
	.string "A:"
	.float 1.7

	.globl readarray;
	.type readarray, @function
readarray:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 4
# Printing...
	push [ offset flat:_constant + 0 ]
	push [ offset flat:.io_format + 12 ]
	call printf
	add %esp, 8
#---- Assignment expression...
	mov %edi, 1
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#While Statement...
.L1:
#Less Than Expression...
	mov %edi, dword ptr [%ebp-4]
	mov %esi, 20
	cmp %edi, %esi
	jge .L2
	mov %edi, 1
	jmp .L3
.L2:
	mov %edi, 0
.L3:
	cmp %edi, 0
	je .L4
# Printing...
	push dword ptr [%ebp-4]
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
# Reading...
	mov %eax, %ebp
	sub %eax, 4
	push %eax
	push [ offset flat:.io_format_in + 0 ]
	call scanf
	add %esp, 8
#---- Assignment expression...
#Adding expression...
	mov %esi, dword ptr [%ebp-4]
	mov %ebx, 1
	add %esi, %ebx
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
	jmp .L1
.L4:
	leave
	ret

	.globl writearray;
	.type writearray, @function
writearray:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 4
# Printing...
	push [ offset flat:_constant + 3 ]
	push [ offset flat:.io_format + 12 ]
	call printf
	add %esp, 8
#---- Assignment expression...
	mov %edi, 1
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#While Statement...
.L5:
#Less Than Expression...
	mov %edi, dword ptr [%ebp-4]
	mov %ebx, 20
	cmp %edi, %ebx
	jge .L6
	mov %edi, 1
	jmp .L7
.L6:
	mov %edi, 0
.L7:
	cmp %edi, 0
	je .L8
# Printing...
	mov %eax, offset flat:.__main_ebp
	mov %eax, dword ptr [%eax]
	mov %ecx, dword ptr [%ebp-4]
	mov %ebx, %ecx
	sub %ebx, 0
	imul %ebx, 4
	add %ebx, -84
	add %ebx, %eax
	mov %ebx, [%ebx]
	mov %eax, offset flat:.__main_ebp
	mov %eax, dword ptr [%eax]
	push %ebx
	push [ offset flat:.io_format + 0 ]
	call printf
	add %esp, 8
#---- Assignment expression...
#Adding expression...
	mov %ebx, dword ptr [%ebp-4]
	mov %ecx, 1
	add %ebx, %ecx
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
	jmp .L5
.L8:
	leave
	ret

	.globl partition;
	.type partition, @function
partition:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 20
#---- Assignment expression...
	mov %ecx, dword ptr [%ebp+12]
	mov %ecx, [%ecx]
	mov %ebx, %ecx
	sub %ebx, 0
	imul %ebx, 4
	mov %eax, dword ptr [%ebp+8]
	add %eax, %ebx
	mov %edi, %eax
#Assigned variable...
	mov dword ptr [%ebp-12], %edi
#---- Assignment expression...
#Substraction expression...
	mov %edi, dword ptr [%ebp+12]
	mov %edi, [%edi]
	mov %ecx, 1
	sub %edi, %ecx
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#---- Assignment expression...
#Adding expression...
	mov %edi, dword ptr [%ebp+8]
	mov %edi, [%edi]
	mov %ecx, 1
	add %edi, %ecx
#Assigned variable...
	mov dword ptr [%ebp-8], %edi
#While Statement...
.L9:
	mov %edi, [ _constant + 6 ]
	push %edi
	fld dword ptr [%esp]
	fisttp dword ptr [%esp]
	pop %edi
	cmp %edi, 0
	je .L10
#---- Assignment expression...
#Substraction expression...
	mov %ecx, dword ptr [%ebp-8]
	push %ecx
	fild dword ptr [%esp]
	fstp dword ptr [%esp]
	pop %ecx
	push 1
	fild dword ptr [%esp]
	fstp dword ptr [%esp]
	pop %edx
#Float substraction...
	push %ecx
	fld dword ptr [%esp]
	add %esp, 4
	push %edx
	fsub dword ptr [%esp]
	add %esp, 4
	sub %esp, 4
	fstp dword ptr [%esp]
	pop %ecx
#Assigned variable...
	push %edi
	fld dword ptr [%esp]
	fisttp dword ptr [%esp]
	pop %edi
	mov dword ptr [%ebp-8], %edi
#While Statement...
.L11:
#Greater Than Expression...
	mov %eax, offset flat:.__main_ebp
	mov %eax, dword ptr [%eax]
	mov %edx, dword ptr [%ebp-8]
	mov %edi, %edx
	sub %edi, 0
	imul %edi, 4
	add %edi, -84
	add %edi, %eax
	mov %edi, [%edi]
	mov %edx, dword ptr [%ebp-12]
	cmp %edi, %edx
	jle .L12
	mov %edi, 1
	jmp .L13
.L12:
	mov %edi, 0
.L13:
	cmp %edi, 0
	je .L14
#---- Assignment expression...
#Substraction expression...
	mov %edx, dword ptr [%ebp-8]
	mov %eax, 1
	sub %edx, %eax
#Assigned variable...
	mov dword ptr [%ebp-8], %edi
	jmp .L11
.L14:
#---- Assignment expression...
#Adding expression...
	mov %edi, dword ptr [%ebp-4]
	mov %eax, 1
	add %edi, %eax
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#While Statement...
.L15:
#Less Than Expression...
	mov %eax, offset flat:.__main_ebp
	mov %eax, dword ptr [%eax]
	mov %eax, dword ptr [%ebp-4]
	mov %edi, %eax
	sub %edi, 0
	imul %edi, 4
	add %edi, -84
	add %edi, %eax
	mov %edi, [%edi]
	mov %eax, dword ptr [%ebp-12]
	cmp %edi, %eax
	jge .L16
	mov %edi, 1
	jmp .L17
.L16:
	mov %edi, 0
.L17:
	cmp %edi, 0
	je .L18
#---- Assignment expression...
#Adding expression...
	mov %eax, dword ptr [%ebp-4]
	mov %NOT ENOUGH REGS, 1
	add %eax, %NOT ENOUGH REGS
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
	jmp .L15
.L18:
#Less Than Expression...
	mov %edi, dword ptr [%ebp-4]
	mov %NOT ENOUGH REGS, dword ptr [%ebp-8]
	cmp %edi, %NOT ENOUGH REGS
	jge .L19
	mov %edi, 1
	jmp .L20
.L19:
	mov %edi, 0
.L20:
#If Statement...
	cmp %edi, 0
	je .L21
#---- Assignment expression...
	mov %eax, offset flat:.__main_ebp
	mov %eax, dword ptr [%eax]
	mov %NOT ENOUGH REGS, dword ptr [%ebp-4]
	mov %NOT ENOUGH REGS, %NOT ENOUGH REGS
	sub %NOT ENOUGH REGS, 0
	imul %NOT ENOUGH REGS, 4
	add %NOT ENOUGH REGS, -84
	add %NOT ENOUGH REGS, %eax
	mov %NOT ENOUGH REGS, [%NOT ENOUGH REGS]
#Assigned variable...
	mov dword ptr [%ebp-16], %edi
#---- Assignment expression...
	mov %eax, offset flat:.__main_ebp
	mov %eax, dword ptr [%eax]
	mov %NOT ENOUGH REGS, dword ptr [%ebp-8]
	mov %edi, %NOT ENOUGH REGS
	sub %edi, 0
	imul %edi, 4
	add %edi, -84
	add %edi, %eax
	mov %edi, [%edi]
#Assigned variable...
	mov %eax, offset flat:.__main_ebp
	mov %eax, dword ptr [%eax]
	mov %NOT ENOUGH REGS, dword ptr [%ebp-4]
	mov %ebx, %NOT ENOUGH REGS
	sub %ebx, 0
	imul %ebx, 4
	add %ebx, -84
	add %ebx, %eax
	mov dword ptr [%ebx], %edi
#---- Assignment expression...
	mov %edi, dword ptr [%ebp-16]
#Assigned variable...
	mov %eax, offset flat:.__main_ebp
	mov %eax, dword ptr [%eax]
	mov %NOT ENOUGH REGS, dword ptr [%ebp-8]
	mov %ebx, %NOT ENOUGH REGS
	sub %ebx, 0
	imul %ebx, 4
	add %ebx, -84
	add %ebx, %eax
	mov dword ptr [%ebx], %edi
	jmp .L22
.L21:
# Returning...
	mov %edi, dword ptr [%ebp-8]
	mov %eax, %edi
.L22:
	jmp .L9
.L10:
	leave
	ret

	.globl quicksort;
	.type quicksort, @function
quicksort:
	push %ebp
	mov %ebp, %esp
#Declaring variables. Amount of declarations * 4
	sub %esp, 4
#Less Than Expression...
	mov %edi, dword ptr [%ebp+12]
	mov %edi, [%edi]
	mov %NOT ENOUGH REGS, dword ptr [%ebp+8]
	mov %NOT ENOUGH REGS, [%NOT ENOUGH REGS]
	cmp %edi, %NOT ENOUGH REGS
	jge .L23
	mov %edi, 1
	jmp .L24
.L23:
	mov %edi, 0
.L24:
#If Statement...
	cmp %edi, 0
	je .L25
#---- Assignment expression...
#Calling a function...
	push %edx
	push %ebx
	push %esi
	push %ecx
	push %edi
	push %eax
#Save the base before the call...
	mov %NOT ENOUGH REGS, %esp
#Checking for constants...
	mov %NOT ENOUGH REGS, %ebp
	add %NOT ENOUGH REGS, 88
	push [%NOT ENOUGH REGS]
	mov %NOT ENOUGH REGS, %ebp
	add %NOT ENOUGH REGS, 88
	push [%NOT ENOUGH REGS]
	mov %NOT ENOUGH REGS, %ebp
	add %NOT ENOUGH REGS, 92
	push [%NOT ENOUGH REGS]
	call partition
	add %esp, 12
	pop %edx
	pop %ebx
	pop %esi
	pop %ecx
	pop %edi
	pop %eax
	mov %NOT ENOUGH REGS, %eax
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#Calling a procedure...
	push %edx
	push %ebx
	push %esi
	push %ecx
	push %eax
#Save the base before the call...
	mov %edi, %esp
#Checking for constants...
#Checking for variables...
	mov %NOT ENOUGH REGS, %ebp
	add %NOT ENOUGH REGS, 88
	push [%NOT ENOUGH REGS]
	mov %NOT ENOUGH REGS, %ebp
	add %NOT ENOUGH REGS, 88
	push [%NOT ENOUGH REGS]
	mov %NOT ENOUGH REGS, %ebp
	sub %NOT ENOUGH REGS, 4
	push %NOT ENOUGH REGS
	call quicksort
	add %esp, 12
	pop %edx
	pop %ebx
	pop %esi
	pop %ecx
	pop %eax
#Calling a procedure...
	push %edx
	push %ebx
	push %esi
	push %ecx
	push %eax
#Save the base before the call...
	mov %edi, %esp
#Checking for constants...
#Adding expression...
	mov %NOT ENOUGH REGS, dword ptr [%ebp-4]
	mov %NOT ENOUGH REGS, 1
	add %NOT ENOUGH REGS, %NOT ENOUGH REGS
	push %NOT ENOUGH REGS
	push %esp
	mov %NOT ENOUGH REGS, dword ptr [%ebp]
#Checking for variables...
	mov %NOT ENOUGH REGS, %ebp
	add %NOT ENOUGH REGS, 88
	push [%NOT ENOUGH REGS]
	mov %eax, %edi
	sub %eax, 4
	push %eax
	mov %NOT ENOUGH REGS, %ebp
	add %NOT ENOUGH REGS, 92
	push [%NOT ENOUGH REGS]
	call quicksort
	add %esp, 20
	pop %edx
	pop %ebx
	pop %esi
	pop %ecx
	pop %eax
.L25:
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
	sub %esp, 84
#---- Assignment expression...
#Substraction expression...
	mov %edi, 0
	mov %NOT ENOUGH REGS, 1
	sub %edi, %NOT ENOUGH REGS
#Assigned variable...
	mov dword ptr [%ebp-84], %edi
#---- Assignment expression...
	mov %edi, 1000
#Assigned variable...
	mov dword ptr [%ebp-4], %edi
#Calling a procedure...
	push %edx
	push %ebx
	push %esi
	push %ecx
	push %eax
#Save the base before the call...
	mov %edi, %esp
#Checking for constants...
#Checking for variables...
	call readarray
	pop %edx
	pop %ebx
	pop %esi
	pop %ecx
	pop %eax
#Calling a procedure...
	push %edx
	push %ebx
	push %esi
	push %ecx
	push %eax
#Save the base before the call...
	mov %edi, %esp
#Checking for constants...
#Checking for variables...
	call writearray
	pop %edx
	pop %ebx
	pop %esi
	pop %ecx
	pop %eax
#Calling a procedure...
	push %edx
	push %ebx
	push %esi
	push %ecx
	push %eax
#Save the base before the call...
	mov %edi, %esp
#Checking for constants...
	mov %NOT ENOUGH REGS, 1
	push %NOT ENOUGH REGS
	push %esp
	mov %NOT ENOUGH REGS, dword ptr [%ebp]
	mov %NOT ENOUGH REGS, 19
	push %NOT ENOUGH REGS
	push %esp
	mov %NOT ENOUGH REGS, dword ptr [%ebp]
#Checking for variables...
	mov %NOT ENOUGH REGS, %ebp
	sub %NOT ENOUGH REGS, 84
	push %NOT ENOUGH REGS
	mov %eax, %edi
	sub %eax, 4
	push %eax
	mov %eax, %edi
	sub %eax, 12
	push %eax
	call quicksort
	add %esp, 28
	pop %edx
	pop %ebx
	pop %esi
	pop %ecx
	pop %eax
#Calling a procedure...
	push %edx
	push %ebx
	push %esi
	push %ecx
	push %eax
#Save the base before the call...
	mov %edi, %esp
#Checking for constants...
#Checking for variables...
	call writearray
	pop %edx
	pop %ebx
	pop %esi
	pop %ecx
	pop %eax
	leave
	ret

.comm .__main_ebp,4,4
