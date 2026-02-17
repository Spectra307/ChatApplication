# Chat Application - Backend Configuration

## Database Configuration

### PostgreSQL Setup

1. **Create Database:**
   ```
   createdb chatapp
   ```

2. **Create Database User:**
   ```
   createuser chatapp_user
   ```

3. **Set User Password:**
   ```
   ALTER USER chatapp_user WITH PASSWORD 'your_secure_password';
   ```

4. **Grant Permissions:**
   ```
   GRANT ALL PRIVILEGES ON DATABASE chatapp TO chatapp_user;
   ```

5. **Run Schema Script:**
   ```
   psql -U chatapp_user -d chatapp -f schema.sql
   ```

### Alternative: Supabase Setup

1. Create a Supabase project at https://supabase.com
2. Copy the database URL
3. Update `DatabaseConnection.java` with your Supabase connection details
4. Run the schema.sql file in Supabase SQL editor

## Backend Dependencies

Install Maven dependencies:
```
mvn install
```

## Build & Run

### Build Backend
```
mvn clean install
```

### Run Backend Server
```
mvn spring-boot:run
```

The backend server will start on `http://localhost:8080`

## API Endpoints (To be implemented)

- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout
- `POST /api/auth/register` - User registration
- `POST /api/chat/send` - Send message
- `GET /api/chat/history/{user1}/{user2}` - Get conversation history
