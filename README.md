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
3. Import toàn bộ cấu trúc bảng và dữ liệu mẫu từ file dump có sẵn trong thư mục `database/`:
   ```bash
   # Cách 1: Sử dụng dòng lệnh MySQL (CMD/Terminal)
   mysql -u root -p cinema2 < database/cinema2_database_dump.sql

   # Cách 2: Sử dụng MySQL Workbench
   # Server -> Data Import -> Import from Self-Contained File -> Chọn database/cinema2_database_dump.sql -> Start Import
   ```
4. Kiểm tra cấu hình kết nối DB trong file `src/main/resources/application.properties`:
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

## 🔑 Tài khoản mẫu mặc định
- **Admin**: `admin` / `123456`
- **Staff (Nhân viên)**: `staff` / `123456`
- **Khách hàng**: `duyetht` / mật khẩu cá nhân