package demo.dto

import java.sql.Timestamp

// pure case class
// without model defined
// be used in response
final case class Item(id: Long, content: String, done: Boolean, startAt: String)
