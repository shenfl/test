package com.test.asm;

import org.objectweb.asm.ClassVisitor;

import static org.objectweb.asm.Opcodes.ASM5;

/**
 * Created by shenfl on 2018/7/30
 */
public class RemoveDebugAdapter extends ClassVisitor {
    public RemoveDebugAdapter(ClassVisitor cv) {
        super(ASM5, cv);
    }
    @Override
    public void visitSource(String source, String debug) {
    }
    @Override
    public void visitOuterClass(String owner, String name, String desc) {
    }
    @Override
    public void visitInnerClass(String name, String outerName,
                                String innerName, int access) {
    }
}
