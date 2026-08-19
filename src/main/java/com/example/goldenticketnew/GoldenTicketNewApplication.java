package com.example.goldenticketnew;

//import com.example.goldenticketnew.config.ConnectCadance;

import com.example.goldenticketnew.enums.SeatType;
import com.example.goldenticketnew.model.*;
import com.example.goldenticketnew.repository.*;
import com.example.goldenticketnew.service.bill.IBillService;
import com.example.goldenticketnew.utils.BeanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@SpringBootApplication
@EnableJpaAuditing
public class GoldenTicketNewApplication {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(GoldenTicketNewApplication.class, args);
//        IRoomRepository roomRepository = BeanUtils.getBean(IRoomRepository.class);
//        ISeatRepository seatRepository = BeanUtils.getBean(ISeatRepository.class);
//        IScheduleRepository scheduleRepository = BeanUtils.getBean(IScheduleRepository.class);
//        IMovieRepository movieRepository = BeanUtils.getBean(IMovieRepository.class);
////		Movie movie = movieRepository.getById(10);
////		Movie movie2 = movieRepository.getById(11);
////		Movie movie3 = movieRepository.getById(9);
////		Movie movie4 = movieRepository.getById(8);
////		Movie movie5 = movieRepository.getById(7);
////		Movie movie6 = movieRepository.getById(5);
//        List<Seat> seats = seatRepository.findAllByNameContaining("D");
//        for(Seat seat : seats){
//            seat.setSeatType(SeatType.VIP);
//            seatRepository.save(seat);
//        }
//		Room room = roomRepository.getById(7);
//		for(int i=1;i<=16;i++){
//			Seat seat = new Seat();
//			seat.setName("A"+i);
//			seat.setRoom(room);
//			seatRepository.save(seat);
//		}
//
//		for(int i=1;i<=16;i++){
//			Seat seat = new Seat();
//			seat.setName("B"+i);
//			seat.setRoom(room);
//			seatRepository.save(seat);
//		}
//		for(int i=1;i<=16;i++){
//			Seat seat = new Seat();
//			seat.setName("C"+i);
//			seat.setRoom(room);
//			seatRepository.save(seat);
//		}
//		for(int i=1;i<=16;i++){
//			Seat seat = new Seat();
//			seat.setName("D"+i);
//			seat.setRoom(room);
//			seatRepository.save(seat);
//		}
//		for(int i=1;i<=16;i++){
//			Seat seat = new Seat();
//			seat.setName("E"+i);
//			seat.setRoom(room);
//			seatRepository.save(seat);
//		}
//		IBranchRepository branchRepository = BeanUtils.getBean(IBranchRepository.class);
//		Branch branch = branchRepository.getById(1);
//		Schedule schedule1 = new Schedule();
//				schedule1.setBranch(branch);
//				schedule1.setMovie(movie);
//				schedule1.setRoom(room);
//				schedule1.setStartDate(LocalDate.parse("2022-12-27"));
//				schedule1.setStartTime(LocalTime.parse("07:10"));
//				schedule1.setPrice(70000);
//				scheduleRepository.save(schedule1);
//		Schedule schedule2 = new Schedule();
//		schedule2.setBranch(branch);
//		schedule2.setMovie(movie6);
//		schedule2.setRoom(room);
//		schedule2.setStartDate(LocalDate.parse("2022-12-27"));
//		schedule2.setStartTime(LocalTime.parse("21:15"));
//		schedule2.setPrice(70000);
//		scheduleRepository.save(schedule1);
//		Room room1 = new Room();
//		room1.setName("Phòng 301");
//		room1.setBranch(branch);
//		room1.setCapacity(40);
//		room1.setTotalArea(80);
//		room1.setImgURL("http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg");
//		roomRepository.save(room1);
//		Room room2 = new Room();
//		room2.setName("Phòng 302");
//		room2.setCapacity(40);
//		room2.setTotalArea(80);
//		room2.setImgURL("http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg");
//		roomRepository.save(room2);
    }

