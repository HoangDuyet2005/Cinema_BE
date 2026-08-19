package com.example.goldenticketnew;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


//
//
//import com.example.goldenticketnew.dtos.MovieDto;
//import com.example.goldenticketnew.model.Movie;
//import com.example.goldenticketnew.payload.response.ResponseBase;
//import com.example.goldenticketnew.repository.IMovieRepository;
//import com.example.goldenticketnew.utils.ModelMapperUtils;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.BDDAssumptions.given;
//import static org.graalvm.word.LocationIdentity.any;
//import static org.mockito.Mockito.when;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
@SpringBootTest
class GoldenTicketNewApplicationTests {
    ////	@Autowired
////	private MockMvc mvc;
//    @Test
//    void contextLoads() {
//    }
}
//	@Autowired
//	IMovieRepository movieRepository;
////	@Test
////	void whenGetAll_shouldReturnList() {
////		// 1. create mock data
//////		List<Movie> mockMovie = new ArrayList<>();
//////		for(int i = 0; i < 5; i++) {
//////			mockMovie.add(new Movie());
//////		}
//////
//////		// 2. define behavior of Repository
//////		when(movieRepository.findAll()).thenReturn(mockMovie);
//////
//////		// 3. call service method
//////		List<Movie> movies = movieRepository.findAll();
//////
//////		// 4. assert the result
//////		assertThat(movies.size()).isEqualTo(mockMovie.size());
////
////		// 4.1 ensure repository is called
//////		verify(movieRepository).findAll();
////	}
//	@Test
//	void isMovieExitsById() {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		Movie nhocTrum = Movie.addNewMovie(2,"Nhóc Trùm: Nối Nghiệp Gia Đình","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_boss_baby_2_24.12.2021_1_1_1__1.jpg",
//                    "Nhóc trùm Ted giờ đây đã trở thành một triệu phú nổi tiếng trong khi Tim lại có một cuộc sống đơn giản bên vợ anh Carol và hai cô con gái nhỏ yêu dấu. Mỗi mùa Giáng sinh tới, cả Tina và Tabitha đều mong được gặp chú Ted",
//                    "Nhóc trùm Ted giờ đây đã trở thành một triệu phú nổi tiếng trong khi Tim lại có một cuộc sống đơn giản bên vợ anh Carol và hai cô con gái nhỏ yêu dấu. Mỗi mùa Giáng sinh tới, cả Tina và Tabitha đều mong được gặp chú Ted nhưng dường như hai anh em nhà Templeton nay đã không còn gần gũi như xưa. Nhưng bất ngờ thay khi Ted lại có màn tái xuất không thể hoành tráng hơn khi đáp thẳng máy bay trực thăng tới nhà Tim trước sự ngỡ ngàng của cả gia đình.",
//                    "https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/r/s/rsz_dr-strange-980x448.jpg",
//                    "Tom McGrath","Amy Sedaris, Jeff Goldblum, James Marsden",
//                    "Hoạt Hình",LocalDate.parse("24/12/2021",formatter),
//                    105,"https://www.youtube.com/embed/Lv8nL2q8yRI",
//                    "Tiếng Anh với phụ đề tiếng Việt và lồng tiếng Việt",
//                    "P - PHIM DÀNH CHO MỌI ĐỐI TƯỢNG",1);
//		Movie nhocT = movieRepository.findMovieById(1);
//		assertThat(nhocTrum).isEqualTo(nhocT);
//	}
//	@Test
//	public void testGetDetail() throws Exception {
//		// Tạo ra
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		Movie nhocTrum = Movie.addNewMovie(1,"Nhóc Trùm: Nối Nghiệp Gia Đình","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_boss_baby_2_24.12.2021_1_1_1__1.jpg",
//			"Nhóc trùm Ted giờ đây đã trở thành một triệu phú nổi tiếng trong khi Tim lại có một cuộc sống đơn giản bên vợ anh Carol và hai cô con gái nhỏ yêu dấu. Mỗi mùa Giáng sinh tới, cả Tina và Tabitha đều mong được gặp chú Ted",
//			"Nhóc trùm Ted giờ đây đã trở thành một triệu phú nổi tiếng trong khi Tim lại có một cuộc sống đơn giản bên vợ anh Carol và hai cô con gái nhỏ yêu dấu. Mỗi mùa Giáng sinh tới, cả Tina và Tabitha đều mong được gặp chú Ted nhưng dường như hai anh em nhà Templeton nay đã không còn gần gũi như xưa. Nhưng bất ngờ thay khi Ted lại có màn tái xuất không thể hoành tráng hơn khi đáp thẳng máy bay trực thăng tới nhà Tim trước sự ngỡ ngàng của cả gia đình.",
//			"https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/r/s/rsz_dr-strange-980x448.jpg",
//			"Tom McGrath","Amy Sedaris, Jeff Goldblum, James Marsden",
//			"Hoạt Hình",LocalDate.parse("24/12/2021",formatter),
//			105,"https://www.youtube.com/embed/Lv8nL2q8yRI",
//			"Tiếng Anh với phụ đề tiếng Việt và lồng tiếng Việt",
//			"P - PHIM DÀNH CHO MỌI ĐỐI TƯỢNG",1);
//		MovieDto movieDto = ModelMapperUtils.mapper(nhocTrum, MovieDto.class);
//
//		// giả lập trả về  mong muốn
//		//given(movieRepository.findMovieById(0)).willReturn(new ResponseBase<>(movieDto))2
//
////		mvc.perform(get("/api/movies/v1/details/1").contentType(MediaType.APPLICATION_JSON)) // Thực hiện GET REQUEST
////			.andExpect(status().isOk()) // Mong muốn Server trả về status 200
////			.andExpect(jsonPath("$"", is("))) // Hi vọng phần tử trả về đầu tiên có title = "title-0"
////
//	}
//
//}
