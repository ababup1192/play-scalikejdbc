package controllers

import javax.inject._
import play.api.mvc._
import models.{User, UserDao}
import scalikejdbc._
import _root_.play.api.libs.json.{JsPath, JsValue, Json, Reads, Writes}
import services.UserService

case class Name(value: String)

@Singleton
class MainController @Inject()(
                                cc: ControllerComponents,
                                userService: UserService
                              ) extends AbstractController(cc) {
  implicit val userWrites: Writes[User] = Json.writes[User]
  implicit val nameReads: Reads[Name] = (JsPath \ "name").read[String].map(Name.apply)

  def listUser() = Action { implicit request: Request[AnyContent] => {
    Ok(Json.toJson(userService.listUser()))
  }
  }

  def createUser(): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[Name].fold(
      errors => BadRequest("name not found")
      , name => {
        Ok(Json.obj("id" -> userService.createUser(name.value)))
      }
    )
  }
}
