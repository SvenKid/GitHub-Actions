# Автоматизация сборки и тестирования с GitHub Actions

[![Java CI](https://github.com/SvenKid/GitHub-Actions/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/SvenKid/GitHub-Actions/actions/workflows/ci.yml)

Учебный проект по дисциплине «Инструменты программной инженерии», вариант А.
На примере небольшого калькулятора показана настройка GitHub Actions с нуля:
сборка приложения, запуск тестов и сохранение готового JAR-файла.

## Приложение

Калькулятор работает из командной строки и выполняет сложение, вычитание, умножение и деление.
При неверных аргументах или делении на ноль он выводит ошибку и завершается с кодом 1.

Используются Java 17, Maven и JUnit 5. В проекте шесть модульных тестов.
База данных, веб-сервер и дополнительные сервисы не нужны.

## Запуск

Для работы нужны JDK 17 или новее, Maven 3.9 и Git.
Проверить установку можно командами `java -version`, `mvn -version` и `git --version`.

```text
git clone https://github.com/SvenKid/GitHub-Actions.git
cd GitHub-Actions
mvn --batch-mode --no-transfer-progress clean verify
java -jar target/calculator-1.0.0.jar add 2 3
```

Последняя команда выводит `Result: 5.0`. Другие примеры:

```text
java -jar target/calculator-1.0.0.jar sub 8 3
java -jar target/calculator-1.0.0.jar mul -3 4
java -jar target/calculator-1.0.0.jar div 5 2
java -jar target/calculator-1.0.0.jar div 10 0
```

Результаты: `5.0`, `-12.0`, `2.5` и сообщение `Cannot divide by zero.`.
Дробные числа вводятся с точкой.

В IntelliJ IDEA откройте `pom.xml` как Maven-проект и выберите JDK 17 или новее.
В окне Maven можно выполнить `clean` и `verify`, используя встроенный Maven.
Для запуска класса `Main` из IDE укажите аргументы программы, например `add 2 3`.

## Как работает CI

Файл `.github/workflows/ci.yml` задаёт один job на Ubuntu. Он запускается:

- при отправке изменений в `main` или ветки `demo/**`;
- при создании и обновлении pull request в `main`;
- вручную через **Actions → Java CI → Run workflow**.

Сначала runner получает исходники и устанавливает Java 17. Затем Maven выполняет
`clean verify`: удаляет старые результаты сборки, компилирует код, запускает тесты
и собирает JAR. Следующий шаг запускает калькулятор.

Отчёты тестов сохраняются даже при падении теста благодаря `if: always()`.
JAR сохраняется только после успешного выполнения предыдущих шагов.
Оба артефакта доступны на странице запуска в течение 14 дней. Скачанный архив
`calculator-jar` нужно распаковать, после чего JAR можно запустить через Java.

Кэш Maven ускоряет повторное скачивание зависимостей. Кэш и артефакты имеют разное назначение:
первый нужен для последующих сборок, вторые содержат результаты конкретного запуска.
Workflow использует `contents: read`; собственные токены и секреты не требуются.
Развёртывание на сервере в этом проекте не выполняется.

## Демонстрация ошибки

В отдельной ветке заменяем в методе `subtract` выражение `a - b` на `a + b`.
Тест `subtractsTwoNumbers` ожидает 5, но получает 11, поэтому сборка падает.
После исправления кода тот же тест проходит. Менять ожидаемый результат теста не нужно.

Пошаговый сценарий и текст выступления на английском находятся в [DEMO.md](DEMO.md).
Настройка Windows и список действий перед сдачей — в [WINDOWS.md](WINDOWS.md).
На защите показываются редактор, терминал и GitHub Actions, без презентации.

## Документация

- [Сборка Java-проекта с Maven в GitHub Actions](https://docs.github.com/en/actions/tutorials/build-and-test-code/java-with-maven)
- [Жизненный цикл Maven](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
- [JUnit 5](https://docs.junit.org/5.11.4/user-guide/)
