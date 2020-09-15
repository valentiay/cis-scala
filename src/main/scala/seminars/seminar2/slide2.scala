package seminars.seminar2

import java.time.Instant

import scala.collection.mutable

// По умолчанию классы публичные
// private[seminar2] class - класс, доступный только в пакете seminars.seminar2
final class Cache(
                   // Параметры конструктора без модификаторов являются приватными значениями (private[this] val)
                   items: Map[Long, String],
                   // Парметры с модификатором val будут являться публичными членами (val)
                   val capacity: Int,
                 ) {
  // Возможные модификаторы доступа для члена класса:
  //  * private[this] - доступен только для текущего экземпляра класса
  //  * private - доступен для текущего и всех других экземпляров классов
  //  * private[seminar2] - доступен в соответствующем пакете
  //  * protected - доступен в классе-наследнике
  //  * без модификатора - публичный член, доступен из любого места
  private val storage: mutable.Map[Long, (Long, String)] =
    new mutable.HashMap[Long, (Long, String)]

  storage.addAll(items.take(capacity).view.mapValues(item => (System.nanoTime, item)))

  private def logAction(message: String): Unit =
    println(s"${Instant.now} | $message")

  def put(id: Long, string: String): Unit = {
    logAction(s"Putting element $id")
    if (storage.size >= capacity) {
      storage.remove(storage.minBy(_._2._1)._1)
    }
    storage.put(id, (System.nanoTime, string))
  }

  def get(id: Long): Option[String] = {
    logAction(s"Getting element $id")
    storage.updateWith(id) {
      case Some((_, value)) => Some((System.nanoTime, value))
      case None => None
    }.map(_._2)
  }

  def dump(): Unit =
    storage.foreach {
      case (id, (nanoTime, value)) => println(s"$id | $value | $nanoTime")
    }
}

object slide2 extends App {
  val items = Map(
    1L -> "Pikachu",
    2L -> "Jigglypuff",
  )

  val cache = new Cache(
    items = items,
    capacity = 3,
  )

  println(cache)

  println()

  // Параметры конструктора, объявленные через val доступны извне
  println(cache.capacity)
  // Параметры без модификаторов недоступны
  //  println(cache.items)

  println()

  cache.put(3L, "Snorlax")
  println(cache.get(1L))
  println(cache.get(2L))
  cache.put(4L, "Magikarp")
  println(cache.get(1L))
  println(cache.get(4L))
}
