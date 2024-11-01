data class EmailModel(
    val senderName: String,
    val subject: String,
    val preview: String,
    val time: String,
    var isStarred: Boolean
)
