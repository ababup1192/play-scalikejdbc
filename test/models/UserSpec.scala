package models

import org.scalatest.fixture
import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback
import settings.DBSettings

class UserSpec extends fixture.FunSpec with DBSettings with AutoRollback {
  val userDao = new UserDaoImpl

  override def fixture(implicit session: DBSession): Unit = {
    userDao.create("Alice")
    userDao.create("John")
  }

  it("Johnが追加されている") { implicit session =>
    val name = "John"
    val id = userDao.create(name)
    assert(userDao.findById(id) === Some(User(id, name)))
  }
}
