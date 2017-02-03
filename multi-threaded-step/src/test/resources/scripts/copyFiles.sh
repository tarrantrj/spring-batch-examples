#!/bin/bash

SAMPLE_FILE=/opt/multi-threaded-step/samples/bike.fit
INPUT_DIR=/opt/multi-threaded-step/input/

COUNTER=0

for ((COUNTER=1; COUNTER <= 125; COUNTER++ )) ; do
   echo "cp $SAMPLE_FILE ${INPUT_DIR}bike.${COUNTER}.fit"
   cp $SAMPLE_FILE ${INPUT_DIR}bike.${COUNTER}.fit
done
