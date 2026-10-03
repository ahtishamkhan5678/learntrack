# Setup Instructions

## Environment

| Item | Value |
|---|---|
| JDK | Oracle JDK **24.0.1** (Java SE 24) |
| OS | macOS (Apple Silicon, arm64) |
| IDE | IntelliJ IDEA (Project SDK: 24, language level: 24) |

## 1. Install the JDK

1. Download the JDK for your OS from the Oracle website (or use any OpenJDK build such as Temurin).
2. Run the installer. On macOS it installs into `/Library/Java/JavaVirtualMachines/`.
3. Open a new terminal and check that both the runtime and the compiler are available.

## 2. Verify the installation

```bash
java -version
```

```text
java version "24.0.1" 2025-04-15
Java(TM) SE Runtime Environment (build 24.0.1+9-30)
Java HotSpot(TM) 64-Bit Server VM (build 24.0.1+9-30, mixed mode, sharing)
```

```bash
javac -version
```

```text
javac 24.0.1
```

`java` is the launcher that starts the JVM. `javac` is the compiler, which only comes with the **JDK**. If `javac` is found, the JDK (not just a runtime) is installed correctly.

> On macOS, `/usr/libexec/java_home -V` lists every installed JDK. My machine also has OpenJDK 17, but JDK 24 is the default and is the one used for this project.

## 3. "Hello World" run

The first program is `Main.java` in the package `com.mohammadahtisham.learntrack.ui`.

### Code

```java
package com.mohammadahtisham.learntrack.ui;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
    }
}
```

### Compile (from the project root)

```bash
javac -d out src/com/mohammadahtisham/learntrack/ui/Main.java
```

- `javac` turns the `.java` source file into a `.class` file containing **bytecode**.
- `-d out` puts the compiled files in the `out` folder. `javac` creates sub-folders that match the package, so the result is `out/com/mohammadahtisham/learntrack/ui/Main.class`.

### Run

```bash
java -cp out com.mohammadahtisham.learntrack.ui.Main
```

- `-cp out` (classpath) tells the JVM where to look for `.class` files.
- The last argument is the **fully qualified class name** (package + class, with dots), not a file path. `javac` reads files from disk, while `java` asks the JVM to load a *class* by name.

### Output

```text
Hello and welcome!
```

### Running from IntelliJ IDEA

Open `Main.java` and click the green ▶ icon next to `main`. IntelliJ compiles into `out/production/Assignment/` and shows the same output in the Run window.

## 4. Running LearnTrack (all source files)

Once the project has several classes, compile all of them together:

```bash
javac -d out $(find src -name "*.java")
java -cp out com.mohammadahtisham.learntrack.ui.Main
```
