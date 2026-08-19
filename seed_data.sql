USE cinema2;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE ticket;
TRUNCATE TABLE bill;
TRUNCATE TABLE user_role;
TRUNCATE TABLE user_save_articles;
TRUNCATE TABLE article;
TRUNCATE TABLE comment;
TRUNCATE TABLE like_user;
TRUNCATE TABLE schedule;
TRUNCATE TABLE seat;
TRUNCATE TABLE room;
TRUNCATE TABLE movie;
TRUNCATE TABLE branch;
TRUNCATE TABLE role;
TRUNCATE TABLE user;

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================
-- 1. ROLES
-- =============================================
INSERT INTO role (id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

-- =============================================
-- 2. USER (password: 123456 - hashed by BCrypt)
-- =============================================
INSERT INTO user (id, name, username, email, password, image, created_at, updated_at) VALUES 
(1, 'Admin CGV', 'admin', 'admin@cgv.vn', '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUawW0O8/i52', 'https://i.pravatar.cc/150?img=11', NOW(), NOW());

INSERT INTO user_role (user_id, role_id) VALUES (1, 2), (1, 1);

-- =============================================
-- 3. MOVIES (from the commented-out code)
-- =============================================
INSERT INTO movie (id, name, small_imageurl, large_imageurl, short_description, long_description, director, actors, categories, release_date, duration, trailerurl, language, rated, is_showing, created_at, updated_at) VALUES 
(1, 'Nhóc Trùm: Nối Nghiệp Gia Đình',
 'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_boss_baby_2_24.12.2021_1_1_1__1.jpg',
 'https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/r/s/rsz_dr-strange-980x448.jpg',
 'Nhóc trùm Ted giờ đây đã trở thành một triệu phú nổi tiếng trong khi Tim lại có một cuộc sống đơn giản bên vợ anh Carol và hai cô con gái nhỏ yêu dấu.',
 'Nhóc trùm Ted giờ đây đã trở thành một triệu phú nổi tiếng trong khi Tim lại có một cuộc sống đơn giản bên vợ anh Carol và hai cô con gái nhỏ yêu dấu. Mỗi mùa Giáng sinh tới, cả Tina và Tabitha đều mong được gặp chú Ted nhưng dường như hai anh em nhà Templeton nay đã không còn gần gũi như xưa. Nhưng bất ngờ thay khi Ted lại có màn tái xuất không thể hoành tráng hơn khi đáp thẳng máy bay trực thăng tới nhà Tim trước sự ngỡ ngàng của cả gia đình.',
 'Tom McGrath', 'Amy Sedaris, Jeff Goldblum, James Marsden',
 'Hoạt Hình', '2021-12-24', 105, 'https://www.youtube.com/embed/Lv8nL2q8yRI',
 'Tiếng Anh với phụ đề tiếng Việt và lồng tiếng Việt', 'P - PHIM DÀNH CHO MỌI ĐỐI TƯỢNG', 1, NOW(), NOW()),

(2, 'Venom: Đối Mặt Tử Thù',
 'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_venom_121121_1__1.jpg',
 'https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/b/l/blackpink-rolling_1_.jpg',
 'Siêu bom tấn VENOM: LET THERE BE CARNAGE hứa hẹn trận chiến khốc liệt nhất giữa Venom và kẻ thù truyền kiếp, Carnage.',
 'Siêu bom tấn VENOM: LET THERE BE CARNAGE hứa hẹn trận chiến khốc liệt nhất giữa Venom và kẻ thù truyền kiếp, Carnage.',
 'Andy Serkis', 'Tom Hardy, Michelle Williams, Woody Harrelson, Naomie Harris',
 'Hành Động, Khoa Học Viễn Tưởng, Phiêu Lưu, Thần thoại', '2021-12-10', 97, 'https://www.youtube.com/embed/EVWdzVtSh1I',
 'Tiếng Anh - Phụ đề Tiếng Việt', 'C13 - PHIM CẤM KHÁN GIẢ DƯỚI 13 TUỔI', 1, NOW(), NOW()),

(3, 'Ma Trận: Hồi Sinh',
 'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_matrix_4_1__1.jpg',
 'https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/b/l/blackpink-rolling_1_.jpg',
 'Sau 20 năm, siêu phẩm ma trận đã trở lại với người xem, Neo is back!',
 'Ma Trận: Hồi Sinh là phần phim tiếp theo rất được trông đợi của loạt phim Ma Trận đình đám, đã góp phần tái định nghĩa thể loại phim khoa học viễn tưởng.',
 'Lana Wachowski', 'Keanu Reeves, Carrie-Anne Moss, Yahya Abdul-Mateen II, Jessica Henwick',
 'Hành Động, Khoa Học Viễn Tưởng', '2021-12-24', 148, 'https://www.youtube.com/embed/l2UTOJC5Tbk',
 'Tiếng Anh - Phụ đề Tiếng Việt, Phụ đề Tiếng Hàn', 'C18 - PHIM CẤM KHÁN GIẢ DƯỚI 18 TUỔI', 1, NOW(), NOW()),

(4, 'Doraemon: Ôi Bạn Ơi 2',
 'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_doremon_2_1__1.jpg',
 'https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/d/o/doreamon.jpg',
 'Một ngày nọ, Nobita vô tình tìm thấy chú gấu bông cũ, món đồ chơi chất chứa bao kỉ niệm cùng người bà đáng kính.',
 'Một ngày nọ, Nobita vô tình tìm thấy chú gấu bông cũ, món đồ chơi chất chứa bao kỉ niệm cùng người bà đáng kính. Với khát khao muốn gặp bà lần nữa, Nobita đã trở về quá khứ bằng cổ máy thời gian.',
 'Ryuichi Yagi, Takashi Yamazaki', 'Wasabi Mizuta, Megumi Oohara, Yumi Kakazu, Subaru Kimura',
 'Hài, Hoạt Hình', '2021-12-17', 96, 'https://www.youtube.com/embed/GXnOs4Hj8MA',
 'Tiếng Nhật - Phụ đề Tiếng Việt; Lồng tiếng', 'P - PHIM DÀNH CHO MỌI ĐỐI TƯỢNG', 1, NOW(), NOW()),

(5, 'Câu Chuyện Phía Tây',
 'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_wss_1200x1800__1.jpg',
 'https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/w/s/wss_sneak_980x448.jpg',
 'Câu chuyện phía Tây kể lại câu chuyện tình yêu kinh điển của Tony và Maria.',
 'Được đạo diễn bởi đạo diễn gạo cội từng giành giải Oscar Steven Spielberg, cùng kịch bản bởi biên kịch từng giành giải Pulitzer Prize và giải Tony Award.',
 'Steven Spielberg', 'Ansel Elgort, Rachel Zegler, Ariana DeBose, David Alvarez, Mike Faist',
 'Nhạc kịch, Tình cảm', '2021-12-24', 156, 'https://www.youtube.com/embed/QPvqV71P0Fo',
 'Tiếng Anh - Phụ đề Tiếng Việt', 'C16 - PHIM CẤM KHÁN GIẢ DƯỚI 16 TUỔI', 1, NOW(), NOW()),

(6, 'BlackPink The Movie',
 'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_blackpink_vie_2_1__1.jpg',
 'https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/b/l/blackpink-rolling_1_.jpg',
 'Nhóm nhạc nữ được yêu thích toàn cầu, BLACKPINK sẽ kỷ niệm năm thứ 5 hoạt động của nhóm.',
 'Nhóm nhạc nữ được yêu thích toàn cầu, BLACKPINK sẽ kỷ niệm năm thứ 5 hoạt động của nhóm với việc phát hành BLACKPINK THE MOVIE, đây cũng như là món quà đặc biệt dành tặng cho các BLINK.',
 'Su Yee Jung, Oh Yoon-Dong', 'JISOO, JENNIE, ROSÉ, LISA',
 'Phim tài liệu', '2021-12-24', 99, 'https://www.youtube.com/embed/Q_rK9UlUN-Q',
 'Tiếng Hàn - Phụ đề tiếng Việt', 'P - PHIM DÀNH CHO MỌI ĐỐI TƯỢNG', 1, NOW(), NOW()),

(7, 'Người Nhện: Không Còn Nhà',
 'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/s/n/snwh_poster_bluemontage_4x5fb_1__1.jpg',
 'https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/r/s/rsz_dr-strange-980x448.jpg',
 'Đa vũ trụ được mở ra, những kẻ phản diện nào sẽ trạm chán spidey, cùng đón xem nhá',
 'Lần đầu tiên trong lịch sử điện ảnh của Người Nhện, thân phận người hàng xóm thân thiện bị lật mở, khiến trách nhiệm làm một Siêu Anh Hùng xung đột với cuộc sống bình thường.',
 'Jon Watts', 'Tom Holland, Zendaya, Benedict Cumberbatch, Jacob Batalon, Jon Favreau',
 'Hành Động, Phiêu Lưu', '2021-12-17', 149, 'https://www.youtube.com/embed/daHCu_jU5mQ',
 'Tiếng Anh - Phụ đề Tiếng Việt', 'C13 - PHIM CẤM KHÁN GIẢ DƯỚI 13 TUỔI', 1, NOW(), NOW());

-- =============================================
-- 4. BRANCHES (from the commented-out code)
-- =============================================
INSERT INTO branch (id, name, address, phone_no, imgurl, created_at, updated_at) VALUES 
(1, 'WORLD CINEMA Hà Đông', 'Tầng 4, Mê Linh Plaza Hà Đông, Đ. Tô Hiệu, P, Hà Đông, Hà Nội', '0938473829', 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', NOW(), NOW()),
(2, 'WORLD CINEMA Thủ Đức', '216 Đ. Võ Văn Ngân, Bình Thọ, Thủ Đức, Thành phố Hồ Chí Minh', '1900 6017', 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', NOW(), NOW()),
(3, 'WORLD CINEMA Ba Đình', '29 P. Liễu Giai, Ngọc Khánh, Ba Đình, Hà Nội 100000', '1900 6017', 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', NOW(), NOW()),
(4, 'WORLD CINEMA Phạm Hùng', 'Phạm Hùng, Mỹ Đình, Nam Từ Liêm, Hà Nội', '1900 6017', 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', NOW(), NOW());

-- =============================================
-- 5. ROOMS (4 rooms per branch = 16 rooms total)
-- =============================================
-- Branch 1: Ha Dong
INSERT INTO room (id, name, capacity, total_area, imgurl, branch_id) VALUES 
(1, 'Phòng 101', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 1),
(2, 'Phòng 202', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 1),
(3, 'Phòng 303', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 1),
(4, 'Phòng 404', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 1),
-- Branch 2: Thu Duc
(5, 'Phòng 101', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 2),
(6, 'Phòng 202', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 2),
(7, 'Phòng 303', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 2),
-- Branch 3: Ba Dinh
(8, 'Phòng 101', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 3),
(9, 'Phòng 202', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 3),
(10, 'Phòng 303', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 3),
(11, 'Phòng 404', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 3),
-- Branch 4: Pham Hung
(12, 'Phòng 101', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 4),
(13, 'Phòng 202', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 4),
(14, 'Phòng 303', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 4),
(15, 'Phòng 404', 40, 80, 'http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg', 4);

-- =============================================
-- 6. SEATS (5 rows x 8 seats = 40 seats per room, for all rooms)
-- seat_type: 0=NORMAL, 1=VIP (row D is VIP as per the original code)
-- =============================================
-- Helper: Generate seats for each room
-- Room 1
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,1),('A2',0,1),('A3',0,1),('A4',0,1),('A5',0,1),('A6',0,1),('A7',0,1),('A8',0,1),
('B1',0,1),('B2',0,1),('B3',0,1),('B4',0,1),('B5',0,1),('B6',0,1),('B7',0,1),('B8',0,1),
('C1',0,1),('C2',0,1),('C3',0,1),('C4',0,1),('C5',0,1),('C6',0,1),('C7',0,1),('C8',0,1),
('D1',1,1),('D2',1,1),('D3',1,1),('D4',1,1),('D5',1,1),('D6',1,1),('D7',1,1),('D8',1,1),
('E1',0,1),('E2',0,1),('E3',0,1),('E4',0,1),('E5',0,1),('E6',0,1),('E7',0,1),('E8',0,1);
-- Room 2
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,2),('A2',0,2),('A3',0,2),('A4',0,2),('A5',0,2),('A6',0,2),('A7',0,2),('A8',0,2),
('B1',0,2),('B2',0,2),('B3',0,2),('B4',0,2),('B5',0,2),('B6',0,2),('B7',0,2),('B8',0,2),
('C1',0,2),('C2',0,2),('C3',0,2),('C4',0,2),('C5',0,2),('C6',0,2),('C7',0,2),('C8',0,2),
('D1',1,2),('D2',1,2),('D3',1,2),('D4',1,2),('D5',1,2),('D6',1,2),('D7',1,2),('D8',1,2),
('E1',0,2),('E2',0,2),('E3',0,2),('E4',0,2),('E5',0,2),('E6',0,2),('E7',0,2),('E8',0,2);
-- Room 3
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,3),('A2',0,3),('A3',0,3),('A4',0,3),('A5',0,3),('A6',0,3),('A7',0,3),('A8',0,3),
('B1',0,3),('B2',0,3),('B3',0,3),('B4',0,3),('B5',0,3),('B6',0,3),('B7',0,3),('B8',0,3),
('C1',0,3),('C2',0,3),('C3',0,3),('C4',0,3),('C5',0,3),('C6',0,3),('C7',0,3),('C8',0,3),
('D1',1,3),('D2',1,3),('D3',1,3),('D4',1,3),('D5',1,3),('D6',1,3),('D7',1,3),('D8',1,3),
('E1',0,3),('E2',0,3),('E3',0,3),('E4',0,3),('E5',0,3),('E6',0,3),('E7',0,3),('E8',0,3);
-- Room 4
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,4),('A2',0,4),('A3',0,4),('A4',0,4),('A5',0,4),('A6',0,4),('A7',0,4),('A8',0,4),
('B1',0,4),('B2',0,4),('B3',0,4),('B4',0,4),('B5',0,4),('B6',0,4),('B7',0,4),('B8',0,4),
('C1',0,4),('C2',0,4),('C3',0,4),('C4',0,4),('C5',0,4),('C6',0,4),('C7',0,4),('C8',0,4),
('D1',1,4),('D2',1,4),('D3',1,4),('D4',1,4),('D5',1,4),('D6',1,4),('D7',1,4),('D8',1,4),
('E1',0,4),('E2',0,4),('E3',0,4),('E4',0,4),('E5',0,4),('E6',0,4),('E7',0,4),('E8',0,4);
-- Room 5
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,5),('A2',0,5),('A3',0,5),('A4',0,5),('A5',0,5),('A6',0,5),('A7',0,5),('A8',0,5),
('B1',0,5),('B2',0,5),('B3',0,5),('B4',0,5),('B5',0,5),('B6',0,5),('B7',0,5),('B8',0,5),
('C1',0,5),('C2',0,5),('C3',0,5),('C4',0,5),('C5',0,5),('C6',0,5),('C7',0,5),('C8',0,5),
('D1',1,5),('D2',1,5),('D3',1,5),('D4',1,5),('D5',1,5),('D6',1,5),('D7',1,5),('D8',1,5),
('E1',0,5),('E2',0,5),('E3',0,5),('E4',0,5),('E5',0,5),('E6',0,5),('E7',0,5),('E8',0,5);
-- Room 6
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,6),('A2',0,6),('A3',0,6),('A4',0,6),('A5',0,6),('A6',0,6),('A7',0,6),('A8',0,6),
('B1',0,6),('B2',0,6),('B3',0,6),('B4',0,6),('B5',0,6),('B6',0,6),('B7',0,6),('B8',0,6),
('C1',0,6),('C2',0,6),('C3',0,6),('C4',0,6),('C5',0,6),('C6',0,6),('C7',0,6),('C8',0,6),
('D1',1,6),('D2',1,6),('D3',1,6),('D4',1,6),('D5',1,6),('D6',1,6),('D7',1,6),('D8',1,6),
('E1',0,6),('E2',0,6),('E3',0,6),('E4',0,6),('E5',0,6),('E6',0,6),('E7',0,6),('E8',0,6);
-- Room 7
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,7),('A2',0,7),('A3',0,7),('A4',0,7),('A5',0,7),('A6',0,7),('A7',0,7),('A8',0,7),
('B1',0,7),('B2',0,7),('B3',0,7),('B4',0,7),('B5',0,7),('B6',0,7),('B7',0,7),('B8',0,7),
('C1',0,7),('C2',0,7),('C3',0,7),('C4',0,7),('C5',0,7),('C6',0,7),('C7',0,7),('C8',0,7),
('D1',1,7),('D2',1,7),('D3',1,7),('D4',1,7),('D5',1,7),('D6',1,7),('D7',1,7),('D8',1,7),
('E1',0,7),('E2',0,7),('E3',0,7),('E4',0,7),('E5',0,7),('E6',0,7),('E7',0,7),('E8',0,7);
-- Room 8
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,8),('A2',0,8),('A3',0,8),('A4',0,8),('A5',0,8),('A6',0,8),('A7',0,8),('A8',0,8),
('B1',0,8),('B2',0,8),('B3',0,8),('B4',0,8),('B5',0,8),('B6',0,8),('B7',0,8),('B8',0,8),
('C1',0,8),('C2',0,8),('C3',0,8),('C4',0,8),('C5',0,8),('C6',0,8),('C7',0,8),('C8',0,8),
('D1',1,8),('D2',1,8),('D3',1,8),('D4',1,8),('D5',1,8),('D6',1,8),('D7',1,8),('D8',1,8),
('E1',0,8),('E2',0,8),('E3',0,8),('E4',0,8),('E5',0,8),('E6',0,8),('E7',0,8),('E8',0,8);
-- Room 9
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,9),('A2',0,9),('A3',0,9),('A4',0,9),('A5',0,9),('A6',0,9),('A7',0,9),('A8',0,9),
('B1',0,9),('B2',0,9),('B3',0,9),('B4',0,9),('B5',0,9),('B6',0,9),('B7',0,9),('B8',0,9),
('C1',0,9),('C2',0,9),('C3',0,9),('C4',0,9),('C5',0,9),('C6',0,9),('C7',0,9),('C8',0,9),
('D1',1,9),('D2',1,9),('D3',1,9),('D4',1,9),('D5',1,9),('D6',1,9),('D7',1,9),('D8',1,9),
('E1',0,9),('E2',0,9),('E3',0,9),('E4',0,9),('E5',0,9),('E6',0,9),('E7',0,9),('E8',0,9);
-- Room 10
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,10),('A2',0,10),('A3',0,10),('A4',0,10),('A5',0,10),('A6',0,10),('A7',0,10),('A8',0,10),
('B1',0,10),('B2',0,10),('B3',0,10),('B4',0,10),('B5',0,10),('B6',0,10),('B7',0,10),('B8',0,10),
('C1',0,10),('C2',0,10),('C3',0,10),('C4',0,10),('C5',0,10),('C6',0,10),('C7',0,10),('C8',0,10),
('D1',1,10),('D2',1,10),('D3',1,10),('D4',1,10),('D5',1,10),('D6',1,10),('D7',1,10),('D8',1,10),
('E1',0,10),('E2',0,10),('E3',0,10),('E4',0,10),('E5',0,10),('E6',0,10),('E7',0,10),('E8',0,10);
-- Room 11
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,11),('A2',0,11),('A3',0,11),('A4',0,11),('A5',0,11),('A6',0,11),('A7',0,11),('A8',0,11),
('B1',0,11),('B2',0,11),('B3',0,11),('B4',0,11),('B5',0,11),('B6',0,11),('B7',0,11),('B8',0,11),
('C1',0,11),('C2',0,11),('C3',0,11),('C4',0,11),('C5',0,11),('C6',0,11),('C7',0,11),('C8',0,11),
('D1',1,11),('D2',1,11),('D3',1,11),('D4',1,11),('D5',1,11),('D6',1,11),('D7',1,11),('D8',1,11),
('E1',0,11),('E2',0,11),('E3',0,11),('E4',0,11),('E5',0,11),('E6',0,11),('E7',0,11),('E8',0,11);
-- Room 12
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,12),('A2',0,12),('A3',0,12),('A4',0,12),('A5',0,12),('A6',0,12),('A7',0,12),('A8',0,12),
('B1',0,12),('B2',0,12),('B3',0,12),('B4',0,12),('B5',0,12),('B6',0,12),('B7',0,12),('B8',0,12),
('C1',0,12),('C2',0,12),('C3',0,12),('C4',0,12),('C5',0,12),('C6',0,12),('C7',0,12),('C8',0,12),
('D1',1,12),('D2',1,12),('D3',1,12),('D4',1,12),('D5',1,12),('D6',1,12),('D7',1,12),('D8',1,12),
('E1',0,12),('E2',0,12),('E3',0,12),('E4',0,12),('E5',0,12),('E6',0,12),('E7',0,12),('E8',0,12);
-- Room 13
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,13),('A2',0,13),('A3',0,13),('A4',0,13),('A5',0,13),('A6',0,13),('A7',0,13),('A8',0,13),
('B1',0,13),('B2',0,13),('B3',0,13),('B4',0,13),('B5',0,13),('B6',0,13),('B7',0,13),('B8',0,13),
('C1',0,13),('C2',0,13),('C3',0,13),('C4',0,13),('C5',0,13),('C6',0,13),('C7',0,13),('C8',0,13),
('D1',1,13),('D2',1,13),('D3',1,13),('D4',1,13),('D5',1,13),('D6',1,13),('D7',1,13),('D8',1,13),
('E1',0,13),('E2',0,13),('E3',0,13),('E4',0,13),('E5',0,13),('E6',0,13),('E7',0,13),('E8',0,13);
-- Room 14
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,14),('A2',0,14),('A3',0,14),('A4',0,14),('A5',0,14),('A6',0,14),('A7',0,14),('A8',0,14),
('B1',0,14),('B2',0,14),('B3',0,14),('B4',0,14),('B5',0,14),('B6',0,14),('B7',0,14),('B8',0,14),
('C1',0,14),('C2',0,14),('C3',0,14),('C4',0,14),('C5',0,14),('C6',0,14),('C7',0,14),('C8',0,14),
('D1',1,14),('D2',1,14),('D3',1,14),('D4',1,14),('D5',1,14),('D6',1,14),('D7',1,14),('D8',1,14),
('E1',0,14),('E2',0,14),('E3',0,14),('E4',0,14),('E5',0,14),('E6',0,14),('E7',0,14),('E8',0,14);
-- Room 15
INSERT INTO seat (name, seat_type, room_id) VALUES 
('A1',0,15),('A2',0,15),('A3',0,15),('A4',0,15),('A5',0,15),('A6',0,15),('A7',0,15),('A8',0,15),
('B1',0,15),('B2',0,15),('B3',0,15),('B4',0,15),('B5',0,15),('B6',0,15),('B7',0,15),('B8',0,15),
('C1',0,15),('C2',0,15),('C3',0,15),('C4',0,15),('C5',0,15),('C6',0,15),('C7',0,15),('C8',0,15),
('D1',1,15),('D2',1,15),('D3',1,15),('D4',1,15),('D5',1,15),('D6',1,15),('D7',1,15),('D8',1,15),
('E1',0,15),('E2',0,15),('E3',0,15),('E4',0,15),('E5',0,15),('E6',0,15),('E7',0,15),('E8',0,15);

