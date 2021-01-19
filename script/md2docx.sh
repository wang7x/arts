#!/bin/bash
set -eu
set -o pipefail

# get script dir
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
outputdir=$DIR/../output/$(date +%Y%m%d)
mkdir -p $outputdir

i=1;
for file in "$@"
do
    echo "converting file - $i: $file";
    fullname=$(basename -- "$file")
    filename="${fullname%.*}"

    pandoc -f gfm+east_asian_line_breaks -t docx $file -o $outputdir/$filename.docx

    i=$((i + 1));
done
