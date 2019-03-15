package controllers

import javax.inject._
import play.api.mvc._
import repository.UserDao
import scalikejdbc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index() = Action { implicit request: Request[AnyContent] => {

    DB.autoCommit { implicit session =>
      sql"""
        drop table if exists users;

        create table users(
          id serial not null primary key,
          name varchar(64)
        )
      """.execute.apply()
    }

    val user = DB.localTx { implicit session =>
      val id = UserDao.create("John")
      UserDao.findById(id)
    }

    Ok(user.toString)
  }
  }
}
