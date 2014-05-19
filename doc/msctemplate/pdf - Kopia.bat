@echo off
rem skrypt do generacji doumentu PDF
rem wygenerowanie pierwszego pliku aux
pdflatex -file-line-error-style -output-directory=tmp -aux-directory=tmp -include-directory=tex tex/main.tex

bibtex -min-crossrefs -1 tmp/main

move tmp\main.pdf Guz_Cezary.pdf
