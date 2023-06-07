#!/bin/sh

BADUACLI="/Users/giuliamenichini/Documents/bookkeeper-1/dfc/ba-dua-cli-0.2.0-all.jar"
BADUASER="/Users/giuliamenichini/apache_openjpa/openjpa-jdbc/target/badua.ser"
CLASSES="/Users/giuliamenichini/apache_openjpa/openjpa-jdbc/target/classes"
BADUAXML="/Users/giuliamenichini/apache_openjpa/openjpa-jdbc/target/badua.xml"

java -jar ${BADUACLI} report    \
        -input ${BADUASER}      \
        -classes ${CLASSES}     \
        -show-classes           \
        -show-methods           \
        -xml ${BADUAXML}
