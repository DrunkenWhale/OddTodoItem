package demo.dto

case class TodoItemListResponse(override val StatusCode: String="0000",
                                body: Map[String, List[Item]])
    extends Response
