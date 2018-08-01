package com.test.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static jdk.internal.org.objectweb.asm.Opcodes.V1_8;
import static org.objectweb.asm.Opcodes.*;

/**
 * Created by shenfl on 2018/7/31
 */
public class TestCreateClass {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, NoSuchMethodException {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER,
                "com/ListHolder", null, "java/lang/Object", null);

        // 构造方法
        MethodVisitor mv;
        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // getList方法
        mv = cw.visitMethod(ACC_PUBLIC, "getList",
                "()Ljava/util/List;", null, null);
        mv.visitCode();
        mv.visitTypeInsn(NEW, "java/util/ArrayList");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 1);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitLdcInsn("ll");
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
        mv.visitInsn(POP);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

        cw.visitEnd();
        byte[] b = cw.toByteArray();


        // 加载类并使用
        MyClassLoader classLoader = new MyClassLoader();
        Class<?> aClass = classLoader.defineClassForName("com.ListHolder", b);
        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor.toGenericString());
        }
        Method method = aClass.getDeclaredMethod("getList");
        Object o = constructors[0].newInstance();
        Object invoke = method.invoke(o);
        System.out.println(invoke);


        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        Object o1 = integers;
        List<String> strings = (List<String>) o1;
        System.out.println(strings);


    }
}
