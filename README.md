# LOGR - log analyzer utility
A small command line utility to search and highlight a set of keywords in a text file 

## Build jar
```shell script
./gradlew clean build
```
## Install
```shell script
cp ./build/libs/logr.jar ~/bin
cp ./logr ~/bin
```

## Usage examples
File *test.txt* content
```shell script
cat <<EOT > "test.txt"
one two one three two one one two
EOT

```
File *test1.txt* content
```shell script
cat <<EOT > "test1.txt"
one three two
one two one three two
two three
one three one two
one three
EOT
```
Show all *test1.txt* lines contains *one*
```shell script
logr "/home/blu/temp/1/test1.txt" one
```
![Screenshots](https://user-images.githubusercontent.com/11032280/107631762-39642980-6c6e-11eb-87de-96a45b33b8bc.png?raw=true)

Show all *test1.txt* lines contains *one* and *two*
```shell script
logr "/home/blu/temp/1/test1.txt" one two
```
![Screenshots](https://user-images.githubusercontent.com/11032280/107631767-3bc68380-6c6e-11eb-9862-a50efcb1ae7a.png?raw=true)

Show all *test1.txt* lines contains *one two*
```shell script
logr "/home/blu/temp/1/test1.txt" "one two"
```
![Screenshots](https://user-images.githubusercontent.com/11032280/107631773-3e28dd80-6c6e-11eb-9403-d2726ee5dfdb.png?raw=true)

Show all *test1.txt* lines contains *one* but does not contain *two*
```shell script
logr "/home/blu/temp/1/test1.txt" one \!two
```
![Screenshots](https://user-images.githubusercontent.com/11032280/107631781-408b3780-6c6e-11eb-980b-3143d2b760d6.png?raw=true)

Show all *test.txt* and *test1.txt* lines contains *one*
```shell script
logr "/home/blu/temp/1/*.txt" one
```
![Screenshots](https://user-images.githubusercontent.com/11032280/107631794-42ed9180-6c6e-11eb-9ea6-5400f24f0ca9.png?raw=true)
