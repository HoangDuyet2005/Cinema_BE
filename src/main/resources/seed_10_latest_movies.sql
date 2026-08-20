USE cinema2;

-- Disable FK checks temporarily for clean migration
SET FOREIGN_KEY_CHECKS = 0;

-- 1. Insert or Update 10 Blockbuster Movies
REPLACE INTO `movie` (`id`, `created_at`, `updated_at`, `created_by`, `modified_by`, `name`, `small_imageurl`, `large_imageurl`, `director`, `actors`, `categories`, `release_date`, `duration`, `trailerurl`, `language`, `rated`, `is_showing`, `short_description`, `long_description`) VALUES
(1, NOW(), NOW(), 'admin', 'admin', 
'Deadpool & Wolverine', 
'https://image.tmdb.org/t/p/w500/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg', 
'https://image.tmdb.org/t/p/original/yDHYTfA3R0jFYba16jBB1jv8MJu.jpg', 
'Shawn Levy', 
'Ryan Reynolds, Hugh Jackman, Emma Corrin, Morena Baccarin', 
'Hành Động, Hài, Phiêu Lưu, Khoa Học Viễn Tưởng', 
'2024-07-26', 
128, 
'https://www.youtube.com/embed/73_1biulkYk', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'T18', 
1, 
'Wade Wilson cùng Wolverine bắt tay vào sứ mệnh giải cứu vũ trụ đa thực tại của TVA.', 
'Wade Wilson đang sống một cuộc đời bình thường sau khi gác lại bộ đồ Deadpool. Nhưng khi TVA xuất hiện và đe dọa vũ trụ quê nhà, anh buộc phải tái xuất và tìm kiếm một biến thể Wolverine để cùng nhau sát cánh cứu lấy thế giới của mình.'),

(2, NOW(), NOW(), 'admin', 'admin', 
'Kẻ Trộm Mặt Trăng 4', 
'https://image.tmdb.org/t/p/w500/wWba3TaojhK7NdycRhoQpsG0FaH.jpg', 
'https://image.tmdb.org/t/p/original/lgkGysTk1fmmZui4mRJQ0gWW7WW.jpg', 
'Chris Renaud, Patrick Delage', 
'Steve Carell, Kristen Wiig, Will Ferrell, Sofia Vergara', 
'Hoạt Hình, Hài, Phiêu Lưu, Gia Đình', 
'2024-07-05', 
94, 
'https://www.youtube.com/embed/qQlr9-rF32A', 
'Tiếng Anh - Phụ đề & Lồng tiếng Tiếng Việt', 
'P', 
1, 
'Gia đình Gru chào đón thành viên mới Gru Jr. và đối đầu kẻ thù nguy hiểm Maxime Le Mal.', 
'Gru cùng Lucy và các con Margo, Edith, Agnes chào đón thành viên nhí Gru Jr. Tuy nhiên, sự xuất hiện của kẻ thù cũ Maxime Le Mal và bạn gái Valentina buộc cả gia đình phải lẩn trốn, đồng thời biệt đội Minions nhận sức mạnh Mega Minions siêu hạng.'),

(3, NOW(), NOW(), 'admin', 'admin', 
'Những Mảnh Ghép Cảm Xúc 2', 
'https://image.tmdb.org/t/p/w500/vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg', 
'https://image.tmdb.org/t/p/original/stKGOm8fM5Z0e5gP7B2807f7o3p.jpg', 
'Kelsey Mann', 
'Amy Poehler, Maya Hawke, Kensington Tallman, Liza Lapira', 
'Hoạt Hình, Gia Đình, Phiêu Lưu, Hài', 
'2024-06-14', 
96, 
'https://www.youtube.com/embed/L4DrolmDxmw', 
'Tiếng Anh - Phụ đề & Lồng tiếng Tiếng Việt', 
'P', 
1, 
'Riley bước vào tuổi dậy thì với sự xuất hiện của các cảm xúc mới: Lo Âu, Ganh Tị, Xấu Hổ, Chán Nản.', 
'Trụ sở tâm trí của cô bé Riley trải qua một cuộc cải tạo bất ngờ khi bước vào tuổi dậy thì. Vui Vẻ, Buồn Bã, Giận Dữ, Sợ Hãi và Ghê Tởm phải đối mặt với nhóm cảm xúc mới do Lo Âu dẫn đầu, tạo nên chuyến hành trình trưởng thành đầy ý nghĩa.'),

