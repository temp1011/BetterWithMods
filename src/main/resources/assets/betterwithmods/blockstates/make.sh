#!/usr/bin/env bash
declare -a array=(white orange magenta light_blue yellow lime pink gray silver cyan purple blue brown green red black)


template=`cat vase_.json`

for item in "${array[@]}"; do
    echo "tile.bwm:vase_$item.name"
#    echo "${template/color/$item}" >> "vase_$item.json"
done
