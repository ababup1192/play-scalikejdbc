package controllers

import akka.stream.Materializer
import models.User
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.mvc.ControllerComponents
import play.api.test.Helpers._
import play.api.test._
import services.UserService

class MainControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {
  "MainController GET" should {

    "/users" can {
      "ユーザがいない -> Ok([])。" in {
        val userService = mock[UserService]
        when(userService.listUser()) thenReturn Seq()

        val controller = new MainController(stubControllerComponents(), userService)
        val result = controller.listUser().apply(FakeRequest(GET, "/users"))

        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.arr()
      }

      "3人のユーザ -> Ok([{}, {}, {}])。" in {
        val userService = mock[UserService]
        when(userService.listUser()) thenReturn Seq(User(1, "John"), User(2, "Mike"), User(3, "Mary"))

        val controller = new MainController(stubControllerComponents(), userService)
        val listUsers = controller.listUser().apply(FakeRequest(GET, "/users"))

        status(listUsers) mustBe OK
        contentType(listUsers) mustBe Some("application/json")
        contentAsJson(listUsers) mustBe Json.arr(
          Json.obj("id" -> 1, "name" -> "John"),
          Json.obj("id" -> 2, "name" -> "Mike"),
          Json.obj("id" -> 3, "name" -> "Mary")
        )
      }
    }
  }

  "MainController POST" should {
    implicit lazy val materializer: Materializer = app.materializer
    "/users" when {

      "正しいJsonを Post -> Ok({id: 1})。" in {
        val name = "Foo"
        val id = 1L
        val userService = mock[UserService]
        when(userService.createUser(name)) thenReturn id

        val controllerComponents = inject[ControllerComponents]
        val controller = new MainController(controllerComponents, userService)
        val action = controller.createUser()
        val request = FakeRequest(POST, "/users").withJsonBody(Json.obj("name" -> name))

        val result = call(action, request)

        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.obj("id" -> id)
      }
    }
  }
}