(4, NOW(), NOW(), 'admin', 'admin', 
'Lật Mặt 7: Một Điều Ước', 
'https://image.tmdb.org/t/p/w500/r0G1wN2332gq5F6i5m6cR0iJbN9.jpg', 
'https://image.tmdb.org/t/p/original/b829p8r21pWwH2L50aH3hL07mC9.jpg', 
'Lý Hải', 
'Thanh Hiền, Trương Minh Cường, Đinh Y Nhung, Quách Ngọc Tuyên', 
'Tình Cảm, Gia Đình, Tâm Lý', 
'2024-04-26', 
138, 
'https://www.youtube.com/embed/d2vY_Z_w9m0', 
'Tiếng Việt - Phụ đề Tiếng Anh', 
'K', 
1, 
'Câu chuyện xúc động về bà Hai 73 tuổi cùng 5 người con trưởng thành tứ tán khắp nơi.', 
'Lật Mặt 7 đưa khán giả đến với câu chuyện của bà Hai ở làng K Long K Lanh, một mình nuôi 5 người con khôn lớn. Khi bà gặp nạn, câu hỏi Ai sẽ chăm sóc mẹ đã làm bộc lộ những góc khuất tình thân, bổn phận và sự gắn kết gia đình thiêng liêng.'),

(5, NOW(), NOW(), 'admin', 'admin', 
'Mai', 
'https://image.tmdb.org/t/p/w500/yK9oZ4W6K43VpX1iK0Jg40r9fW4.jpg', 
'https://image.tmdb.org/t/p/original/9w90k6hR8f5Y2Y1wH3b0hN07mD8.jpg', 
'Trấn Thành', 
'Phương Anh Đào, Tuấn Trần, Trấn Thành, Hồng Đào, Uyển Ân', 
'Tình Cảm, Tâm Lý, Chính Kịch', 
'2024-02-10', 
131, 
'https://www.youtube.com/embed/rQk58189c4s', 
'Tiếng Việt - Phụ đề Tiếng Anh', 
'T18', 
1, 
'Mối tình đầy trắc trở giữa Mai - người phụ nữ massage chịu nhiều tổn thương và Dương - chàng nhạc công đào hoa.', 
'Mai là một người phụ nữ gần 40 tuổi làm nghề massage trị liệu, luôn khao khát một cuộc sống bình yên. Định mệnh đưa cô gặp gỡ Dương, một chàng trai trẻ đam mê âm nhạc. Tình yêu nảy nở giữa họ phải đối mặt với định kiến xã hội, quá khứ nặng nề và thử thách của số phận.'),

(6, NOW(), NOW(), 'admin', 'admin', 
'Kung Fu Panda 4', 
'https://image.tmdb.org/t/p/w500/kDp1vUBnMpe8ak4rjgl3cLELqjU.jpg', 
'https://image.tmdb.org/t/p/original/1XDDXPXGiI8id7MrUxK36ke7gkX.jpg', 
'Mike Mitchell', 
'Jack Black, Awkwafina, Viola Davis, Dustin Hoffman', 
'Hoạt Hình, Hành Động, Hài, Phiêu Lưu', 
'2024-03-08', 
94, 
'https://www.youtube.com/embed/_inKs4eeHiI', 
'Tiếng Anh - Phụ đề & Lồng tiếng Tiếng Việt', 
'P', 
1, 
'Thần Long Đại Hiệp Po tìm kiếm người kế vị và đối đầu với Tắc Kè Bông Tắc Kè Hoa tàn bạo.', 
'Sau khi được chỉ định trở thành Thủ lĩnh Tinh thần của Thung lũng Hòa bình, Po cần tìm và huấn luyện một Thần Long Đại Hiệp mới. Cùng với cô cáo Zhen, Po bước vào cuộc chiến chống lại mụ phù thủy Tắc Kè Hoa có khả năng biến hình thành bất kỳ kẻ thù nào trong quá khứ.'),

