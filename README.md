# BE_Cineme

This repository contains the Spring Boot backend for the Cineme / GoldenTicket project.

Goal: make it easy for someone who clones this repo to run the backend locally.

Prerequisites
- Java 17 (OpenJDK/Temurin)
- MySQL 8 (or compatible) and `mysql` CLI available in PATH
- Git
- (Optional) Docker & Docker Compose

Quick start (Windows PowerShell)

1) Clone the repo

```powershell
git clone https://github.com/HoangDuyet2005/BE_Cineme.git
cd BE_Cineme
```

2) Create local application properties

Copy the example file and edit values:

```powershell
copy src\main\resources\application.properties.example src\main\resources\application.properties
# Edit src/main/resources/application.properties: set spring.datasource.username/password and app.jwtSecret
```

3) Create the MySQL database and import sample data

You can run the provided PowerShell helper (Windows) which creates the `cinema2` database and imports seed files:

```powershell
Set-Location -Path .\
.
.
scripts\setup-db.ps1
```

Or run the commands manually:

```sql
CREATE DATABASE cinema2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

```powershell
mysql -u <user> -p cinema2 < seed_data.sql
mysql -u <user> -p cinema2 < seed_news.sql
mysql -u <user> -p cinema2 < seed_promotions.sql
```

4) Run the application

Using the bundled Maven wrapper (Windows PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

Or build and run the jar:

```powershell
.\mvnw.cmd clean package
java -jar target/*.jar
```

API base URL

By default: `http://localhost:8080/api`

Scripts included
- `scripts/setup-db.ps1` — PowerShell script that creates `cinema2` DB and imports `seed_*.sql` (Windows)
- `scripts/setup-db.sh` — Bash script for Linux/macOS that does the same

Important notes
- `src/main/resources/application.properties` is intentionally gitignored. Do not commit secrets.
- If you see CORS or authentication errors, ensure `app.jwtSecret` is set in your `application.properties`.
- If the app fails to connect to DB, verify `spring.datasource.url` and the DB user can connect remotely.

If you want, I can also add a Docker Compose file that sets up MySQL + the app for local development.

