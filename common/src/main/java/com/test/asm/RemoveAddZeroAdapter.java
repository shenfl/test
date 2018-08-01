package com.test.asm;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM5;

/**
 * Created by shenfl on 2018/7/31
 */
public class RemoveAddZeroAdapter extends PatternMethodAdapter {
    public RemoveAddZeroAdapter(MethodVisitor mv) {
        super(ASM5, mv);
    }

    @Override
    public void visitInsn(int opcode) {
        if (state == 1)
        super.visitInsn(opcode);
    }

    @Override
    void visitInsn() {

    }
}
