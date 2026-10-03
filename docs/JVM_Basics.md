# JVM Basics

## JDK, JRE and JVM

**JVM (Java Virtual Machine)**: the program that actually *runs* Java code. It loads `.class` files, checks them, and executes the bytecode inside them, turning it into instructions for the real CPU. It also manages memory for us (garbage collection).

**JRE (Java Runtime Environment)**: everything needed to *run* a Java program: the JVM **plus** the standard class libraries (`String`, `ArrayList`, `Scanner`, `System`, …). It cannot compile code.

**JDK (Java Development Kit)**: everything needed to *write and build* Java programs: the JRE **plus** developer tools such as `javac` (compiler), `javap` (class file viewer), `jar`, `jdb` (debugger) and `jshell`.

They sit inside each other:

```text
JDK  =  JRE  +  development tools (javac, javap, jar, jdb, ...)
JRE  =  JVM  +  standard libraries (java.lang, java.util, ...)
JVM  =  the engine that executes bytecode
```

A developer needs the JDK. Someone who only runs a finished program needs just the runtime.

> Since Java 11, Oracle no longer ships a separate JRE download. The JDK includes the runtime, and the `jlink` tool can build a small custom runtime when only "running" is needed. The JDK / JRE / JVM split is still the right way to understand the parts.

## What is bytecode?

When we run `javac Main.java`, the compiler does **not** produce machine code for a specific CPU (Intel, Apple M-series, …). It produces **bytecode**: a compact, platform-neutral set of instructions designed for the JVM. Bytecode is stored in `.class` files.

We can look at the bytecode of our `Main` class with the `javap` tool:

```bash
javap -c -cp out com.mohammadahtisham.learntrack.ui.Main
```

```text
public static void main(java.lang.String[]);
  Code:
     0: getstatic     #7    // Field java/lang/System.out:Ljava/io/PrintStream;
     3: ldc           #13   // String Hello and welcome!
     5: iconst_0
     6: anewarray     #2    // class java/lang/Object
     9: invokevirtual #15   // Method java/io/PrintStream.printf:(...)
    12: pop
    13: return
```

Reading it line by line: get the static field `System.out`, load the constant string `"Hello and welcome!"`, build an empty argument array for `printf`, call `printf`, discard its return value, and return. Each line is one simple JVM instruction.

Every `.class` file also starts with the "magic number" `CAFEBABE`, followed by a version number. Our file has version **68**, which means "compiled for Java 24".

## Write once, run anywhere

Because `javac` produces bytecode instead of OS-specific machine code, the **same `.class` file** can run on Windows, macOS or Linux without recompiling. Only the JVM is platform-specific: each OS has its own JVM, and that JVM translates the common bytecode into instructions for its own machine. We write and compile the program once, and any computer with a suitable JVM can run it.

```text
Main.java --javac--> Main.class (bytecode) --> JVM on Windows --> runs
                                           --> JVM on macOS   --> runs
                                           --> JVM on Linux   --> runs
```

There is one condition: the JVM must be the **same Java version or newer** than the one used to compile. I tested this by running my Java 24 class file with Java 17:

```text
java.lang.UnsupportedClassVersionError: com/mohammadahtisham/learntrack/ui/Main has been
compiled by a more recent version of the Java Runtime (class file version 68.0), this version
of the Java Runtime only recognizes class file versions up to 61.0
```

Recompiling with `javac --release 17 ...` produces class file version 61, which then runs on Java 17 as well. So "run anywhere" really means "run on any platform that has a compatible JVM".
