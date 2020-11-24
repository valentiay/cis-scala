package seminars.seminar11

import seminars.seminar11.elections.ElectionsModule
import seminars.seminar11.math.MathModule

final class Modules(val math: MathModule.type, val elections: ElectionsModule)

object Modules {
  def apply(services: Services): Modules = {
    import services._

    new Modules(
      math = MathModule,
      elections = new ElectionsModule
    )
  }
}
