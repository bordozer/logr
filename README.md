# Show me
A small command line utility to search and highlight a set of keywords in a text file 

## Build jar
```shell script
./gradlew clean build
```
## Install
```shell script
cp ./showme.sh ~/bin
cp ./build/libs/showme.jar ~/bin
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
showme.sh "/home/blu/temp/1/test1.txt" one
```
![Screenshots](https://user-images.githubusercontent.com/11032280/103278223-ab8cf180-49d3-11eb-9227-1db0e0f0bb08.png?raw=true)

Show all *test1.txt* lines contains *one* and *two*
```shell script
showme.sh "/home/blu/temp/1/test1.txt" one two
```
![Screenshots](https://user-images.githubusercontent.com/11032280/103278250-bc3d6780-49d3-11eb-8a82-c57e77c4c3a0.png?raw=true)

Show all *test1.txt* lines contains *one two*
```shell script
showme.sh "/home/blu/temp/1/test1.txt" "one two"
```
![Screenshots](https://user-images.githubusercontent.com/11032280/103278288-cd867400-49d3-11eb-8592-e8ca2608500e.png?raw=true)

Show all *test1.txt* lines contains *one* but does not contain *two*
```shell script
showme.sh "/home/blu/temp/1/test1.txt" one \!two
```
![Screenshots](https://user-images.githubusercontent.com/11032280/103278308-dc6d2680-49d3-11eb-9220-d76945240f59.png?raw=true)

Show all *test.txt* and *test1.txt* lines contains *one*
```shell script
showme.sh "/home/blu/temp/1/*.txt" one
```
![Screenshots](https://user-images.githubusercontent.com/11032280/103278332-e98a1580-49d3-11eb-8956-f0844ff84e2d.png?raw=true)
