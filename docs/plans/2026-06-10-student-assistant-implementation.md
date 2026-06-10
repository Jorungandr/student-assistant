# Student Assistant Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build a full-stack university student personal assistant with Vue 3, Spring Boot, and MySQL.

**Architecture:** The system uses a frontend/backend split. Vue renders the dashboard, forms, tables, and charts; Spring Boot exposes authenticated REST APIs; MySQL stores user-scoped business data. JWT protects all business APIs, and each query is filtered by the current user.

**Tech Stack:** Vue 3, Vite, Pinia, Vue Router, Element Plus, ECharts, Axios, Spring Boot, Spring Security, JWT, MyBatis Plus, MySQL, JUnit, Spring Boot Test.

---

### Task 1: Initialize Repository Structure

**Files:**
- Create: `README.md`
- Create: `.gitignore`
- Create: `frontend/`
- Create: `backend/`
- Create: `docs/database-design.md`
- Create: `docs/api-design.md`
- Create: `docs/test-plan.md`
- Create: `docs/user-guide.md`

**Step 1: Create base folders**

Create the project folders shown in the design document:

```text
frontend/
backend/
docs/
```

**Step 2: Add root `.gitignore`**

Include:

```gitignore
node_modules/
dist/
target/
.idea/
.vscode/
*.iml
*.log
.env
```

**Step 3: Add root `README.md`**

Document the project purpose, module list, tech stack, and startup overview.

**Step 4: Commit**

```bash
git add README.md .gitignore frontend backend docs
git commit -m "chore: initialize project structure"
```

### Task 2: Scaffold Spring Boot Backend

**Files:**
- Create: `backend/pom.xml`
- Create: `backend/src/main/java/com/example/studentassistant/StudentAssistantApplication.java`
- Create: `backend/src/main/resources/application.yml`
- Create: `backend/src/main/resources/db/schema.sql`
- Create: `backend/src/main/resources/db/data.sql`

**Step 1: Add Maven dependencies**

Use Spring Boot dependencies for:

- `spring-boot-starter-web`
- `spring-boot-starter-security`
- `spring-boot-starter-validation`
- `mybatis-plus-spring-boot3-starter`
- `mysql-connector-j`
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson`
- `spring-boot-starter-test`

**Step 2: Add application entry point**

Create `StudentAssistantApplication` with `@SpringBootApplication`.

**Step 3: Add `application.yml`**

Configure:

- server port `8080`
- MySQL datasource
- MyBatis Plus mapper location
- JWT secret and expiration
- CORS allowed frontend origin `http://localhost:5173`

**Step 4: Run backend compile**

```bash
cd backend
mvn test
```

Expected: build passes with no tests or only context-load test.

**Step 5: Commit**

```bash
git add backend
git commit -m "chore: scaffold spring boot backend"
```

### Task 3: Implement Database Schema

**Files:**
- Modify: `backend/src/main/resources/db/schema.sql`
- Modify: `docs/database-design.md`

**Step 1: Write MySQL schema**

Create tables:

- `sys_user`
- `course`
- `task`
- `schedule`
- `study_record`
- `health_record`
- `finance_record`
- `budget`
- `goal`
- `habit`
- `habit_checkin`

Use `BIGINT` primary keys, `user_id` indexes, `created_at`, and `updated_at` where useful.

**Step 2: Add constraints**

Add indexes for:

- `user_id`
- date fields used in dashboards and statistics
- `course_id`
- `habit_id`

**Step 3: Document schema**

Write table descriptions, important fields, and relationships in `docs/database-design.md`.

**Step 4: Verify SQL**

Run the SQL in MySQL:

```bash
mysql -u root -p < backend/src/main/resources/db/schema.sql
```

Expected: database and all tables are created.

**Step 5: Commit**

```bash
git add backend/src/main/resources/db/schema.sql docs/database-design.md
git commit -m "feat: add mysql database schema"
```

### Task 4: Implement Backend Common Foundation

