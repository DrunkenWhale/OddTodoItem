package demo.model

import org.squeryl.KeyedEntity

class User(val uniqueIdentity:String,val password:String) extends KeyedEntity[Long]{
    override def id: Long = 0L
}
