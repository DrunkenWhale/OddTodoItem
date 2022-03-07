package demo.model

import akka.http.scaladsl.model.DateTime
import org.squeryl.KeyedEntity

class TodoItem(val id: Long, val content: String, val done: Boolean, val startAt: DateTime) extends KeyedEntity[Long] {
    override def toString: String = s"TodoItem(id= $id, content = $content, done = $done, startAt = $startAt)"
}