**Files:**
- Create: `backend/src/main/java/com/example/studentassistant/common/ApiResponse.java`
- Create: `backend/src/main/java/com/example/studentassistant/common/PageResult.java`
- Create: `backend/src/main/java/com/example/studentassistant/common/CurrentUser.java`
- Create: `backend/src/main/java/com/example/studentassistant/exception/GlobalExceptionHandler.java`
- Create: `backend/src/main/java/com/example/studentassistant/config/CorsConfig.java`
- Create: `backend/src/main/java/com/example/studentassistant/config/MyBatisPlusConfig.java`

**Step 1: Add unified response model**

Create `ApiResponse<T>` with `code`, `message`, and `data`.

**Step 2: Add pagination model**

Create `PageResult<T>` with `records`, `total`, `page`, and `size`.

**Step 3: Add global exception handling**

Handle validation errors, authentication errors, business errors, and generic server errors.

**Step 4: Add CORS config**

Allow frontend origin `http://localhost:5173`.

**Step 5: Add MyBatis Plus config**

Enable pagination interceptor.

**Step 6: Test compile**

```bash
cd backend
mvn test
```

Expected: compile succeeds.

**Step 7: Commit**

```bash
git add backend/src/main/java/com/example/studentassistant
git commit -m "feat: add backend common foundation"
```

### Task 5: Implement Authentication and Security

**Files:**
- Create: `backend/src/main/java/com/example/studentassistant/entity/User.java`
- Create: `backend/src/main/java/com/example/studentassistant/mapper/UserMapper.java`
- Create: `backend/src/main/java/com/example/studentassistant/security/JwtUtil.java`
- Create: `backend/src/main/java/com/example/studentassistant/security/JwtAuthenticationFilter.java`
- Create: `backend/src/main/java/com/example/studentassistant/security/SecurityConfig.java`
- Create: `backend/src/main/java/com/example/studentassistant/dto/auth/LoginRequest.java`
- Create: `backend/src/main/java/com/example/studentassistant/dto/auth/LoginResponse.java`
- Create: `backend/src/main/java/com/example/studentassistant/dto/auth/RegisterRequest.java`
- Create: `backend/src/main/java/com/example/studentassistant/service/AuthService.java`
- Create: `backend/src/main/java/com/example/studentassistant/service/impl/AuthServiceImpl.java`
- Create: `backend/src/main/java/com/example/studentassistant/controller/AuthController.java`
- Test: `backend/src/test/java/com/example/studentassistant/service/AuthServiceTest.java`

**Step 1: Write failing tests**

Test:

- duplicate username cannot register
- login fails with wrong password
- login succeeds with correct password

**Step 2: Implement user entity and mapper**

Map `sys_user` to `User`.

**Step 3: Implement password hashing**

Use `BCryptPasswordEncoder`.

**Step 4: Implement JWT utility**

Generate and validate JWT with user id and username.

**Step 5: Implement security config**

Permit:

- `POST /api/auth/register`
- `POST /api/auth/login`

Require authentication for all other `/api/**` routes.

**Step 6: Implement auth controller**

Expose register, login, profile, update profile, and password update.

**Step 7: Run tests**

```bash
cd backend
mvn test
```

Expected: auth tests pass.

**Step 8: Commit**

```bash
git add backend/src
git commit -m "feat: implement jwt authentication"
```

### Task 6: Implement Course, Schedule, and Task APIs

**Files:**
- Create entities, mappers, DTOs, services, and controllers for:
  - `Course`
  - `Schedule`
  - `Task`
- Test:
  - `backend/src/test/java/com/example/studentassistant/service/CourseServiceTest.java`
  - `backend/src/test/java/com/example/studentassistant/service/TaskServiceTest.java`

**Step 1: Write service tests**

Test:

- user can create and list own courses
- today courses are filtered by weekday
- user cannot update another user's course
- task status can change to completed
- overdue task statistics count only current user records

**Step 2: Implement entities and mappers**

Use MyBatis Plus base mappers.

**Step 3: Implement services**

Every query must filter by `user_id`.

**Step 4: Implement controllers**

