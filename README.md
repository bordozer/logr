# Show me

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
```text
one two one three two one one two
```
File *test1.txt* content
```text
one three two
one two one three two
two three
one three one two
one three
```
Show all *test1.txt* lines contains *one*
```shell script
showme.sh "/home/blu/temp/1/test1.txt" one
```

Show all *test1.txt* lines contains *one* and *two*
```shell script
showme.sh "/home/blu/temp/1/test1.txt" one two
```

Show all *test1.txt* lines contains *one two*
```shell script
showme.sh "/home/blu/temp/1/test1.txt" "one two"
```

Show all *test1.txt* lines contains *one* but does not contain *two*
```shell script
showme.sh "/home/blu/temp/1/test1.txt" one \!two
```

Show all *test.txt* and *test1.txt* lines contains *one*
```shell script
showme.sh "/home/blu/temp/1/*.txt" one
```
