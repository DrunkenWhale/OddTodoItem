package demo.model

import org.squeryl.PrimitiveTypeMode.{transaction, _}
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.{Schema, Session, SessionFactory, Table}

object DataBase extends Schema {

  val users: Table[User] = table[User]
  val todoItems: Table[TodoItem] = table[TodoItem]

  on(users)(user => declare(
    user.id is(primaryKey, autoIncremented),
    user.uniqueIdentity is unique
  ))

  on(todoItems)(todoItem => declare(
    todoItem.id is(primaryKey, autoIncremented)
  ))

  val userToItem: OneToManyRelationImpl[User, TodoItem] =
    oneToManyRelation(users, todoItems).via((user, item) => user.id === item.userID)


  def register(): Unit = {
    Class.forName("com.mysql.cj.jdbc.Driver")
    SessionFactory.concreteFactory = Some(() => Session.create(
      java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/todoitem", "root", "3777777"),
      new MySQLAdapter
    ))
    transaction {
      DataBase.create
    }
  }


}
