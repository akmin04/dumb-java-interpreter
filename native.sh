#!/bin/sh

set -e

./gradlew :cli:shadowJar
/Library/Java/JavaVirtualMachines/graalvm-ce-java11-21.1.0.jdk/Contents/Home/bin/native-image -cp picocli-4.6.1.jar -jar cli/build/libs/cli-0.1.1-all.jar dji
rm dji.build_artifacts.txt