USE cinema2;

INSERT INTO article (title, brief, description, type, status, main_image, thumbnail, view, created_at)
VALUES
(
    'Happy Day - Vé Chỉ Từ 45K',
    'Thứ Ba Vui Vẻ - Giá vé xem phim cực ưu đãi chỉ từ 45.000đ dành cho mọi khán giả tại rạp World Cinema.',
    '<p>Thưởng thức các siêu phẩm điện ảnh với mức giá siêu hạt dẻ chỉ từ 45k vào mỗi Thứ Ba hàng tuần tại tất cả các cụm rạp World Cinema trên toàn quốc!</p>',
    1,
    0,
    '/img/hinh-1-14.jpg',
    '/img/hinh-1-14.jpg',
    1250,
    NOW()
),
(
    'Ưu Đãi Thành Viên World Cinema 2026',
    'Quyền lợi và chính sách tích điểm, quà tặng sinh nhật, vé xem phim miễn phí dành riêng cho thành viên World Cinema.',
    '<p>Trở thành thành viên World Cinema ngay hôm nay để nhận vô vàn đặc quyền hấp dẫn: tích điểm đổi vé xem phim, bắp nước và quà sinh nhật đặc biệt!</p>',
    1,
    0,
    '/img/nghi-he-so-nghi-huu-1_1786436532090.jpg',
    '/img/nghi-he-so-nghi-huu-1_1786436532090.jpg',
    2100,
    NOW()
),
(
    'Ngày Tri Ân Của World Cinema - Ngày Thứ Hai ĐẦU TIÊN Mỗi Tháng',
    'Ngày Tri Ân Khách Hàng - Đồng giá vé 45K/vé 2D và miễn phí 1 lần châm thêm bắp & nước vào Thứ Hai đầu tiên mỗi tháng.',
    '<p>World Cinema gửi lời tri ân sâu sắc đến quý khách hàng với chương trình đồng giá 45K và refill bắp nước miễn phí vào Thứ Hai đầu tiên mỗi tháng.</p>',
    1,
    0,
    '/img/insidious-sneak-2048_1786436889562.jpg',
    '/img/insidious-sneak-2048_1786436889562.jpg',
    980,
    NOW()
),
(
    'Hai Combo Conan Mừng Thám Tử Lừng Danh Trở Lại!',
    'Bộ đôi Combo Conan độc quyền kèm bình nước và xô bắp phiên bản giới hạn mừng thám tử lừng danh tái xuất.',
    '<p>Sở hữu ngay các vật phẩm Conan siêu độc lạ khi thưởng thức bộ phim mới nhất tại rạp World Cinema.</p>',
    1,
    0,
    'https://res.cloudinary.com/dfb5p3kus/image/upload/v1787134429/dsbevwwpcsnmnyn2been.jpg',
    'https://res.cloudinary.com/dfb5p3kus/image/upload/v1787134429/dsbevwwpcsnmnyn2been.jpg',
    3400,
    NOW()
);