package fd.cmp.movie.data.local.db.service

import fd.cmp.movie.data.local.db.Database
import fd.cmp.movie.data.mapper.UserMapper.toUser
import fd.cmp.movie.data.model.User

class UserDbService {

    private val queries get() = Database.database.tUserQueries

    fun insertUser(user: User?) {
        if (user?.email == null) return
        try {
            queries.insertUser(
                email = user.email,
                name = user.name,
                phone = user.phone,
                dateOfBirth = user.dateOfBirth,
                address = user.address,
                latitude = user.latitude,
                longitude = user.longitude,
                imagePath = user.imagePath
            )
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun updateUser(user: User?) {
        if (user?.email == null) return
        try {
            queries.updateUserByEmail(
                email = user.email,
                name = user.name,
                phone = user.phone,
                dateOfBirth = user.dateOfBirth,
                address = user.address,
                latitude = user.latitude,
                longitude = user.longitude,
                imagePath = user.imagePath,
            )
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun selectAllUsers(): Result<List<User>> {
        val tUsers = queries.selectAllUsers().executeAsList()
        if (tUsers.isEmpty()) return Result.failure(Exception("No users found"))
        return Result.success(tUsers.map { it.toUser() })
    }

    fun selectUserById(id: Long): Result<User> {
        val tUser = queries.selectUserById(id).executeAsOneOrNull()
        if (tUser == null) return Result.failure(Exception("User not found"))
        return Result.success(tUser.toUser())
    }

    fun selectUserByEmail(email: String): Result<User> {
        val tUser = queries.selectUserByEmail(email).executeAsOneOrNull()
        if (tUser == null) return Result.failure(Exception("User not found"))
        return Result.success(tUser.toUser())
    }

    fun deleteUserById(id: Long) {
        queries.deleteUserById(id)
    }

}