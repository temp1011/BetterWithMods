#!/usr/bin/env bash
#declare -a array=(white orange magenta light_blue yellow lime pink gray silver cyan purple blue brown green red black)

declare -a array=(oak spruce birch jungle acacia dark_oak)

template=`cat metal_chime.json`

for item in "${array[@]}"
do
    echo "${template/plank/$item}" >> "bamboo_chime_$item.json"
done
