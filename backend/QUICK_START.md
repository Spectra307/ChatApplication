# Quick Start - Skip Database Setup

## Option 1: Run Without Database (Demo Mode)

The application now runs in **demo mode** if PostgreSQL is unavailable.

```bash
cd backend
mvn spring-boot:run
```

**What works in demo mode:**
- All REST API endpoints available
- In-memory services (AuthService, ChatServer)
- User registration and login (stored in memory only)
- Message sending (stored in memory only)
- Frontend can be tested

**Limitations:**
- Data NOT persisted (cleared on restart)
- No multi-process persistence

---

## Option 2: Setup PostgreSQL (Recommended for Production)

### Windows

#### Step 1: Install PostgreSQL
Download and install from: https://www.postgresql.org/download/windows/

#### Step 2: Start PostgreSQL Service
```powershell
# Check if PostgreSQL service is running
Get-Service | Where-Object { $_.Name -like '*postgres*' } | Select-Object Name, Status

# Or manually start via Services app
# Search → Services → Find PostgreSQL → Right-click → Start
```

#### Step 3: Create Database & User
```bash
# Open PostgreSQL command line
psql -U postgres

# In PostgreSQL prompt:
CREATE DATABASE chatapp;
CREATE USER chatapp_user WITH PASSWORD 'your_secure_password';
GRANT ALL PRIVILEGES ON DATABASE chatapp TO chatapp_user;
\q
```

#### Step 4: Update Backend Configuration
Edit `backend/src/main/java/com/chatapp/database/DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:postgresql://localhost:5432/chatapp";
private static final String DB_USER = "chatapp_user";
private static final String DB_PASSWORD = "your_secure_password";
```

#### Step 5: Initialize Database Schema
```bash
psql -U chatapp_user -d chatapp -f database/schema.sql
```

#### Step 6: Run Backend
```bash
cd backend
mvn spring-boot:run
```

---

### macOS

#### Step 1: Install PostgreSQL (Homebrew)
```bash
brew install postgresql@15
brew services start postgresql@15
```

#### Step 2: Create Database & User
```bash
psql -U postgres
```
Then run the same SQL commands as Windows (Step 3).

#### Step 3-6: Same as Windows

---

### Linux (Ubuntu/Debian)

#### Step 1: Install PostgreSQL
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
```

#### Step 2: Create Database & User
```bash
sudo -u postgres psql
```
Then run the same SQL commands as Windows (Step 3).

#### Step 3-6: Same as Windows

---

## Option 3: Use Supabase (Cloud Database)

### Setup
1. Sign up at https://supabase.com
2. Create a new project
3. Go to SQL Editor → New Query
4. Paste contents of `database/schema.sql`
5. Run the query

### Update Configuration
Edit `DatabaseConnection.java`:
```java
private static final String DB_URL = "jdbc:postgresql://[PROJECT_ID].supabase.co:5432/postgres";
private static final String DB_USER = "postgres";
private static final String DB_PASSWORD = "[YOUR_PASSWORD]";
```

### Run Backend
```bash
cd backend
mvn spring-boot:run
```

---

## Environment Variables (Optional)

Instead of hardcoding credentials, use environment variables:

```bash
# Linux/macOS
export DB_URL="jdbc:postgresql://localhost:5432/chatapp"
export DB_USER="chatapp_user"
export DB_PASSWORD="your_secure_password"
mvn spring-boot:run

# Windows PowerShell
$env:DB_URL = "jdbc:postgresql://localhost:5432/chatapp"
$env:DB_USER = "chatapp_user"
$env:DB_PASSWORD = "your_secure_password"
mvn spring-boot:run
```

Then update `DatabaseConnection.java`:
```java
private static final String DB_URL = System.getenv("DB_URL") != null ? 
    System.getenv("DB_URL") : "jdbc:postgresql://localhost:5432/chatapp";
private static final String DB_USER = System.getenv("DB_USER") != null ? 
    System.getenv("DB_USER") : "chatapp_user";
private static final String DB_PASSWORD = System.getenv("DB_PASSWORD") != null ? 
    System.getenv("DB_PASSWORD") : "your_secure_password";
```

---

## Troubleshooting

### PostgreSQL Not Installed
```bash
# Check if installed
psql --version

# Or use demo mode (data not persisted)
```

### Connection Refused
```
⚠️  Database connection failed: Connection to localhost:5432 refused
```

**Solutions:**
- PostgreSQL service not running → Start it
- Wrong hostname/port → Check `DatabaseConnection.java`
- Wrong credentials → Verify DB_USER and DB_PASSWORD
- Firewall blocking port 5432 → Allow in firewall

### Port Already in Use
```bash
# If port 5432 is in use by another process
# Change DB_URL to different port: jdbc:postgresql://localhost:5433/chatapp
```

---

## Testing Without Database

Run unit tests (no database required):
```bash
cd backend
mvn clean test
```

---

**Current Status**: ✅ App runs in demo mode if PostgreSQL unavailable
**Date**: February 17, 2026
