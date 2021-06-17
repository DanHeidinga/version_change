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

	static void method1() {
		Runnable r = () -> {System.out.println("method1");};
		r.run();
	}

	static void method2() {
		Runnable r = () -> {System.out.println("method2");};
		r.run();
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
			classfileBuffer[7] = 51; //java 7 - 0x33
			//classfileBuffer[7] = 50; //java 6 - 0x32	// results in ClassFormatError
			forByte(classfileBuffer);
			return classfileBuffer;
		}
		return null;
	}

}

}
