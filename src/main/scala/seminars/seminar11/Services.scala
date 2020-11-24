package seminars.seminar11

import cats.effect.IO
import seminars.seminar11.elections.{ElectionsService, ElectionsServiceImpl}

final class Services(implicit val elections: ElectionsService)

object Services {
  def apply(cfg: AppConfig): IO[Services] =
    for {
      elections <- ElectionsServiceImpl.apply(cfg.elections)
    } yield new Services()(elections)
}
