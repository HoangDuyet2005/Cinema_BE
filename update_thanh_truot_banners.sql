USE cinema2;

-- Cập nhật banner ngang chính thức từ folder thanh trượt
UPDATE `movie` SET `large_imageurl` = '/img/movies/nghi-he-so-nghi-huu-1_1786436532090.jpg' WHERE `id` = 8;
UPDATE `movie` SET `large_imageurl` = '/img/movies/spiderman-brand-new-day-1_1784708435729.jpg' WHERE `id` = 9;
UPDATE `movie` SET `large_imageurl` = '/img/movies/hinh-1-14.jpg' WHERE `id` = 12;
UPDATE `movie` SET `large_imageurl` = '/img/movies/insidious-sneak-2048_1786436889562.jpg' WHERE `id` = 13;

-- Kiểm tra kết quả
SELECT id, name, large_imageurl, small_imageurl FROM `movie` WHERE `id` >= 8;