//	@Autowired
//	private UserRepository userRepository;
//
//
//
//	@Autowired
//	private IBranchRepository branchRepository;
////
//	@Autowired
//	private IRoomRepository roomRepository;
//	@Autowired
//	private IMovieRepository movieRepository;
//	@Autowired
//	private IScheduleRepository scheduleRepository;
//
//	@Autowired
//	private ISeatRepository seatRepository;
//
//	//     Do chưa có trang admin để thêm phim và lịch chiếu nên thêm tạm dữ liệu xuống db để demo
//	@PostConstruct
//	public void init() {
////		Chạy 1 lần đầu app rồi bỏ comment đoạn này rồi chạy lại để add data ghế ngồi cho phòng 1
////		Room room = roomRepository.findById(1).get();
////
////		for(int i=1;i<=8;i++){
////			Seat seat = new Seat();
////			seat.setName("A"+i);
////			seat.setRoom(room);
////			seatRepository.save(seat);
////		}
////
////		for(int i=1;i<=8;i++){
////			Seat seat = new Seat();
////			seat.setName("B"+i);
////			seat.setRoom(room);
////			seatRepository.save(seat);
////		}
////		for(int i=1;i<=8;i++){
////			Seat seat = new Seat();
////			seat.setName("C"+i);
////			seat.setRoom(room);
////			seatRepository.save(seat);
////		}
////		for(int i=1;i<=8;i++){
////			Seat seat = new Seat();
////			seat.setName("D"+i);
////			seat.setRoom(room);
////			seatRepository.save(seat);
////		}
////		for(int i=1;i<=8;i++){
////			Seat seat = new Seat();
////			seat.setName("E"+i);
////			seat.setRoom(room);
////			seatRepository.save(seat);
////		}
////
////
//
//
//
//		List<Movie> movies = movieRepository.findAll();
//		if (movies.isEmpty()) {
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//			Movie nhocTrum = addNewMovie("Nhóc Trùm: Nối Nghiệp Gia Đình","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_boss_baby_2_24.12.2021_1_1_1__1.jpg",
//				"Nhóc trùm Ted giờ đây đã trở thành một triệu phú nổi tiếng trong khi Tim lại có một cuộc sống đơn giản bên vợ anh Carol và hai cô con gái nhỏ yêu dấu. Mỗi mùa Giáng sinh tới, cả Tina và Tabitha đều mong được gặp chú Ted",
//				"Nhóc trùm Ted giờ đây đã trở thành một triệu phú nổi tiếng trong khi Tim lại có một cuộc sống đơn giản bên vợ anh Carol và hai cô con gái nhỏ yêu dấu. Mỗi mùa Giáng sinh tới, cả Tina và Tabitha đều mong được gặp chú Ted nhưng dường như hai anh em nhà Templeton nay đã không còn gần gũi như xưa. Nhưng bất ngờ thay khi Ted lại có màn tái xuất không thể hoành tráng hơn khi đáp thẳng máy bay trực thăng tới nhà Tim trước sự ngỡ ngàng của cả gia đình.",
//				"https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/r/s/rsz_dr-strange-980x448.jpg",
//				"Tom McGrath","Amy Sedaris, Jeff Goldblum, James Marsden",
//				"Hoạt Hình",LocalDate.parse("24/12/2021",formatter),
//				105,"https://www.youtube.com/embed/Lv8nL2q8yRI",
//				"Tiếng Anh với phụ đề tiếng Việt và lồng tiếng Việt",
//				"P - PHIM DÀNH CHO MỌI ĐỐI TƯỢNG",1);
//			Movie venom = addNewMovie("Venom: Đối Mặt Tử Thù","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_venom_121121_1__1.jpg",
//				"Siêu bom tấn #VENOM: LET THERE BE CARNAGE hứa hẹn trận chiến khốc liệt nhất giữa Venom và kẻ thù truyền kiếp, Carnage.",
//				"Siêu bom tấn #VENOM: LET THERE BE CARNAGE hứa hẹn trận chiến khốc liệt nhất giữa Venom và kẻ thù truyền kiếp, Carnage.",
//				"https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/b/l/blackpink-rolling_1_.jpg",
//				"Andy Serkis","Tom Hardy, Michelle Williams, Woody Harrelson, Naomie Harris",
//				"Hành Động, Khoa Học Viễn Tưởng, Phiêu Lưu, Thần thoại",
//				LocalDate.parse("10/12/2021",formatter),97,"https://www.youtube.com/embed/EVWdzVtSh1I",
//				"Tiếng Anh - Phụ đề Tiếng Việt","C13 - PHIM CẤM KHÁN GIẢ DƯỚI 13 TUỔI",1);
//			Movie maTran = addNewMovie("Ma Trận: Hồi Sinh","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_matrix_4_1__1.jpg",
//				"Sau 20 năm, siêu phẩm ma trận đã trờ lại với người xem, Neo is back! Liệu đây có phải phần kết cho franchise này",
//				"Ma Trận: Hồi Sinh là phần phim tiếp theo rất được trông đợi của loạt phim “Ma Trận” đình đám, đã góp phần tái định nghĩa thể loại phim khoa học viễn tưởng. Phần phim mới nhất này đón chào sự trở lại của cặp đôi Keanu Reeves và Carrie-Anne Moss với vai diễn biểu tượng đã làm nên tên tuổi của họ, Neo và Trinity. Ngoài ra, phim còn có sự góp mặt của dàn diễn viên đầy tài năng gồm Yahya Abdul-Mateen II, Jessica Henwick, Jonathan Groff, Neil Patrick Harris, Priyanka Chopra Jonas và Christina Ricci.",
//				"https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/b/l/blackpink-rolling_1_.jpg",
//				"Lana Wachowski","Keanu Reeves, Carrie-Anne Moss, Yahya Abdul-Mateen II, Jessica Henwick, Jonathan Groff, Neil Patrick Harris, Priyanka Chopra Jonas và Christina Ricci",
//				"Hành Động, Khoa Học Viễn Tưởng",LocalDate.parse("24/12/2021",formatter),
//				148,"https://www.youtube.com/embed/l2UTOJC5Tbk",
//				"Tiếng Anh - Phụ đề Tiếng Việt, Phụ đề Tiếng Hàn",
//				"C18 - PHIM CẤM KHÁN GIẢ DƯỚI 18 TUỔI",1);
//			Movie doremon = addNewMovie("Doraemon: Ôi Bạn Ơi 2","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_doremon_2_1__1.jpg",
//				"Một ngày nọ, Nobita vô tình tìm thấy chú gấu bông cũ, món đồ chơi chất chứa bao kỉ niệm cùng người bà đáng kính. Với khát khao “muốn gặp bà lần nữa”, Nobita đã trở về quá khứ bằng cổ máy thời gian, bất chấp sự phản đối của Doraemon",
//				"Một ngày nọ, Nobita vô tình tìm thấy chú gấu bông cũ, món đồ chơi chất chứa bao kỉ niệm cùng người bà đáng kính. Với khát khao “muốn gặp bà lần nữa”, Nobita đã trở về quá khứ bằng cổ máy thời gian, bất chấp sự phản đối của Doraemon. Dù ngạc nhiên, bà vẫn tin cậu thiếu niên lớn tướng trước mặt mình là cháu mình. Trước nguyện vọng tha thiết “mong được thấy cháu dâu một lần”, chuyến phiêu lưu của Doraemon và Nobita bắt đầu. Nobita muốn cho bà xem đám cưới của mình, nhưng đúng ngày thành hôn với Shizuka, chú rể Nobita lại trốn mất? Jaian và Suneo chạy đôn chạy đáo tìm bạn, còn Shizuka vẫn tin tưởng chờ đợi Nobita. Để thực hiện nguyện vọng của bà, đáp lại niềm tin của gia đình, bạn bà và Shizuka yêu quý, Nobita sẽ cùng Doraemon du hành vượt thời gian. Họ sẽ mang đến cho chúng ta một câu chuyện cảm động đến rơi lệ về quan hệ con người, kết nối giữa quá khứ, hiện tại và tương lai.",
//				"https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/d/o/doreamon.jpg","Ryuichi Yagi, Takashi Yamazaki",
//				"Wasabi Mizuta, Megumi Oohara, Yumi Kakazu, Subaru Kimura, Tomokazu Seki",
//				"Hài, Hoạt Hình",LocalDate.parse("17/12/2021",formatter),
//				96,"https://www.youtube.com/embed/GXnOs4Hj8MA","Tiếng Nhật - Phụ đề Tiếng Việt; Lồng tiếng",
//				"P - PHIM DÀNH CHO MỌI ĐỐI TƯỢNG",1);
//			Movie cauChuyenPhiaTay = addNewMovie("Câu Chuyện Phía Tây","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_wss_1200x1800__1.jpg",
//				"“Câu chuyện phía Tây” kể lại câu chuyện tình yêu kinh điển của Tony và Maria, giữa sự giằng xé của tình yêu trẻ tuổi và sự ngăn cấm, thù hằn ở thành phố NewYork những năm 1950",
//				"Được đạo diễn bởi đạo diễn gạo cội từng giành giải Oscar Steven Spielberg, cùng kịch bản bởi biên kịch từng giành giải Pulitzer Prize và giải Tony Award, Tony Kushner, “Câu chuyện phía Tây” kể lại câu chuyện tình yêu kinh điển của Tony và Maria, giữa sự giằng xé của tình yêu trẻ tuổi và sự ngăn cấm, thù hằn ở thành phố NewYork những năm 1950.",
//				"https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/w/s/wss_sneak_980x448.jpg",
//				"Steven Spielberg","Ansel Elgort, Rachel Zegler, Ariana DeBose, David Alvarez, Mike Faist, Josh Andrés Rivera, Ana Isabelle, Corey Stoll, Brian d’Arcy James, Rita Moreno",
//				"Nhạc kịch, Tình cảm",LocalDate.parse("24/12/2021",formatter),
//				156,"https://www.youtube.com/embed/QPvqV71P0Fo","Tiếng Anh - Phụ đề Tiếng Việt",
//				"C16 - PHIM CẤM KHÁN GIẢ DƯỚI 16 TUỔI",1);
//			Movie blackPink = addNewMovie("BlackPink The Movie","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_blackpink_vie_2_1__1.jpg",
//				"Nhóm nhạc nữ được yêu thích toàn cầu, BLACKPINK sẽ kỷ niệm năm thứ 5 hoạt động của nhóm với việc phát hành BLACKPINK THE MOVIE",
//				"Nhóm nhạc nữ được yêu thích toàn cầu, BLACKPINK sẽ kỷ niệm năm thứ 5 hoạt động của nhóm với việc phát hành BLACKPINK THE MOVIE, đây cũng như là món quà đặc biệt dành tặng cho các BLINK— fandom của BLACKPINK — bộ phim sẽ tái hiện một cách sống động những kỷ niệm không thể quên cùng những màn trinh diễn đầy cuồng nhiệt đúng tinh thần lễ hội.",
//				"https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/b/l/blackpink-rolling_1_.jpg",
//				"Su Yee Jung, Oh Yoon-Dong","JISOO, JENNIE, ROSÉ, LISA",
//				"Phim tài liệu",LocalDate.parse("24/12/2021",formatter),99,
//				"https://www.youtube.com/embed/Q_rK9UlUN-Q","Tiếng Hàn - Phụ đề tiếng Việt",
//				"P - PHIM DÀNH CHO MỌI ĐỐI TƯỢNG",1);
//			Movie nguoiNhen = addNewMovie("Người Nhện: Không Còn Nhà","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/s/n/snwh_poster_bluemontage_4x5fb_1__1.jpg"
//				,"Đa vũ trụ được mở ra, những kẻ phản diện nào sẽ trạm chán spidey, cùng đón xem nhá",
//				"Lần đầu tiên trong lịch sử điện ảnh của Người Nhện, thân phận người hàng xóm thân thiện bị lật mở, khiến trách nhiệm làm một Siêu Anh Hùng xung đột với cuộc sống bình thường và đặt người anh quan tâm nhất vào tình thế nguy hiểm. Khi anh nhờ đến giúp đỡ của Doctor Strange để khôi phục lại bí mật, phép thuật đã gây ra lỗ hổng thời không, giải phóng những ác nhân mạnh mẽ nhất từng đối đầu với Người Nhện từ mọi vũ trụ. Bây giờ, Peter sẽ phải vượt qua thử thách lớn nhất của mình, nó sẽ thay đổi không chỉ tương lai của chính anh mà còn là tương lai của cả Đa Vũ Trụ."
//				,"https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/r/s/rsz_dr-strange-980x448.jpg",
//				"Jon Watts","Tom Holland, Zendaya, Benedict Cumberbatch, Jacob Batalon, Jon Favreau","Hành Động, Phiêu Lưu",
//				LocalDate.parse("17/12/2021",formatter),
//				149,"https://www.youtube.com/embed/daHCu_jU5mQ",
//				"Tiếng Anh - Phụ đề Tiếng Việt",
//				"C13 - PHIM CẤM KHÁN GIẢ DƯỚI 13 TUỔI",1);
//
//			// Tạo mới các chi nhánh
//			List<Branch> listBranch = branchRepository.findAll();
//			if(listBranch.isEmpty()){
//				Branch branch1 = new Branch();
//				branch1.setName("GOLDENNEW Hà Đông");
//				branch1.setAddress("Tầng 4, Mê Linh Plaza Hà Đông, Đ. Tô Hiệu, P, Hà Đông, Hà Nội");
//				branch1.setPhoneNo("0938473829");
//				branch1.setImgURL("httpsd");
//				branch1 = branchRepository.save(branch1);
//				Room room1 = new Room();
//				room1.setName("Phòng 101");
//				room1.setBranch(branch1);
//				room1.setCapacity(40);
//				room1.setTotalArea(80);
//				room1.setImgURL("http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg");
//				Room r1 = roomRepository.save(room1);
//				Schedule schedule1 = new Schedule();
//				schedule1.setBranch(branch1);
//				schedule1.setMovie(nguoiNhen);
//				schedule1.setRoom(r1);
//				schedule1.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule1.setStartTime(LocalTime.parse("10:15"));
//				schedule1.setPrice(70000);
//				scheduleRepository.save(schedule1);
//
//				Schedule schedule5 = new Schedule();
//				schedule5.setBranch(branch1);
//				schedule5.setMovie(nguoiNhen);
//				schedule5.setRoom(r1);
//				schedule5.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule5.setStartTime(LocalTime.parse("13:05"));
//				schedule5.setPrice(70000);
//				scheduleRepository.save(schedule5);
//
//				Schedule schedule6 = new Schedule();
//				schedule6.setBranch(branch1);
//				schedule6.setMovie(nguoiNhen);
//				schedule6.setRoom(r1);
//				schedule6.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule6.setStartTime(LocalTime.parse("14:05"));
//				schedule6.setPrice(70000);
//				scheduleRepository.save(schedule6);
//
//				Schedule schedule7 = new Schedule();
//				schedule7.setBranch(branch1);
//				schedule7.setMovie(nguoiNhen);
//				schedule7.setRoom(r1);
//				schedule7.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule7.setStartTime(LocalTime.parse("16:20"));
//				schedule7.setPrice(70000);
//				scheduleRepository.save(schedule7);
//
//
//				Schedule schedule8 = new Schedule();
//				schedule8.setBranch(branch1);
//				schedule8.setMovie(nguoiNhen);
//				schedule8.setRoom(r1);
//				schedule8.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule8.setStartTime(LocalTime.parse("16:20"));
//				schedule8.setPrice(70000);
//				scheduleRepository.save(schedule8);
//
//				Schedule schedule9 = new Schedule();
//				schedule9.setBranch(branch1);
//				schedule9.setMovie(nguoiNhen);
//				schedule9.setRoom(r1);
//				schedule9.setStartDate(LocalDate.parse("2021-01-06"));
//				schedule9.setStartTime(LocalTime.parse("16:20"));
//				schedule9.setPrice(70000);
//				scheduleRepository.save(schedule9);
//
//				Schedule schedule10 = new Schedule();
//				schedule10.setBranch(branch1);
//				schedule10.setMovie(nguoiNhen);
//				schedule10.setRoom(r1);
//				schedule10.setStartDate(LocalDate.parse("2021-01-06"));
//				schedule10.setStartTime(LocalTime.parse("19:20"));
//				schedule10.setPrice(70000);
//				scheduleRepository.save(schedule10);
//
//				Schedule schedule = new Schedule();
//				schedule.setBranch(branch1);
//				schedule.setMovie(blackPink);
//				schedule.setRoom(r1);
//				schedule.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule.setStartTime(LocalTime.parse("19:15"));
//				schedule.setPrice(70000);
//				scheduleRepository.save(schedule);
//
//				Room room2 = new Room();
//				room2.setName("Phòng 202");
//				room2.setBranch(branch1);
//				room2.setCapacity(40);
//				room2.setTotalArea(80);
//				room2.setImgURL("http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg");
//				Room r2 = roomRepository.save(room2);
//				Schedule schedule2 = new Schedule();
//				schedule2.setBranch(branch1);
//				schedule2.setMovie(blackPink);
//				schedule2.setRoom(r2);
//				schedule2.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule2.setStartTime(LocalTime.parse("10:15"));
//				schedule2.setPrice(70000);
//				scheduleRepository.save(schedule2);
//
//				Schedule schedule77 = new Schedule();
//				schedule77.setBranch(branch1);
//				schedule77.setMovie(nguoiNhen);
//				schedule77.setRoom(r2);
//				schedule77.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule77.setStartTime(LocalTime.parse("16:20"));
//				schedule77.setPrice(70000);
//				scheduleRepository.save(schedule77);
//
//
//				Room room3 = new Room();
//				room3.setName("Phòng 303");
//				room3.setBranch(branch1);
//				room3.setCapacity(40);
//				room3.setTotalArea(80);
//				room3.setImgURL("http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg");
//				Room r3 = roomRepository.save(room3);
//				Schedule schedule3 = new Schedule();
//				schedule3.setBranch(branch1);
//				schedule3.setMovie(venom);
//				schedule3.setRoom(r3);
//				schedule3.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule3.setStartTime(LocalTime.parse("10:15"));
//				schedule3.setPrice(70000);
//				scheduleRepository.save(schedule3);
//
//				Schedule schedule88 = new Schedule();
//				schedule88.setBranch(branch1);
//				schedule88.setMovie(nguoiNhen);
//				schedule88.setRoom(r3);
//				schedule88.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule88.setStartTime(LocalTime.parse("16:20"));
//				schedule88.setPrice(70000);
//				scheduleRepository.save(schedule88);
//
//				Room room4 = new Room();
//				room4.setName("Phòng 404");
//				room4.setBranch(branch1);
//				room4.setCapacity(40);
//				room4.setTotalArea(80);
//				room4.setImgURL("http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg");
//				Room r4 = roomRepository.save(room4);
//
//				Schedule schedule99 = new Schedule();
//				schedule99.setBranch(branch1);
//				schedule99.setMovie(nguoiNhen);
//				schedule99.setRoom(r4);
//				schedule99.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule99.setStartTime(LocalTime.parse("16:20"));
//				schedule99.setPrice(70000);
//				scheduleRepository.save(schedule99);
//
//				Branch branch2 = new Branch();
//				branch2.setName("GOLDENNEW Thủ Đức");
//				branch2.setAddress("216 Đ. Võ Văn Ngân, Bình Thọ, Thủ Đức, Thành phố Hồ Chí Minh");
//				branch2.setPhoneNo("1900 6017");
//				branch2.setImgURL("htt");
//				branch2 = branchRepository.save(branch2);
//				room1.setBranch(branch2);
//				room2.setBranch(branch2);
//				room3.setBranch(branch2);
//				Room r5 = roomRepository.save(room1);
//				Room r6 = roomRepository.save(room2);
//				Room r7 = roomRepository.save(room3);
//				Schedule schedule11 = new Schedule();
//				schedule11.setBranch(branch2);
//				schedule11.setMovie(nguoiNhen);
//				schedule11.setRoom(r5);
//				schedule11.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule11.setStartTime(LocalTime.parse("10:15"));
//				schedule11.setPrice(70000);
//				scheduleRepository.save(schedule11);
//
//
//				Branch branch3 = new Branch();
//				branch3.setName("GOLDENNEW Ba Đình");
//				branch3.setAddress("29 P. Liễu Giai, Ngọc Khánh, Ba Đình, Hà Nội 100000");
//				branch3.setPhoneNo("1900 6017");
//				branch3.setImgURL("ME");
//				branch3 = branchRepository.save(branch3);
//				room1.setBranch(branch3);
//				room2.setBranch(branch3);
//				room3.setBranch(branch3);
//				room4.setBranch(branch3);
//				Room r8= roomRepository.save(room1);
//				Room r9 = roomRepository.save(room2);
//				Room r10 = roomRepository.save(room3);
//				Room r11 = roomRepository.save(room4);
//				Schedule schedule12 = new Schedule();
//				schedule12.setBranch(branch3);
//				schedule12.setMovie(nguoiNhen);
//				schedule12.setRoom(r8);
//				schedule12.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule12.setStartTime(LocalTime.parse("10:15"));
//				schedule12.setPrice(70000);
//				scheduleRepository.save(schedule12);
//				Schedule schedule13 = new Schedule();
//				schedule13.setBranch(branch3);
//				schedule13.setMovie(blackPink);
//				schedule13.setRoom(r9);
//				schedule13.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule13.setStartTime(LocalTime.parse("22:15"));
//				schedule13.setPrice(70000);
//				scheduleRepository.save(schedule12);
//
//				Branch branch4 = new Branch();
//				branch4.setName("GOLDENNEW Phạm Hùng");
//				branch4.setAddress("Phạm Hùng, Mỹ Đình, Nam Từ Liêm, Hà Nội");
//				branch4.setPhoneNo("1900 6017");
//				branch4.setImgURL("https://www.google.com/maps/vt/data=YVeSKtAT_T4Tie7xjlIh8lVV_");
//				branch4 = branchRepository.save(branch4);
//				room1.setBranch(branch4);
//				room2.setBranch(branch4);
//				room3.setBranch(branch4);
//				room4.setBranch(branch4);
//				Room r12 = roomRepository.save(room1);
//				roomRepository.save(room2);
//				roomRepository.save(room3);
//				roomRepository.save(room4);
//
//				Schedule schedule14 = new Schedule();
//				schedule14.setBranch(branch4);
//				schedule14.setMovie(nguoiNhen);
//				schedule14.setRoom(r12);
//				schedule14.setStartDate(LocalDate.parse("2021-01-05"));
//				schedule14.setStartTime(LocalTime.parse("10:15"));
//				schedule14.setPrice(70000);
//				scheduleRepository.save(schedule14);
//			}
//		}
//
//
//
//
//	}
//
//	public Movie addNewMovie(
//		String name,
//		String smallImageURl,
//		String shortDescription,
//		String longDescription,
//		String largeImageURL,
//		String director,
//		String actors,
//		String categories,
//		LocalDate releaseDate,
//		int duration,
//		String trailerURL,
//		String language,
//		String rated,
//		int isShowing) {
//		Movie movie = new Movie();
//		movie.setName(name);
//		movie.setSmallImageURl(smallImageURl);
//		movie.setShortDescription(shortDescription);
//		movie.setLongDescription(longDescription);
//		movie.setLargeImageURL(largeImageURL);
//		movie.setDirector(director);
//		movie.setActors(actors);
//		movie.setCategories(categories);
//		movie.setReleaseDate(releaseDate);
//		movie.setDuration(duration);
//		movie.setTrailerURL(trailerURL);
//		movie.setLanguage(language);
//		movie.setRated(rated);
//		movie.setIsShowing(isShowing);
//		movie = movieRepository.save(movie);
//		return movie;
//	}


}
