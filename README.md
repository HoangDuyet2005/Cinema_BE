# World Cinema - Backend (Spring Boot)

Hệ thống Backend quản lý rạp chiếu phim, đặt vé xem phim online, thanh toán VNPay, soát vé mã QR và Socket Realtime.

---

## 🛠️ Yêu cầu môi trường
- **Java**: JDK 17 (khuyên dùng Eclipse Adoptium JDK 17 hoặc OpenJDK 17)
- **Cơ sở dữ liệu**: MySQL 8.0+
- **Maven**: Đã tích hợp sẵn Maven Wrapper (`mvnw` / `mvnw.cmd`)

---

## 🗄️ Cấu hình Cơ sở dữ liệu (MySQL)
1. Mở MySQL Workbench hoặc Terminal MySQL.
2. Tạo cơ sở dữ liệu `cinema2`:
   ```sql
   CREATE DATABASE IF NOT EXISTS cinema2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
3. `spring.jpa.hibernate.ddl-auto=update` sẽ tự tạo bảng khi chạy lần đầu. File dump dữ liệu thật trước đây
   đã bị gỡ khỏi repo (chứa email/mật khẩu hash thật của người dùng - không nên public); nếu cần dữ liệu mẫu
   để test, hãy tự tạo tài khoản qua `/api/auth/signup` và nhập phim/lịch chiếu qua Swagger, hoặc tự export
   một bản dump **đã ẩn danh** từ máy của bạn rồi lưu riêng (không commit lên git).
4. Sao chép `src/main/resources/application.properties.example` thành `src/main/resources/application.properties`
   rồi điền giá trị thật của bạn (DB, `app.jwtSecret`, `vnpay.*`...). File `application.properties` đã được
   `.gitignore`, không commit lên git.
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cinema2?serverTimezone=Asia/Ho_Chi_Minh&useSSL=false&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=Your_MySQL_Password
   ```

---

## 🚀 Hướng dẫn khởi chạy Backend
Chạy bằng lệnh Maven Wrapper trong thư mục gốc của dự án:

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / MacOS
./mvnw spring-boot:run
```

Sau khi khởi chạy thành công:
- **Server URL**: `http://localhost:8080`
- **Swagger API Docs**: `http://localhost:8080/swagger-ui/index.html`

---

## 🔑 Tài khoản mẫu
Tự tạo tài khoản admin/staff/khách hàng qua `/api/auth/signup` (khách) và `/api/auth/registerStaff`
(cần đăng nhập bằng tài khoản ADMIN có sẵn) sau khi khởi tạo dữ liệu lần đầu. Không commit tài khoản/mật khẩu
thật vào README hay bất kỳ file nào trong repo.