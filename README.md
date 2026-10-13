# 🚀 EasyTestWeb

[![Java](https://img.shields.io/badge/Java-11%20%2F%2021-orange.svg)](https://www.oracle.com/java/)
[![Playwright](https://img.shields.io/badge/Playwright-1.49.0-2EAD33.svg)](https://playwright.dev/java/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5.11.0-25A162.svg)](https://junit.org/junit5/)
[![Maven](https://img.shields.io/badge/Build-Maven-C71A36.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**EasyTestWeb** is an enterprise-grade Web UI and API test automation framework built with **Java**, **Microsoft Playwright for Java**, **JUnit 5**, and the **Page Object Model (POM)** design pattern.

---

## 🌟 Key Features

- **⚡ Blazing Fast Execution**: Powered by Playwright's native DevTools protocol for auto-waiting, resilient locators, and zero flaky sleeps.
- **🌐 Cross-Browser Testing**: Seamless execution across **Chromium**, **Firefox**, **WebKit (Safari)**, **Google Chrome**, and **Microsoft Edge**.
- **🧩 Page Object Model (POM)**: Decoupled page locators and interaction logic for maximum test maintainability and readability.
- **🧵 Thread-Safe Driver Management**: `ThreadLocal` implementation enabling reliable parallel test runs.
- **⚙️ Dynamic Configuration**: Centralized settings via `config.properties` with instant CLI runtime override via `-D` flags.
- **📸 Automatic Failure Artifacts**: Automatic screenshots and full zip execution traces captured upon test failures.
- **🔌 Unified UI & API Testing**: Built-in support for both end-to-end browser workflows and backend REST API validation using Playwright's `APIRequestContext`.
- **🚀 CI/CD Ready**: Pre-configured GitHub Actions workflow for automated test execution and artifact archiving.

---

## 📁 Project Structure

```
EasyTestWeb/
├── .github/
│   └── workflows/
│       └── playwright.yml            # CI/CD GitHub Actions pipeline
├── src/
│   ├── main/
│   │   ├── java/com/easytestweb/
│   │   │   ├── config/
│   │   │   │   └── ConfigManager.java     # Environment & runtime property loader
│   │   │   ├── driver/
│   │   │   │   └── PlaywrightManager.java # Thread-safe driver & browser lifecycle
│   │   │   ├── pages/
│   │   │   │   ├── BasePage.java          # Common reusable interactions & locators
│   │   │   │   ├── LoginPage.java         # Login Page Object
│   │   │   │   └── InventoryPage.java     # Products/Inventory Page Object
│   │   │   └── utils/
│   │   │       └── ScreenshotUtils.java   # Full-page and element screenshot capture
│   │   └── resources/
│   │       ├── config.properties          # Default framework configurations
│   │       └── logback.xml                # Logging configuration
│   └── test/
│       ├── java/com/easytestweb/
│       │   ├── extensions/
│       │   │   └── TestWatcherExtension.java # Failure listener, screenshots & tracing
│       │   └── tests/
│       │       ├── BaseTest.java             # JUnit 5 setup & teardown lifecycle
│       │       ├── LoginTest.java            # Authentication test scenarios
│       │       ├── InventoryTest.java        # Catalog, sorting, and cart test suite
│       │       └── ApiAndNetworkTest.java    # Playwright REST API tests
│       └── resources/
├── .gitignore
├── pom.xml                                    # Maven project object model
└── README.md
```

---

## 🛠️ Prerequisites

- **Java JDK**: 11 or higher (OpenJDK / Temurin recommended)
- **Apache Maven**: 3.8.0 or higher
- **Git**: Installed and configured

Verify prerequisites in your terminal:
```bash
java -version
mvn -version
git --version
```

---

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/NeelTamboli29/EasyTestWeb.git
cd EasyTestWeb
```

### 2. Compile the Project
```bash
mvn clean test-compile
```

### 3. Install Playwright Browsers (First time only)
```bash
mvn exec:java -e -D "exec.mainClass=com.microsoft.playwright.CLI" -D "exec.args=install --with-deps"
```

---

## 🧪 Running Tests

### Run All Tests
```bash
mvn test
```

### Run Tests in Headed Mode (Watch Browser UI)
```bash
mvn test -Dheadless=false
```

### Run Against Different Browsers
```bash
# Chromium (Default)
mvn test -Dbrowser=chromium

# Firefox
mvn test -Dbrowser=firefox

# WebKit (Safari engine)
mvn test -Dbrowser=webkit

# Google Chrome
mvn test -Dbrowser=chrome
```

### Run a Specific Test Class
```bash
mvn test -Dtest=LoginTest
```

### Run a Single Test Method
```bash
mvn test -Dtest=LoginTest#testValidLogin
```

### Override Base URL or Custom Options
```bash
mvn test -DbaseUrl=https://www.saucedemo.com -Dtimeout=15000 -DslowMo=500
```

---

## 🔍 Traces & Failure Screenshots

### Inspecting Execution Traces
When tracing is enabled or a test fails, zip trace files are saved to `target/traces/`. You can view interactive traces with DOM snapshots, network calls, and console logs using Playwright CLI:

```bash
mvn exec:java -e -D "exec.mainClass=com.microsoft.playwright.CLI" -D "exec.args=show-trace target/traces/<test_name>_trace.zip"
```

### Screenshots
Screenshots captured automatically on test failure or explicitly via code are saved in:
```
target/screenshots/
```

---

## ⚙️ Configuration Reference

Edit `src/main/resources/config.properties` or override via CLI flags (`-Dkey=value`):

| Property | Default Value | Description |
| :--- | :--- | :--- |
| `browser` | `chromium` | Target browser engine (`chromium`, `firefox`, `webkit`, `chrome`, `msedge`) |
| `headless` | `true` | Run without opening browser UI window (`true` or `false`) |
| `baseUrl` | `https://www.saucedemo.com` | Root application URL under test |
| `timeout` | `30000` | Default element and navigation timeout in ms |
| `slowMo` | `0` | Delay between actions in milliseconds (helpful for debugging) |
| `tracing` | `true` | Capture Playwright trace artifacts |
| `screenshotOnFailure` | `true` | Capture full-page screenshot when a test fails |

---

## 📄 License
This project is open source and available under the [MIT License](LICENSE).
