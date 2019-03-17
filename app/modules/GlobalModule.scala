package modules

import javax.inject._

@Singleton
class Global {
}

class GlobalModule extends com.google.inject.AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Global]).asEagerSingleton()
  }
}
