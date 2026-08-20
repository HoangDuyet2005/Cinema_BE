USE cinema2;

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Chuyển trạng thái các phim cũ (1-7) sang đã chiếu xong (is_showing = 0)
UPDATE `movie` SET `is_showing` = 0 WHERE `id` <= 7;

-- 2. Thêm mới 10 bộ phim đang chiếu
INSERT INTO `movie` (`id`, `name`, `small_imageurl`, `large_imageurl`, `short_description`, `long_description`, `director`, `actors`, `categories`, `release_date`, `duration`, `trailerurl`, `language`, `rated`, `is_showing`, `created_at`, `updated_at`, `created_by`, `modified_by`) VALUES
(8, 'Nghỉ Hè Sợ Nghỉ Hưu', 
'/img/movies/nghi-he-so-nghi-huu.jpg', 
'/img/movies/nghi-he-so-nghi-huu.jpg', 
'Nghỉ Hè Sợ Nghỉ Hưu xoay quanh người cháu trai thế hệ Z Trí Bình và người ông nội cựu chiến binh Thời.', 
'Nghỉ Hè Sợ Nghỉ Hưu xoay quanh người cháu trai thuộc thế hệ Z - Trí Bình và người ông nội là cựu chiến binh - ông Thời. Giữa hai ông cháu có sự đối lập rõ rệt khi một bên là vẻ hiện đại, thời thượng đặc trưng của người trẻ và một bên là nét truyền thống, nghiêm trang của người lính. Nếu ông Thời luôn trăn trở về những ký ức xưa và khát khao tìm được đồng đội thời chiến tranh, thì Trí Bình lại loay hoay đối mặt với khủng hoảng đi tìm sự rực rỡ cho mình ở đời sống hiện tại.', 
'Huỳnh Lập', 
'Cody Nam Võ, NSƯT Cao Minh, Huỳnh Lập, Hồng Ánh, Hứa Vĩ Văn, Tín Nguyễn', 
'Hài, Gia Đình', 
'2026-08-21', 
110, 
'https://www.youtube.com/embed/RgFO3duBbPw', 
'Tiếng Việt', 
'T13', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(9, 'Người Nhện: Khởi Đầu Mới', 
'/img/movies/spiderman-brand-new-day-2_1784708394982.jpg', 
'/img/movies/spiderman-brand-new-day-2_1784708394982.jpg', 
'Bốn năm sau các sự kiện trước, Peter giờ đã trưởng thành và sống hoàn toàn một mình làm Spider-Man toàn thời gian.', 
'Bốn năm sau các sự kiện trước, Peter giờ đã trưởng thành và sống hoàn toàn một mình, khi anh tự nguyện xóa bản thân khỏi cuộc sống và ký ức của những người mình yêu thương. Trong một New York không còn ai biết đến danh tính của mình, anh dốc toàn lực làm người hùng trở thành Spider-Man toàn thời gian để bảo vệ thành phố. Tuy nhiên, khi áp lực ngày càng gia tăng, nó kích hoạt một sự biến đổi thể chất bất ngờ, đe dọa chính sự tồn tại của anh. Đồng thời, một chuỗi tội phạm bí ẩn mới xuất hiện, kéo theo một trong những mối đe dọa mạnh mẽ nhất mà Spider-Man từng đối mặt.', 
'Destin Daniel Cretton', 
'Tom Holland, Zendaya, Sadie Sink, Jacob Batalon, Mark Ruffalo', 
'Giả Tưởng, Hành Động, Phiêu Lưu', 
'2026-07-31', 
145, 
'https://www.youtube.com/embed/b_FbrHg9118', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'T13', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(10, 'Ngày Tàn Của Phố Oak', 
'/img/movies/pho-oak-500_1782273857915.jpg', 
'/img/movies/pho-oak-500_1782273857915.jpg', 
'Sau khi một thảm họa thiên nhiên xé toạc Phố Oak, gia đình Platt phải kề vai sát cánh vượt qua vận mệnh nghiệt ngã.', 
'Sau khi một thảm họa thiên nhiên xé toạc Phố Oak khỏi khu ngoại ô và đưa con phố đến một nơi xa lạ, gia đình Platt nhanh chóng nhận ra rằng chỉ bằng cách luôn đứng bên nhau, họ mới có thể vượt qua vận mệnh nghiệt ngã này.', 
'David Robert Mitchell', 
'Ewan McGregor, Anne Hathaway', 
'Kinh Dị, Bí Ẩn', 
'2026-08-14', 
105, 
'https://www.youtube.com/embed/MwqWi3L4Kvk', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'T13', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(11, 'Thư Tình Gửi Ngoại', 
'/img/movies/thu-tinh-gui-ngoai-500_1784276463437.jpg', 
'/img/movies/thu-tinh-gui-ngoai-500_1784276463437.jpg', 
'Câu chuyện gia đình ấm áp, giàu nghĩa tình sau những lá thư mà Trịnh Mộc Sinh gửi cho vợ suốt nhiều thập kỷ.', 
'Tác phẩm lấy bối cảnh Trung Quốc, Thái Lan từ thập niên 1950 tới hiện tại, nói về cuộc đời của bà Diệp Thục Như (Ngô Thiếu Khanh đóng). Xuất thân tiểu thư, bà trốn nhà đi theo chàng trai nghèo Trịnh Mộc Sinh (Vương Ngạn Đồng đóng), sinh ba người con. Vì biến cố chính trị, ông Mộc Sinh phiếu bạt tới Thái Lan, ở đây làm lụng kiếm tiền gửi về cho vợ con. Trịnh Mộc Sinh đầu óc nhanh nhạy, giỏi kiếm tiền, trợ cấp đủ đầy cho gia đình và một lòng một dạ với vợ. Đôi vợ chồng gửi gắm tình yêu qua những lá thư.', 
'Lam Hồng Xuân', 
'Lý Tư Đồng, Vương Ngạn Đồng, Ngô Thiếu Khanh', 
'Tâm Lý, Gia Đình', 
'2026-08-07', 
115, 
'https://www.youtube.com/embed/eaFBU-Tp8lA', 
'Tiếng Trung - Phụ đề Tiếng Việt', 
'T13', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(12, 'The Odyssey: Khúc Ca Khải Hoàn', 
'/img/movies/the-oddysey-500_1778060370554.jpg', 
'/img/movies/the-oddysey-500_1778060370554.jpg', 
'Theo chân Odysseus trong hành trình trở về nhà sau cuộc chiến thành Troy đầy hiểm nguy và gian nan.', 
'Theo chân Odysseus trong hành trình trở về nhà sau cuộc chiến thành Troy, tràn ngập thử thách như cuộc chạm trán của ông với Polyphemus, các nàng tiên cá, Circe và kết thúc bằng cuộc đoàn tụ với vợ mình, Penelope.', 
'Christopher Nolan', 
'Matt Damon, Tom Holland, Charlize Theron, Anne Hathaway, Jon Bernthal, Zendaya, Lupita Nyong o, Robert Pattinson', 
'Hành Động, Lịch Sử, Phiêu Lưu', 
'2026-07-17', 
155, 
'https://www.youtube.com/embed/hk0JVCJ8NOA', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'T16', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(13, 'Quỷ Quyệt: Ranh Giới Vô Định', 
'/img/movies/insidious-sneak-500_1786436825174.jpg', 
'/img/movies/insidious-sneak-500_1786436825174.jpg', 
'Gemma phát hiện mình có thể bước vào Cõi Vô Định và đưa những thực thể từ đó trở về thế giới thực.', 
'Quỷ Quyệt: Ranh Giới Vô Định xoay quanh Gemma (Amelia Eve), một bà mẹ trẻ trở về sống trong ngôi nhà thời thơ ấu cùng con gái và phát hiện mình có thể bước vào Cõi Vô Định, nơi giam giữ những linh hồn lạc lối. Nhưng Gemma không chỉ có thể bước vào Cõi Vô Định, cô còn sở hữu năng lực đưa những thực thể từ đó trở về thế giới thực. Khi các thế lực tà ác phát hiện ra sức mạnh này, ranh giới giữa hai thế giới dần sụp đổ, biến thế giới của người sống thành sân chơi của quỷ dữ.', 
'Jacob Chase', 
'Amelia Eve, Island Austin, Lin Shaye', 
'Kinh Dị', 
'2026-08-21', 
108, 
'https://www.youtube.com/embed/bmDLI7kCPPw', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'T16', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(14, 'Shin Cậu Bé Bút Chì: Xứ Sở Yêu Quái', 
'/img/movies/shin-chan-500_1785817182150.jpg', 
'/img/movies/shin-chan-500_1785817182150.jpg', 
'Shin và gia đình Nohara lạc vào Xứ sở Yêu quái bí ẩn trong chuyến về quê mùa hè đầy bất ngờ.', 
'Lấy bối cảnh mùa hè tại Akita, bộ phim theo chân Shin và gia đình Nohara trong chuyến về quê đầy háo hức. Tuy nhiên, một sự kiện kỳ lạ đã đưa cả gia đình lạc vào Xứ sở Yêu quái bí ẩn – nơi con người không được phép đặt chân tới. Tại đây, Shin và gia đình phải đối mặt với những thử thách chưa từng có, đồng thời gặp gỡ hàng loạt yêu quái độc đáo, hài hước và đáng yêu.', 
'Watanabe Masaki', 
'Kobayashi Yumiko, Narahashi Miki, Morikawa Toshiyuki, Koorogi Satomi', 
'Hoạt Hình, Giả Tưởng', 
'2026-08-21', 
98, 
'https://www.youtube.com/embed/7PsrUVHTfUo', 
'Tiếng Nhật - Lồng tiếng & Phụ đề Tiếng Việt', 
'P', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(15, 'Điểm Mù (Blind Spot)', 
'/img/movies/diem-mu-500_1785986748944.jpg', 
'/img/movies/diem-mu-500_1785986748944.jpg', 
'Seo-jin quyết tâm dấn thân vào cuộc săn đuổi vạch mặt kẻ thủ ác sát hại người chị em song sinh.', 
'Phát hiện người chị em song sinh qua đời bất thường dưới vỏ bọc tự sát, Seo-jin (Shin Min-A) quyết tâm dấn thân vào cuộc săn đuổi vạch mặt kẻ thủ ác. Đối mặt với căn bệnh di truyền đang rút cạn thị lực từng ngày và chính bản thân cô trở thành mục tiêu tiếp theo của kẻ sát nhân, hành trình giải mã ma trận tâm lý và truy lùng hung thủ của Seo-jin trở nên căng thẳng và nguy cấp hơn bao giờ hết.', 
'Yeom Ji Ho', 
'Shin Min Ah, Kim Nam Hee, Lee Seung Ryong', 
'Tội Phạm, Giật Gân', 
'2026-08-14', 
112, 
'https://www.youtube.com/embed/rhrv7w4EwUo', 
'Tiếng Hàn - Phụ đề Tiếng Việt', 
'T18', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(16, 'Kamen Rider Agito: Psychic War', 
'/img/movies/agito-adaptation-main-website-1200-x-1800-_1785138379150.jpg', 
'/img/movies/agito-adaptation-main-website-1200-x-1800-_1785138379150.jpg', 
'Viên cảnh sát Makoto Hikawa dũng cảm đứng lên chiến đấu khi năng lực siêu nhiên thức tỉnh khắp nơi.', 
'Agito: Psychic War kể về thế giới nơi mà con người bắt đầu thức tỉnh những năng lực siêu nhiên, hậu quả kéo theo đó là sự hỗn loạn dần trở nên mất kiểm soát. Với ý chí sắt đá của mình, viên cảnh sát Makoto Hikawa sẽ là người dám đứng lên chiến đấu giữa thế giới đầy biến động này.', 
'Tasaki Ryuta', 
'Kaname Jun, Kashu Toshiki, Yamasaki Jun', 
'Hành Động', 
'2026-08-14', 
95, 
'https://www.youtube.com/embed/itCosOs0_T0', 
'Tiếng Nhật - Phụ đề Tiếng Việt', 
'T16', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(17, 'Attack on Titan: The Last Attack', 
'/img/movies/attack-on-titan-2_1785484162552.jpg', 
'/img/movies/attack-on-titan-2_1785484162552.jpg', 
'Trận chiến cuối cùng định đoạt số phận thế giới giữa Eren Yeager cùng đội quân Titan Đại hình và nhân loại.', 
'Attack on Titan the Movie: The Last Attack sẽ đưa trận chiến dữ dội nhất của Eren Yeager lên màn ảnh rộng. Trong cuộc chiến cuối cùng định đoạt số phận thế giới, Eren Yeager đã giải phóng sức mạnh tối thượng của các Titan. Dẫn đầu đội quân Titan Đại hình khổng lồ, Eren quyết tâm hủy diệt mọi kẻ thù đe dọa đến quê hương Eldia.', 
'Hayashi Yuichiro', 
'Kamiya Hiroshi, Kaji Yuuki, Ishikawa Yui', 
'Hoạt Hình', 
'2026-08-14', 
145, 
'https://www.youtube.com/embed/YfQ7Ftv_Sqg', 
'Tiếng Nhật - Phụ đề Tiếng Việt', 
'T16', 
1, 
NOW(), NOW(), 'admin', 'admin')
ON DUPLICATE KEY UPDATE
`name` = VALUES(`name`),
`small_imageurl` = VALUES(`small_imageurl`),
`large_imageurl` = VALUES(`large_imageurl`),
`short_description` = VALUES(`short_description`),
`long_description` = VALUES(`long_description`),
`director` = VALUES(`director`),
`actors` = VALUES(`actors`),
`categories` = VALUES(`categories`),
`release_date` = VALUES(`release_date`),
`duration` = VALUES(`duration`),
`trailerurl` = VALUES(`trailerurl`),
`language` = VALUES(`language`),
`rated` = VALUES(`rated`),
`is_showing` = VALUES(`is_showing`);

-- 3. Tạo lịch chiếu mới cho 10 bộ phim đang chiếu (8 đến 17) cho hôm nay và 7 ngày tới
DELETE FROM `schedule`;

DROP PROCEDURE IF EXISTS Generate10NewSchedules;
DELIMITER $$
CREATE PROCEDURE Generate10NewSchedules()
BEGIN
    DECLARE day_offset INT DEFAULT 0;
    DECLARE cur_date DATE;
    
    WHILE day_offset <= 7 DO
        SET cur_date = DATE_ADD(CURDATE(), INTERVAL day_offset DAY);
        
        -- Phim 8: Nghỉ Hè Sợ Nghỉ Hưu
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '09:00:00', 1, 8, 1),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '14:30:00', 1, 8, 2),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '19:00:00', 1, 8, 3),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '10:00:00', 2, 8, 5),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '16:30:00', 2, 8, 6),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '11:15:00', 3, 8, 8),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '18:00:00', 3, 8, 9),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '13:00:00', 4, 8, 12),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:00:00', 4, 8, 13);

        -- Phim 9: Người Nhện: Khởi Đầu Mới
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '09:30:00', 1, 9, 2),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '15:00:00', 1, 9, 3),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '20:30:00', 1, 9, 4),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '10:30:00', 2, 9, 6),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '18:45:00', 2, 9, 7),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '13:30:00', 3, 9, 10),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '19:30:00', 3, 9, 11),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '11:00:00', 4, 9, 14),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '17:30:00', 4, 9, 15);

        -- Phim 10: Ngày Tàn Của Phố Oak
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '10:00:00', 1, 10, 3),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '16:00:00', 1, 10, 4),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:15:00', 1, 10, 1),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '11:00:00', 2, 10, 5),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '19:15:00', 2, 10, 6),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '12:00:00', 3, 10, 8),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '17:15:00', 3, 10, 9),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '14:15:00', 4, 10, 12),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:45:00', 4, 10, 13);

        -- Phim 11: Thư Tình Gửi Ngoại
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '08:45:00', 1, 11, 4),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '13:15:00', 1, 11, 1),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '18:15:00', 1, 11, 2),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '14:00:00', 2, 11, 7),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '20:15:00', 2, 11, 5),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '15:00:00', 3, 11, 10),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '20:00:00', 3, 11, 11),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '09:30:00', 4, 11, 14),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '16:00:00', 4, 11, 15);

        -- Phim 12: The Odyssey: Khúc Ca Khải Hoàn
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '10:15:00', 1, 12, 1),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '17:00:00', 1, 12, 2),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '21:00:00', 1, 12, 3),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '12:30:00', 2, 12, 6),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '17:45:00', 2, 12, 7),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '10:00:00', 3, 12, 8),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '16:30:00', 3, 12, 9),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '12:00:00', 4, 12, 12),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '19:15:00', 4, 12, 13);

        -- Phim 13: Quỷ Quyệt: Ranh Giới Vô Định
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '11:30:00', 1, 13, 2),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '18:30:00', 1, 13, 3),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '22:00:00', 1, 13, 4),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '15:15:00', 2, 13, 5),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:00:00', 2, 13, 6),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '14:00:00', 3, 13, 10),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:30:00', 3, 13, 11),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '15:15:00', 4, 13, 14),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:45:00', 4, 13, 15);

        -- Phim 14: Shin Cậu Bé Bút Chì: Xứ Sở Yêu Quái
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '08:30:00', 1, 14, 3),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '12:00:00', 1, 14, 4),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '16:15:00', 1, 14, 1),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '09:15:00', 2, 14, 7),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '13:30:00', 2, 14, 5),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '09:00:00', 3, 14, 8),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '13:30:00', 3, 14, 9),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '10:15:00', 4, 14, 12),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '14:45:00', 4, 14, 13);

        -- Phim 15: Điểm Mù (Blind Spot)
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '12:45:00', 1, 15, 4),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '17:30:00', 1, 15, 1),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:30:00', 1, 15, 2),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '16:00:00', 2, 15, 6),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:30:00', 2, 15, 7),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '15:45:00', 3, 15, 10),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:45:00', 3, 15, 11),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '17:00:00', 4, 15, 14),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:15:00', 4, 15, 15);

        -- Phim 16: Kamen Rider Agito: Psychic War
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '09:15:00', 1, 16, 1),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '13:45:00', 1, 16, 2),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '19:45:00', 1, 16, 3),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '11:45:00', 2, 16, 5),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '17:15:00', 2, 16, 6),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '11:00:00', 3, 16, 8),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '18:45:00', 3, 16, 9),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '13:30:00', 4, 16, 12),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '18:45:00', 4, 16, 13);

        -- Phim 17: Attack on Titan: The Last Attack
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '10:45:00', 1, 17, 2),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '15:30:00', 1, 17, 3),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '20:15:00', 1, 17, 4),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '14:30:00', 2, 17, 7),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '19:45:00', 2, 17, 5),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '12:30:00', 3, 17, 10),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '19:00:00', 3, 17, 11),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '11:45:00', 4, 17, 14),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '18:00:00', 4, 17, 15);

        SET day_offset = day_offset + 1;
    END WHILE;
END$$
DELIMITER ;

CALL Generate10NewSchedules();
DROP PROCEDURE Generate10NewSchedules;

SET FOREIGN_KEY_CHECKS = 1;
