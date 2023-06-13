#!/bin/sh

CLI="../dfc/ba-dua-cli-0.6.0-all.jar"
SER="./coverage.ser"
CLASSES="./target/classes"
XML="./target/badua.xml"

java -jar ${BADUACLI} report -input ${BADUASER} -classes ${CLASSES} -show-classes -show-methods -xml ${BADUAXML}
