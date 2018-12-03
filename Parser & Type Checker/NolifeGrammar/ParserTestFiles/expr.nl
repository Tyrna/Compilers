PROGRAM expr;

VAR a:INTEGER;
    b:CHARACTER;
    c:FLOAT;
    d:ARRAY [1..10] OF INTEGER;

BEGIN

   IF (b = b) THEN
    BEGIN
     a := a + a;
     c := c * a;
     c := a - c
    END;

   IF (c >= a) THEN
     b := b OR b;

   a := a AND c

END


  .intel_syntax
  .section .rodata
.io_format:
  .string "%f\12"
  .string "%d\12"
  .string "%c\12"
  .string "%s\12"
  .string "%f"
  .string "%d"
  .string "%c"
  .text
  .globl main;
  .type main, @function
_constant:
main:
  push   %ebp
  mov    %ebp, %esp
  sub    %esp, 12

# Read...
  mov    %ebx, %ebp
  sub    %ebx, 12
  push   %ebx
  push   [offset flat:.io_format + 16]
  call scanf
  add  %esp, 8

# Float Reference...
  mov    %ecx, dword ptr [%ebp - 12] 
  push   %ecx
  fld    dword ptr [%esp]
  add    %esp, 4
  sub    %esp, 8
  fstp   qword ptr [%esp]
  push   [offset flat:.io_format + 0]
  call   printf
  add    %esp, 12
  leave
  ret