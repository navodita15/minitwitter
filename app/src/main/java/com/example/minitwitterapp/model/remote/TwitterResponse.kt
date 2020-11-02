package com.example.minitwitterapp.model.remote

data class TwitterResponse(
    var success: Boolean,
    var data: List<Data>
) {
    override fun toString(): String {
        return "TwitterResponse(success=$success, data=$data)"
    }
}