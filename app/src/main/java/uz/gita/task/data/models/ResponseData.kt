package uz.gita.task.data.models

data class ResponseData<T>(
    val message: String,
    val data: T
)