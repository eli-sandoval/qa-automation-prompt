# QA Automation Challenge - Execution Instructions

This document explains how to clone, build, and run the project **qa-automation-prompt** on both macOS and Windows.
The reviewer will be able to run the monitoring script and calculate service uptime easily. They will also be able to run the test that consistently reproduces the name formatting bug.

---

## 1. Clone the Repository

You can use either **HTTPS** or **SSH** to clone the repository.

### HTTPS (no SSH key required)
```bash
git clone https://github.com/eli-sandoval/qa-automation-prompt.git
```

### SSH (requires SSH key configured in your GitHub account)
```bash
git clone git@github.com:eli-sandoval/qa-automation-prompt.git
```

---

## 2. Navigate to the Project Directory

```bash
cd qa-automation-prompt
```

Make sure you see the `gradlew` file in this directory.

---

## 3. Build the Project

The project uses **Gradle Wrapper**, so you don’t need to install Gradle manually.

### macOS / Linux
```bash
./gradlew build
```

### Windows (PowerShell or Command Prompt)
```powershell
gradlew.bat build
```

This will compile the source code and download all required dependencies.

---

## 4. Run the Monitoring Script

The **monitoring** script sends names to the API at a fixed interval (set in application.properties) and stores results in `request_logs.db`.

### macOS / Linux
```bash
./gradlew runMonitor
```

### Windows
```powershell
gradlew.bat runMonitor
```

> The script runs for the configured duration (`monitor.duration.sec` in `application.properties`).  
> During/after completion, you can inspect the database contents.

---

## 5. Run the Uptime Calculation Script

This script calculates the percentage of successful API responses from the existing database.

### macOS / Linux
```bash
./gradlew runUptime
```

### Windows
```powershell
gradlew.bat runUptime
```

---

## 6. Inspect the Database

The application stores logs in **SQLite** format. You can open it using the `sqlite3` CLI.

### macOS / Linux

### Install SQLite3 on macOS
If SQLite3 is not installed, run:
```bash
brew install sqlite3
```
Then
```bash
sqlite3 request_logs.db
```
Then inside SQLite:
```sql
SELECT * FROM request_logs;
```

### Windows
1. Download SQLite from: https://www.sqlite.org/download.html
2. Open `cmd` or PowerShell in the project folder.
3. Run:
```powershell
sqlite3 request_logs.db
```
Then inside SQLite:
```sql
SELECT * FROM request_logs;
```

---

## 7. Run the Bug Reproduction Test

A JUnit test is included to reproduce the bug when the API receives names containing two lowercase `p` characters.

### macOS / Linux
```bash
./gradlew test
```

### Windows
```powershell
gradlew.bat test
```

Test results will be displayed in the console and stored under `build/reports/tests/test/index.html`.
When checking the DB, the records will show 5 consistent instances where the bug was present.