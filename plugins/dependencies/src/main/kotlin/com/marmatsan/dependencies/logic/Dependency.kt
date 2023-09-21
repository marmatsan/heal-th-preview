package com.marmatsan.dependencies.logic

abstract class Dependency(
    open val name: String,
    open val group: String,
    open val version: String
)
