# GitHub Actions demo

[![Java CI](https://github.com/SvenKid/GitHub-Actions/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/SvenKid/GitHub-Actions/actions/workflows/ci.yml)

Variant A for Software Engineering Tools: build and test a small Java application with GitHub Actions.

## Application

The command-line calculator supports addition, subtraction, multiplication and division.
It rejects division by zero, unknown operations and invalid input.
The project uses Java 17, Maven and JUnit 5. It has six unit tests.

## Build and run

Install a JDK (17 or later), Maven 3.9 and Git. Check `java -version`, `mvn -version` and `git --version`.

```text
git clone https://github.com/SvenKid/GitHub-Actions.git
cd GitHub-Actions
mvn --batch-mode --no-transfer-progress clean verify
java -jar target/calculator-1.0.0.jar add 2 3
java -jar target/calculator-1.0.0.jar div 5 2
```

Expected results: `Result: 5.0` and `Result: 2.5`.
Use `sub`, `mul` or `div` for other operations. For example, `div 10 0` prints an error and exits with code 1.

In IntelliJ IDEA, open `pom.xml` as a project. Select a JDK 17 or later and reload Maven.
You can run `clean` and `verify` from the Maven tool window without a separate Maven installation.
To run `Main`, set its program arguments to `add 2 3`.

## GitHub Actions

The configuration is `.github/workflows/ci.yml`. It runs on pushes to `main` and `demo/**`,
on pull requests into `main`, and on a manual start from the Actions tab.

One Ubuntu job checks out the code, sets up Java 17, restores the Maven cache,
runs `clean verify`, and starts the application. A successful run saves a runnable JAR.
Test reports are uploaded even if a test fails. Artifacts are kept for 14 days.
The workflow needs read access to repository contents; no personal token or extra secret is required.

Open **Actions → Java CI → a run** to see the steps, logs and artifacts.
The JAR artifact is a ZIP: extract it and run the JAR with Java 17 or later.
This project demonstrates CI. It does not deploy an application to a server.

## Live demo

See [DEMO.md](DEMO.md) for the English script, failure scenario and questions.
See [WINDOWS.md](WINDOWS.md) for Russian setup and submission instructions.
Do not use slides: show the editor, terminal and GitHub Actions.

## Sources

- [GitHub Actions with Maven](https://docs.github.com/en/actions/tutorials/build-and-test-code/java-with-maven)
- [Maven lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
- [JUnit 5 guide](https://docs.junit.org/5.11.4/user-guide/)
