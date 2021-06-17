package pkg;

import java.lang.instrument.*;
import java.security.*;

public class Test {
	static Instrumentation instrument;

	public static void agentmain(String agentArgs, Instrumentation inst) {
		instrument = inst;
		System.out.println("agentmain called");
	}

	public static void premain(String agentArgs, Instrumentation inst) {
		instrument = inst;
		System.out.println("premain called");
	}

	public static void main(String[] args) throws Throwable {
		Foo.method1();
		if (instrument != null) {
			if (instrument.isRetransformClassesSupported()) {
				System.out.println("== Retransform  ==");
				instrument.addTransformer(new ChangeVersion(), true);
				instrument.retransformClasses(Foo.class);
			} else {
				System.out.println("== No retransform ==");
			}
		} else {
			System.out.println("== No agent ==");
		}
		Foo.method2();
	}


static class Foo {

	private static void method1() {
		System.out.println("method1");
	}

	private static void method2() {
		System.out.println("method2");
	}
}

static class ChangeVersion implements ClassFileTransformer {

	static void forByte(byte[] bs) {
		for (int i = 0; i < 10; i++) {
			System.out.print(Integer.toHexString(bs[i]));
		}
		System.out.println();
	}

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		System.out.println("transform: "+className);
		if (classBeingRedefined == Foo.class) {
			System.out.println("Modifying ("+className+") from " + Integer.toHexString(classfileBuffer[7]));
			forByte(classfileBuffer);
			classfileBuffer[7] = 52; //java 8 - 0x34
			forByte(classfileBuffer);
			return classfileBuffer;
		}
		return null;
	}


	public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		System.out.println("transform: "+className);
		if (classBeingRedefined == Foo.class) {
			System.out.println("Modifying ("+className+") from " + Integer.toHexString(classfileBuffer[7]));
			forByte(classfileBuffer);
			classfileBuffer[7] = 52; //java 8 - 0x34
			forByte(classfileBuffer);
			return classfileBuffer;
		}
		return null;
	}

}

}