-- =============================================
-- 7. SCHEDULES - ALL movies at ALL branches (today + tomorrow)
-- Movies: 1-7, Branches: 1-4
-- Branch 1 rooms: 1,2,3,4  |  Branch 2 rooms: 5,6,7
-- Branch 3 rooms: 8,9,10,11  |  Branch 4 rooms: 12,13,14,15
-- =============================================

-- ===== BRANCH 1 (Hà Đông) =====
INSERT INTO schedule (price, start_date, start_time, branch_id, movie_id, room_id, created_at, updated_at) VALUES 
-- Movie 1 - Bẫy Ngọt Ngào
(70000, CURDATE(), '09:00:00', 1, 1, 1, NOW(), NOW()),
(70000, CURDATE(), '14:30:00', 1, 1, 1, NOW(), NOW()),
(70000, CURDATE(), '20:00:00', 1, 1, 2, NOW(), NOW()),
-- Movie 2 - Nhà Bà Nữ
(70000, CURDATE(), '10:00:00', 1, 2, 2, NOW(), NOW()),
(70000, CURDATE(), '15:00:00', 1, 2, 2, NOW(), NOW()),
(70000, CURDATE(), '21:00:00', 1, 2, 3, NOW(), NOW()),
-- Movie 3 - Ma Trận: Hồi Sinh
(70000, CURDATE(), '09:30:00', 1, 3, 3, NOW(), NOW()),
(70000, CURDATE(), '14:00:00', 1, 3, 3, NOW(), NOW()),
(70000, CURDATE(), '19:30:00', 1, 3, 4, NOW(), NOW()),
-- Movie 4 - Doraemon
(65000, CURDATE(), '10:15:00', 1, 4, 4, NOW(), NOW()),
(65000, CURDATE(), '13:00:00', 1, 4, 1, NOW(), NOW()),
(65000, CURDATE(), '16:30:00', 1, 4, 2, NOW(), NOW()),
-- Movie 5 - Câu Chuyện Phía Tây
(70000, CURDATE(), '11:00:00', 1, 5, 1, NOW(), NOW()),
(70000, CURDATE(), '16:00:00', 1, 5, 3, NOW(), NOW()),
(70000, CURDATE(), '21:30:00', 1, 5, 4, NOW(), NOW()),
-- Movie 6 - BlackPink The Movie
(75000, CURDATE(), '10:30:00', 1, 6, 2, NOW(), NOW()),
(75000, CURDATE(), '15:30:00', 1, 6, 4, NOW(), NOW()),
(75000, CURDATE(), '19:00:00', 1, 6, 1, NOW(), NOW()),
-- Movie 7 - Người Nhện: Không Còn Nhà
(80000, CURDATE(), '10:15:00', 1, 7, 1, NOW(), NOW()),
(80000, CURDATE(), '13:05:00', 1, 7, 2, NOW(), NOW()),
(80000, CURDATE(), '16:20:00', 1, 7, 3, NOW(), NOW()),
(80000, CURDATE(), '19:15:00', 1, 7, 4, NOW(), NOW()),
(80000, CURDATE(), '22:00:00', 1, 7, 1, NOW(), NOW()),

