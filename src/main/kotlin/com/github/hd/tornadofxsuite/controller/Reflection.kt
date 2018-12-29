package com.github.hd.tornadofxsuite.controller

import java.util.ArrayList
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberExtensionProperties
import kotlin.reflect.full.memberProperties

data class ClassBreakDown(val className: String,
                          val classProperties: ArrayList<ClassProperties>,
                          val classMethods: ArrayList<String>)

data class ClassProperties(val propertyName: String,
                           val propertyType: String)


class Reflection(val kClass: String) {

    init {
        classBreakDown()
    }

    fun classBreakDown() {
        // constructor
        println("Constructor: ")
        kClass::class.constructors.forEach(::println)
        println("------------------------------------------------------")
        // constructor
        println("Java Constructor: ")
        kClass::class.java.constructors.forEach(::println)
        println("------------------------------------------------------")
        // functions
        println("Functions: ")
        kClass::class.functions.forEach(::println)
        println("------------------------------------------------------")
        // memberProperties
        println("Member Properties: ")
        kClass::class.memberProperties.forEach(::println)
        println("------------------------------------------------------")
        // memberExtensionFunctions
        println("Member Properties: ")
        kClass::class.memberExtensionProperties.forEach(::println)
        println("------------------------------------------------------")
        // methods
        println("Java Methods: ")
        kClass::class.java.methods.forEach(::println)
        println("------------------------------------------------------")
        // companion object
        println("Java Object: ")
        kClass::class.companionObject.also(::println)
        println("------------------------------------------------------")
    }
}