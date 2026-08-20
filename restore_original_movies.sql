USE cinema2;

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Restore the original 7 movies
DELETE FROM `movie`;

INSERT INTO `movie` (`id`, `name`, `small_imageurl`, `large_imageurl`, `short_description`, `long_description`, `director`, `actors`, `categories`, `release_date`, `duration`, `trailerurl`, `language`, `rated`, `is_showing`, `created_at`, `updated_at`, `created_by`, `modified_by`) VALUES
(1, 'Nhóc Trùm: Nối Nghiệp Gia Đình', 
'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/b/o/boss_baby_2_payoff_poster_vietnam.jpg', 
'https://photo2.tinhte.vn/data/attachment-files/2021/07/5548680_the_boss_baby_family_business_cover.jpg', 
'Nhóc trùm Ted giờ đây đã trở thành một triệu phú nổi tiếng trong khi Tim lại có một cuộc sống đơn giản bên vợ anh Carol và hai cô con gái nhỏ yêu dấu.', 
'Nhóc trùm Ted giờ đây đã trở thành một triệu phú nổi tiếng trong khi Tim lại có một cuộc sống đơn giản bên vợ anh Carol và hai cô con gái nhỏ yêu dấu. Mỗi mùa Giáng sinh tới, cả Tina và Tabitha đều mong được gặp chú Ted nhưng dường như hai anh em nhà Templeton nay đã không còn gần gũi như xưa. Nhưng bất ngờ thay khi Ted lại có màn tái xuất không thể hoành tráng hơn khi đáp thẳng máy bay trực thăng tới nhà Tim trước sự ngỡ ngàng của cả gia đình.', 
'Tom McGrath', 
'Amy Sedaris, James Marsden, Jeff Goldblum', 
'Hoạt Hình', 
'2021-12-24', 
107, 
'https://www.youtube.com/embed/k92cQ2K0FhY', 
'Tiếng Anh - Phụ đề Tiếng Việt, Lồng tiếng', 
'P', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(2, 'Venom: Đối Mặt Tử Thù', 
'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_venom_121121_1__1.jpg', 
'https://images2.thanhnien.vn/528040425431871488/2023/12/26/av-17035541620941917770857.jpg', 
'Siêu bom tấn VENOM: LET THERE BE CARNAGE hứa hẹn trận chiến khốc liệt nhất giữa Venom và kẻ thù truyền kiếp, Carnage.', 
'Siêu bom tấn VENOM: LET THERE BE CARNAGE hứa hẹn trận chiến khốc liệt nhất giữa Venom và kẻ thù truyền kiếp, Carnage.', 
'Andy Serkis', 
'Tom Hardy, Michelle Williams, Woody Harrelson, Naomie Harris', 
'Hành Động, Khoa Học Viễn Tưởng, Phiêu Lưu', 
'2021-12-10', 
97, 
'https://www.youtube.com/embed/-FmWuCgJmxo', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'C13', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(3, 'Ma Trận: Hồi Sinh', 
'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/m/a/matrix_4_poster_1_.jpg', 
'https://kenh14cdn.com/203336854389633024/2021/9/10/matrix-1631248066708688755030.jpg', 
'Từ đạo diễn tài năng Lana Wachowski, The Matrix Resurrections (tựa Việt: Ma Trận: Hồi Sinh) là phần phim tiếp theo rất được trông đợi của loạt phim Ma Trận đình đám.', 
'Từ đạo diễn tài năng Lana Wachowski, The Matrix Resurrections (tựa Việt: Ma Trận: Hồi Sinh) là phần phim tiếp theo rất được trông đợi của loạt phim Ma Trận đình đám, từng tái định nghĩa cả một thể loại điện ảnh. Phần phim mới đón chào sự trở lại của hai ngôi sao Keanu Reeves và Carrie-Anne Moss trong vai diễn mang tính biểu tượng làm nên tên tuổi của họ, Neo và Trinity.', 
'Lana Wachowski', 
'Keanu Reeves, Carrie-Anne Moss, Yahya Abdul-Mateen II, Jessica Henwick, Jonathan Groff, Neil Patrick Harris, Priyanka Chopra Jonas, Christina Ricci', 
'Hành Động, Khoa Học Viễn Tưởng', 
'2021-12-24', 
148, 
'https://www.youtube.com/embed/9ix7TUGVYIo', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'C18', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(4, 'Doraemon: Đôi Bạn Thân 2', 
'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_stand_by_me_2_1_.jpg', 
'https://images.squarespace-cdn.com/content/v1/5ad9326e857862ee5b3fc8f6/1628177439169-E65T2T099W1C9N0QY9B0/Doraemon+Stand+by+Me+2.jpg', 
'Một ngày nọ, Nobita tình cờ tìm thấy chú gấu bông cũ, món đồ kỷ niệm gắn liền với người bà quá cố mà cậu vô cùng yêu quý. Nobita quyết định cùng Doraemon quay về quá khứ để gặp lại bà.', 
'Một ngày nọ, Nobita tình cờ tìm thấy chú gấu bông cũ, món đồ kỷ niệm gắn liền với người bà quá cố mà cậu vô cùng yêu quý. Nobita quyết định cùng Doraemon quay về quá khứ để gặp lại bà. Lắng nghe nguyện vọng của bà là được nhìn thấy cháu dâu tương lai một lần, chuyến phiêu lưu đến tương lai của Doraemon và Nobita bắt đầu. Tuy nhiên, trong ngày diễn ra hôn lễ, chú rể Nobita lại trốn mất? Liệu Nobita có thể thực hiện được nguyện vọng của bà, và đem lại hạnh phúc cho Shizuka, người con gái cậu yêu thương nhất không?', 
'Ryuichi Yagi, Takashi Yamazaki', 
'Wasabi Mizuta, Megumi Ohara, Yumi Kakazu, Subaru Kimura, Tomokazu Seki', 
'Hài, Hoạt Hình', 
'2021-12-17', 
96, 
'https://www.youtube.com/embed/GXnOs4Hj8MA', 
'Tiếng Nhật - Phụ đề Tiếng Việt, Lồng tiếng', 
'P', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(5, 'Câu Chuyện Phía Tây', 
'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/w/s/wss_sneak_poster_vietnam_1_.jpg', 
'https://kenh14cdn.com/203336854389633024/2021/12/17/photo-1-16397262078652077926942.jpg', 
'Được đạo diễn bởi Steven Spielberg, kịch bản bởi nhà biên kịch từng đoạt giải Pulitzer và Tony - Tony Kushner, Câu Chuyện Phía Tây kể câu chuyện kinh điển về sự cạnh tranh khốc liệt và tình yêu tuổi trẻ ở thành phố New York năm 1957.', 
'Được đạo diễn bởi Steven Spielberg, kịch bản bởi nhà biên kịch từng đoạt giải Pulitzer và Tony - Tony Kushner, Câu Chuyện Phía Tây kể câu chuyện kinh điển về sự cạnh tranh khốc liệt và tình yêu tuổi trẻ ở thành phố New York năm 1957.', 
'Steven Spielberg', 
'Ansel Elgort, Rachel Zegler, Ariana DeBose, David Alvarez, Mike Faist, Josh Andrés Rivera, Ana Isabelle, Corey Stoll, Brian d’Arcy James, Rita Moreno', 
'Nhạc kịch, Tình cảm', 
'2021-12-24', 
156, 
'https://www.youtube.com/embed/A5GJLwWiYSg', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'C16', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(6, 'BlackPink The Movie', 
'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/b/l/blackpink_the_movie_-_payoff_poster_1_.jpg', 
'https://kenh14cdn.com/203336854389633024/2021/8/4/photo-1-1628046162382433010170.jpg', 
'Nhóm nhạc nữ được yêu thích toàn cầu, BLACKPINK sẽ kỷ niệm 5 năm ra mắt với việc phát hành BLACKPINK THE MOVIE, bộ phim như một món quà đặc biệt dành tặng cho các BLINK— fandom cuồng nhiệt của BLACKPINK.', 
'Nhóm nhạc nữ được yêu thích toàn cầu, BLACKPINK sẽ kỷ niệm 5 năm ra mắt với việc phát hành BLACKPINK THE MOVIE, bộ phim như một món quà đặc biệt dành tặng cho các BLINK— fandom cuồng nhiệt của BLACKPINK — bộ phim giúp người hâm mộ hồi tưởng lại những kỷ niệm khó quên cùng các màn trình diễn cuồng nhiệt với BLACKPINK.', 
'Su Yee Jung, Oh Yoon-Dong', 
'JISOO, JENNIE, ROSÉ, LISA', 
'Phim tài liệu', 
'2021-12-24', 
100, 
'https://www.youtube.com/embed/Q_rK9UlUN-Q', 
'Tiếng Hàn - Phụ đề Tiếng Việt', 
'P', 
1, 
NOW(), NOW(), 'admin', 'admin'),

(7, 'Người Nhện: Không Còn Nhà', 
'https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/s/n/snwh_poster_bluemontage_4x5_1_.jpg', 
'https://photo2.tinhte.vn/data/attachment-files/2021/12/5794438_cover.jpg', 
'Lần đầu tiên trong lịch sử điện ảnh của Người Nhện, thân phận người hàng xóm thân thiện của anh hùng chúng ta bị lật mở, khiến trách nhiệm siêu anh hùng xung đột với cuộc sống bình thường của anh và đặt những người anh quan tâm nhất vào tình thế nguy hiểm.', 
'Lần đầu tiên trong lịch sử điện ảnh của Người Nhện, thân phận người hàng xóm thân thiện của anh hùng chúng ta bị lật mở, khiến trách nhiệm siêu anh hùng xung đột với cuộc sống bình thường của anh và đặt những người anh quan tâm nhất vào tình thế nguy hiểm. Khi anh nhờ đến sự giúp đỡ của Doctor Strange để khôi phục lại bí mật của mình, phép thuật đã tạo ra một lỗ hổng trong thế giới của họ, giải phóng những kẻ phản diện mạnh nhất từng chiến đấu với Người Nhện trong bất kỳ vũ trụ nào. Giờ đây, Peter sẽ phải vượt qua thử thách lớn nhất của mình, thử thách này sẽ không chỉ thay đổi tương lai của chính anh mà còn là tương lai của Đa Vũ trụ.', 
'Jon Watts', 
'Tom Holland, Zendaya, Benedict Cumberbatch, Jacob Batalon, Jon Favreau, Marisa Tomei', 
'Hành Động, Phiêu Lưu', 
'2021-12-17', 
149, 
'https://www.youtube.com/embed/daHCu_jU5mQ', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'C13', 
1, 
NOW(), NOW(), 'admin', 'admin');

-- 2. Restore schedules for the 7 movies starting from today
DELETE FROM `schedule`;

DROP PROCEDURE IF EXISTS GenerateOriginalSchedules;
DELIMITER $$
CREATE PROCEDURE GenerateOriginalSchedules()
BEGIN
    DECLARE day_offset INT DEFAULT 0;
    DECLARE cur_date DATE;
    
    WHILE day_offset <= 7 DO
        SET cur_date = DATE_ADD(CURDATE(), INTERVAL day_offset DAY);
        
        -- Branch 1
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '09:00:00', 1, 1, 1),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '14:30:00', 1, 1, 1),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '20:00:00', 1, 1, 2),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '10:00:00', 1, 2, 2),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '15:00:00', 1, 2, 2),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '21:00:00', 1, 2, 3),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '09:30:00', 1, 3, 3),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '14:00:00', 1, 3, 3),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '19:30:00', 1, 3, 4),
        (NOW(), NOW(), 'admin', 'admin', 65000, cur_date, '10:15:00', 1, 4, 4),
        (NOW(), NOW(), 'admin', 'admin', 65000, cur_date, '13:00:00', 1, 4, 1),
        (NOW(), NOW(), 'admin', 'admin', 65000, cur_date, '16:30:00', 1, 4, 2),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '11:00:00', 1, 5, 1),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '16:00:00', 1, 5, 3),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '19:45:00', 1, 5, 4),
        (NOW(), NOW(), 'admin', 'admin', 75000, cur_date, '09:15:00', 1, 6, 2),
        (NOW(), NOW(), 'admin', 'admin', 75000, cur_date, '14:15:00', 1, 6, 4),
        (NOW(), NOW(), 'admin', 'admin', 75000, cur_date, '18:45:00', 1, 6, 1),
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '10:30:00', 1, 7, 3),
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '15:30:00', 1, 7, 2),
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '20:30:00', 1, 7, 1);

        -- Branch 2
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '09:15:00', 2, 1, 5),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '14:00:00', 2, 1, 6),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '19:15:00', 2, 1, 7),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '10:30:00', 2, 2, 5),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '15:30:00', 2, 2, 6),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '20:30:00', 2, 2, 7),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '11:00:00', 2, 3, 5),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '16:00:00', 2, 3, 6),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '21:00:00', 2, 3, 7),
        (NOW(), NOW(), 'admin', 'admin', 65000, cur_date, '09:45:00', 2, 4, 6),
        (NOW(), NOW(), 'admin', 'admin', 65000, cur_date, '13:30:00', 2, 4, 7),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '12:00:00', 2, 5, 5),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '17:30:00', 2, 5, 6),
        (NOW(), NOW(), 'admin', 'admin', 75000, cur_date, '10:00:00', 2, 6, 7),
        (NOW(), NOW(), 'admin', 'admin', 75000, cur_date, '15:00:00', 2, 6, 5),
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '11:30:00', 2, 7, 6),
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '18:00:00', 2, 7, 7);

        -- Branch 3
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '09:00:00', 3, 1, 8),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '14:00:00', 3, 1, 9),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '10:00:00', 3, 2, 10),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '15:00:00', 3, 2, 11),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '11:00:00', 3, 3, 8),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '16:00:00', 3, 3, 9),
        (NOW(), NOW(), 'admin', 'admin', 65000, cur_date, '09:30:00', 3, 4, 10),
        (NOW(), NOW(), 'admin', 'admin', 65000, cur_date, '14:30:00', 3, 4, 11),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '12:00:00', 3, 5, 8),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '17:00:00', 3, 5, 9),
        (NOW(), NOW(), 'admin', 'admin', 75000, cur_date, '10:30:00', 3, 6, 10),
        (NOW(), NOW(), 'admin', 'admin', 75000, cur_date, '15:30:00', 3, 6, 11),
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '13:00:00', 3, 7, 8),
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '18:30:00', 3, 7, 9);

        -- Branch 4
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '09:30:00', 4, 1, 12),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '14:30:00', 4, 1, 13),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '10:30:00', 4, 2, 14),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '15:30:00', 4, 2, 15),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '11:30:00', 4, 3, 12),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '16:30:00', 4, 3, 13),
        (NOW(), NOW(), 'admin', 'admin', 65000, cur_date, '10:00:00', 4, 4, 14),
        (NOW(), NOW(), 'admin', 'admin', 65000, cur_date, '15:00:00', 4, 4, 15),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '12:30:00', 4, 5, 12),
        (NOW(), NOW(), 'admin', 'admin', 70000, cur_date, '17:30:00', 4, 5, 13),
        (NOW(), NOW(), 'admin', 'admin', 75000, cur_date, '11:00:00', 4, 6, 14),
        (NOW(), NOW(), 'admin', 'admin', 75000, cur_date, '16:00:00', 4, 6, 15),
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '13:30:00', 4, 7, 12),
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '19:00:00', 4, 7, 13);

        SET day_offset = day_offset + 1;
    END WHILE;
END$$
DELIMITER ;

CALL GenerateOriginalSchedules();
DROP PROCEDURE GenerateOriginalSchedules;

SET FOREIGN_KEY_CHECKS = 1;
