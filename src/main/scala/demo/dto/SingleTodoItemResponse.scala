package demo.dto

case class SingleTodoItemResponse(override val StatusCode: String = "0000",
                                  body: Map[String, Item]) extends Response