package com.test.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM5;

/**
 * Created by shenfl on 2018/7/30
 */
public class RemoveMethodAdapter extends ClassVisitor {
    private String name;
    private String desc;
    public RemoveMethodAdapter(ClassVisitor cv, String name, String desc) {
        super(ASM5);
        this.name = name;
        this.desc = desc;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (this.name.equals(name) && this.desc.equals(desc)) {
            // 不要委托至下一个访问器 -> 这样将移除该方法
            return null;
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }
}
