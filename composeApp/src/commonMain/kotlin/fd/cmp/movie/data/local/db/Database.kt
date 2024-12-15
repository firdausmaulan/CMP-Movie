package fd.cmp.movie.data.local.db

import app.cash.sqldelight.db.SqlDriver

object Database {
    lateinit var database: AppDatabase
        private set

    fun init(driverFactory: DriverFactory) {
        if (!this::database.isInitialized) {
            val driver: SqlDriver = driverFactory.createDriver()
            database = AppDatabase(driver)
        }
    }
}