Expose:

- `/api/courses`
- `/api/courses/today`
- `/api/schedules`
- `/api/tasks`
- `/api/tasks/upcoming`
- `/api/tasks/statistics`

**Step 5: Run tests**

```bash
cd backend
mvn test
```

Expected: course, schedule, and task tests pass.

**Step 6: Commit**

```bash
git add backend/src
git commit -m "feat: add course schedule and task APIs"
```

### Task 7: Implement Study, Health, Finance, Budget, Goal, and Habit APIs

**Files:**
- Create entities, mappers, DTOs, services, and controllers for:
  - `StudyRecord`
  - `HealthRecord`
  - `FinanceRecord`
  - `Budget`
  - `Goal`
  - `Habit`
  - `HabitCheckin`
- Test:
  - `backend/src/test/java/com/example/studentassistant/service/AnalysisServiceTest.java`
  - `backend/src/test/java/com/example/studentassistant/service/BudgetServiceTest.java`
  - `backend/src/test/java/com/example/studentassistant/service/HabitServiceTest.java`

**Step 1: Write service tests**

Test:

- study duration grouped by date
- task completion rate calculation
- course study ratio calculation
- budget warning when spending reaches threshold
- habit checkin creates or updates today's record
- continuous checkin days calculation

**Step 2: Implement CRUD services**

Each module gets list, create, update, and delete behavior.

**Step 3: Implement statistics services**

Add statistics endpoints for:

- study duration
- task completion
- course ratio
- priority tasks
- health trends
- finance category totals
- budget warnings
- habit statistics

**Step 4: Run tests**

```bash
cd backend
mvn test
```

Expected: analysis, budget, and habit tests pass.

**Step 5: Commit**

```bash
git add backend/src
git commit -m "feat: add analysis health finance goal and habit APIs"
```

### Task 8: Implement Dashboard API

**Files:**
- Create: `backend/src/main/java/com/example/studentassistant/dto/dashboard/DashboardSummaryResponse.java`
- Create: `backend/src/main/java/com/example/studentassistant/service/DashboardService.java`
- Create: `backend/src/main/java/com/example/studentassistant/service/impl/DashboardServiceImpl.java`
- Create: `backend/src/main/java/com/example/studentassistant/controller/DashboardController.java`
- Test: `backend/src/test/java/com/example/studentassistant/service/DashboardServiceTest.java`

**Step 1: Write failing dashboard test**

Seed one user with:

- today course
- pending task
- health record
- finance record
- budget
- goal

Assert `/summary` data includes all expected sections.

**Step 2: Implement aggregation DTO**

Include:

- today courses
- todo tasks
- health overview
- budget overview
- goal progress
- lightweight status flags

**Step 3: Implement service**

Use existing services or mappers to aggregate data.

**Step 4: Implement controller**

Expose `GET /api/dashboard/summary`.

**Step 5: Run tests**

```bash
cd backend
mvn test
```

Expected: dashboard test passes.

**Step 6: Commit**

```bash
git add backend/src
git commit -m "feat: add dashboard summary API"
```

### Task 9: Scaffold Vue Frontend

**Files:**
- Create: `frontend/package.json`
- Create: `frontend/vite.config.js`
- Create: `frontend/index.html`
- Create: `frontend/src/main.js`
- Create: `frontend/src/App.vue`
- Create: `frontend/src/router/index.js`
- Create: `frontend/src/stores/auth.js`
- Create: `frontend/src/api/http.js`

**Step 1: Create Vite Vue app files**

Use Vue 3 with Element Plus, Pinia, Vue Router, Axios, and ECharts.

**Step 2: Configure API client**

Axios base URL:

```js
baseURL: 'http://localhost:8080/api'
```

Attach JWT from Pinia/localStorage.

**Step 3: Configure router guards**

Protected routes require token.

**Step 4: Run frontend build**

```bash
cd frontend
npm install
npm run build
```

Expected: Vite production build succeeds.

**Step 5: Commit**

```bash
git add frontend
git commit -m "chore: scaffold vue frontend"
```

