package com.example;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

public class CodeGenerate {
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
            // java源文件的根路径，会根据上面传入的包名自动创建源文件
            javaFile.writeTo(new File("D:\\develop\\workspace\\testworkspace\\JavaPoet\\app\\src\\main\\java"));
//            javaFile.writeTo(System.out);//输出在控制板
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createFile();
    }
}
