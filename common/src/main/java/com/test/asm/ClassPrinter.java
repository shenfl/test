package com.test.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;

import static jdk.internal.org.objectweb.asm.Opcodes.V1_8;
import static org.objectweb.asm.Opcodes.*;

/**
 * Created by shenfl on 2018/7/30
 */
public class ClassPrinter extends ClassVisitor {
    public ClassPrinter() {
        super(Opcodes.ASM5);
    }
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        System.out.println(name + " extends " + superName + " {");
    }
    public void visitSource(String source, String debug) {
    }
    public void visitOuterClass(String owner, String name, String desc) {
    }
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }
    public void visitAttribute(Attribute attr) {
    }
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
    }
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println(" " + desc + " " + name);
        return null;
    }
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println(" " + name + desc);
        return null;
    }
    public void visitEnd() {
        System.out.println("}");
    }

    public static void main(String[] args) throws IOException {
        ClassPrinter cp = new ClassPrinter();
        ClassReader cr = new ClassReader("java.lang.Runnable");
        cr.accept(cp, 0);


        // 定义类
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object",
                new String[] {});
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd();
        byte[] b = cw.toByteArray();

        // load类
        MyClassLoader classLoader = new MyClassLoader();
        Class<?> aClass = classLoader.defineClassForName("pkg.Comparable", b);
        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor.toGenericString());
        }


        // change byte
        ClassReader cr1 = new ClassReader(b);
        ClassWriter cw1 = new ClassWriter(0);
        ClassVisitor cv1 = new ClassVisitor(ASM5, cw1) {};
        cr1.accept(cv1, 0);
        byte[] bytes = cw1.toByteArray();
        System.out.println(bytes.length);
        System.out.println(b.length);
        System.out.println(bytes.equals(b)); // false why?



        // change version
        ClassReader cr2 = new ClassReader(b);
        ClassWriter cw2 = new ClassWriter(cr2, 0);
        ChangeVersionAdapter adapter = new ChangeVersionAdapter(cw2);
        cr2.accept(adapter, 0);
        byte[] bytes2 = cw2.toByteArray();
        System.out.println(bytes2.length);


        // type api
        System.out.println(Type.getType(String.class).getInternalName());
        System.out.println(Type.getType(String.class).getDescriptor());
        System.out.println(Type.INT_TYPE.getDescriptor());
        Type[] argumentTypes = Type.getArgumentTypes("(I)V");
        for (Type argumentType : argumentTypes) {
            System.out.println(argumentType.getDescriptor());
        }
        int opcode = Type.FLOAT_TYPE.getOpcode(IMUL);
        System.out.println(opcode);


        // test trace class
        ClassWriter cw3 = new ClassWriter(0);
        ClassReader cr3 = new ClassReader(b);
        TraceClassVisitor tv = new TraceClassVisitor(cw3, new PrintWriter(new OutputStreamWriter(System.out)));
        cr3.accept(tv, 0);

    }
}
