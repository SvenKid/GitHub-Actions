# Live demo guide

Use a live screen, not slides. Plan about 15 minutes for the demo and 3–5 minutes for questions.
The assignment also contains the range “15–120 minutes”; check the final time limit with the teacher.
The times below include typing, opening logs and waiting for GitHub Actions.

## Before the lesson

Build the project once to download dependencies. Open the repository, the Actions tab,
`pom.xml`, `ci.yml`, `Calculator.java` and `CalculatorTest.java`.
Check the internet connection and your GitHub login. Keep previous successful and failed runs open
as a backup, but explain clearly if you show an earlier run.
Keep `main` correct. Use a new `demo/lesson-1` branch for the live mistake.

## 0–2 minutes — Introduction

“Today I will show how GitHub Actions builds and tests a Java application.
Developers can forget to run tests before they share code. This can cause problems for other people.
GitHub Actions runs the same checks after a code change. The result is visible in the repository.
My example is a small calculator. It uses Java 17, Maven and JUnit 5.
The main topic is the build process. The calculator gives us a simple way to test it.”

Show the four calculator methods and run:

```text
java -jar target/calculator-1.0.0.jar add 2 3
java -jar target/calculator-1.0.0.jar div 10 0
```

“The first command returns five. The second command shows a normal input error.
This error is handled by the application. Later, I will introduce a code error that makes a test fail.”

## 2–5 minutes — Configuration

Open `ci.yml` and explain each block:

“The name of the workflow is Java CI. The on block defines when it runs.
A push to main or a demo branch starts it. A pull request into main also starts it.
Workflow dispatch lets me start it manually.
The job runs on an Ubuntu machine provided by GitHub. Checkout downloads the code.
Setup Java selects Java 17 and caches Maven dependencies.
Maven clean removes old build files. Verify compiles the code, runs the tests and builds a JAR.
The next step starts the application. Upload artifact saves the files that I can download.
Always means that GitHub also tries to save test reports after a failed test.
The JAR step uses the default success condition, so it does not run after a failed build.”

Open `pom.xml`:

“This file describes the Maven project. Release 17 sets the Java target version.
JUnit is a test dependency. Surefire runs the unit tests.
The JAR plugin writes the main class into the manifest, so I can use java dash jar.”

## 5–8 minutes — Successful build

Run `mvn --batch-mode --no-transfer-progress clean verify`.
Show six tests with zero failures. Open a successful Actions run, expand Build and test,
then show Run the application and the two artifacts.

“All six tests passed. Maven created the JAR. GitHub also ran the application.
An artifact is an output file from a workflow. A cache has a different purpose:
it helps the next build reuse dependencies. It is not the application release.”

## 8–12 minutes — Make a test fail

Start from an up-to-date main branch with no unrelated local changes:

```text
git switch main
git pull --ff-only
git switch -c demo/lesson-1
```

In `Calculator.java`, change only the subtraction method from `return a - b;` to `return a + b;`.
Do not change the test. Save the file, then run:

```text
git add src/main/java/edu/demo/Calculator.java
git commit -m "Demo: introduce a subtraction bug"
git push -u origin demo/lesson-1
```

Open the new Actions run. Expand the failed step and find `subtractsTwoNumbers`.

“I changed subtraction to addition. The test expects five for eight minus three,
but the application returns eleven. Maven returns a non-zero exit code.
GitHub marks the job as failed. The application and JAR upload steps are skipped.
The test report is still available. I can use it to find the cause.”

## 12–15 minutes — Fix and finish

Restore `return a - b;` in the same file, then:

```text
git add src/main/java/edu/demo/Calculator.java
git commit -m "Fix the subtraction bug"
git push
```

Show the successful run for the new commit. If the run is still queued, explain the queue and
show the local test result while you wait. Do not describe an unfinished run as successful.

“I fixed the application without changing the expected result.
The new run passes. This shows that the test can detect the error and confirm the fix.
CI gives fast feedback, but it only checks what our tests cover.
A green build does not prove that the whole application has no bugs.”

## Questions and answers

**What is CI?** Continuous integration means checking code changes with an automatic build and tests.

**What is a runner?** It is the machine that runs a job.

**What is the difference between a job and a step?** A job contains steps. The steps in this job run in order on the same runner.

**Why use Maven?** It manages dependencies and gives us standard build commands.

**Why use Java 17?** It is a stable target for this small project. The CI configuration selects the same version for each run.

**Why use a delta in assertEquals?** Decimal values may have small rounding errors. The delta allows a small difference.

**Why test division by zero?** It checks that the application rejects an invalid operation with the expected exception.

**Does a failed test stop a merge?** It marks the check as failed. Blocking a merge also needs a branch rule that requires this check. This project does not set that rule.

**Why is the workflow token read-only?** The job only reads source code. It does not push changes back to the repository.

**Is this CD?** No. It builds and tests the application and saves a JAR. It does not deploy to a server.

**What if GitHub or the network is unavailable?** I can run Maven locally. I can show earlier CI runs as recorded evidence, but they are not a new live run.
