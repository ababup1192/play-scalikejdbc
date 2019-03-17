package services

import com.google.inject.ImplementedBy
import javax.inject.Inject
import models.{User, UserDao}
import scalikejdbc.DB

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {
  def listUser(): Seq[User]

  def createUser(name: String): Long
}

class UserServiceImpl @Inject()(userDao: UserDao) extends UserService {
  override def listUser(): Seq[User] =
    DB.readOnly { implicit session => userDao.findAll() }

  override def createUser(name: String): Long =
    DB.autoCommit { implicit session => userDao.create(name) }
}
