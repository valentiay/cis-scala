package seminars.seminar6

object explicitDI extends App {
  final case class Config(a: String)
  val cfg = Config("Some configuration")

  class HttpClient(cfg: Config)
  class DatabaseClient(cfg: Config)
  class ServiceA(cfg: Config, http: HttpClient, database: DatabaseClient) {
    def methodA: String = s"$cfg $http $database"
  }
  class ServiceB(cfg: Config, http: HttpClient, database: DatabaseClient, serviceA: ServiceA) {
    def methodB: String = s"$cfg $http $database $serviceA"
  }
  class ServiceC(cfg: Config, http: HttpClient, database: DatabaseClient, serviceB: ServiceB) {
    def methodC: String = s"$cfg $http $database $serviceC"
  }

  val http = new HttpClient(cfg)
  val database = new DatabaseClient(cfg)
  val serviceA = new ServiceA(cfg, http, database)
  val serviceB = new ServiceB(cfg, http, database, serviceA)
  val serviceC = new ServiceC(cfg, http, database, serviceB)

  println(serviceA.methodA)
  println(serviceB.methodB)
  println(serviceC.methodC)
}

object implicitDI extends App {
  final case class Config(a: String)
  val cfg = Config("Some configuration")

  class HttpClient(cfg: Config)
  class DatabaseClient(cfg: Config)
  // Компилятор будет искать параметры типа HttpClient и DatabaseClient в области видимости неявных параметров.
  // В области видимости должно быть ровно одно значение, подходящее по типу
  class ServiceA(cfg: Config)(implicit http: HttpClient, database: DatabaseClient) {
    // Неявные параметры можно использовать как обычные параметры внутри функции
    def methodA: String = s"$cfg $http $database"
  }
  class ServiceB(cfg: Config)(implicit http: HttpClient, database: DatabaseClient, serviceA: ServiceA) {
    def methodB: String = s"$cfg $http $database $serviceA"
  }
  class ServiceC(cfg: Config)(implicit http: HttpClient, database: DatabaseClient, serviceB: ServiceB) {
    def methodC: String = s"$cfg $http $database $serviceC"
  }

  // Неявным может быть как val, так и lazy val и def
  implicit val http = new HttpClient(cfg)
  implicit val database = new DatabaseClient(cfg)
  // Параметры с пометкой implicit подставляются компилятором по типу из значений с пометкой implicit
  implicit val serviceA = new ServiceA(cfg)
  implicit val serviceB = new ServiceB(cfg)
  implicit val serviceC = new ServiceC(cfg)

  println(serviceA.methodA)
  println(serviceB.methodB)
  println(serviceC.methodC)
}
