package seminars.seminar10

import fs2.Stream
import cats.effect.{ExitCode, IO, IOApp}

object slide2 extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(println("++"))
      plusPlus = (Stream(1, 2, 3) ++ Stream(4, 5)).toList
      _ <- IO(println(plusPlus))

      _ <- IO(println("map"))
      map = Stream(1, 2, 3).map(_ + 1).toList
      _ <- IO(println(map))

      _ <- IO(println("filter"))
      filter = Stream(1, 2, 3).filter(_ % 2 != 0).toList
      _ <- IO(println(filter))

      _ <- IO(println("fold"))
      fold = Stream(1, 2, 3).fold(0)(_ + _).toList
      _ <- IO(println(fold))

      _ <- IO(println("collect"))
      collect = Stream(None, Some(2), Some(3)).collect { case Some(i) => i }.toList
      _ <- IO(println(collect))

      _ <- IO(println("intersperse"))
      intersperse = Stream.range(0, 5).intersperse(42).toList
      _ <- IO(println(intersperse))

      _ <- IO(println("flatMap"))
      flatMap = Stream(1, 2, 3).flatMap(i => Stream(i, i)).toList
      _ <- IO(println(flatMap))

      _ <- IO(println("evalMap"))
      evalMap <- Stream(1, 2, 3).evalMap(i => IO(i.toString  * i)).compile.toList
      _ <- IO(println(evalMap))

      _ <- IO(println("repeat"))
      repeat = Stream(1, 2, 3).repeat.take(9).toList
      _ <- IO(println(repeat))

      _ <- IO(println("repeatN"))
      repeatN = Stream(1, 2, 3).repeatN(2).toList
      _ <- IO(println(repeatN))
    } yield ExitCode.Success
}
