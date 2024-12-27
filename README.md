# Movie Compose Multiplatform (Android & iOS)

---

## 🚀 Tech Stack

- **Programming Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- **Architecture**: MVVM (Model-View-ViewModel)
- **HTTP Client**: [Ktor](https://ktor.io/) with [OkHttp](https://square.github.io/okhttp/) and [Darwin](https://github.com/ktorio/ktor-darwin)
- **Dependency Injection**: [Koin](https://insert-koin.io/)
- **Image Loader**: [Coil](https://coil-kt.github.io/coil/)
- **Navigation**: Compose Navigation
- **Preferences/Session Management**: [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)
- **Local Database**: [SQLDelight](https://github.com/sqldelight/sqldelight)
- **Date-Time Handling**: [Kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime)
- **Maps Integration**: [KGoogle Map](https://github.com/the-best-is-best/kgoogle-map)
- **Camera and Image Picker**: [Peekaboo](https://github.com/onseok/peekaboo)
- **Permission Handler**: [KMP Utilities](https://github.com/azisanw19/kmp-utilities)

---

## 🎨 Features

### Screens
- **Login Screen**: Includes TextField and Button.
- **Movie List Screen**:  
  - Search with debounce using `DebounceTextField`.  
  - LazyColumn with load more functionality.  
  - Movie items displayed with title and image.
- **Movie Detail Screen**: Displays movie title, image, description, and cast.
- **User Screen**:  
  - Circle Avatar.  
  - Editable TextField.  
  - Date Picker.  
  - Map Integration.  
  - Actionable Button.
- **Edit Photo Screen**:  
  - Camera functionality.  
  - Image Picker.
- **Edit Location Screen**:  
  - Search with debounce.  
  - Address list, description, and confirmation button.

### Reusable Components
- LoadingScreen  
- EmptyScreen  
- ErrorScreen  
- StarRatingScreen  
- MapScreen  
- DatePicker (BottomSheet Dialog)  
- Success (BottomSheet Dialog)  
- Error (BottomSheet Dialog)

---

## 📸 Screenshots

<div align="center">
  <img src="https://raw.githubusercontent.com/firdausmaulan/CMP-Movie/refs/heads/master/screenshoot/login.jpeg" width="250">
  <img src="https://raw.githubusercontent.com/firdausmaulan/CMP-Movie/refs/heads/master/screenshoot/movies.jpeg" width="250">
  <img src="https://raw.githubusercontent.com/firdausmaulan/CMP-Movie/refs/heads/master/screenshoot/movie-detail.jpeg" width="250">
</div>

<div align="center">
  <img src="https://raw.githubusercontent.com/firdausmaulan/CMP-Movie/refs/heads/master/screenshoot/user.jpeg" width="250">
  <img src="https://raw.githubusercontent.com/firdausmaulan/CMP-Movie/refs/heads/master/screenshoot/date-picker.jpeg" width="250">
  <img src="https://raw.githubusercontent.com/firdausmaulan/CMP-Movie/refs/heads/master/screenshoot/location.jpeg" width="250">
</div>

---

## 📚 Sources

- [Kotlin KMP App Template](https://github.com/Kotlin/KMP-App-Template)  
- [Official KMP Template](https://kmp.jetbrains.com)  
- [Philipp Lackner YouTube Channel](https://www.youtube.com/@PhilippLackner/videos)  
- [My Blog Jetpack Compose](https://github.com/firdausmaulan/My-Blog-Jetpack-Compose)  
- [Kotlin Multiplatform Updates and Showcases](https://www.linkedin.com/company/kotlin-multiplatform-updates-and-showcases/posts/?feedView=all)  
- [Julsapargi Nursam's LinkedIn Post](https://www.linkedin.com/posts/julsapargi-nursam_composemultiplatform-kotlinmultiplatform-activity-7264232968619999235-Idwf?utm_source=share&utm_medium=member_desktop)  

---

Feel free to explore, fork, and contribute!