-- ===== BRANCH 2 (Thủ Đức) =====
-- Movie 1
(70000, CURDATE(), '09:00:00', 2, 1, 5, NOW(), NOW()),
(70000, CURDATE(), '14:00:00', 2, 1, 5, NOW(), NOW()),
(70000, CURDATE(), '20:00:00', 2, 1, 6, NOW(), NOW()),
-- Movie 2
(70000, CURDATE(), '10:00:00', 2, 2, 6, NOW(), NOW()),
(70000, CURDATE(), '15:30:00', 2, 2, 6, NOW(), NOW()),
(70000, CURDATE(), '21:00:00', 2, 2, 7, NOW(), NOW()),
-- Movie 3
(70000, CURDATE(), '09:30:00', 2, 3, 7, NOW(), NOW()),
(70000, CURDATE(), '14:30:00', 2, 3, 5, NOW(), NOW()),
(70000, CURDATE(), '19:30:00', 2, 3, 6, NOW(), NOW()),
-- Movie 4
(65000, CURDATE(), '10:15:00', 2, 4, 5, NOW(), NOW()),
(65000, CURDATE(), '13:00:00', 2, 4, 6, NOW(), NOW()),
(65000, CURDATE(), '16:00:00', 2, 4, 7, NOW(), NOW()),
-- Movie 5
(70000, CURDATE(), '11:00:00', 2, 5, 7, NOW(), NOW()),
(70000, CURDATE(), '17:00:00', 2, 5, 5, NOW(), NOW()),
(70000, CURDATE(), '21:30:00', 2, 5, 6, NOW(), NOW()),
-- Movie 6
(75000, CURDATE(), '10:30:00', 2, 6, 6, NOW(), NOW()),
(75000, CURDATE(), '15:00:00', 2, 6, 7, NOW(), NOW()),
(75000, CURDATE(), '19:00:00', 2, 6, 5, NOW(), NOW()),
-- Movie 7
(80000, CURDATE(), '10:15:00', 2, 7, 5, NOW(), NOW()),
(80000, CURDATE(), '13:30:00', 2, 7, 6, NOW(), NOW()),
(80000, CURDATE(), '16:45:00', 2, 7, 7, NOW(), NOW()),
(80000, CURDATE(), '20:00:00', 2, 7, 5, NOW(), NOW()),

