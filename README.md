# DeviceAuth Backend (Java / Spring Boot)

## Requirements
- JDK 21
- MySQL 8+
- Maven 3.9+ (or add Maven Wrapper from your IDE)

## Setup
1. Create database: `CREATE DATABASE deviceauth_db;`
2. Set environment variables in PowerShell:
   ```powershell
   $env:DB_USERNAME="root"
   $env:DB_PASSWORD="your_mysql_password"
   $env:JWT_SECRET="<base64-encoded-secret-of-at-least-32-bytes>"
   ```
   The default JWT secret is for local demonstration only. Replace it before use.
3. Run from this folder: `mvn spring-boot:run`
4. API base URL: `http://localhost:8080/api`

## API overview
- `POST /api/auth/register` JSON `{ "name":"Demo User", "email":"user@example.com", "password":"ChangeMe123" }`
- `POST /api/auth/login` JSON `{ "email":"user@example.com", "password":"ChangeMe123" }`
- `GET /api/devices` (Bearer JWT)
- `POST /api/devices` JSON `{ "deviceName":"Laptop", "deviceIdentifier":"LAPTOP-DEMO-001" }` (Bearer JWT)
- `POST /api/devices/{id}/authenticate` (Bearer JWT)
- `GET /api/admin/devices` (ADMIN JWT)
- `PATCH /api/admin/devices/{id}/status` JSON `{ "status":"TRUSTED" }` or `{ "status":"BLOCKED" }` (ADMIN JWT)
- `GET /api/admin/audit-logs` (ADMIN JWT)

## Important setup note
Public registration intentionally always creates USER accounts. For local evaluation, register a user, then promote that account directly in your local MySQL database with `UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';` (replace the email). Log out and sign in again so the session is refreshed. Never expose public role selection or an unauthenticated admin-creation endpoint. Use proper database migrations, secret management, HTTPS, rate limiting, CSRF review if switching to cookies, and security testing before production deployment.
