package demo.model

import org.squeryl.KeyedEntity
import org.squeryl.dsl.OneToMany

class User(val uniqueIdentity: String, val password: String) extends KeyedEntity[Long] {

    override def id: Long = 0L

    lazy val todoItems: OneToMany[TodoItem] = DataBase.userToItem.left(this)

}