(7, NOW(), NOW(), 'admin', 'admin', 
'Godzilla x Kong: Đế Chế Mới', 
'https://image.tmdb.org/t/p/w500/bQ2ywkchIiaKLSEaMrcT6e29f91.jpg', 
'https://image.tmdb.org/t/p/original/qrGtVF3YZvJc34w7q4vS9T6Vb7M.jpg', 
'Adam Wingard', 
'Rebecca Hall, Brian Tyree Henry, Dan Stevens, Kaylee Hottle', 
'Hành Động, Khoa Học Viễn Tưởng, Phiêu Lưu', 
'2024-03-29', 
115, 
'https://www.youtube.com/embed/lV1OOlGwExg', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'T13', 
1, 
'Godzilla và Kong hợp sức chống lại mối hiểm họa khổng lồ đe dọa Trái Đất Rỗng và nhân loại.', 
'Hai quái thú huyền thoại Godzilla và Kong buộc phải gác lại hiềm khích để cùng nhau chống lại Skar King – kẻ thống trị tàn bạo của Trái Đất Rỗng đang âm mưu thôn tính mặt đất cùng với quái thú băng giá Shimo.'),

(8, NOW(), NOW(), 'admin', 'admin', 
'Dune: Hành Tinh Cát - Phần Hai', 
'https://image.tmdb.org/t/p/w500/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg', 
'https://image.tmdb.org/t/p/original/xOMo8BRK7PfcJv9JCnx7s5hj0x2.jpg', 
'Denis Villeneuve', 
'Timothée Chalamet, Zendaya, Rebecca Ferguson, Javier Bardem', 
'Khoa Học Viễn Tưởng, Hành Động, Phiêu Lưu', 
'2024-03-01', 
166, 
'https://www.youtube.com/embed/Way9Dexny3w', 
'Tiếng Anh - Phụ đề Tiếng Việt', 
'T16', 
1, 
'Paul Atreides hợp lực cùng người Fremen trả thù cho gia tộc và định đoạt tương lai vũ trụ Arrakis.', 
'Paul Atreides tiếp tục hành trình trả thù những kẻ đã hủy hoại gia tộc mình. Đứng trước sự lựa chọn giữa tình yêu cuộc đời và số phận của vũ trụ, anh phải ngăn chặn một tương lai đen tối mà chỉ mình anh có thể nhìn thấy trước.'),

(9, NOW(), NOW(), 'admin', 'admin', 
'Exhuma: Quật Mộ Trùng Ma', 
'https://image.tmdb.org/t/p/w500/kOygk9m0k20l9mB9X4r7qR5hY6z.jpg', 
'https://image.tmdb.org/t/p/original/7kHiW8rX5gG2l2M4hQ7wF0gR8yZ.jpg', 
'Jang Jae-hyun', 
'Choi Min-sik, Kim Go-eun, Yoo Hae-jin, Lee Do-hyun', 
'Kinh Dị, Bí Ẩn, Giật Gân', 
'2024-03-15', 
134, 
'https://www.youtube.com/embed/yA1U6b76ZfM', 
'Tiếng Hàn - Phụ đề Tiếng Việt', 
'T18', 
1, 
'Hai pháp sư, thầy phong thủy và chuyên gia mai táng khai quật ngôi mộ cổ bí ẩn của gia đình tài phiệt.', 
'Bộ tứ gồm hai pháp sư trẻ tài năng Hwa-rim và Bong-gil, cùng thầy phong thủy Sang-deok và chuyên gia mai táng Yeong-geun nhận lời khai quật ngôi mộ cổ của một gia tộc giàu có, nhưng vô tình giải thoát một thế lực tà ác đáng sợ chôn giấu hàng thế kỷ.'),

