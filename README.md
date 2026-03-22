# MediConnect 🏥
> Full Stack Hospital Queue Management System

A production-ready full stack application that eliminates clinic waiting chaos through real-time queue tracking, seamless appointment booking and intelligent patient management.

## 🌟 Live Demo
- **🌐 Frontend:** [MediConnect App](https://profound-pie-914901.netlify.app)
- **📋 Swagger API Docs:** [Swagger UI](https://mediconnect-1xln.onrender.com/swagger-ui/index.html)
- **💻 GitHub:** [Source Code](https://github.com/Ankeerthana/mediconnect)

## 🚀 Features
- Patient registration and JWT login
- Browse doctors and book appointments
- Real-time queue position tracking
- Doctor dashboard — accept, complete, cancel appointments
- Admin dashboard — manage doctors and appointments
- Role based access — Patient, Doctor, Admin

## 🛠️ Tech Stack
- **Backend:** Java 21, Spring Boot 3.5
- **Security:** Spring Security + JWT
- **Database:** PostgreSQL
- **ORM:** JPA / Hibernate
- **Docs:** Swagger / OpenAPI
- **Frontend:** HTML, CSS, JavaScript
- **Deploy:** Docker + Render + Netlify

## ⚙️ API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register user |
| POST | /api/auth/login | Login get JWT |
| POST | /api/appointments/book | Book appointment |
| GET | /api/appointments/my | My appointments |
| GET | /api/appointments/queue/{doctorId} | Doctor queue |
| PUT | /api/appointments/update/{id} | Update status |
| GET | /api/doctors/all | All doctors |

## 🔐 Security
- JWT token authentication
- Role based access control
- BCrypt password encoding
- CORS configured

## 💻 Local Setup
```bash
git clone https://github.com/Ankeerthana/mediconnect.git
cd mediconnect
# Copy application.properties.example to application.properties
# Update with your DB and mail credentials
./mvnw spring-boot:run
```

## 👩‍💻 Developer
**A N Keerthana** — [@Ankeerthana](https://github.com/Ankeerthana)
