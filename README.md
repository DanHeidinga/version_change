Classfile Version Change
----

This is an exmaple repo that shows the affects of changing the
classfile version at runtime using the Java instrumentation
package.

The reason for creating this was to explore the behaviour differences
between OpenJ9 and Hotspot with respect to potential specification
clarifications coming out of Project Valhalla.

See the [JEP draft: Better-defined JVM class file validation](http://openjdk.java.net/jeps/8267650)
and the example spec cleanups in http://cr.openjdk.java.net/~dlsmith/8267650/8267650-20210603/specs/

In particular, this [format-checking-jvms](http://cr.openjdk.java.net/~dlsmith/8267650/8267650-20210603/specs/format-checking-jvms.html)
one that adds the following clarification to section `4.7.1 Nonstandard Attributes`:

> An attribute is nonstandard if one of the following are true:
> 
> The attribute's name is not one of the predefined attribute names (see Table 4.7-A).
>
> The attribute appears in a class file whose version number is v, and the predefined attribute of the same name was first defined in a class file version later than v (see Table 4.7-B).
> 
> The attribute appears in a location other than where the predefined attribute of the same name is defined to appear (see Table 4.7-C).

