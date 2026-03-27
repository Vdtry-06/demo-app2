# Cinema App Workflow

Ứng dụng này là một ứng dụng giả lập hệ thống đặt vé xem phim với cấu trúc Clean Architecture cơ bản kết hợp Dagger Hilt và Room Database (hiện đang sử dụng Data giả lập để demo UI). 

Dưới đây là mô tả luồng (workflow) hoạt động chính của ứng dụng:

## 1. Duyệt cấu trúc và chọn Phim (`MovieFragment`)
Màn hình đầu tiên khởi chạy là màn hình trang chủ / danh sách phim. Tại đây, ứng dụng cung cấp giao diện để người dùng có thể duyệt qua danh sách các bộ phim đang được chiếu tại các rạp.

## 2. Truy cập Rạp chiếu phim (`TheatreFragment`)
Sau khi chọn một bộ phim cụ thể (nhấn vào `Movie`), người dùng sẽ được đưa sang màn hình hiển thị danh sách các rạp (`TheatreFragment`) có chiếu bộ phim đó trong ngày. `movieId` được truyền qua Navigation Component dưới dạng Safe Argument.

## 3. Xem các Suất chiếu (`ShowtimeFragment`)
Khi chọn một rạp chiếu, người dùng sẽ đọc được danh sách các suất chiếu (`Showtimes`) bao gồm thông tin phòng chiếu và giờ chiếu. 
- Tại đây, ứng dụng sử dụng Adapter để tạo danh sách suất chiếu.
- **Tính năng xác thực (Auth Check)**: Khi người dùng bấm vào một suất chiếu bất kỳ để đặt ghế, ứng dụng sẽ nhờ `SessionManager` kiểm tra xem người dùng đã ở trạng thái đăng nhập hay chưa bằng cách đọc dữ liệu `SharedPreferences`.

## 4. Kiểm tra Đăng nhập & Điều hướng thông minh (`LoginFragment`)
- **Trường hợp đã đăng nhập**: Người dùng tự động được chuyển sang giao diện Chọn ghế (`BookingFragment`) kèm theo `showtimeId`.
- **Trường hợp chưa đăng nhập**: Người dùng bị đẩy sang màn `LoginFragment`. Điểm đặc biệt là `showtimeId` vẫn được ghi nhớ và truyền theo.
  - Sau khi đăng nhập thành công vào hệ thống, `SessionManager` sẽ lưu trữ `userId`.
  - App phân tích: Nếu biến `showtimeId != -1L` (tức là người dùng đang định book vé), người dùng sẽ được chuyển tiếp sang màn `BookingFragment` ngay thay vì bị quay trở về dòng thời gian mặc định (Home).
  - Nếu đăng nhập từ màn thông thường (không gài `showtimeId`), app đưa về trang chủ `MainFragment`.

## 5. Đặt vé & Chọn ghế (`BookingFragment`)
Tại màn `BookingFragment`, một Grid giao diện 30 ghế (từ `A1` đến `C10`) sẽ được sinh ra trên UI.
- Dao truy vấn Database để lọc ra các ghế *đã được đặt trước đó* (`BOOKED` - màu Đỏ).
- Các ghế trống xuất hiện mặc định với trạng thái `AVAILABLE` (màu Xanh) và người dùng có thể bấm vào để tạo trạng thái `SELECTED` (màu Cam).
- Sau khi bấm `Book Selected`, `BookingViewModel` sẽ dựa trên `userId` hiện tại lưu dữ liệu vào Data layer nhằm chốt việc giữ ghế. Một sự kiện `bookingSuccess` được thiết kế bằng `SharedFlow` từ ViewModel đánh tiếng cho Fragment.
- Thay vì ở lại, màn hình sau đó tự động chuyển hướng người dùng sang `TicketListFragment`.

## 6. Lịch sử đặt vé (`TicketListFragment`)
Tại màn hình cuối danh mục hành trình, ứng dụng gọi Query để Load toàn bộ các vé của chính User đó ra (`userId`). Mỗi vé được định dạng chuẩn với Số ghế định danh, chi phí, cũng như TimeStamp ngày giờ mà người dùng quyết định mua.

---
## Công nghệ Core Implementations
- **Room Database**: Entity definitions (`UserEntity`, `MovieEntity`, `TheaterEntity`, `ShowtimeEntity`, `TicketEntity`).
- **Dagger Hilt**: Inject `CinemaRepository`, `SessionManager` trên toàn app.
- **Coroutines Flow**: Quan sát dữ liệu reactive.
- **Navigation Component**: Action Graph & Arguments truyền thông tin điều hướng chính xác giữa các màn (Fragment).