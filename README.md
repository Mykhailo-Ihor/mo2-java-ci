# mo2-java-ci

Small Maven project used to demonstrate a Java CI pipeline running a build and test stage
on every push and pull request.

## Layout

| Path | Purpose |
| --- | --- |
| `src/main/java/com/example/inventory` | `Item` record and `Inventory` stock rules |
| `src/test/java/com/example/inventory` | JUnit 5 tests for both classes |

## CI

`.github/workflows/ci.yml` runs one job on every push and pull request to `main`:

- **build-and-test** — `actions/setup-java` with Temurin 21 and the Maven cache enabled,
  then `mvn -B verify`. Surefire XML reports are published as a workflow artifact.

`.github/workflows/dependency-review.yml` runs `dependency-review-action` on pull requests
and fails the check when a dependency introduces a vulnerability of **high** severity or above.

`build-and-test` is a required status check on `main`.

## Running locally

```bash
mvn -B verify
```