### Task 10: Implement Frontend Layout and Auth Pages

**Files:**
- Create: `frontend/src/layout/MainLayout.vue`
- Create: `frontend/src/views/auth/LoginView.vue`
- Create: `frontend/src/views/auth/RegisterView.vue`
- Create: `frontend/src/views/profile/ProfileView.vue`
- Modify: `frontend/src/router/index.js`
- Modify: `frontend/src/stores/auth.js`

**Step 1: Implement layout**

Build:

- left sidebar
- top user bar
- main content area

**Step 2: Implement login and register**

Connect forms to `/auth/login` and `/auth/register`.

**Step 3: Implement profile page**

Load and update profile.

**Step 4: Verify manually**

Run:

```bash
cd frontend
npm run dev
```

Expected:

- register page submits
- login stores token
- protected routes redirect when logged out

**Step 5: Commit**

```bash
git add frontend/src
git commit -m "feat: add frontend auth and layout"
```

### Task 11: Implement Dashboard and Core Module Pages

**Files:**
- Create API modules under `frontend/src/api/`
- Create views under:
  - `frontend/src/views/dashboard/`
  - `frontend/src/views/courses/`
  - `frontend/src/views/study/`
  - `frontend/src/views/health/`
  - `frontend/src/views/finance/`
  - `frontend/src/views/goals/`
- Create reusable components under `frontend/src/components/`

**Step 1: Implement dashboard**

Use cards and charts for:

- today courses
- todo tasks
- health overview
- budget overview
- goal progress

**Step 2: Implement course and schedule pages**

Support list, create, edit, delete.

**Step 3: Implement task management**

Support priority, deadline, status, and status update.

**Step 4: Implement study analysis**

Use ECharts for duration trend, completion rate, and course ratio.

**Step 5: Implement health records**

Support CRUD and trend display.

**Step 6: Implement finance and budget**

Support income, expense, category statistics, budget threshold display.

**Step 7: Implement goals and habits**

Support goal progress update, habit checkin, and streak display.

**Step 8: Run frontend build**

```bash
cd frontend
npm run build
```

Expected: build succeeds.

**Step 9: Commit**

```bash
git add frontend/src
git commit -m "feat: implement student assistant frontend pages"
```

### Task 12: Add Documentation

**Files:**
- Modify: `docs/api-design.md`
- Modify: `docs/test-plan.md`
- Modify: `docs/user-guide.md`
- Modify: `README.md`

**Step 1: Write API design**

List endpoints, request parameters, response examples, and auth requirements.

**Step 2: Write test plan**

Document unit tests, integration tests, frontend tests, and system scenarios.

**Step 3: Write user guide**

Explain:

- how to register and log in
- how to manage courses and tasks
- how to record study, health, finance, goals, and habits
- how to read dashboard statistics

**Step 4: Update README**

Add:

- prerequisites
- MySQL setup
- backend startup
- frontend startup
- test commands

**Step 5: Commit**

```bash
git add README.md docs
git commit -m "docs: add project usage and test documentation"
```

### Task 13: Final Verification

**Files:**
- Modify only if bugs are found during verification.

**Step 1: Start MySQL**

Ensure database `student_assistant` exists and schema is loaded.

**Step 2: Run backend tests**

```bash
cd backend
mvn test
```

Expected: all backend tests pass.

**Step 3: Start backend**

```bash
cd backend
mvn spring-boot:run
```

Expected: backend runs on `http://localhost:8080`.

**Step 4: Start frontend**

```bash
cd frontend
npm run dev
```

Expected: frontend runs on `http://localhost:5173`.

**Step 5: Manual system test**

Complete these flows:

- register and log in
- create course, task, and schedule
- add study record and view analysis
- add health record and view dashboard
- add expense and budget, verify warning status
- create goal and habit, check in habit

**Step 6: Commit final fixes**

```bash
git add .
git commit -m "fix: polish final verification issues"
```

**Step 7: Final status**

```bash
git status --short
```

Expected: no uncommitted changes.
