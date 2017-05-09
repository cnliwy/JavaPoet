android 可以用来写代码的代码（JavaPoet）
==
**引言**
```
JavaPoet 一个可以用来创建源代码文件的强大库。用处还是相当多的，比如可以用来根据模板快速创建源文件，减少代码的重复编写，大大节约开发时间。还可以结合注解去创建相应的功能代码，降低运行时注解效率低的弊端等等。这应该是编写第三方库和功能插件的必备神器了吧！
```
**提前准备**
```
新建一个java library
然后再build.gradle的dependencies引入JavaPoet库
compile 'com.squareup:javapoet:1.6.1'。
不要在android module或主程序里引入此java library,否则会报错啊！
```
**JavaPoet API简介**


    FieldSpec 创建成员变量，即声明字段。
    MethodSpec 创建构造函数或方法。
    TypeSpec 代表一个类，接口，或者枚举声明。
    JavaFile 根据创建好的变量方法和类信息创建Java源文件。

**开始改变世界**
假设我们要创建一个User类，User有两个字段，分别是name和age,并且有两个方法，分别是
printUser和getUserName。好，我们开始通过JavaPoet创建此类,代码如下所示：
```
 public static void createFile(){
        //创建属性
        FieldSpec nameField = FieldSpec.builder(String.class,"name",Modifier.PRIVATE).build();
        FieldSpec ageField = FieldSpec.builder(int.class,"age",Modifier.PRIVATE).build();

        // 创建方法printUser和getUserName
        MethodSpec printUserMethod =  MethodSpec.methodBuilder("printUser") //方法名
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)  // 设置方法为public和static
                .addParameter(String.class,"name")              // 设置参数String name
                .addStatement("$T.out.println(name)",System.class)  //添加代码System.out.println(name);
                .build();
        MethodSpec getUserNameMethod = MethodSpec.methodBuilder("getUserName")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)              // 设置返回值类型为String
                .addStatement("return $S","tom")   //添加代码return "tom";
                .build();
        // 创建User类
        TypeSpec typeSpec = TypeSpec.classBuilder("User")
                .addModifiers(Modifier.FINAL,Modifier.PUBLIC)
                .addMethod(printUserMethod)
                .addMethod(getUserNameMethod)
                .addField(nameField)
                .addField(ageField)
                .build();

        // 传入包名和TypeSpec类信息，生成代码源文件
              JavaFile javaFile = JavaFile.builder("com.liwy.javapoet",typeSpec).build();
              try {
                  // java源文件的根路径（路径记得换成自己的哦！！！），会根据上面传入的包名自动创建源文件
                  javaFile.writeTo(new File("D:\\develop\\workspace\\testworkspace\\JavaPoet\\app\\src\\main\\java"));
      //            javaFile.writeTo(System.out);//输出在控制板
              } catch (IOException e) {
                  e.printStackTrace();
              }
    }
```

**运行createFile方法**
```
public static void main(String[] args) {
        createFile();
 }
```
**见证奇迹的时刻**
运行此方法后你会发现User类已经出现在了com.liwy.javapoet下面！生成的User类的代码如下：
```
package com.liwy.javapoet;

import java.lang.String;
import java.lang.System;

public final class User {
  private String name;

  private int age;

  public static void printUser(String name) {
    System.out.println(name);
  }

  public String getUserName() {
    return "tom";
  }
}
```