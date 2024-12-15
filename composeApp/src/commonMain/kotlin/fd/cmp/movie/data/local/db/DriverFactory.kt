package fd.cmp.movie.data.local.db

import app.cash.sqldelight.db.SqlDriver

const val DATABASE_NAME = "myapp.db"

expect class DriverFactory {
    fun createDriver(): SqlDriver
}