package fd.cmp.movie.helper

object TextHelper {

    fun roundToOneDecimal(number: Double): String {
        val iNumber: Int = (number * 10).toInt()
        val dNumber = iNumber.toDouble() / 10
        if (dNumber % 1 == 0.0) {
            return dNumber.toInt().toString()
        }
        return dNumber.toString()
    }

    fun formatTitle(title: String): String {
        if (title.length in 35..50 && title.contains(":")) {
            return title.reversed().replaceFirst(" :", "\n:").reversed()
        }
        return title
    }

    fun setTextSizeBasedOnText(text: String): Int {
        val length = text.length
        if (length <= 50) {
            return 20
        } else if (length <= 75) {
            return 18
        } else if (length <= 100) {
            return 16
        }
        return 14
    }

    fun isValidEmailFormat(email: String?): Boolean {
        if (email == null) return false
        val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        return emailRegex.matches(email)
    }

}