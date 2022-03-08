package demo.model

import org.squeryl.KeyedEntity
import org.squeryl.dsl.ManyToOne

import java.sql.Timestamp

class TodoItem(val content: String, val done: Boolean, val startAt: Timestamp, val userID: Long) extends KeyedEntity[Long] {

    override def toString: String = s"TodoItem(id= $id, content = $content, done = $done, startAt = $startAt)"
    override val id: Long = 0


    lazy val user: ManyToOne[User] = DataBase.userToItem.right(this)

}

