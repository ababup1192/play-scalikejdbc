package controllers

import javax.inject._
import play.api.mvc._
import models.UserDao
import scalikejdbc._

@Singleton
class MainController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def listUser() = Action { implicit request: Request[AnyContent] => {

    val users = DB.readOnly { implicit session => UserDao.findAll() }

    Ok(users.toString)
  }
  }
}