(10, NOW(), NOW(), 'admin', 'admin', 
'Transformers Một', 
'https://image.tmdb.org/t/p/w500/qbkPtZWeU2z6emb2q6vR5v8W7mQ.jpg', 
'https://image.tmdb.org/t/p/original/9BBTo63ANSm5P6IH2x8ZwOCqYp9.jpg', 
'Josh Cooley', 
'Chris Hemsworth, Brian Tyree Henry, Scarlett Johansson, Keegan-Michael Key', 
'Hoạt Hình, Hành Động, Khoa Học Viễn Tưởng', 
'2024-09-20', 
104, 
'https://www.youtube.com/embed/u2NuUVbfEag', 
'Tiếng Anh - Phụ đề & Lồng tiếng Tiếng Việt', 
'P', 
1, 
'Nguồn gốc câu chuyện tình bạn tri kỷ giữa Orion Pax (Optimus Prime) và D-16 (Megatron) tại Cybertron.', 
'Khám phá nguồn gốc chưa từng kể của Cybertron, nơi hai người thợ mỏ Orion Pax và D-16 bắt đầu từ tình bạn keo sơn gắn bó rồi dần dần trở thành hai thủ lĩnh đối đầu không khoan nhượng: Optimus Prime của phe Autobot và Megatron của phe Decepticon.');

-- 2. Clear old schedules & regenerate fresh schedules for today and the next 7 days
DELETE FROM `schedule`;

