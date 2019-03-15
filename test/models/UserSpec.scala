package models

import org.scalatest.fixture
import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback
import settings.DBSettings

class UserSpec extends fixture.FunSpec with DBSettings with AutoRollback {

  override def fixture(implicit session: DBSession): Unit = {
    UserDao.create("Alice")
    UserDao.create("John")
  }

  it("Johnが追加されている") { implicit session =>
    val name = "John"
    val id = UserDao.create(name)
    assert(UserDao.findById(id) === Some(User(id, name)))
  }
}
