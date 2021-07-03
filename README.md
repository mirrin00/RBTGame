# Summer practice on MO EVM

A repository for summer practice on MO EVM

## Code style

Use a separated file for each class. Use a directory for each module level.
For example, the class ```com.example.demo.MyClass``` should be placed in
```com/example/demo/MyClass.kt```.

The file name should be the same as the class name. If in file has several
classes or no classes, use the name that describe the code inside. If there is
only one public class in the file, the file can be named with the name of that
class. Content order of class:
* Properties
* inti block + constructors
* Methods
* Companion object

Class name starts with uppercase letter, and new words also start with uppercase
letter. Do not use an underscore. The variable name should be in lowercase with
an underscore between the words. A package name is the same as a variable, but
without an underscore. For constant names, uppercase letters should be used with
an underscore between words. Functions and methods names start with lowercase
letter, but each word start with a capital letter. Example:
```kotlin
package com.example.demo

const val ANSWER = 42

class MyNewClass(code: Int){
    
    private var print_code_cat: Int
    
    init{
        print_code_cat = code * ANSWER
    }
    
    constructor(var1: Int, var2: Int): this(var1 + var2){
        println("Second")
    }
    
    fun printSomeThing(new_code_for_fun: Int){/* ... */}
}
```

Use four spaces instead of tabs. Put a space around binary operators, exception:
range operator - ```0..n```. Never put a space after ```(```, ```[```,
or before ```]```, ```)```, around ```.``` and ```?.```, after ```<``` and
before ```>``` in template objects. You should not put space before ```:```

Try to use no more 80 characters on one line. For functions with many arguments,
you can write each argument on a new line:
```kotlin
fun foo(argument1: SomeTypeOfClass,
        argument2: String,
        another_argument: MyClass): Int {
    /* ... */
}
```
or
```kotlin
fun foo(
        argument1: SomeTypeOfClass,
        argument2: String,
        another_argument: MyClass
): Int {
    /* ... */
}
```

Try to comment out each property of class, every function, even on one line:
```kotlin
// Class for writing to file
class FileReader{
    var path: String = "" //file path
    var str: String = "Hello" // string for writing

    /*
     * It is multi-line comment
     * This function writes str to file by path
     */
    fun write() {/* ... */}
    
    // This function return random number
    fun getRandom(): Int {/* ... */}
}
```

Examples of getter and setter:
```kotlin
class MyClass{
    var var1: Int = 0
        get() = field * MY_CONSTANT // change getter
        private set // delete setter
    
    var var2: Double = 2.0
        set(value){ // change setter
            field = when{
                value < 0.0 -> 0.0
                value > 100.0 -> 100.0/value
                else -> value
            }
        }
    /* ... */
}
```