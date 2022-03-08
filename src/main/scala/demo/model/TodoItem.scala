package demo.model

import akka.http.scaladsl.model.DateTime
import org.squeryl.KeyedEntity
import org.squeryl.dsl.ManyToOne

class TodoItem(val content: String, val done: Boolean, val startAt: DateTime, val userID: Long) extends KeyedEntity[Long] {

    override def toString: String = s"TodoItem(id= $id, content = $content, done = $done, startAt = $startAt)"

    override def id: Long = 0L

    lazy val user: ManyToOne[User] = DataBase.userToItem.right(this)

}