-- ===== BRANCH 3 (Ba Đình) =====
-- Movie 1
(70000, CURDATE(), '09:00:00', 3, 1, 8, NOW(), NOW()),
(70000, CURDATE(), '14:00:00', 3, 1, 9, NOW(), NOW()),
(70000, CURDATE(), '19:30:00', 3, 1, 10, NOW(), NOW()),
-- Movie 2
(70000, CURDATE(), '10:00:00', 3, 2, 9, NOW(), NOW()),
(70000, CURDATE(), '15:00:00', 3, 2, 10, NOW(), NOW()),
(70000, CURDATE(), '20:30:00', 3, 2, 11, NOW(), NOW()),
-- Movie 3
(70000, CURDATE(), '09:30:00', 3, 3, 10, NOW(), NOW()),
(70000, CURDATE(), '14:30:00', 3, 3, 11, NOW(), NOW()),
(70000, CURDATE(), '19:00:00', 3, 3, 8, NOW(), NOW()),
-- Movie 4
(65000, CURDATE(), '10:15:00', 3, 4, 11, NOW(), NOW()),
(65000, CURDATE(), '13:30:00', 3, 4, 8, NOW(), NOW()),
(65000, CURDATE(), '16:00:00', 3, 4, 9, NOW(), NOW()),
-- Movie 5
(70000, CURDATE(), '11:00:00', 3, 5, 8, NOW(), NOW()),
(70000, CURDATE(), '16:30:00', 3, 5, 9, NOW(), NOW()),
(70000, CURDATE(), '21:00:00', 3, 5, 10, NOW(), NOW()),
-- Movie 6
(75000, CURDATE(), '10:30:00', 3, 6, 9, NOW(), NOW()),
(75000, CURDATE(), '15:30:00', 3, 6, 10, NOW(), NOW()),
(75000, CURDATE(), '20:00:00', 3, 6, 11, NOW(), NOW()),
-- Movie 7
(80000, CURDATE(), '10:15:00', 3, 7, 8, NOW(), NOW()),
(80000, CURDATE(), '13:30:00', 3, 7, 9, NOW(), NOW()),
(80000, CURDATE(), '17:00:00', 3, 7, 10, NOW(), NOW()),
(80000, CURDATE(), '20:15:00', 3, 7, 11, NOW(), NOW()),

