package fd.cmp.movie.data.mapper

import fd.cmp.movie.data.local.db.TUser
import fd.cmp.movie.data.model.User


object UserMapper {

    fun TUser.toUser(): User {
        return User(
            id = id,
            email = email,
            name = name,
            phone = phone,
            dateOfBirth = dateOfBirth,
            address = address,
            latitude = latitude,
            longitude = longitude,
            imagePath = imagePath
        )
    }

    fun User.toTUser(): TUser {
        return TUser(
            id = id!!,
            email = email,
            name = name,
            phone = phone,
            dateOfBirth = dateOfBirth,
            address = address,
            latitude = latitude,
            longitude = longitude,
            imagePath = imagePath
        )
    }

}