package com.test.asm;

import org.objectweb.asm.MethodVisitor;

/**
 * Created by shenfl on 2018/7/31
 */
public abstract class PatternMethodAdapter extends MethodVisitor {

    protected int state;

    public PatternMethodAdapter(int api, MethodVisitor mv) {
        super(api);
    }

    @Override
    public void visitInsn(int opcode) {
        visitInsn();
        mv.visitInsn(opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        visitInsn();
        mv.visitIntInsn(opcode, operand);
    }

    abstract void visitInsn();
}