-- Schedule generation procedure
DROP PROCEDURE IF EXISTS GenerateSchedules;
DELIMITER $$
CREATE PROCEDURE GenerateSchedules()
BEGIN
    DECLARE day_offset INT DEFAULT 0;
    DECLARE cur_date DATE;
    
    WHILE day_offset <= 7 DO
        SET cur_date = DATE_ADD(CURDATE(), INTERVAL day_offset DAY);
        
        -- Movie 1: Deadpool & Wolverine (Branch 1, 2, 3, 4)
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '09:30:00', 1, 1, 1),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '14:00:00', 1, 1, 2),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '18:30:00', 1, 1, 3),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:45:00', 1, 1, 1),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '10:15:00', 2, 1, 5),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '16:30:00', 2, 1, 6),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:00:00', 2, 1, 7),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '11:00:00', 3, 1, 8),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '17:15:00', 3, 1, 9),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:30:00', 3, 1, 10),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '13:00:00', 4, 1, 12),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '19:00:00', 4, 1, 13);

        -- Movie 2: Kẻ Trộm Mặt Trăng 4
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '08:30:00', 1, 2, 2),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '11:00:00', 1, 2, 3),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '15:30:00', 1, 2, 4),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '18:00:00', 1, 2, 2),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '09:00:00', 2, 2, 6),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '14:15:00', 2, 2, 7),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '10:00:00', 3, 2, 10),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '15:00:00', 3, 2, 11),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '09:30:00', 4, 2, 14),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '14:30:00', 4, 2, 15);

        -- Movie 3: Những Mảnh Ghép Cảm Xúc 2
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '09:00:00', 1, 3, 3),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '13:15:00', 1, 3, 4),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '17:30:00', 1, 3, 1),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '11:30:00', 2, 3, 5),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '18:15:00', 2, 3, 6),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '12:30:00', 3, 3, 8),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '18:45:00', 3, 3, 9),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '11:15:00', 4, 3, 12),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '17:00:00', 4, 3, 13);

        -- Movie 4: Lật Mặt 7: Một Điều Ước
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '10:00:00', 1, 4, 4),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '15:00:00', 1, 4, 1),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:15:00', 1, 4, 2),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '13:00:00', 2, 4, 7),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '19:30:00', 2, 4, 5),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '14:00:00', 3, 4, 11),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '19:45:00', 3, 4, 8),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '15:30:00', 4, 4, 14),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:45:00', 4, 4, 15);

        -- Movie 5: Mai
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '11:45:00', 1, 5, 2),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '17:00:00', 1, 5, 3),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:00:00', 1, 5, 4),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '15:00:00', 2, 5, 6),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:45:00', 2, 5, 7),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '16:00:00', 3, 5, 10),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:15:00', 3, 5, 11),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '18:15:00', 4, 5, 13),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:30:00', 4, 5, 12);

        -- Movie 6: Kung Fu Panda 4
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 80000, cur_date, '08:45:00', 1, 6, 1),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '12:30:00', 1, 6, 2),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '16:15:00', 1, 6, 3),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '10:00:00', 2, 6, 5),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '15:45:00', 2, 6, 6),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '11:15:00', 3, 6, 8),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '16:30:00', 3, 6, 9),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '10:30:00', 4, 6, 12),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '16:00:00', 4, 6, 14);

        -- Movie 7: Godzilla x Kong: Đế Chế Mới
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '10:30:00', 1, 7, 4),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '15:45:00', 1, 7, 1),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '19:30:00', 1, 7, 2),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '14:00:00', 2, 7, 7),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '18:45:00', 2, 7, 5),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '13:30:00', 3, 7, 10),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '19:00:00', 3, 7, 11),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '14:00:00', 4, 7, 15),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '19:15:00', 4, 7, 13);

        -- Movie 8: Dune: Hành Tinh Cát - Phần Hai
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '11:15:00', 1, 8, 3),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '17:45:00', 1, 8, 4),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '21:15:00', 1, 8, 1),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '12:00:00', 2, 8, 6),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '19:00:00', 2, 8, 7),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '17:30:00', 3, 8, 8),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '21:00:00', 3, 8, 9),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '17:30:00', 4, 8, 12),
        (NOW(), NOW(), 'admin', 'admin', 105000, cur_date, '21:00:00', 4, 8, 14);

        -- Movie 9: Exhuma: Quật Mộ Trùng Ma
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '12:00:00', 1, 9, 1),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '16:45:00', 1, 9, 2),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:30:00', 1, 9, 3),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '16:00:00', 2, 9, 5),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '21:15:00', 2, 9, 6),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '15:15:00', 3, 9, 9),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:15:00', 3, 9, 10),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '16:15:00', 4, 9, 13),
        (NOW(), NOW(), 'admin', 'admin', 100000, cur_date, '20:30:00', 4, 9, 15);

        -- Movie 10: Transformers Một
        INSERT INTO `schedule` (`created_at`, `updated_at`, `created_by`, `modified_by`, `price`, `start_date`, `start_time`, `branch_id`, `movie_id`, `room_id`) VALUES
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '09:15:00', 1, 10, 2),
        (NOW(), NOW(), 'admin', 'admin', 90000, cur_date, '13:45:00', 1, 10, 3),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '18:15:00', 1, 10, 4),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '10:45:00', 2, 10, 7),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '17:30:00', 2, 10, 5),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '12:00:00', 3, 10, 8),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '18:00:00', 3, 10, 11),
        (NOW(), NOW(), 'admin', 'admin', 85000, cur_date, '12:45:00', 4, 10, 14),
        (NOW(), NOW(), 'admin', 'admin', 95000, cur_date, '18:30:00', 4, 10, 12);

        SET day_offset = day_offset + 1;
    END WHILE;
END$$
DELIMITER ;

CALL GenerateSchedules();
DROP PROCEDURE GenerateSchedules;

SET FOREIGN_KEY_CHECKS = 1;
