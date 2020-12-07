package seminars.seminar12

import io.circe.generic.JsonCodec
import io.circe.generic.extras._

object data {

  // Для работа аннотаций нужно добавить
  // флаг компилятора `scalacOptions += "-Ymacro-annotations"` в build.sbt
  @ConfiguredJsonCodec sealed trait Bar

  object Bar {
    implicit val circeConfig: Configuration =
      Configuration.default.withDiscriminator("type").withSnakeCaseConstructorNames

    @JsonCodec final case class Baz(int: Int, string: String) extends Bar
    @JsonCodec final case class Qux(long: Long) extends Bar
  }

  @JsonCodec final case class Foo(
    boolean: Boolean,
    bar: Option[Bar],
    doubles: List[Double],
    maybeChar: Option[Char]
  )

}

