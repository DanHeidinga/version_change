To easily run these tests, run one of the following commands from the `test/8` directory to launch 
a container to test the example:

OpenJ9
```
docker run --rm -it -v `pwd`:/test adoptopenjdk/openjdk8-openj9 /bin/bash
```

Hotspot
```
docker run --rm -it -v `pwd`:/test adoptopenjdk/openjdk8 /bin/bash
```

In the container, run the following commands:
```
cd /test
./build8.sh
./run8.sh
```

The code uses an agent to change the Class file version from Java 8
to Java 7.  This works as all the supporting classfile structures
are available in both releases.

There is a commented out line to change the version to Java 6 and
doing so fails with a ClassFormatError on both Hotspot and OpenJ9.