-- ===== BRANCH 4 (Phạm Hùng) =====
-- Movie 1
(70000, CURDATE(), '09:00:00', 4, 1, 12, NOW(), NOW()),
(70000, CURDATE(), '14:30:00', 4, 1, 13, NOW(), NOW()),
(70000, CURDATE(), '19:00:00', 4, 1, 14, NOW(), NOW()),
-- Movie 2
(70000, CURDATE(), '10:00:00', 4, 2, 13, NOW(), NOW()),
(70000, CURDATE(), '15:30:00', 4, 2, 14, NOW(), NOW()),
(70000, CURDATE(), '20:30:00', 4, 2, 15, NOW(), NOW()),
-- Movie 3
(70000, CURDATE(), '09:30:00', 4, 3, 14, NOW(), NOW()),
(70000, CURDATE(), '14:00:00', 4, 3, 15, NOW(), NOW()),
(70000, CURDATE(), '19:30:00', 4, 3, 12, NOW(), NOW()),
-- Movie 4
(65000, CURDATE(), '10:15:00', 4, 4, 15, NOW(), NOW()),
(65000, CURDATE(), '13:00:00', 4, 4, 12, NOW(), NOW()),
(65000, CURDATE(), '16:30:00', 4, 4, 13, NOW(), NOW()),
-- Movie 5
(70000, CURDATE(), '11:00:00', 4, 5, 12, NOW(), NOW()),
(70000, CURDATE(), '16:00:00', 4, 5, 13, NOW(), NOW()),
(70000, CURDATE(), '21:30:00', 4, 5, 14, NOW(), NOW()),
-- Movie 6
(75000, CURDATE(), '10:30:00', 4, 6, 13, NOW(), NOW()),
(75000, CURDATE(), '15:00:00', 4, 6, 14, NOW(), NOW()),
(75000, CURDATE(), '19:30:00', 4, 6, 15, NOW(), NOW()),
-- Movie 7
(80000, CURDATE(), '10:15:00', 4, 7, 12, NOW(), NOW()),
(80000, CURDATE(), '13:30:00', 4, 7, 13, NOW(), NOW()),
(80000, CURDATE(), '16:45:00', 4, 7, 14, NOW(), NOW()),
(80000, CURDATE(), '20:00:00', 4, 7, 15, NOW(), NOW()),

-- ===== TOMORROW SCHEDULES (all branches) =====
(80000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:15:00', 1, 7, 1, NOW(), NOW()),
(80000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '16:20:00', 1, 7, 2, NOW(), NOW()),
(80000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '19:20:00', 1, 7, 3, NOW(), NOW()),
(70000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:00:00', 1, 1, 1, NOW(), NOW()),
(70000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '14:00:00', 1, 2, 2, NOW(), NOW()),
(70000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '18:00:00', 1, 3, 3, NOW(), NOW()),
(65000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:00:00', 2, 4, 5, NOW(), NOW()),
(70000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '14:00:00', 2, 5, 6, NOW(), NOW()),
(75000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '18:00:00', 2, 6, 7, NOW(), NOW()),
(70000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:00:00', 3, 1, 8, NOW(), NOW()),
(70000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '14:00:00', 3, 2, 9, NOW(), NOW()),
(80000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '18:00:00', 3, 7, 10, NOW(), NOW()),
(70000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:00:00', 4, 3, 12, NOW(), NOW()),
(65000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '14:00:00', 4, 4, 13, NOW(), NOW()),
(70000, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '18:00:00', 4, 5, 14, NOW(), NOW());

SELECT 'Seed data loaded successfully!' AS status;

