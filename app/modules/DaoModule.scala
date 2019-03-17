package modules

import models.{UserDao, UserDaoImpl}
import services.{UserService, UserServiceImpl}

class DaoModule extends com.google.inject.AbstractModule {
  override def configure(): Unit = {
    // bind(classOf[UserService]).toInstance(new UserServiceImpl)
  }
}