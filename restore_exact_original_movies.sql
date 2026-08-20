USE cinema2;

SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM `movie`;

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

SET FOREIGN_KEY_CHECKS = 1;
