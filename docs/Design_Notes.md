# Design Notes

## 1. Why ArrayList instead of an array?

| Array (`Student[]`) | ArrayList (`List<Student>`) |
|---|---|
| Size is fixed when it is created | Grows automatically when we call `add()` |
| We must track how many slots are used ourselves | `size()` and `isEmpty()` are built in |
| Adding past the end throws `ArrayIndexOutOfBoundsException` | `add()` always works |
| No helper methods | `add`, `get`, `remove`, `contains`, for-each loop |

LearnTrack does not know in advance how many students, courses or enrollments the admin will add,
so a fixed-size array would either waste memory or run out of space. `ArrayList` grows as needed
and keeps the service code short and readable.

Generics (`List<Student>`) make the list type-safe: only `Student` objects can be added, and no
casting is needed when reading them back.

The fields are declared with the interface type and created with the class:

```java
private List<Student> students = new ArrayList<>();
```

This is polymorphism: the rest of the code only depends on `List`, so the implementation could be
changed later (for example to `LinkedList`) without changing any method.

`getEnrollmentsForStudent` also creates a new `ArrayList` for its result, because the number of
matching enrollments is not known until the loop has finished.

## 2. Where static members are used and why

| Where | Static member | Why static |
|---|---|---|
| `util.IdGenerator` | `studentIdCounter`, `courseIdCounter`, `enrollmentIdCounter` | There must be **one shared counter** for the whole program. If each object had its own counter, ids would repeat. |
| `util.IdGenerator` | `getNextStudentId()`, `getNextCourseId()`, `getNextEnrollmentId()` | Called as `IdGenerator.getNextStudentId()` without creating an object. |
| `util.InputValidator` | `requireNonEmpty`, `validateEmail`, `requirePositive` | Pure helper methods: they need no object state, only their parameters. |
| `util.IdGenerator`, `util.InputValidator` | `private` constructor | Both are utility classes with only static members, so creating an instance makes no sense. The private constructor prevents `new IdGenerator()`. |
| `ui.Main` | `main(String[] args)` | The JVM calls `main` before any object exists, so it must be static. |
| `ui.Main` | `scanner`, `studentService`, `courseService`, `enrollmentService` | `main` and the menu helper methods are static, and a static method can only use static fields directly. There is exactly one of each for the whole program. |
| `ui.Main` | `addStudent()`, `readInt()`, `printStudent()` and the other menu methods | Small helpers called from the static `main`. |

**Instance** members are used everywhere data belongs to one object: each `Student` has its own
`firstName` and `active` flag, and each service has its own list.

`EnrollmentService` receives the **same** `StudentService` and `CourseService` objects through its
constructor instead of creating new ones. Creating new ones would give it separate, empty lists.

## 3. Where inheritance is used and what we gained

```text
Person (id, firstName, lastName, email, getDisplayName())
 ├── Student  (+ batch, active)
 └── Trainer  (+ expertise)

Exception
 ├── EntityNotFoundException
 └── InvalidInputException
```

### Person → Student / Trainer

- **Less duplicated code:** `id`, `firstName`, `lastName`, `email` and their getters/setters are written
  once in `Person` and reused by both `Student` and `Trainer`. For example, `updateStudent` calls
  `setFirstName()`, which is defined only in `Person`.
- **`super` in constructors:** `Student` and `Trainer` pass the shared fields up with
  `super(id, firstName, lastName, email)`, so `Person` stays responsible for its own fields.
- **Method overriding and polymorphism:** `getDisplayName()` is defined in `Person` and overridden
  with `@Override`:
  - `Person`: `Ali Khan`
  - `Student`: `Ali Khan (Jan-2026)`, using `super.getDisplayName()` and adding the batch
  - `Trainer`: `Trainer: John Doe - Java`

  With `Person p = new Student(...)`, calling `p.getDisplayName()` runs the **Student** version,
  because Java picks the method from the real object type at runtime.

### Exception → custom exceptions

`EntityNotFoundException` and `InvalidInputException` extend `Exception` and only pass the message
up with `super(message)`. We reuse `getMessage()` and the whole try/catch mechanism for free.
Because they extend `Exception` (checked), the compiler forces every caller to handle them, so a
"not found" or "invalid input" case can never be forgotten and crash the program.

## 4. Other design decisions

### Separation of concerns

- **Entities** only hold data.
- **Services** hold the business rules (finding by id, refusing to enroll in an inactive course,
  preventing duplicate active enrollments). They never print or read input.
- **`Main`** only shows the menu, reads input, calls a service and prints the result or error.

### Deactivate instead of delete

Students are deactivated (`active = false`) rather than removed from the list. Enrollments store a
`studentId`, so deleting a student would leave enrollments pointing to a student who no longer
exists. Deactivating keeps the history and blocks new enrollments for that student.
`StudentService.removeStudent(id)` is therefore a **soft delete**: it calls `deactivateStudent(id)`
instead of removing the object from the list. Menu option 5 uses it.

### Overloading

- `Student` has two constructors: with and without email.
- `StudentService.addStudent` has two versions: `(firstName, lastName, email, batch)` and
  `(firstName, lastName, batch)`. Java picks the right one at compile time from the number of arguments.

### Access modifiers

- `private`: all entity fields, the service lists, utility-class constructors and the menu helper methods.
- `public`: methods that other packages need (getters/setters, service methods, validators).
- default (package-private): the no-argument `Person()` constructor, which only classes in the
  `entity` package may use.

### Enum for enrollment status

`EnrollmentStatus` (`ACTIVE`, `COMPLETED`, `CANCELLED`) is used instead of a `String`. A typo such as
`"COMPLTED"` would be accepted silently as a String, but `EnrollmentStatus.COMPLTED` does not compile.

### Exception handling in the UI

- `NumberFormatException` (unchecked) from `Integer.parseInt` is caught when the user types text
  instead of a number.
- `EntityNotFoundException` and `InvalidInputException` (checked) are caught in each menu method and
  shown as `Error: <message>`.
- `try` blocks wrap only the code that can fail, and success messages are printed only after the
  service call has succeeded.
