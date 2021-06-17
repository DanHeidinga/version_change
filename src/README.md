To easily run these tests, run one of the following commands from the `test/` directory to launch 
a container to test the example:

OpenJ9
```
docker run --rm -it -v `pwd`:/test adoptopenjdk/openjdk16-openj9 /bin/bash
```

Hotspot
```
docker run --rm -it -v `pwd`:/test adoptopenjdk/openjdk16 /bin/bash
```

In the container, run the following commands:
```
cd /test
./build.sh
./run.sh
```

The code uses an agent to change the Class file version from Java 16
to Java 8.  

Hotspot uses the classfile version to determine which attributes to
parse and so it errors on the redefinition as it doesn't see the 
`Nest*` attributes for older classfile versions:
```
java.lang.UnsupportedOperationException: class redefinition failed: attempted to change the class NestHost, NestMembers, Record, or PermittedSubclasses attribute
transform: java/lang/StackTraceElement$HashedModules
```

OpenJ9 doesn't use the classfile version to determine which attributes
to parse - viewing it as something to be parsed either way - and thus
doesn't see a change to the `Nest*` attributes.

The test passes on OpenJ9 and throws an exception on Hotspot.

Both behaviours are acceptable given the spec today.
